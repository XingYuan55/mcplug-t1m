package com.mc.t1mplugin.t1m;

import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlugMain extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("t1m plugin enabled!");
        
        // 初始化配置
        HealthConfig.init(this);
        
        // 注册事件监听器
        getServer().getPluginManager().registerEvents(new DeathProcess(), this);
        
        // Paper 插件必须使用 CommandMap 注册命令（不能使用 plugin.yml）
        PlugCommand commandExecutor = new PlugCommand();
        registerCommand("t1m", commandExecutor, "T1M插件管理命令", "/t1m [set|get|reload|start|stop]");
        
        getLogger().info("命令已注册: /t1m");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("t1m plugin disabled!");
    }
    
    // 辅助方法：注册命令（Paper 插件方式）
    private void registerCommand(String name, PlugCommand executor, String description, String usage) {
        Command command = new Command(name) {
            @Override
            public boolean execute(org.bukkit.command.CommandSender sender, String commandLabel, String[] args) {
                return executor.onCommand(sender, this, commandLabel, args);
            }
            

            // 这段啥意思AI写的没看懂
            @Override
            public java.util.List<String> tabComplete(org.bukkit.command.CommandSender sender, String alias, String[] args) {
                if (executor instanceof TabCompleter) {
                    return ((TabCompleter) executor).onTabComplete(sender, this, alias, args);
                }
                return super.tabComplete(sender, alias, args);
            }
        };
        command.setDescription(description);
        command.setUsage(usage);
        
        // 使用 CommandMap 注册命令
        CommandMap commandMap = getServer().getCommandMap();
        commandMap.register(this.getName().toLowerCase(), command);
    }
}
