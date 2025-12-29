package com.mc.t1mplugin.t1m;

import org.bukkit.command.Command; 
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import com.mc.t1mplugin.t1m.HealthConfig;
import com.mc.t1mplugin.t1m.WhetherStartConfig;


public class PlugCommand implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // 检查权限：OP 或拥有 t1m.admin 权限
        if (!sender.isOp() && !sender.hasPermission("t1m.admin")) {
            sender.sendMessage("§c你没有权限使用这个命令！");
            return true;
        }

        try {
            if (args.length == 0) {
                sendHelp(sender);
                return true;
            }

            String subCommand = args[0].toLowerCase();

            switch (subCommand) {
                case "reload":
                    return handleReloadCommand(sender);
                    
                case "set":
                    if (args.length < 3) {
                        sender.sendMessage("§c用法: /t1m set <类型> <值>");
                        return true;
                    }
                    switch (args[1]) {
                        case "killer_applied_group":
                        case "victim_applied_group":
                            return handleSetAppliedGroupCommands(sender, args);
                            
                        case "killer_delta":
                        case "victim_delta":
                            return handleSetDeltaCommands(sender, args);
                            
                        default:
                            sender.sendMessage("§c未知的类型: " + args[1]);
                            return true;
                    }
                    
                case "get":
                    if (args.length < 2) {
                        sender.sendMessage("§c用法: /t1m get <类型>");
                        return true;
                    }
                    switch (args[1]) {
                        case "killer_applied_group":
                        case "victim_applied_group":
                            return handleGetAppliedGroupCommands(sender, args);
                            
                        case "killer_delta":
                        case "victim_delta":
                            return handleGetDeltaCommands(sender, args);
                            
                        default:
                            sender.sendMessage("§c未知的类型: " + args[1]);
                            return true;
                    }
                    
                case "start":
                    return handleStartCommand(sender);
                    
                case "stop":
                    return handleStopCommand(sender);
                    
                default:
                    sendHelp(sender);
                    return true;
            }


        } catch (IndexOutOfBoundsException e) {
            sender.sendMessage("§c参数不足！使用 /t1m 查看帮助");
            return true;
        } catch (Exception f){
            sender.sendMessage("命令错误！" + f.getMessage() + " 使用 /t1m 查看帮助")
        }
    }

    private boolean handleReloadCommand(Sender sender){
        HealthConfig.loadConfig();
        Bukkit.broadcastMessage(sender.getName() + "重新加载了t1m插件");
        return true;
    }


    // 显示帮助信息
    private boolean sendHelp(CommandSender sender) {
        sender.sendMessage("§6=== T1M 插件命令帮助 ===");
        sender.sendMessage("§e/t1m set <类型> <值> §7- 设置生命值增减配置");
        sender.sendMessage("§7  类型: killer_delta, victim_delta");
        sender.sendMessage("§e/t1m set <类型> <玩家...> §7- 设置启用生命值增减的玩家");
        sender.sendMessage("§7  类型: killer, victim");
        sender.sendMessage("§e/t1m get <类型> §7- 查看配置");
        sender.sendMessage("§e/t1m reload §7- 重新加载配置");
        sender.sendMessage("§7当前配置:");
        sender.sendMessage("§7  死亡者最大生命值增减: §e" + HealthConfig.getVictimHealthDelta());
        sender.sendMessage("§7  击杀者最大生命值增减: §e" + HealthConfig.getKillerHealthDelta());
        sender.sendMessage("§7  启用最大生命值增减的击杀者: §e" + HealthConfig.getAppliedVictimGroup());
        sender.sendMessage("§7  启用最大生命值增减的死亡者: §e" + HealthConfig.getAppliedKillerGroup());
        return true;
    }
    
    private boolean handleSetDeltaCommands(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage("§c用法: /t1m set <killer_delta|victim_delta> <数值>");
            sender.sendMessage("§7示例: /t1m set victim_delta 2");
            sender.sendMessage("§7示例: /t1m set killer_delta -2");
            return true;
        }

        try{
            String type = args[1].toLowerCase();
            int value = Integer.parseInt(args[2]);

            if (type == "killer_delta"){
                HealthConfig.setKillerHealthDelta(value);
                Bukkit.broadcastMessage(sender.getName() + "设置击杀者最大生命值增减为：" + value);
            }

            if (type == "victim_delta"){
                HealthConfig.setVictimHealthDelta(value);
                Bukkit.broadcastMessage(sender.getName() + "设置死亡者最大生命值增减为：" + value);
            }
        } catch (NumberFormatException e){
            sender.sendMessage("§c错误: 请输入有效的整数！使用 /t1m 查看帮助");
            return true;
        } catch (Exception f){
            throw f;
        } 
    }
    
    private boolean handleGetDeltaCommands(CommandSender sender, String[] args){
        String type = args[1];

        if (type == "killer_delta") {
            sender.sendMessage("§7击杀者生命值增减: §e" + HealthConfig.getKillerHealthDelta());
        } 
        else 
            if (type =="victim_delta") 
            {
                sender.sendMessage("§7死亡者生命值增减: §e" + HealthConfig.getVictimHealthDelta());
            } 
            else {
                sender.sendMessage("§c错误: 类型必须是 'killer_delta' 或 'victim_delta'");
                return true;
            }
    }

    private boolean handleSetAppliedGroupCommands(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage("§c用法: /t1m set <killer_applied_group|victim_applied_group> <玩家名...>");
            return true;
        }

        try{
            String type = args[1].toLowerCase();
            String[] playerNames = args.subarray(args[2], arg[args.length]);

            if (type == "killer_applied_group"){
                HealthConfig.setAppliedKiller(playerNames);
                Bukkit.broadcastMessage(sender.getName() + "设置适用生命值增减的击杀者为：" + " ".join(playerNames));
            }

            if (type == "victim_applied_group"){
                HealthConfig.setAppliedVictim(playerNames);
                Bukkit.broadcastMessage(sender.getName() + "设置适用生命值增减的死亡者为：" + " ".join(playerNames));
            }

        } catch (NumberFormatException e){
            sender.sendMessage("§c错误: 请输入有效的整数！使用 /t1m 查看帮助");
            return true;
        } catch (Exception f){
            throw f;
        } 
    }   

    private boolean handleGetAppliedGroupCommands(CommandSender sender, String[] args) {
        String type = args[1].toLowerCase();

        if (type.equals("killer_applied_group")){
            String[] players = HealthConfig.getAppliedKiller();

            if (players.equals(new String[0])){
                sender.sendMessage("§7启用生命值增减的击杀者: §c无");
            } else {
                sender.sendMessage("§7启用生命值增减的击杀者: §e" + String.join(", ", players));
            }
        }
        
        if (type.equals("victim_applied_group")){
            String[] players = HealthConfig.getAppliedVictim();

            if (players.equals(new String[0])){
                sender.sendMessage("§7启用生命值增减的死亡者: §c无");
            } else {
                sender.sendMessage("§7启用生命值增减的死亡者: §e" + String.join(", ", players));
            }
        }
    }

    private boolean handleStartCommand(CommandSender sender){
        WhetherStartConfig.is_started_flag = true;
        Bukkit.broadcastMessage(sender.getName() + "启用了生命值增减！");
    }

    private boolean handleStopCommland(CommandSender sender){
        WhetherStartConfig.is_started_flag = false;
        Bukkit.broadcastMessage(sender.getName() + "禁用了生命值增减！");
    }

    private boolean handleReloadCommand(CommandSender sender){
        HealthConfig.loadConfig();
        sender.sendMessage("重新加载了配置！");
    }

    @Override
    public java.util.ArrayList<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args){
        java.util.ArrayList<String> completions = new java.util.ArrayList<>();
        if (args.length == 1){
            completions.add("set");
            completions.add("get");
            completions.add("start");
            completions.add("stop");
            completions.add("reload");
        }

        if (args.length == 2){
            if (args[0].toLowerCase().equals("set") || args[0].toLowerCase().equals("get")){
                completions.add("killer_delta");
                completions.add("victim_delta");
                completions.add("killer_applied_group");
                completions.add("victim_applied_group");
            }
        }

        // 过滤匹配的补全项
        // 这两行AI写的，啥意思我咋没看懂
        String lastArg = args[args.length - 1].toLowerCase();
        completions.removeIf(s -> !s.toLowerCase().startsWith(lastArg));
        
        return completions;

    }
}
