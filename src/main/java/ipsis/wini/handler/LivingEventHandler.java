package ipsis.wini.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import ipsis.wini.item.ItemVoidBag;
import ipsis.wini.registry.VoidBagRegistry;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LivingEventHandler {

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {

        /* Server only and end phase */
        if (event.player == null || event.phase != TickEvent.Phase.END || event.side == Side.CLIENT)
            return;

        List<UUID> currBags = new ArrayList<UUID>();

        for (ItemStack itemStack : event.player.inventory.mainInventory) {
            if (itemStack == null)
                continue;

            if (!(itemStack.getItem() instanceof ItemVoidBag))
                continue;

            UUID uuid = ItemVoidBag.getBagUUID(itemStack);
            if (uuid != null) {
                currBags.add(uuid);
                if (VoidBagRegistry.getInstance().hasBag(event.player, itemStack) == false)
                    VoidBagRegistry.getInstance().setBag(event.player, itemStack);
            }
        }

        if (!currBags.isEmpty())
            VoidBagRegistry.getInstance().purgeBags(event.player, currBags);
    }
}
