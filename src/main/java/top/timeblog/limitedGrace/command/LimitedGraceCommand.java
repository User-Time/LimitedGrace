package top.timeblog.limitedGrace.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;
import top.timeblog.limitedGrace.LimitedGrace;
import top.timeblog.limitedGrace.manager.DeathManager;

import java.text.MessageFormat;
import java.util.*;


import static top.timeblog.limitedGrace.LimitedGrace.*;
import static top.timeblog.limitedGrace.manager.DeathManager.*;

public class LimitedGraceCommand implements CommandExecutor, TabCompleter {

    private String getDeathMessage(String name, Boolean protect) {
        Player player = Bukkit.getPlayer(name);
        if (player == null) {
            return CONFIG_P404_MSG;
        }
        int deathCounts = DeathManager.getDeaths(player);
        int AddedProtectionsNumber =  DeathManager.getAddedProtections(player);

        // 返回 保护次数
        if (protect){
            int NewManProtections = Math.max(0, CONFIG_DP_COUNT - deathCounts);
            return MessageFormat.format(
                    CONFIG_P_MSG,
                    name,AddedProtectionsNumber+NewManProtections, NewManProtections, AddedProtectionsNumber
            );
        }

        // 返回死亡次数
        return MessageFormat.format(
                CONFIG_D_MSG,name,
                Integer.toString(deathCounts)
        );

    }



    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, String label, String[] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            sender.sendMessage("§6§lLimitedGrace 帮助");
            sender.sendMessage("§e/lg help            §7- 查看插件帮助");
            sender.sendMessage("§e/lg get [玩家]       §7- 查看 自己/其他玩家 的剩余保护次数");
            sender.sendMessage("§e/lg getDeaths [玩家] §7- 查看 自己/其他玩家 死亡次数");
            sender.sendMessage("§e/lg set [玩家] <次数> §7- 修改 自己/其他玩家 死亡次数");
            sender.sendMessage("§e/lg add [玩家] <次数> §7- 添加 自己/其他玩家 的额外死亡保护");
            sender.sendMessage("§e/lg reload           §7- 重载插件配置");
            return true;
        }
        else if (args.length > 0 ) {
            if (args[0].equalsIgnoreCase("reload")){

                if (!sender.hasPermission("limitedgrace.reload")){
                    sender.sendMessage(CONFIG_NP_MSG);
                    return true;
                }
                LimitedGrace.getInstance().loadConfigValues();
                sender.sendMessage(CONFIG_RELOAD_MSG);
            }

            else if (args[0].equalsIgnoreCase("getDeaths")) {
                if (!sender.hasPermission("limitedgrace.get")){
                    sender.sendMessage(CONFIG_NP_MSG);
                    return true;
                }

                if (args.length == 1){
                    if (!(sender instanceof Player)) {
                        return true;
                    }
                    sender.sendMessage(getDeathMessage(sender.getName(),false));
                }else if (args.length == 2) {
                    if (!sender.hasPermission("limitedgrace.get.it")){
                        sender.sendMessage(CONFIG_NP_MSG);
                        return true;
                    }
                    sender.sendMessage(getDeathMessage(args[1], false));
                }
            }
            else if (args[0].equalsIgnoreCase("get")) {
                if (!sender.hasPermission("limitedgrace.get")){
                    sender.sendMessage(CONFIG_NP_MSG);
                    return true;
                }

                if (args.length == 1) {
                    if (!(sender instanceof Player)) {
                        return true;
                    }
                    sender.sendMessage(getDeathMessage(sender.getName(), true));
                } else if (args.length == 2) {
                    if (!sender.hasPermission("limitedgrace.get.it")){
                        sender.sendMessage(CONFIG_NP_MSG);
                        return true;
                    }
                    sender.sendMessage(getDeathMessage(args[1], true));
                }
            }
            else if (args[0].equalsIgnoreCase("setDeath")) {
                if (!sender.hasPermission("limitedgrace.setdeath")){
                    sender.sendMessage(CONFIG_NP_MSG);
                    return true;
                }
                if (args.length == 2) {
                    int count;
                    try {
                        count = Integer.parseInt(args[1]);
                    }catch (Exception e){
                        sender.sendMessage("%s is not a valid integer type.".formatted(args[1]));
                        return true;
                    }
                    sender.sendMessage(setDeathCounts(sender.getName(), count));
                } else if (args.length == 3) {
                    int count;
                    try {
                        count = Integer.parseInt(args[2]);
                    }catch (Exception e){
                        sender.sendMessage("%s is not a valid integer type.".formatted(args[2]));
                        return true;
                    }
                    sender.sendMessage(setDeathCounts(args[1], count));
                }
            }
            else if (args[0].equalsIgnoreCase("add")) {
                if (!sender.hasPermission("limitedgrace.add")){
                    sender.sendMessage(CONFIG_NP_MSG);
                    return true;
                }
                if (args.length == 2) {
                    // @s
                    int count;
                    try {
                        count = Integer.parseInt(args[1]);
                    }catch (Exception e){
                        sender.sendMessage("%s is not a valid integer type.".formatted(args[1]));
                        return true;
                    }

                    sender.sendMessage(addAddedProtectionsNumber(Bukkit.getPlayer(sender.getName()), count));
                } else if (args.length == 3) {
                    // it
                    int count;
                    try {
                        count = Integer.parseInt(args[2]);
                    }catch (Exception e){
                        sender.sendMessage("%s is not a valid integer type.".formatted(args[2]));
                        return true;
                    }
                    sender.sendMessage(addAddedProtectionsNumber(Bukkit.getPlayer(args[1]), count));
                }
            }
            else if (args[0].equalsIgnoreCase("set")) {
                if (!sender.hasPermission("limitedgrace.set")){
                    sender.sendMessage(CONFIG_NP_MSG);
                    return true;
                }
                if (args.length == 2) {
                    // @s
                    int count;
                    try {
                        count = Integer.parseInt(args[1]);
                    }catch (Exception e){
                        sender.sendMessage("%s is not a valid integer type.".formatted(args[1]));
                        return true;
                    }
                    sender.sendMessage(setAddedProtectionsCounts(sender.getName(), count));
                } else if (args.length == 3) {
                    // it
                    int count;
                    try {
                        count = Integer.parseInt(args[2]);
                    }catch (Exception e){
                        sender.sendMessage("%s is not a valid integer type.".formatted(args[2]));
                        return true;
                    }
                    sender.sendMessage(setAddedProtectionsCounts(args[1], count));
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
                return Arrays.asList("reload", "get", "getDeaths", "setDeath","set","add");
            }else {
                return Arrays.asList("get", "getDeaths");
            }

        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("get")
                    || args[0].equalsIgnoreCase("getDeaths")
                    || args[0].equalsIgnoreCase("setDeath")
                    || args[0].equalsIgnoreCase("set")
                    || args[0].equalsIgnoreCase("add")) {
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