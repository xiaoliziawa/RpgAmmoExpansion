package net.prizowo.rpgammoexpansion;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.prizowo.rpgammoexpansion.config.RpgAmmoExpansionConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(RpgAmmoExpansion.MOD_ID)
public class RpgAmmoExpansion {
    public static final String MOD_ID = "rpgammoexpansion";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public RpgAmmoExpansion() {

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, RpgAmmoExpansionConfig.COMMON_SPEC);
        
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);

        MinecraftForge.EVENT_BUS.register(this);
        
    }
    
    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("RPG Ammo Expansion Done");
    }
}