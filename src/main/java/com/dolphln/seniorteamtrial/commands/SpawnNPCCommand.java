package com.dolphln.seniorteamtrial.commands;

import com.dolphln.seniorteamtrial.SeniorTeamTrial;
import com.dolphln.seniorteamtrial.nms.NPCHandler;
import com.dolphln.seniorteamtrial.nms.NPCHandler_1_16_R3;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SpawnNPCCommand implements CommandExecutor {

    private final SeniorTeamTrial plugin;

    public static NPCHandler npc;

    public SpawnNPCCommand(SeniorTeamTrial plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (npc == null) {
                p.sendMessage(ChatColor.GREEN + "Running NPC animation...");

                Location loc = plugin.getConfigFile().getLocation("npc_spawn").add(0.5, 0, 0.5);
                npc = plugin.getNpcHandler();
                npc.createNPC(loc);
                npc.sendNPCPackets();

                /* First Step: Make NPC Jump */
                new BukkitRunnable() { @Override public void run(){ npc.makeNPCJump();}}.runTaskLater(plugin, 20L);

                /* Second Step: Make NPC Jump */
                new BukkitRunnable() { @Override public void run(){ npc.makeNPCJump();}}.runTaskLater(plugin, 40L);

                /* Third Step: Make NPC Crouch */
                new BukkitRunnable() { @Override public void run(){ npc.makeNPCSneak();}}.runTaskLater(plugin, 60L);

                /* Fourth Step: Make NPC Stand */
                new BukkitRunnable() { @Override public void run(){ npc.makeNPCStand();}}.runTaskLater(plugin, 80L);

                /* Fifth Step: Make NPC Crouch */
                new BukkitRunnable() { @Override public void run(){ npc.makeNPCSneak();}}.runTaskLater(plugin, 100L);

                /* Sixth Step: Make NPC Hit */
                new BukkitRunnable() { @Override public void run(){ npc.makeNPCHit();}}.runTaskLater(plugin, 120L);

                /* Seventh Step: Make NPC Stand */
                new BukkitRunnable() { @Override public void run(){ npc.makeNPCStand();}}.runTaskLater(plugin, 140L);

                /* Eight Step: Despawn NPC */
                new BukkitRunnable() {
                    @Override
                    public void run(){
                        npc.despawnNPC();
                        npc = null;
                        p.sendMessage(ChatColor.GREEN + "NPC animation finished.");
                    }
                }.runTaskLater(plugin, 160L);
            } else {
                p.sendMessage(ChatColor.RED + "A NPC is already running the animation.");
            }
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "You cannot use this command on console.");
        }

        return false;
    }
}
