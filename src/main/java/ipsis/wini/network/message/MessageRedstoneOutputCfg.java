package ipsis.wini.network.message;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import ipsis.wini.utils.IRedstoneOutput;
import net.minecraft.tileentity.TileEntity;

public class MessageRedstoneOutputCfg implements IMessage, IMessageHandler<MessageRedstoneOutputCfg, IMessage> {

    public int x, y, z;
    public boolean strength;
    public boolean sense;
    public byte level;

    public MessageRedstoneOutputCfg() { }

    public MessageRedstoneOutputCfg(IRedstoneOutput.Strength strength, IRedstoneOutput.Sense sense, int level, int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.strength = strength == IRedstoneOutput.Strength.STRONG ? true : false;
        this.sense = sense == IRedstoneOutput.Sense.NORMAL ? true : false;
        this.level = (byte)level;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.strength = buf.readBoolean();
        this.sense = buf.readBoolean();
        this.level = buf.readByte();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.x);
        buf.writeInt(this.y);
        buf.writeInt(this.z);
        buf.writeBoolean(strength);
        buf.writeBoolean(sense);
        buf.writeByte(level);
    }

    @Override
    public IMessage onMessage(MessageRedstoneOutputCfg message, MessageContext ctx) {
        if (ctx.side.isServer()) {
            if (ctx.getServerHandler().playerEntity != null) {
                TileEntity te = ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.x, message.y, message.z);
                if (te instanceof IRedstoneOutput)
                    ((IRedstoneOutput) te).handleMessageRedstoneOutputCfg(message, ctx.getServerHandler().playerEntity);
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return String.format("MessageRedstoneOutputCfg - strength:%b sense:%b level:%d",
                this.strength, this.sense, this.level);
    }
}
