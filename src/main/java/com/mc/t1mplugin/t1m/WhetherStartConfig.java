package com.mc.t1mplugin.t1m;

import org.bukkit.Bukkit;

public class WhetherStartConfig {
    public static boolean is_started_flag = false;

    public void loggingAndBoardcasting(String[] messages, int level){
        System.out.println(String.join(" ", messages));
        
        if (level){
            if (level == 2){
                Bukkit.broadcastMessage("§6" + messages);
                return;
            }
            if (level == 3){
                Bukkit.broadcastMessage("§c" + messages);
                return;
            }
            Bukkit.broadcastMessage(messages);
        }
    }

    public void loggingAndBoardcasting(String[] messages){
        System.out.println(String.join(" ", messages));
    }

}
