package ipsis.wini.tileentity;

import ipsis.wini.helper.MonitorType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TileEntityHysteresisInventory extends TileEntityHysteresis {

    public TileEntityHysteresisInventory() {
        super(MonitorType.INVENTORY);
    }

    @Override
    public boolean isAdjacentBlockValid(TileEntity te) {
        if (te == null || !(te instanceof IInventory))
            return false;

        return true;
    }

    @Override
    public int getCurrentValue(TileEntity te) {

        int value = 0;

        /* We are not extracting so ISidedInventory is not necessary */
        if (te != null && te instanceof IInventory) {
            IInventory inv = (IInventory) te;
            for (int i = 0; i < inv.getSizeInventory(); i++) {
                ItemStack itemStack = inv.getStackInSlot(i);
                if (itemStack != null)
                    value += itemStack.stackSize;
            }
        }
        return value;
    }
}
