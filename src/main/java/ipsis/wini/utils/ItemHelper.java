package ipsis.wini.utils;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemHelper {

    public static final boolean isTorch(ItemStack itemStack) {

        if (itemStack == null)
            return false;

        if (itemStack.getItem() == Item.getItemFromBlock(Blocks.torch))
            return true;

        return false;
    }
}
