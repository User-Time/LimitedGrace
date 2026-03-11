package top.timeblog.limitedGrace.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import top.timeblog.limitedGrace.LimitedGrace;

import java.text.MessageFormat;

import static top.timeblog.limitedGrace.LimitedGrace.*;

public class DeathManager {
    /// ==============
    ///    共用函数
    /// ==============
    // 死亡不掉落 函数
    private static void DeathItemNotLose(PlayerDeathEvent event){
        event.setKeepInventory(true);
        event.setKeepLevel(true);
        event.getDrops().clear();
        event.setDroppedExp(0);
    }
    /// ================
    ///   死亡次数 相关
    /// ================
    // 设置
    public static boolean setDeath(Player player, int number) {
        PersistentDataContainer thePlayer = player.getPersistentDataContainer();
        thePlayer.set(LimitedGrace.DEATH_COUNT_KEY, PersistentDataType.INTEGER, number);
        return true;
    }

    // 查询
    public static int getDeaths(Player player) {
        PersistentDataContainer thePlayer = player.getPersistentDataContainer();
        return thePlayer.getOrDefault(LimitedGrace.DEATH_COUNT_KEY, PersistentDataType.INTEGER, 0);
    }

    // 增加
    public static void addDeath(Player player, int number) {
        int oldValue = getDeaths(player);
        setDeath(player, oldValue + number);
    }

    /// ======================
    ///       额外保护方法
    /// ======================
    // 查询 次数
    public static int getAddedProtections(Player player) {
        PersistentDataContainer thePlayer = player.getPersistentDataContainer();
        return thePlayer.getOrDefault(LimitedGrace.ADDED_PROTECTION_COUNT_KEY, PersistentDataType.INTEGER, CONFIG_AP_COUNT);
    }

    // 设置 次数
    public static boolean setAddedProtectionsNumber(Player player, int number) {
        PersistentDataContainer thePlayer = player.getPersistentDataContainer();
        thePlayer.set(LimitedGrace.ADDED_PROTECTION_COUNT_KEY, PersistentDataType.INTEGER, number);
        return true;
    }

    // 增加 次数
    public static String addAddedProtectionsNumber(Player player, int number) {
        int oldValue = getAddedProtections(player);
        setAddedProtectionsNumber(player, oldValue + number);
        return CONFIG_SPAP_MSG.formatted(player.getName(), oldValue+number);
    }

    // 减少 次数
    public static void minusAddedProtectionsNumber(Player player, int number) {
        int oldValue = getAddedProtections(player);
        setAddedProtectionsNumber(player, oldValue - number);
    }
    /// ==================
    /// 触发 死亡 的事件方法
    /// ==================
    public static void handleDeath(Player player, PlayerDeathEvent event) {
        addDeath(player,1);
        int deathsCount = getDeaths(player);
        int left = Math.max(0, CONFIG_DP_COUNT - deathsCount);

        if (deathsCount <= CONFIG_DP_COUNT) {
            DeathItemNotLose(event);
            // 保护次数警告
            if (CONFIG_PW_COUNT.contains(left + getAddedProtections(player))){
                player.sendMessage(MessageFormat.format(CONFIG_PW_MSG, left+getAddedProtections(player), left, getAddedProtections(player)));
            }
        }else if (getAddedProtections(player) > 0) {
            // 根据 额外保护次数
            minusAddedProtectionsNumber(player, 1);
            DeathItemNotLose(event);
            // 保护次数警告
            if (CONFIG_PW_COUNT.contains(left + getAddedProtections(player))){
                player.sendMessage(MessageFormat.format(CONFIG_PW_MSG, left+getAddedProtections(player), left, getAddedProtections(player)));
            }
        }


    }

    /// ==============================
    ///   以下方法提供给 Command 使用
    /// ==============================

    public static String setDeathCounts(String name, Integer count) {
        if (count < 0) {
            return CONFIG_VE_MSG;
        }
        Player player = Bukkit.getPlayer(name);
        if (player == null) {
            return CONFIG_P404_MSG;
        } else if (count == DeathManager.getDeaths(player)) {
            // 没区别
            return "§c数值无变化";
        }
        boolean status = DeathManager.setDeath(player, count);
        if (status) {
            return CONFIG_SPD_MSG.formatted(name, count);
        }
        Log.warn("§c player Death data update error！");
        return "§c player Death data update error！";

    }
    public static String setAddedProtectionsCounts(String name, Integer count) {
        if (count < 0) {
            return CONFIG_VE_MSG;
        }
        // 获取玩家对象
        Player player = Bukkit.getPlayer(name);
        if (player == null) {
            // 找不到对象
            return CONFIG_P404_MSG;
        } else if (count == DeathManager.getAddedProtections(player)) {
            // 没区别
            return "§c数值无变化";
        }
        boolean status = DeathManager.setAddedProtectionsNumber(player, count);
        if (status) {
            return CONFIG_SPAP_MSG.formatted(name, count);
        }
        Log.warn("§c player AddedProtections data update error！");
        return "§c player AddedProtections data update error！";
    }
}