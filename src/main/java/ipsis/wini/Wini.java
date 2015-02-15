package ipsis.wini;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import ipsis.wini.init.ModBlocks;
import ipsis.wini.init.ModItems;
import ipsis.wini.reference.Reference;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION)
public class Wini {

    @Mod.Instance(Reference.MOD_ID)
    public static Wini instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        ModBlocks.preInit();
        ModItems.preInit();
    }

    @Mod.EventHandler
    public void initialize(FMLInitializationEvent event) {

        ModBlocks.initialize();
        ModItems.initialize();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

        ModBlocks.postInit();
        ModItems.postInit();
    }
}
