package ipsis.wini.proxy;

import ipsis.wini.handler.DrawBlockHighlightEventHandler;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerEventHandlers() {
        super.registerEventHandlers();

        MinecraftForge.EVENT_BUS.register(new DrawBlockHighlightEventHandler());
    }
}
