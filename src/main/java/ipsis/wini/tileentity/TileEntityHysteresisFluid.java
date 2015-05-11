package ipsis.wini.tileentity;

import ipsis.wini.helper.MonitorType;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityHysteresisFluid extends TileEntityHysteresis {

    public TileEntityHysteresisFluid() {
        super(MonitorType.FLUID);
    }

    @Override
    public int getCurrentValue(TileEntity te) {

        int value = 0;
        if (te != null && te instanceof IFluidHandler) {
            IFluidHandler fluidHandler = (IFluidHandler)te;
            FluidTankInfo tankInfo[] = fluidHandler.getTankInfo(getFacing());

        }
        return value;
    }

    @Override
    public boolean isAdjacentBlockValid(TileEntity te) {
        if (te == null || !(te instanceof IFluidHandler))
            return false;

        return true;
    }
}
