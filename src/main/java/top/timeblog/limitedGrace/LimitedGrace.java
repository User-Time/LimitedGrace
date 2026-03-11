package top.timeblog.limitedGrace;

import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.NamespacedKey;

import org.bukkit.plugin.java.JavaPlugin;
import top.timeblog.limitedGrace.command.LimitedGraceCommand;
import top.timeblog.limitedGrace.listener.PlayerDeathListener;

import java.util.List;

public final class LimitedGrace extends JavaPlugin {
    private static final Logger log = LogManager.getLogger(LimitedGrace.class);
    public static ComponentLogger Log;
    private static LimitedGrace instance;
    public static NamespacedKey DEATH_COUNT_KEY;
    public static NamespacedKey ADDED_PROTECTION_COUNT_KEY;
    public static int CONFIG_DP_COUNT;
    public static int CONFIG_AP_COUNT;
    public static List<Integer> CONFIG_PW_COUNT;
    public static String CONFIG_P_MSG;
    public static String CONFIG_PW_MSG;
    public static String CONFIG_D_MSG;
    public static String CONFIG_NP_MSG;
    public static String CONFIG_RELOAD_MSG;
    public static String CONFIG_SPD_MSG;
    public static String CONFIG_SPAP_MSG;
    public static String CONFIG_P404_MSG;
    public static String CONFIG_VE_MSG;
    public static String CONFIG_SAP_MSG;


    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        Log = this.getComponentLogger();
        loadConfigValues();
        DEATH_COUNT_KEY = new NamespacedKey(this, "death_count");
        ADDED_PROTECTION_COUNT_KEY = new NamespacedKey(this, "added_protections_count");
        getServer().getPluginManager().registerEvents(
                new PlayerDeathListener(),
                this
        );
        getCommand("limitedGrace").setExecutor(new LimitedGraceCommand());
        Log.info("Enabled!");
    }
    public void loadConfigValues() {
        try {
            log.debug("Config Loading!");
            CONFIG_DP_COUNT = getConfig().getInt("death-protections-number",10);
            CONFIG_AP_COUNT = getConfig().getInt("default-added-protections-number",0);
            CONFIG_PW_COUNT = getConfig().getIntegerList("protect-warn");
            CONFIG_P_MSG = getConfig().getString("protect-message", "§a玩家 §f{0} §a具有 §e{1}次 §a死亡保护§f,\n§a其中包含 §e{2}次 §a新人保护§f,§a以及 §e{3}次 §a的额外死亡保护§f.");
            CONFIG_PW_MSG = getConfig().getString("protect-warn-message", "§a你只剩 §e{0}次 §a死亡保护了§f, \n§a其中新人保护仅剩 §e{1}次§f, §a额外死亡保护仅剩 §e{2}次§f.");
            CONFIG_D_MSG = getConfig().getString("death-message", "§a玩家 §f{0} §a已死亡：§e{1}次");
            CONFIG_NP_MSG = getConfig().getString("not-permission-message", "§c你没有使用该命令的权限！");
            CONFIG_RELOAD_MSG = getConfig().getString("reload-message", "§a配置已重新加载");
            CONFIG_SPD_MSG = getConfig().getString("set-player-death-message", "§a已将玩家§f %s §a的死亡次数修改为:§e %d");
            CONFIG_SPAP_MSG = getConfig().getString("set-player-added-permission-message", "§a已将玩家§f %s §a的额外死亡保护次数修改为:§e %d次");
            CONFIG_P404_MSG = getConfig().getString("player-404-message", "§c玩家不存在或不在线!");
            CONFIG_VE_MSG = getConfig().getString("value-err-message", "§c数值不合法");
            CONFIG_SAP_MSG = getConfig().getString("set-added-protect-message", "§a已将玩家 §f{0} §a的额外死亡保护设为：§e{1}次");

        }catch (Exception e){
            log.warn("Config File Load Error!");
            this.saveResource("config.yml", true);
            log.info("Config file replace success !");
        }
    }
    public static LimitedGrace getInstance() {
        return instance;
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Log.info("Bye");
    }
}