package com.icrazyblaze.boppinhops.util;

import com.icrazyblaze.boppinhops.DetectBhops;
import com.icrazyblaze.boppinhops.Main;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ConfigManager {

    private static Configuration config;

    private static ConfigManager instance;

    public static void initialize() {
        instance = new ConfigManager();
    }

    public static ConfigManager getInstance() {
        return instance;
    }

    public Configuration getConfig() {
        return config;
    }

    public static void loadConfig() { // Gets called from preInit

        // Config file is made in the Main class
        config = Main.config;

        try {

            // Load config
            config.load();

            // Read props from config
            Property enabledProp = config.get(Configuration.CATEGORY_GENERAL, // What category will it be saved to, can be any string
                    "ENABLE_MOD", // Property name
                    true, // Default value
                    "Enable music while bhopping"); // Comment

            DetectBhops.modEnabled = enabledProp.getBoolean();


        } catch (Exception e) {
            // Failed reading/writing, just continue
        }
    }

    public static void saveConfig() {

        // Config file is made in the Main class
        config = Main.config;

        try {

            // Load config
            config.load();

            // Read props from config
            Property enabledProp = config.get(Configuration.CATEGORY_GENERAL, // What category will it be saved to, can be any string
                    "ENABLE_MOD", // Property name
                    true, // Default value
                    "Enable music while bhopping"); // Comment

            enabledProp.set(DetectBhops.modEnabled);

        } catch (Exception e) {
            // Failed reading/writing, just continue
        } finally {
            // Save properties to config file
            config.save();
        }
    }

}
