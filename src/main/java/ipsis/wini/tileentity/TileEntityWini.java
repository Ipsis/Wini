package ipsis.wini.tileentity;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.relauncher.Side;
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
        facing = ForgeDirection.SOUTH;
        state = 0;
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

    protected void sendBlockUpdate(int x, int y, int z) {
        if (worldObj != null)
            worldObj.markBlockForUpdate(x, y, z);
    }

    /**
     * Notify all the blocks adjacent block in all 6 directions
     */
    protected void sendNbrBlockUpdate(int x, int y, int z) {
        if (worldObj != null) {
            //worldObj.func_147453_f(x, y, z, getBlockType());
            worldObj.notifyBlocksOfNeighborChange(x, y, z, getBlockType());
        }
    }

    protected void sendUpdatePacketTo(Side side, IMessage m) {
        if (side == Side.CLIENT && !worldObj.isRemote) {
            worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            PacketHandler.INSTANCE.sendToAllAround(m,
                    new NetworkRegistry.TargetPoint(
                            worldObj.provider.dimensionId,
                            this.xCoord, this.yCoord, this.zCoord, 196));
        } else if (side == Side.SERVER && worldObj.isRemote) {
            PacketHandler.INSTANCE.sendToServer(m);
        }
    }
}
