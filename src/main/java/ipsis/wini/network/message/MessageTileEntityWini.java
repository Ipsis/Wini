package ipsis.wini.network.message;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import ipsis.wini.tileentity.TileEntityWini;
import net.minecraft.tileentity.TileEntity;

public class MessageTileEntityWini implements IMessage, IMessageHandler<MessageTileEntityWini, IMessage>{

    public int x, y, z;
    public byte facing;
    public int state;

    public MessageTileEntityWini() {

    }

    public MessageTileEntityWini(TileEntityWini te) {
        this.x = te.xCoord;
        this.y = te.yCoord;
        this.z = te.zCoord;
        this.facing = (byte)te.getFacing().ordinal();
        this.state = te.getState();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.facing = buf.readByte();
        this.state = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeByte(facing);
        buf.writeInt(state);
    }

    @Override
    public IMessage onMessage(MessageTileEntityWini message, MessageContext ctx) {

        TileEntity te = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.x, message.y, message.z);
        if (te instanceof TileEntityWini)
            ((TileEntityWini)te).handleDescriptionPacket(message);

        return null;
    }

    @Override
    public String toString() {
        return String.format("MessageTileEntityWini: x:%d y:%d z:%d facing:%d state:%d", x, y, z, facing, state);
    }
}
