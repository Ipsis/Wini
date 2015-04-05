package ipsis.wini.inventory;

import cofh.lib.gui.slot.SlotLocked;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerVoidBag extends Container {

    private ItemInventoryWini voidBag;

    public ContainerVoidBag(EntityPlayer entityPlayer, ItemStack itemStack) {

        voidBag = new ItemInventoryVoidBag(itemStack);

        /* Assuming in a straight line */
        for (int i = 0; i < voidBag.getSizeInventory(); i++)
            this.addSlotToContainer(new Slot(voidBag, i, 44 + (i * 18), 20));

         /* Player inventory */
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++)
                this.addSlotToContainer(new Slot(entityPlayer.inventory, x + y * 9 + 9, 8 + x * 18, 51 + y * 18));
        }
        /* Player hotbar */
        for (int x = 0; x < 9; x++) {
            /* Dont allow the bag inventory item to be changed */
            if (x == entityPlayer.inventory.currentItem)
                this.addSlotToContainer(new SlotLocked(entityPlayer.inventory, x, 8 + x * 18, 109));
            else
                this.addSlotToContainer(new Slot(entityPlayer.inventory, x, 8 + x * 18, 109));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer) {
        /* You are holding it, you cannot be that far away */
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slotNum) {

        /* There is nothing to shift-click out or in */
        return null;
    }

    @Override
    public void onContainerClosed(EntityPlayer entityPlayer) {

        super.onContainerClosed(entityPlayer);
        if (!entityPlayer.worldObj.isRemote)
            voidBag.saveInventoryToStack(entityPlayer.getHeldItem());
    }
}
