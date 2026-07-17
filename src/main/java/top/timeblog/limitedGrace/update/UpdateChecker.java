package top.timeblog.limitedGrace.update;

import top.timeblog.limitedGrace.LimitedGrace;

import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class UpdateChecker {
    private static final URI LATEST_RELEASE_URI = URI.create(
            "https://api.github.com/repos/User-Time/LimitedGrace/releases/latest"
    );
    private static final Duration CONNECT_TIMEOUT = Duration.ofSeconds(5);
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(10);
    private static final Pattern VERSION_PATTERN = Pattern.compile("^[vV]?(\\d+(?:\\.\\d+)*)$");

    private final LimitedGrace plugin;
    private final HttpClient httpClient;

    public UpdateChecker(LimitedGrace plugin) {
        this.plugin = plugin;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(CONNECT_TIMEOUT)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
    }

    public void checkForUpdates() {
        String currentVersion = plugin.getPluginMeta().getVersion();
        HttpRequest request = HttpRequest.newBuilder(LATEST_RELEASE_URI)
                .timeout(REQUEST_TIMEOUT)
                .header("Accept", "application/vnd.github+json")
                .header("User-Agent", "LimitedGrace/" + currentVersion)
                .GET()
                .build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8))
                .thenAccept(response -> handleResponse(currentVersion, response))
                .exceptionally(error -> {
                    logFailure(error);
                    return null;
                });
    }

    private void handleResponse(String currentVersion, HttpResponse<String> response) {
        if (response.statusCode() != 200) {
            plugin.getComponentLogger().warn(
                    "Update check failed: GitHub API returned HTTP " + response.statusCode()
            );
            return;
        }

        String latestVersion = extractJsonString(response.body(), "tag_name");
        String releaseUrl = extractJsonString(response.body(), "html_url");
        if (latestVersion == null || releaseUrl == null) {
            plugin.getComponentLogger().warn("Update check failed: invalid GitHub API response");
            return;
        }

        NumericVersion current = NumericVersion.parse(currentVersion);
        NumericVersion latest = NumericVersion.parse(latestVersion);
        if (current == null || latest == null) {
            plugin.getComponentLogger().warn("Update check failed: unsupported version format");
            return;
        }

        if (latest.compareTo(current) > 0) {
            plugin.getComponentLogger().warn("A new LimitedGrace version is available!");
            plugin.getComponentLogger().warn("Current version: " + currentVersion);
            plugin.getComponentLogger().warn("Latest version: " + latestVersion);
            plugin.getComponentLogger().warn("Download: " + releaseUrl);
        } else {
            plugin.getComponentLogger().info("LimitedGrace is up to date.");
        }
    }

    private void logFailure(Throwable error) {
        Throwable cause = error instanceof CompletionException && error.getCause() != null
                ? error.getCause()
                : error;
        String detail = cause.getMessage();
        String message = "Update check failed: " + cause.getClass().getSimpleName();
        if (detail != null && !detail.isBlank()) {
            message += " - " + detail;
        }
        plugin.getComponentLogger().warn(message);
    }

    private static String extractJsonString(String json, String field) {
        Pattern fieldPattern = Pattern.compile(
                "\\\"" + Pattern.quote(field) + "\\\"\\s*:\\s*\\\"((?:\\\\.|[^\\\"\\\\])*)\\\""
        );
        Matcher matcher = fieldPattern.matcher(json);
        return matcher.find() ? unescapeJsonString(matcher.group(1)) : null;
    }

    private static String unescapeJsonString(String value) {
        StringBuilder result = new StringBuilder(value.length());
        for (int index = 0; index < value.length(); index++) {
            char current = value.charAt(index);
            if (current != '\\') {
                result.append(current);
                continue;
            }

            if (++index >= value.length()) {
                return value;
            }
            char escaped = value.charAt(index);
            switch (escaped) {
                case '"', '\\', '/' -> result.append(escaped);
                case 'b' -> result.append('\b');
                case 'f' -> result.append('\f');
                case 'n' -> result.append('\n');
                case 'r' -> result.append('\r');
                case 't' -> result.append('\t');
                case 'u' -> {
                    if (index + 4 >= value.length()) {
                        return value;
                    }
                    try {
                        result.append((char) Integer.parseInt(value.substring(index + 1, index + 5), 16));
                        index += 4;
                    } catch (NumberFormatException exception) {
                        return value;
                    }
                }
                default -> result.append(escaped);
            }
        }
        return result.toString();
    }

    private record NumericVersion(List<BigInteger> parts) implements Comparable<NumericVersion> {
        private static NumericVersion parse(String version) {
            Matcher matcher = VERSION_PATTERN.matcher(version.trim());
            if (!matcher.matches()) {
                return null;
            }

            String[] rawParts = matcher.group(1).split("\\.");
            List<BigInteger> parts = new ArrayList<>(rawParts.length);
            for (String rawPart : rawParts) {
                parts.add(new BigInteger(rawPart));
            }
            return new NumericVersion(List.copyOf(parts));
        }

        @Override
        public int compareTo(NumericVersion other) {
            int length = Math.max(parts.size(), other.parts.size());
            for (int index = 0; index < length; index++) {
                BigInteger currentPart = index < parts.size() ? parts.get(index) : BigInteger.ZERO;
                BigInteger otherPart = index < other.parts.size() ? other.parts.get(index) : BigInteger.ZERO;
                int comparison = currentPart.compareTo(otherPart);
                if (comparison != 0) {
                    return comparison;
                }
            }
            return 0;
        }
    }
}
