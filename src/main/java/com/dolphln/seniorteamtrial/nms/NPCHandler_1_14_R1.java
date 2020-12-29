package com.dolphln.seniorteamtrial.nms;

import com.dolphln.seniorteamtrial.SeniorTeamTrial;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_14_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_14_R1.CraftServer;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class NPCHandler_1_14_R1 implements NPCHandler {

    private EntityPlayer npc;
    private GameProfile gameProfile;

    @Override
    public void createNPC(Location loc) {
        MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer nmsWorld = ((CraftWorld) loc.getWorld()).getHandle();
        this.gameProfile = new GameProfile(UUID.randomUUID(), "SampleNPC");
        this.npc = new EntityPlayer(nmsServer, nmsWorld, this.gameProfile, new PlayerInteractManager(nmsWorld));
        this.npc.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        /*connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
        connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc));
        connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) ((0 * 256.0F) / 360.0F)));*/
    }

    @Override
    public void sendNPCPackets() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            sendNPCPackets(player);
        }
    }

    @Override
    public void sendNPCPackets(Player p) {
        sendPacket(p, new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, this.npc));
        sendPacket(p, new PacketPlayOutNamedEntitySpawn(this.npc));
        sendPacket(p, new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, this.npc));
        sendPacket(p, new PacketPlayOutEntityHeadRotation(this.npc, (byte) ((0 * 256.0F) / 360.0F)));
    }

    @Override
    public void makeNPCJump() {
        sendPacketToPlayers(new PacketPlayOutEntity.PacketPlayOutRelEntityMove(npc.getId(), (short) 0, (short) 4096, (short) 0, true));
        new BukkitRunnable() {
            @Override
            public void run() {
                sendPacketToPlayers(new PacketPlayOutEntity.PacketPlayOutRelEntityMove(npc.getId(), (short) 0, (short) -4096, (short) 0, true));
            }
        }.runTaskLater(SeniorTeamTrial.instance, 5L);
    }

    @Override
    public void makeNPCSneak() {
        DataWatcher datas = this.npc.getDataWatcher();
        datas.set(DataWatcherRegistry.s.a(6), EntityPose.SNEAKING);
        sendPacketToPlayers(new PacketPlayOutEntityMetadata(this.npc.getId(), datas, false));
    }

    @Override
    public void makeNPCStand() {
        DataWatcher datas = this.npc.getDataWatcher();
        datas.set(DataWatcherRegistry.s.a(6), EntityPose.STANDING);
        sendPacketToPlayers(new PacketPlayOutEntityMetadata(this.npc.getId(), datas, false));
    }

    @Override
    public void makeNPCHit() {
        sendPacketToPlayers(new PacketPlayOutAnimation(this.npc, 0));
    }

    @Override
    public void despawnNPC()  {
        int[] ids = {this.npc.getId()};
        sendPacketToPlayers(new PacketPlayOutEntityDestroy(ids));
        this.npc = null;
    }

    @Override
    public void sendPacketToPlayers(Object packet) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket((Packet) packet);
        }
    }

    @Override
    public void sendPacket(Player player, Object packet) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket((Packet) packet);
    }

}
