package com.mc.t1mplugin.t1m;

import org.bukkit.plugin.java.JavaPlugin;

public final class T1m extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("t1m plugin enabled!");
        getServer().getPluginManager().registerEvents(new DeathCoordinates(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("t1m plugin disabled!");
    }
}
