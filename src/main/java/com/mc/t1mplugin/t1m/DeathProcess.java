package com.mc.t1mplugin.t1m;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.Bukkit;
import com.mc.t1mplugin.t1m.HealthConfig;

public class DeathProcess implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
            
        WhetherStartConfig.loggingAndBoardcasting("DeathProcess.Listener.onPlayerDeath");
        if (!WhetherStartConfig.is_started_flag)
        {
            // event.setCancelled(true); 这会导致玩家立刻在原地满血重生，像是免疫死亡一样
            return;
        }
        Player victim = event.getEntity().getPlayer();
        Player killer = event.getEntity().getKiller();
        WhetherStartConfig.loggingAndBoardcasting("DeathProcess line22");
        WhetherStartConfig.loggingAndBoardcasting("victim:" + victim.getName());
        WhetherStartConfig.loggingAndBoardcasting("victim:" + killer.getName());

        
        if (killer == null){
            return;
        }
        WhetherStartConfig.loggingAndBoardcasting("DeathProcess line30"); WhetherStartConfig.loggingAndBoardcasting(Boolean.toString(!HealthConfig.getAppliedKiller().isEmpty()) + !HealthConfig.getAppliedKiller().contains(killer.getName()));Bukkit.broadcastMessage(Boolean.toString(!HealthConfig.getAppliedVictim().isEmpty()) + !HealthConfig.getAppliedVictim().contains(victim.getName()));


        // 不是适用玩家
        if (
            (!HealthConfig.getAppliedKiller().isEmpty()) &&
            (!HealthConfig.getAppliedKiller().contains(killer.getName()))
        )
        {
            return;
        }
        WhetherStartConfig.loggingAndBoardcasting("DeathProcess line40");

        if (
            (!HealthConfig.getAppliedVictim().isEmpty()) &&
            (!HealthConfig.getAppliedVictim().contains(victim.getName()))
        )
        {
            return;
        }
        // 获取玩家死亡的位置

        
        // 从配置中读取生命值增减
        int delta_victim_health = HealthConfig.getVictimHealthDelta();
        int delta_killer_health = HealthConfig.getKillerHealthDelta();
        // 在游戏聊天框内输出坐标
        // event.getEntity().sendMessage("你死亡的坐标1是: " + location.getX() + ", " + location.getY() + ", " + location.getZ());
        // victim.sendMessage("你死亡的坐标2是: " + location.getX() + ", " + location.getY() + ", " + location.getZ());
        WhetherStartConfig.loggingAndBoardcasting(Integer.toString(delta_killer_health) + Integer.toString(delta_victim_health));
        
        victim.sendMessage("§6"+killer.getName() + "§r击杀了你，你变化§e§l" + delta_victim_health + "点生命上限！");
        killer.sendMessage("§r你击杀了§6" + killer.getName() + "§r，你变化§e§l" + delta_killer_health + "点生命上限！");


        // 被玩家击杀
        victim.setMaxHealth(victim.getMaxHealth() + delta_victim_health);
        killer.setMaxHealth(killer.getMaxHealth() + delta_killer_health);
    }
}
