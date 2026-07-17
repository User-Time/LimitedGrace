package top.timeblog.limitedGrace.manager;

import org.bukkit.configuration.file.FileConfiguration;
import top.timeblog.limitedGrace.LimitedGrace;

import java.util.List;

public final class ConfigManager {
    private final LimitedGrace plugin;
    private FileConfiguration config;

    public ConfigManager(LimitedGrace plugin) {
        this.plugin = plugin;
        updateConfigReference();
    }

    public void reload() {
        plugin.reloadConfig();
        updateConfigReference();
    }

    private void updateConfigReference() {
        config = plugin.getConfig();
    }

    public boolean isEnabled() {
        return config.getBoolean("enabled", true);
    }

    public void setEnabled(boolean enabled) {
        config.set("enabled", enabled);
        plugin.saveConfig();
    }

    public int getProtectionCount() {
        return config.getInt("death-protections-number", 10);
    }

    public int getDefaultAddedProtectionCount() {
        return config.getInt("default-added-protections-number", 0);
    }

    public List<Integer> getProtectionWarnCounts() {
        return config.getIntegerList("protect-warn");
    }

    public String getMessage(String path) {
        return config.getString(path, "");
    }

    public List<String> getMessageList(String path) {
        return config.getStringList(path);
    }
}
