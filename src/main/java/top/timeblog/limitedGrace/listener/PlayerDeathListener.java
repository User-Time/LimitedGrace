package top.timeblog.limitedGrace.listener;
import org.bukkit.GameRule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import top.timeblog.limitedGrace.LimitedGrace;
import top.timeblog.limitedGrace.manager.DeathManager;
import top.timeblog.limitedGrace.manager.ConfigManager;

import static top.timeblog.limitedGrace.manager.DeathManager.getAllSwitch;
import static top.timeblog.limitedGrace.manager.DeathManager.getSwitch;


public class PlayerDeathListener implements Listener {
    private static ConfigManager config() {
        return LimitedGrace.getInstance().getConfigManager();
    }
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        if (Boolean.TRUE.equals(event.getEntity().getWorld().getGameRuleValue(GameRule.KEEP_INVENTORY)) && !config().keepInventoryConsumesCharge()) return;
        if (!getAllSwitch()) return;
        Player player = event.getEntity();
        if (!getSwitch(player)) return;
        // 调用 Manager
        DeathManager.handleDeath(player, event);
    }

}