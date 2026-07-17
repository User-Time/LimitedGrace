package top.timeblog.limitedGrace;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;
import top.timeblog.limitedGrace.command.LimitedGraceCommand;
import top.timeblog.limitedGrace.listener.PlayerDeathListener;
import top.timeblog.limitedGrace.manager.ConfigManager;

public final class LimitedGrace extends JavaPlugin {
    private static LimitedGrace instance;
    private ConfigManager configManager;
    public static NamespacedKey DEATH_COUNT_KEY;
    public static NamespacedKey DEATH_BOOL_KEY;
    public static NamespacedKey ADDED_PROTECTION_COUNT_KEY;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        configManager = new ConfigManager(this);
        DEATH_COUNT_KEY = new NamespacedKey(this, "death_count");
        DEATH_BOOL_KEY = new NamespacedKey(this, "lg_bool");
        ADDED_PROTECTION_COUNT_KEY = new NamespacedKey(this, "added_protections_count");
        getServer().getPluginManager().registerEvents(
                new PlayerDeathListener(),
                this
        );
        getCommand("limitedGrace").setExecutor(new LimitedGraceCommand());
        getComponentLogger().info("Enabled!");
    }

    public static LimitedGrace getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getComponentLogger().info("Bye");
    }
}
