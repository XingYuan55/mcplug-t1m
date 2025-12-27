package com.mc.t1mplugin.t1m;
import org.bukkit.command.Command; 
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;


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
                            return handleAppliedGroupCommands(sender, args);
                            
                        case "killer_delta":
                        case "victim_delta":
                            return handleDeltaCommands(sender, args);
                            
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
        }
}
