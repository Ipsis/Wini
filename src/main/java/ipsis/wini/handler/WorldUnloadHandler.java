package ipsis.wini.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ipsis.wini.registry.LocationRegistry;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraftforge.event.world.WorldEvent;

public class WorldUnloadHandler {

    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event) {

        if (event.world instanceof WorldClient)
            LocationRegistry.getInstance().clearLocation();
    }
}
