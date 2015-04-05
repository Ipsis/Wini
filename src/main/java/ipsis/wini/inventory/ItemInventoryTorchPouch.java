package ipsis.wini.inventory;

import ipsis.wini.reference.Nbt;
import ipsis.wini.utils.ItemHelper;
import net.minecraft.item.ItemStack;

public class ItemInventoryTorchPouch extends ItemInventoryWini{

    public ItemInventoryTorchPouch(ItemStack itemStack) {
        super(5, itemStack, 64);
    }

    @Override
    public boolean isItemValidForSlot(int slotNum, ItemStack itemStack) {

        return ItemHelper.isTorch(itemStack);
    }

    @Override
    public void saveInventoryToStack(ItemStack itemStack) {
        super.saveInventoryToStack(itemStack);

        itemStack.getTagCompound().setShort(Nbt.TORCH_POUCH_COUNT, getItemCount());
    }
}
