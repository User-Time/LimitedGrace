package top.timeblog.limitedGrace.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import top.timeblog.limitedGrace.LimitedGrace;

import java.text.MessageFormat;

public class DeathManager {
    private static ConfigManager config() {
        return LimitedGrace.getInstance().getConfigManager();
    }

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
    ///    掉落保护开关
    /// ================

    public static Boolean getSwitch(Player player){
        PersistentDataContainer thePlayer = player.getPersistentDataContainer();
        return thePlayer.getOrDefault(LimitedGrace.DEATH_BOOL_KEY, PersistentDataType.BOOLEAN, true);
    }
    public static void setSwitch(Player player, boolean value){
        PersistentDataContainer thePlayer = player.getPersistentDataContainer();
        thePlayer.set(LimitedGrace.DEATH_BOOL_KEY, PersistentDataType.BOOLEAN, value);
    }
    public static boolean getAllSwitch(){
        return config().isEnabled();
    }
    public static void  setAllSwitch(boolean value){
        config().setEnabled(value);
    }

    /// ================
    ///     死亡次数
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
    ///      额外保护方法
    /// ======================
    // 查询 次数
    public static int getAddedProtections(Player player) {
        PersistentDataContainer thePlayer = player.getPersistentDataContainer();
        return thePlayer.getOrDefault(
                LimitedGrace.ADDED_PROTECTION_COUNT_KEY,
                PersistentDataType.INTEGER,
                config().getDefaultAddedProtectionCount()
        );
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
        return config().getMessage("set-player-added-permission-message").formatted(player.getName(), oldValue+number);
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
        if (player.hasPermission("limitedgrace.unlimited")) {
            DeathItemNotLose(event);
            return;
        }

        addDeath(player,1);
        int deathsCount = getDeaths(player);
        int left = Math.max(0, config().getProtectionCount() - deathsCount);

        if (deathsCount <= config().getProtectionCount()) {
            DeathItemNotLose(event);
            // 保护次数警告
            if (config().getProtectionWarnCounts().contains(left + getAddedProtections(player))){
                player.sendMessage(MessageFormat.format(config().getMessage("protect-warn-message"), left+getAddedProtections(player), left, getAddedProtections(player)));
            }
        }else if (getAddedProtections(player) > 0) {
            // 根据 额外保护次数
            minusAddedProtectionsNumber(player, 1);
            DeathItemNotLose(event);
            // 保护次数警告
            if (config().getProtectionWarnCounts().contains(left + getAddedProtections(player))){
                player.sendMessage(MessageFormat.format(config().getMessage("protect-warn-message"), left+getAddedProtections(player), left, getAddedProtections(player)));
            }
        }
    }

    /// ==============================
    ///   以下方法提供给 Command 使用
    /// ==============================

    public static String setDeathCounts(String name, Integer count) {
        if (count < 0) {
            return config().getMessage("value-err-message");
        }
        Player player = Bukkit.getPlayer(name);
        if (player == null) {
            return config().getMessage("player-404-message");
        } else if (count == DeathManager.getDeaths(player)) {
            // 没区别
            return config().getMessage("value-unchanged-message");
        }
        boolean status = DeathManager.setDeath(player, count);
        if (status) {
            return config().getMessage("set-player-death-message").formatted(name, count);
        }
        String errorMessage = config().getMessage("death-update-error-message");
        LimitedGrace.getInstance().getComponentLogger().warn(errorMessage);
        return errorMessage;

    }
    public static String setAddedProtectionsCounts(String name, Integer count) {
        if (count < 0) {
            return config().getMessage("value-err-message");
        }
        // 获取玩家对象
        Player player = Bukkit.getPlayer(name);
        if (player == null) {
            // 找不到对象
            return config().getMessage("player-404-message");
        } else if (count == DeathManager.getAddedProtections(player)) {
            // 没区别
            return config().getMessage("value-unchanged-message");
        }
        boolean status = DeathManager.setAddedProtectionsNumber(player, count);
        if (status) {
            return config().getMessage("set-player-added-permission-message").formatted(name, count);
        }
        String errorMessage = config().getMessage("protection-update-error-message");
        LimitedGrace.getInstance().getComponentLogger().warn(errorMessage);
        return errorMessage;
    }
}
