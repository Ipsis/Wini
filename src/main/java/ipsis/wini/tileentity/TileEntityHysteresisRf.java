package ipsis.wini.tileentity;

import cofh.api.energy.IEnergyHandler;
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
        if (te != null) {
            if (te instanceof IEnergyStorage) {
                IEnergyStorage storage = (IEnergyStorage)te;
                value = storage.getEnergyStored();
            } else if (te instanceof IEnergyHandler) {
                IEnergyHandler handler = (IEnergyHandler)te;
                value = handler.getEnergyStored(getFacing());
            }
        }
        if (te != null && te instanceof IEnergyStorage) {
            IEnergyStorage storage = (IEnergyStorage)te;
            value = storage.getEnergyStored();
        }
        return value;
    }

    @Override
    public boolean isAdjacentBlockValid(TileEntity te) {

        if (te != null && (te instanceof  IEnergyHandler || te instanceof IEnergyStorage))
            return true;

        return false;
    }
}
