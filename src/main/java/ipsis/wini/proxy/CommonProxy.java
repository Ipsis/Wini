package ipsis.wini.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import ipsis.wini.handler.ItemEventHandler;
import ipsis.wini.handler.LivingEventHandler;
import ipsis.wini.handler.PlayerEventHandler;
import ipsis.wini.handler.TextureEventHandlers;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy implements  IProxy {

    @Override
    public void registerEventHandlers() {

        /**
         *  TODO LivingEventHandler registered twice!
         *  FMLCommonHandler.instance().bus().register for PlayerTickEvent == LivingEventHandler
         */
        MinecraftForge.EVENT_BUS.register(new TextureEventHandlers());
        MinecraftForge.EVENT_BUS.register(new ItemEventHandler());
        MinecraftForge.EVENT_BUS.register(new LivingEventHandler());
        FMLCommonHandler.instance().bus().register(new PlayerEventHandler());
        FMLCommonHandler.instance().bus().register(new LivingEventHandler());
    }
}
