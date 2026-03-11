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
    private static LimitedGrace instance;
    public static NamespacedKey DEATH_COUNT_KEY;
    public static int CONFIG_COUNT;
    public static List<Integer> CONFIG_PW_COUNT;
    public static String CONFIG_P_MSG;
    public static String CONFIG_PW_MSG;
    public static String CONFIG_D_MSG;
    public static String CONFIG_NP_MSG;
    public static String CONFIG_RELOAD_MSG;
    public static ComponentLogger Log;
    public static String CONFIG_SPD_MSG;
    public static String CONFIG_P404_MSG;
    public static String CONFIG_VE_MSG;


    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        Log = this.getComponentLogger();
        loadConfigValues();
        DEATH_COUNT_KEY = new NamespacedKey(this, "death_count");
        getServer().getPluginManager().registerEvents(
                new PlayerDeathListener(),
                this
        );
        getCommand("limitedGrace").setExecutor(new LimitedGraceCommand());
        Log.info("[LimitedGrace] Enabled");
    }
    public void loadConfigValues() {
        try {
            CONFIG_COUNT = getConfig().getInt("protections-number");
            CONFIG_PW_COUNT = getConfig().getIntegerList("protect-warn");
            CONFIG_P_MSG = getConfig().getString("protect-message", "§a具有 §f{0} §a次死亡保护");
            CONFIG_PW_MSG = getConfig().getString("protect-warn-message", "§a你只剩 §e{0} 次死亡保护了");
            CONFIG_D_MSG = getConfig().getString("death-message", "§c已死亡：§f{0} §a次");
            CONFIG_NP_MSG = getConfig().getString("not-permission-message", "§c你没有使用该命令的权限！");
            CONFIG_RELOAD_MSG = getConfig().getString("reload-message", "§a配置已重新加载");
            CONFIG_SPD_MSG = getConfig().getString("set-player-death-message", "§a已将玩家§e %s §a的死亡次数修改为 %d");
            CONFIG_P404_MSG = getConfig().getString("player-404-message", "§c玩家不存在或不在线");
            CONFIG_VE_MSG = getConfig().getString("value-err-message", "§c数值不合法");



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
    }
}