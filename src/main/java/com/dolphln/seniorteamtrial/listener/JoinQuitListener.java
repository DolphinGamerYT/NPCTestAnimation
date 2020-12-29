package com.dolphln.seniorteamtrial.listener;

import com.dolphln.seniorteamtrial.commands.SpawnNPCCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinQuitListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (SpawnNPCCommand.npc != null) {
            SpawnNPCCommand.npc.sendNPCPackets(e.getPlayer());
        }
    }

}
