package com.dolphln.seniorteamtrial.config;

import com.dolphln.seniorteamtrial.SeniorTeamTrial;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

public class ConfigFile {

    private final SeniorTeamTrial plugin;

    private YamlConfiguration config;
    private File configFile;

    private HashMap<Material, Double> materialExperience = new HashMap<>();

    public ConfigFile(SeniorTeamTrial plugin) {
        this.plugin = plugin;
        setup();
    }

    public void setup() {

        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        configFile = new File(plugin.getDataFolder(), "config.yml");

        if (!configFile.exists()) {
            try {
                plugin.saveResource("config.yml", true);
            } catch (Exception e) {
                plugin.getLogger().log(Level.SEVERE, "Could not create config.yml file.");
            }
        }

        config = YamlConfiguration.loadConfiguration(configFile);

        plugin.getLogger().log(Level.FINE, "File config.yml loaded correctly.");
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public File getFile() {
        return configFile;
    }

    public void reloadConfig() {
        if (!configFile.exists()) {
            setup();
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public Location getLocation(String path) {
        World world = Bukkit.getWorld(getConfig().getString(path + ".world"));
        int x = getConfig().getInt(path + ".x");
        int y = getConfig().getInt(path + ".y");
        int z = getConfig().getInt(path + ".z");


        return new Location(world, x, y, z);
    }

    public String getMessage(String messageName) {
        return ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages." + messageName));
    }

}
