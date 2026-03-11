package top.timeblog.limitedGrace.listener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import top.timeblog.limitedGrace.manager.DeathManager;

public class PlayerDeathListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        Player player = event.getEntity();
        // 调用 Manager
        DeathManager.handleDeath(player, event);
    }

}