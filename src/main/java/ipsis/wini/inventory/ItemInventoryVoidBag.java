package ipsis.wini.inventory;

import ipsis.wini.item.ItemVoidBag;
import net.minecraft.item.ItemStack;

public class ItemInventoryVoidBag extends ItemInventoryWini {

    public ItemInventoryVoidBag(ItemStack itemStack,ItemVoidBag.BagSize bagSize) {
        super(bagSize.getSize(), itemStack, 1);
    }
}
