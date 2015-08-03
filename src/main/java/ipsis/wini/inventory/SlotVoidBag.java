package ipsis.wini.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class SlotVoidBag extends Slot {

    public SlotVoidBag(IInventory var1, int var2, int var3, int var4) {
        super(var1, var2, var3, var4);
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }
}
