package top.timeblog.limitedGrace.listener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import top.timeblog.limitedGrace.manager.DeathManager;

import static top.timeblog.limitedGrace.manager.DeathManager.getAllSwitch;
import static top.timeblog.limitedGrace.manager.DeathManager.getSwitch;

public class PlayerDeathListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        if (!getAllSwitch()) return;
        Player player = event.getEntity();
        if (!getSwitch(player)) return;
        // 调用 Manager
        DeathManager.handleDeath(player, event);
    }

}