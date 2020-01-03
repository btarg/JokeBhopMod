package com.icrazyblaze.boppinhops;

import com.icrazyblaze.boppinhops.util.ConfigManager;
import com.icrazyblaze.boppinhops.util.Reference;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;


@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION, guiFactory = Reference.GUI_FACTORY, dependencies = "before:squake", clientSideOnly = true)
public class Main {

    public static Logger logger;
    public static Configuration config;

    @Instance
    public static Main instance;

    @EventHandler
    public static void preInit(FMLPreInitializationEvent event) {

        logger = event.getModLog();
        config = new Configuration(event.getSuggestedConfigurationFile());
        ConfigManager.loadConfig();
        ConfigManager.initialize();

    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new DetectBhops());

    }

    @SubscribeEvent
    public void registerSounds(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().register(DetectBhops.bhopSoundEvent);
    }

}