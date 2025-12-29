/* No use */

package com.mc.t1mplugin.t1m;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.Bukkit;

public class DeathCoordinates implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        // 获取玩家死亡的位置
        Location location = event.getEntity().getLocation();
        Player victim = event.getEntity().getPlayer();
        Player killer = event.getEntity().getKiller();
        
        int delta_victim_health = 1;
        int delta_killer_health = -1;
        // 在游戏聊天框内输出坐标
        // event.getEntity().sendMessage("你死亡的坐标1是: " + location.getX() + ", " + location.getY() + ", " + location.getZ());
        // victim.sendMessage("你死亡的坐标2是: " + location.getX() + ", " + location.getY() + ", " + location.getZ());
        
        // 显示击杀者信息
        if (killer != null) {
            // 被玩家击杀
            victim.setMaxHealth(victim.getMaxHealth() + delta_victim_health);
            killer.setMaxHealth(killer.getMaxHealth() + delta_killer_health);
        } else {
            // 非玩家击杀（怪物、掉落、自然伤害等）
            victim.sendMessage("击杀者: 未知（非玩家击杀）");
        }
    }
}