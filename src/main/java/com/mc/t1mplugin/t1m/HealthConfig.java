package com.mc.t1mplugin.t1m;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class HealthConfig{
    private static JavaPlugin plugin;
    private static int victimHealthDelta = 1;
    private static int killerHealthDelta = -1;

    private static final int DEFAULT_VICTIM_HEALTH_DELTA = 1;
    private static final int DEFAULT_KILLER_HEALTH_DELTA = -1;


    // 存储启用生命值增减的玩家列表
    private static ArrayList<String> appliedVictim = new ArrayList<>();
    private static ArrayList<String> appliedKiller = new ArrayList<>();

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
        if (!config.contains("players.applied_victim")) {
            config.set("players.applied_victim", new ArrayList<String>());
        }
        if (!config.contains("players.applied_killer")) {
            config.set("players.applied_killer", new ArrayList<String>());
        }
        plugin.saveConfig();

        victimHealthDelta = config.getInt("health.victim_delta", DEFAULT_VICTIM_HEALTH_DELTA);
        killerHealthDelta = config.getInt("health.killer_delta", DEFAULT_KILLER_HEALTH_DELTA);
        
        // 加载玩家列表
        appliedVictim = new ArrayList<>(config.getStringList("players.applied_victim"));
        appliedKiller = new ArrayList<>(config.getStringList("players.applied_killer"));
    }

    public static void saveConfig() {
        FileConfiguration config = plugin.getConfig();
        config.set("health.victim_delta", victimHealthDelta);
        config.set("health.killer_delta", killerHealthDelta);
        config.set("players.applied_victim", appliedVictim);
        config.set("players.applied_killer", appliedKiller);

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

    public static ArrayList<String> getAppliedVictim() {
        return new ArrayList<>(appliedVictim); // 返回副本，防止外部修改
    }
    
    public static void setAppliedVictim(ArrayList<String> players) {
        appliedVictim = new ArrayList<>(players);
        saveConfig();
    }

    public static ArrayList<String> getAppliedKiller() {
        return new ArrayList<>(appliedKiller); // 返回副本，防止外部修改
    }

    public static void setAppliedKiller(ArrayList<String> players) {
        appliedKiller = new ArrayList<>(players);
        saveConfig();
    }

    // 检查玩家是否在列表中
    public static boolean isAppliedVictim(String playerName) {
        return appliedVictim.contains(playerName);
    }

    public static boolean isAppliedKiller(String playerName) {
        return appliedKiller.contains(playerName);
    }
}