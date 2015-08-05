package ipsis.wini.utils;

import cofh.api.item.IInventoryContainerItem;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemContainerHelper {

    public static List<ItemStack> getContents(ItemStack itemStack) {

        List<ItemStack> contents = new ArrayList<ItemStack>();

        if (itemStack != null && itemStack.getItem() instanceof IInventoryContainerItem && itemStack.stackTagCompound != null) {
            int slots = ((IInventoryContainerItem)itemStack.getItem()).getSizeInventory(itemStack);

            for (int i = 0; i < slots; i++) {
                if (!itemStack.getTagCompound().hasKey("Slot" + i))
                    continue;

                ItemStack currStack = ItemStack.loadItemStackFromNBT(itemStack.getTagCompound().getCompoundTag("Slot" + i));
                if (currStack != null)
                    contents.add(currStack);
            }
        }

        return contents;
    }
}
