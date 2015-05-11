package ipsis.wini.tileentity;

import cofh.api.energy.IEnergyStorage;
import ipsis.wini.helper.MonitorType;
import net.minecraft.tileentity.TileEntity;

public class TileEntityHysteresisRf extends TileEntityHysteresis {

    public TileEntityHysteresisRf() {
        super(MonitorType.RF);
    }

    @Override
    public int getCurrentValue(TileEntity te) {

        int value = 0;
        if (te != null && te instanceof IEnergyStorage) {
            IEnergyStorage storage = (IEnergyStorage)te;
            value = storage.getEnergyStored();
        }
        return value;
    }

    @Override
    public boolean isAdjacentBlockValid(TileEntity te) {
        if (te == null || !(te instanceof IEnergyStorage))
            return false;

        return true;
    }
}
