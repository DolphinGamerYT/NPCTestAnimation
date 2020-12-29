package com.dolphln.seniorteamtrial.nms;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface NPCHandler {

    void createNPC(Location loc);

    void sendNPCPackets();

    void sendNPCPackets(Player p);

    void makeNPCJump();

    void makeNPCSneak();

    void makeNPCStand();

    void makeNPCHit();

    void despawnNPC();

    void sendPacketToPlayers(Object packet);

    void sendPacket(Player player, Object packet);

}
