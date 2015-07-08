package ipsis.wini.handler;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ipsis.wini.registry.VoidBagRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;

public class ItemEventHandler {

    /* Let everything else handle the item and we can be last-ish */
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onEntityItemPickupEvent(EntityItemPickupEvent event) {

        /* Server only event */
        if (event.isCanceled())
            return;

        ItemStack pickedItemStack = event.item.getEntityItem();
        if (pickedItemStack == null || pickedItemStack.stackSize <= 0)
            return;

        if (VoidBagRegistry.getInstance().hasMatch(event.entityPlayer, pickedItemStack)) {
            event.setCanceled(true);
            event.item.setDead();
        }
    }
}
