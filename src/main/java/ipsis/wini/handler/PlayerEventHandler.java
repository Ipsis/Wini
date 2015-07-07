package ipsis.wini.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import ipsis.wini.registry.VoidBagRegistry;


public class PlayerEventHandler {

    @SubscribeEvent
    public void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {

        if (event.player == null)
            return;

        VoidBagRegistry.getInstance().addPlayer(event.player);
    }

    @SubscribeEvent
    public void onPlayerLoggedOutEvent(PlayerEvent.PlayerLoggedOutEvent event) {

        if (event.player == null)
            return;
        VoidBagRegistry.getInstance().delPlayer(event.player);
    }
}
