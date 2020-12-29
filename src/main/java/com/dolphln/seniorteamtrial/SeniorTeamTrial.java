package com.dolphln.seniorteamtrial;

import com.dolphln.seniorteamtrial.commands.ReloadConfigCommand;
import com.dolphln.seniorteamtrial.commands.SpawnNPCCommand;
import com.dolphln.seniorteamtrial.config.ConfigFile;
import com.dolphln.seniorteamtrial.listener.JoinQuitListener;
import com.dolphln.seniorteamtrial.nms.NPCHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class SeniorTeamTrial extends JavaPlugin {

    public static SeniorTeamTrial instance;

    private ConfigFile configFile;
    private NPCHandler npcHandler;

    @Override
    public void onEnable() {
        instance = this;

        this.configFile = new ConfigFile(this);

        try {
            //Set your nms field
            System.out.println("com.dolphln.seniorteamtrial.nms.NPCHandler_" + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3].substring(1));
            this.npcHandler = (NPCHandler) Class.forName("com.dolphln.seniorteamtrial.nms.NPCHandler_" + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3].substring(1)).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        Bukkit.getPluginManager().registerEvents(new JoinQuitListener(), this);
        getCommand("spawnnpc").setExecutor(new SpawnNPCCommand(this));
        getCommand("npcreload").setExecutor(new ReloadConfigCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public ConfigFile getConfigFile() {
        return configFile;
    }

    public NPCHandler getNpcHandler() {
        return npcHandler;
    }
}
