package ipsis.wini.inventory;

import cofh.lib.gui.slot.SlotAcceptValid;
import cofh.lib.gui.slot.SlotLocked;
import cofh.lib.util.helpers.InventoryHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerTorchPouch extends Container {

    private ItemInventoryWini torchBag;

    public ContainerTorchPouch(EntityPlayer entityPlayer, ItemStack itemStack) {

        torchBag = new ItemInventoryTorchPouch(itemStack);

        /* Assuming in a straight line */
        for (int i = 0; i < torchBag.getSizeInventory(); i++)
            this.addSlotToContainer(new SlotAcceptValid(torchBag, i, 44 + (i * 18), 20));

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

        Slot slot = getSlot(slotNum);
        if (slot == null || !slot.getHasStack())
            return null;

        ItemStack stack = slot.getStack();
        ItemStack result = stack.copy();

        if (slotNum < 5) {
            /* machine to player */
            if (!InventoryHelper.mergeItemStack(this.inventorySlots, stack, 5, 5 + 27 + 9, false))
                return null;
        } else {
            /* player to machine */
            if (!InventoryHelper.mergeItemStack(this.inventorySlots, stack, 0, 4, false))
                return null;
        }

        if (stack.stackSize == 0)
            slot.putStack(null);
        else
            slot.onSlotChanged();

        slot.onPickupFromSlot(entityPlayer, stack);
        return result;
    }

    @Override
    public void onContainerClosed(EntityPlayer entityPlayer) {

        super.onContainerClosed(entityPlayer);
        if (!entityPlayer.worldObj.isRemote)
            torchBag.saveInventoryToStack(entityPlayer.getHeldItem());
    }
}
