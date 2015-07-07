package ipsis.wini.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import ipsis.wini.handler.ItemEventHandler;
import ipsis.wini.handler.LivingEventHandler;
import ipsis.wini.handler.PlayerEventHandler;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy implements  IProxy {

    @Override
    public void registerEventHandlers() {
        MinecraftForge.EVENT_BUS.register(new ItemEventHandler());
        MinecraftForge.EVENT_BUS.register(new LivingEventHandler());
        FMLCommonHandler.instance().bus().register(new PlayerEventHandler());
        FMLCommonHandler.instance().bus().register(new LivingEventHandler());
    }
}
