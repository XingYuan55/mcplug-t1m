package com.mc.t1mplugin.t1m;

import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlugMain extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("t1m plugin enabled!");
        getServer().getPluginManager().registerEvents(new DeathProcess(), this);
        
        // Paper 插件必须使用 CommandMap 注册命令（不能使用 plugin.yml）
        registerCommand("t1m", new PlugCommand(), "test", "/t1m args...");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("t1m plugin disabled!");
    }
    
    // 辅助方法：注册命令（Paper 插件方式）
    private void registerCommand(String name, org.bukkit.command.CommandExecutor executor, String description, String usage) {
        Command command = new Command(name) {
            @Override
            public boolean execute(org.bukkit.command.CommandSender sender, String commandLabel, String[] args) {
                return executor.onCommand(sender, this, commandLabel, args);
            }
        };
        command.setDescription(description);
        command.setUsage(usage);
        
        // 使用 CommandMap 注册命令
        CommandMap commandMap = getServer().getCommandMap();
        commandMap.register(this.getName().toLowerCase(), command);
    }
}
