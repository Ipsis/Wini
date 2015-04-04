package ipsis.wini.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import ipsis.wini.handler.ItemEventHandler;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy implements  IProxy {

    @Override
    public void registerEventHandlers() {

        //FMLCommonHandler.instance().bus().register(new ItemEventHandler());
        MinecraftForge.EVENT_BUS.register(new ItemEventHandler());
    }
}
