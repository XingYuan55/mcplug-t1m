package com.mc.t1mplugin.t1m;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class HealthConfig{
    private static JavaPlugin plugin;
    private static int victimHealthDelta = 1;
    private static int killerHealthDelta = -1;

    private static final int DEFAULT_VICTIM_HEALTH_DELTA = 1;
    private static final int DEFAULT_KILLER_HEALTH_DELTA = -1;

    public static void init(JavaPlugin pluginInstance) {
        plugin = pluginInstance;
        loadConfig();
    }

    public static void loadConfig() {
        plugin.reloadConfig();
        FileConfiguration config = plugin.getConfig();

        if (!config.contains("health.victim_delta")) {
            config.set("health.victim_delta", DEFAULT_VICTIM_HEALTH_DELTA);
        }
        if (!config.contains("health.killer_delta")) {
            config.set("health.killer_delta", DEFAULT_KILLER_HEALTH_DELTA);
        }
        plugin.saveConfig();

        victimHealthDelta = config.getInt("health.victim_delta", DEFAULT_VICTIM_HEALTH_DELTA);
        killerHealthDelta = config.getInt("health.killer_delta", DEFAULT_KILLER_HEALTH_DELTA);
    }

    public static void saveConfig(){
        FileConfiguration config = plugin.getConfig();
        config.set("health.victim_delta", victimHealthDelta);
        config.set("health.killer_delta", killerHealthDelta);

        plugin.saveConfig();
    }

    public static int getVictimHealthDelta() {
        return victimHealthDelta;
    }

    public static void setVictimHealthDelta(int value) {
        victimHealthDelta = value;
        saveConfig();
    }

    public static int getKillerHealthDelta() {
        return killerHealthDelta;
    }

    public static void setKillerHealthDelta(int value) {
        killerHealthDelta = value;
        saveConfig();
    }
}