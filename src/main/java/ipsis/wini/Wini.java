package ipsis.wini;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import ipsis.wini.handler.ConfigHandler;
import ipsis.wini.handler.GuiHandler;
import ipsis.wini.init.ModBlocks;
import ipsis.wini.init.ModItems;
import ipsis.wini.init.ModTileEntities;
import ipsis.wini.init.Recipes;
import ipsis.wini.network.PacketHandler;
import ipsis.wini.proxy.IProxy;
import ipsis.wini.reference.Reference;
import ipsis.wini.registry.TorchRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION)
public class Wini {

    @Mod.Instance(Reference.MOD_ID)
    public static Wini instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static IProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        ConfigHandler.init(event.getSuggestedConfigurationFile());

        PacketHandler.init();

        ModBlocks.preInit();
        ModItems.preInit();

        ModTileEntities.preInit();

        FMLInterModComms.sendMessage("Waila", "register", "ipsis.wini.plugins.waila.WiniWailaProvider.callbackRegister");
    }

    @Mod.EventHandler
    public void initialize(FMLInitializationEvent event) {

        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

        ModBlocks.initialize();
        ModItems.initialize();

        ModTileEntities.initialize();

        proxy.registerEventHandlers();

        Recipes.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

        ModBlocks.postInit();
        ModItems.postInit();

        ModTileEntities.postInit();

        TorchRegistry.getInstance().addModItems();
    }
}
