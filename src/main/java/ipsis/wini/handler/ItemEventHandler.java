package ipsis.wini.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ipsis.oss.util.LogHelper;
import ipsis.wini.item.ItemVoidBag;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;

public class ItemEventHandler {

    @SubscribeEvent
    public void onEntityItemPickupEvent(EntityItemPickupEvent event) {

        /* Server only event */

        if (event.isCanceled())
            return;

        ItemStack pickedItemStack = event.item.getEntityItem();
        if (pickedItemStack == null || pickedItemStack.stackSize <= 0)
            return;

        /* Find the void bag */
        for (ItemStack itemstack : event.entityPlayer.inventory.mainInventory) {

            if (itemstack == null || itemstack.stackSize <= 0 || !(itemstack.getItem() instanceof ItemVoidBag))
                continue;

            /* This is a void bag then */
            ItemVoidBag bag = ((ItemVoidBag)itemstack.getItem());
            if (bag.handleItem(event.entityPlayer, itemstack, pickedItemStack)) {
                pickedItemStack.stackSize = 0;
                break;
            }
        }
    }
}
