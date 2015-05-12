package ipsis.wini.proxy;

import ipsis.wini.handler.ItemEventHandler;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy implements  IProxy {

    @Override
    public void registerEventHandlers() {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new ItemEventHandler());
    }
}
