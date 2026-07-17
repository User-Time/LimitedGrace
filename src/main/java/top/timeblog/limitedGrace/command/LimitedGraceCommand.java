package top.timeblog.limitedGrace.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;
import top.timeblog.limitedGrace.LimitedGrace;
import top.timeblog.limitedGrace.manager.ConfigManager;
import top.timeblog.limitedGrace.manager.DeathManager;

import java.text.MessageFormat;
import java.util.*;


import static org.bukkit.Bukkit.getPlayer;
import static top.timeblog.limitedGrace.manager.DeathManager.*;

public class LimitedGraceCommand implements CommandExecutor, TabCompleter {
    private final ConfigManager config = LimitedGrace.getInstance().getConfigManager();

    private String getDeathMessage(String name, Boolean protect) {
        Player player = getPlayer(name);
        if (player == null) {
            return config.getMessage("player-404-message");
        }
        int deathCounts = DeathManager.getDeaths(player);
        int AddedProtectionsNumber =  DeathManager.getAddedProtections(player);

        // 返回 保护次数
        if (protect){
            int NewManProtections = Math.max(0, config.getProtectionCount() - deathCounts);
            return MessageFormat.format(
                    config.getMessage("protect-message"),
                    name,AddedProtectionsNumber+NewManProtections, NewManProtections, AddedProtectionsNumber
            );
        }

        // 返回死亡次数
        return MessageFormat.format(
                config.getMessage("death-message"), name,
                Integer.toString(deathCounts)
        );

    }



    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, String label, String[] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            config.getMessageList("help-message").forEach(sender::sendMessage);
            return true;
        }
        else if (args.length > 0 ) {
            if (args[0].equalsIgnoreCase("reload")){

                if (!sender.hasPermission("limitedgrace.reload")){
                    sender.sendMessage(config.getMessage("not-permission-message"));
                    return true;
                }
                config.reload();
                sender.sendMessage(config.getMessage("reload-message"));
            }

            else if (args[0].equalsIgnoreCase("getDeaths")) {
                if (!sender.hasPermission("limitedgrace.get")){
                    sender.sendMessage(config.getMessage("not-permission-message"));
                    return true;
                }

                if (args.length == 1){
                    if (!(sender instanceof Player)) {
                        return true;
                    }
                    sender.sendMessage(getDeathMessage(sender.getName(),false));
                }else if (args.length == 2) {
                    if (!sender.hasPermission("limitedgrace.get.it")){
                        sender.sendMessage(config.getMessage("not-permission-message"));
                        return true;
                    }
                    sender.sendMessage(getDeathMessage(args[1], false));
                }
            }
            else if (args[0].equalsIgnoreCase("get")) {
                if (!sender.hasPermission("limitedgrace.get")){
                    sender.sendMessage(config.getMessage("not-permission-message"));
                    return true;
                }
                if (args.length == 1) {
                    if (!(sender instanceof Player)) {
                        return true;
                    }
                    sender.sendMessage(getDeathMessage(sender.getName(), true));
                } else if (args.length == 2) {
                    if (!sender.hasPermission("limitedgrace.get.it")){
                        sender.sendMessage(config.getMessage("not-permission-message"));
                        return true;
                    }
                    sender.sendMessage(getDeathMessage(args[1], true));
                }
            }
            else if (args[0].equalsIgnoreCase("setDeath")) {
                if (!sender.hasPermission("limitedgrace.setdeath")){
                    sender.sendMessage(config.getMessage("not-permission-message"));
                    return true;
                }
                if (args.length == 2) {
                    int count;
                    try {
                        count = Integer.parseInt(args[1]);
                    }catch (Exception e){
                        sender.sendMessage(MessageFormat.format(config.getMessage("integer-error-message"), args[1]));
                        return true;
                    }
                    sender.sendMessage(setDeathCounts(sender.getName(), count));
                } else if (args.length == 3) {
                    int count;
                    try {
                        count = Integer.parseInt(args[2]);
                    }catch (Exception e){
                        sender.sendMessage(MessageFormat.format(config.getMessage("integer-error-message"), args[2]));
                        return true;
                    }
                    sender.sendMessage(setDeathCounts(args[1], count));
                }
            }
            else if (args[0].equalsIgnoreCase("add")) {
                if (!sender.hasPermission("limitedgrace.add")){
                    sender.sendMessage(config.getMessage("not-permission-message"));
                    return true;
                }
                if (args.length == 2) {
                    // @s
                    int count;
                    try {
                        count = Integer.parseInt(args[1]);
                    }catch (Exception e){
                        sender.sendMessage(MessageFormat.format(config.getMessage("integer-error-message"), args[1]));
                        return true;
                    }

                    sender.sendMessage(addAddedProtectionsNumber(getPlayer(sender.getName()), count));
                } else if (args.length == 3) {
                    // it
                    int count;
                    try {
                        count = Integer.parseInt(args[2]);
                    }catch (Exception e){
                        sender.sendMessage(MessageFormat.format(config.getMessage("integer-error-message"), args[2]));
                        return true;
                    }
                    sender.sendMessage(addAddedProtectionsNumber(getPlayer(args[1]), count));
                }
            }
            else if (args[0].equalsIgnoreCase("set")) {
                if (!sender.hasPermission("limitedgrace.set")){
                    sender.sendMessage(config.getMessage("not-permission-message"));
                    return true;
                }
                if (args.length == 2) {
                    // @s
                    int count;
                    try {
                        count = Integer.parseInt(args[1]);
                    }catch (Exception e){
                        sender.sendMessage(MessageFormat.format(config.getMessage("integer-error-message"), args[1]));
                        return true;
                    }
                    sender.sendMessage(setAddedProtectionsCounts(sender.getName(), count));
                } else if (args.length == 3) {
                    // it
                    int count;
                    try {
                        count = Integer.parseInt(args[2]);
                    }catch (Exception e){
                        sender.sendMessage(MessageFormat.format(config.getMessage("integer-error-message"), args[2]));
                        return true;
                    }
                    sender.sendMessage(setAddedProtectionsCounts(args[1], count));
                }

            }
            else if (args[0].equalsIgnoreCase("switch")){
                if (!sender.hasPermission("limitedgrace.switch")){
                    sender.sendMessage(config.getMessage("not-permission-message"));
                    return true;
                }
                if (args.length == 1) {
                    if (!(sender instanceof Player)) {
                        return true;
                    }
                    setSwitch((Player)sender, !getSwitch((Player)sender));
                    sender.sendMessage(MessageFormat.format(config.getMessage("switch-self-message"), getSwitch((Player) sender)));
                } else if (args.length == 2) {
                    if (!sender.hasPermission("limitedgrace.switch.it")){
                        sender.sendMessage(config.getMessage("not-permission-message"));
                        return true;
                    }
                    Player player = Bukkit.getPlayer(args[1]);
                    if (player == null) {
                        sender.sendMessage(config.getMessage("player-404-message"));
                        return true;
                    }
                    setSwitch(player, !getSwitch(player));
                    sender.sendMessage(MessageFormat.format(config.getMessage("switch-player-message"), player.getName(), getSwitch(player)));
                }
            }else if (args[0].equalsIgnoreCase("switchAll")){
                if (!sender.hasPermission("limitedgrace.switch.all")){
                    sender.sendMessage(config.getMessage("not-permission-message"));
                    return true;
                }
                if (args.length == 1) {
                    setAllSwitch(!getAllSwitch());
                    sender.sendMessage(MessageFormat.format(config.getMessage("switch-all-message"), getAllSwitch()));
                }
            }
        }
        return true;
    }

    // 命令提示
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            if (sender.hasPermission("limitedgrace.admin")) {
                return Arrays.asList("reload", "get", "getDeaths", "switch", "switchAll", "setDeath","set","add");
            }else {
                return Arrays.asList("get", "getDeaths", "switch");
            }

        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("get")
                    || args[0].equalsIgnoreCase("getDeaths")
                    || args[0].equalsIgnoreCase("setDeath")
                    || args[0].equalsIgnoreCase("set")
                    || args[0].equalsIgnoreCase("add")
                    || args[0].equalsIgnoreCase("switch")
            ) {
                List<String> players = new ArrayList<>();
                for (Player p : Bukkit.getOnlinePlayers()) {
                    players.add(p.getName());
                }
                return players;
            }
        }

        return Collections.emptyList();
    }
}
