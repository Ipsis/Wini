package ipsis.wini.proxy;

import ipsis.wini.handler.DrawBlockHighlightEventHandler;
import ipsis.wini.handler.TextureEventHandlers;
import ipsis.wini.handler.WorldUnloadHandler;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerEventHandlers() {
        super.registerEventHandlers();

        MinecraftForge.EVENT_BUS.register(new TextureEventHandlers());
        MinecraftForge.EVENT_BUS.register(new DrawBlockHighlightEventHandler());
        MinecraftForge.EVENT_BUS.register(new WorldUnloadHandler());
    }
}
