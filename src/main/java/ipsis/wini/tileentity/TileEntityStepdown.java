package ipsis.wini.tileentity;

import cofh.lib.util.helpers.MathHelper;
import cofh.lib.util.position.BlockPosition;
import ipsis.wini.reference.Nbt;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityStepdown extends TileEntityWini {

    short redstoneLevel;
    short currInputLevel;

    public TileEntityStepdown() {
        super();
        redstoneLevel = 15;
        currInputLevel = 0;
    }

    public void setRedstoneLevel(int v) {
        v = MathHelper.clampI(v, 0, 15);
        if (v != redstoneLevel) {
            redstoneLevel = (short) v;
            sendNbrBlockUpdate(xCoord, yCoord, zCoord);
        }
    }

    public int getOutputLevel() {
        return this.redstoneLevel;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        redstoneLevel = nbtTagCompound.getShort(Nbt.STEPDOWN_REDSTONE_LEVEL);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setShort(Nbt.STEPDOWN_REDSTONE_LEVEL, redstoneLevel);
    }

    public int isProvidingStrongPower(ForgeDirection dir) {
        return isProvidingWeakPower(dir);
    }

    ForgeDirection getOutputFace() {
        return getFacing().getOpposite();
    }

    /**
     * Direction is inverted
     */
    public int isProvidingWeakPower(ForgeDirection dir) {
        int power = 0;
        if (dir == getOutputFace() && currInputLevel > 0)
                power =  MathHelper.clampI(redstoneLevel, 0, currInputLevel);
        return power;
    }

    int getInputPower(ForgeDirection dir) {
        /**
         * getBlockPowerInput is not direction specific
         * It checks the following:
         * isBlockProvidingPowerTo on the adjacent block
         */
        BlockPosition bPos = new BlockPosition(this).step(dir);
        return worldObj.isBlockProvidingPowerTo(bPos.x, bPos.y, bPos.z, dir.ordinal());
    }

    public void onNeighborChange() {
        int power = getInputPower(getFacing());
        if (power != currInputLevel) {
            currInputLevel = (short)power;
            sendNbrBlockUpdate(xCoord, yCoord, zCoord);
        }
    }
}
