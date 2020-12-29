package com.dolphln.seniorteamtrial.commands;

import com.dolphln.seniorteamtrial.SeniorTeamTrial;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadConfigCommand implements CommandExecutor {

    private final SeniorTeamTrial plugin;

    public ReloadConfigCommand(SeniorTeamTrial plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        plugin.getConfigFile().reloadConfig();

        if (sender instanceof Player) {
            ((Player) sender).sendMessage(ChatColor.GREEN + "Config has been reloaded correctly!");
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Config has been reloaded correctly!");
        }

        return false;
    }
}
