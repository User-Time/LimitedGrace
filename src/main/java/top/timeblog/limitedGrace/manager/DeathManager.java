package top.timeblog.limitedGrace.manager;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import top.timeblog.limitedGrace.LimitedGrace;

import java.text.MessageFormat;

import static top.timeblog.limitedGrace.LimitedGrace.*;

public class DeathManager {

    // 查询死亡次数
    public static int getDeaths(Player player) {
        PersistentDataContainer thePlayer = player.getPersistentDataContainer();
        return thePlayer.getOrDefault(LimitedGrace.DEATH_COUNT_KEY, PersistentDataType.INTEGER, 0);
    }
    // 增加死亡次数
    public static boolean setDeath(Player player, int number) {
        PersistentDataContainer thePlayer = player.getPersistentDataContainer();
        thePlayer.set(LimitedGrace.DEATH_COUNT_KEY, PersistentDataType.INTEGER, number);
        return true;
    }
    // 增加死亡次数
    public static void addDeath(Player player, int number) {
        PersistentDataContainer thePlayer = player.getPersistentDataContainer();
        int oldValue = getDeaths(player);
        thePlayer.set(LimitedGrace.DEATH_COUNT_KEY, PersistentDataType.INTEGER, oldValue+number);
    }
    // 死亡后触发
    public static void handleDeath(Player player, PlayerDeathEvent event) {
        int deathsCount = getDeaths(player);
        if (deathsCount < CONFIG_COUNT) {
            event.setKeepInventory(true);
            event.setKeepLevel(true);
            event.getDrops().clear();
            event.setDroppedExp(0);
            int left = CONFIG_COUNT - deathsCount - 1;
            if (CONFIG_PW_COUNT.contains(left)){
                player.sendMessage(MessageFormat.format(CONFIG_PW_MSG, left));
            }
        }
        addDeath(player,1);
    }
}