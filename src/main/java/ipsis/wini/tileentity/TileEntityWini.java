package ipsis.wini.tileentity;

import ipsis.wini.network.PacketHandler;
import ipsis.wini.network.message.MessageTileEntityWini;
import ipsis.wini.reference.Nbt;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEntityWini extends TileEntity {

    private ForgeDirection facing;
    private int state; /* Some space for more than an on/off */

    public TileEntityWini() {
        super();
    }

    public ForgeDirection getFacing() {
        return facing;
    }

    public void setFacing(ForgeDirection facing) {
        this.facing = facing;
    }

    public void setFacing(int facing) {
        this.facing = ForgeDirection.getOrientation(facing);
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return this.state;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);

        setFacing(nbtTagCompound.getByte(Nbt.TE_FACING));
        setState(nbtTagCompound.getInteger(Nbt.TE_STATE));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);

        nbtTagCompound.setByte(Nbt.TE_FACING, (byte) facing.ordinal());
        nbtTagCompound.setInteger(Nbt.TE_STATE, state);
    }

    @Override
    public Packet getDescriptionPacket() {
        return PacketHandler.INSTANCE.getPacketFrom(new MessageTileEntityWini(this));
    }

    public void handleDescriptionPacket(MessageTileEntityWini message) {
        setFacing(message.facing);
        setState(message.state);
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }
}
