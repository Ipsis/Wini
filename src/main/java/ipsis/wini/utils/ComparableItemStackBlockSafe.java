package ipsis.wini.utils;

import cofh.lib.inventory.ComparableItemStack;
import cofh.lib.util.helpers.ItemHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * The CoFH ComparableItemStackSafe uses the ore dictionary for
 * entries that start with "block" (along with others).
 * This subclass basically removes the oreID part of ComparableItemStack
 */
public class ComparableItemStackBlockSafe extends ComparableItemStack {

    private static final int INVALID_ORE_ID = -1;

    public static boolean safeOreType(String oreName) {

        return false;
    }

    public static int getOreID(ItemStack stack) {
        return INVALID_ORE_ID;
    }

    public static int getOreID(String oreName) {
        return INVALID_ORE_ID;
    }

    public ComparableItemStackBlockSafe(ItemStack stack) {

        super(stack);
        oreID = INVALID_ORE_ID;
    }

    public ComparableItemStackBlockSafe(Item item, int damage, int stackSize) {

        super(item, damage, stackSize);
        this.oreID = -1;
    }

    @Override
    public ComparableItemStackBlockSafe set(ItemStack stack) {

        super.set(stack);
        oreID = INVALID_ORE_ID;

        return this;
    }
}
