package ipsis.wini.inventory;

import cofh.lib.gui.slot.SlotLocked;
import ipsis.wini.item.ItemVoidBag;
import ipsis.wini.registry.VoidBagRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerVoidBag extends ContainerInventoryItemWini {

    public ContainerVoidBag(ItemStack itemStack, InventoryPlayer invPlayer) {

        super(itemStack, invPlayer);

        ItemVoidBag.BagSize bagSize = ((ItemVoidBag)itemStack.getItem()).getBagSize();

        if (bagSize == ItemVoidBag.BagSize.SMALL) {
            for (int i = 0; i < bagSize.getSize(); i++)
                this.addSlotToContainer(new Slot(this.containerWrapper, i, 8 + (i * 18), 20));
        } else {
            for (int j = 0; j < 2; j++) {
                for (int i = 0; i < bagSize.getSize() / 2; i++)
                    this.addSlotToContainer(new Slot(this.containerWrapper, i + (9 * j), 8 + (i * 18), 9 + (j * 18)));
            }
        }

         /* Player inventory */
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++)
                this.addSlotToContainer(new Slot(invPlayer, x + y * 9 + 9, 8 + x * 18, 59 + y * 18));
        }
        /* Player hotbar */
        for (int x = 0; x < 9; x++) {
            /* Dont allow the bag inventory item to be changed */
            if (x == invPlayer.currentItem)
                this.addSlotToContainer(new SlotLocked(invPlayer, x, 8 + x * 18, 117));
            else
                this.addSlotToContainer(new Slot(invPlayer, x, 8 + x * 18, 117));
        }
    }

    @Override
    public void onContainerClosed(EntityPlayer entityPlayer) {
        super.onContainerClosed(entityPlayer);

        if (!entityPlayer.worldObj.isRemote && entityPlayer.getCurrentEquippedItem() != null && entityPlayer.getCurrentEquippedItem().getItem() instanceof ItemVoidBag)
            VoidBagRegistry.getInstance().addBag(entityPlayer, entityPlayer.getCurrentEquippedItem());
    }
}
