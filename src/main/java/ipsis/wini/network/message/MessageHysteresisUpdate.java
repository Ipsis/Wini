package ipsis.wini.network.message;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import ipsis.oss.util.LogHelper;
import ipsis.wini.tileentity.TileEntityHysteresis;
import ipsis.wini.utils.CompareFunc;
import ipsis.wini.utils.IRedstoneOutput;
import net.minecraft.tileentity.TileEntity;

public class MessageHysteresisUpdate implements IMessage, IMessageHandler<MessageHysteresisUpdate, IMessage> {

    public int x, y, z;
    public int trigger;
    public int reset;
    public byte triggerFunc;
    public byte resetFunc;
    public byte enabled;
    public boolean strength;
    public boolean sense;
    public byte level;

    public MessageHysteresisUpdate() {
    }

    public MessageHysteresisUpdate(TileEntityHysteresis te) {
        this.x = te.xCoord;
        this.y = te.yCoord;
        this.z = te.zCoord;
        this.trigger = te.getTriggerLevel();
        this.reset = te.getResetLevel();
        this.triggerFunc = (byte) te.getTriggerFunc().ordinal();
        this.resetFunc = (byte) te.getResetFunc().ordinal();
        this.enabled = (byte) (te.isEnabled() ? 1 : 0);
        this.strength = te.getRedstoneStrength() == IRedstoneOutput.Strength.STRONG ? true : false;
        this.sense = te.getRedstoneSense() == IRedstoneOutput.Sense.NORMAL ? true : false;
        this.level = (byte) te.getRedstoneLevel();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.trigger = buf.readInt();
        this.reset = buf.readInt();
        this.triggerFunc = buf.readByte();
        this.resetFunc = buf.readByte();
        this.enabled = buf.readByte();
        this.strength = buf.readBoolean();
        this.sense = buf.readBoolean();
        this.level = buf.readByte();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.x);
        buf.writeInt(this.y);
        buf.writeInt(this.z);
        buf.writeInt(this.trigger);
        buf.writeInt(this.reset);
        buf.writeByte(this.triggerFunc);
        buf.writeByte(this.resetFunc);
        buf.writeByte(this.enabled);
        buf.writeBoolean(strength);
        buf.writeBoolean(sense);
        buf.writeByte(level);
    }

    @Override
    public IMessage onMessage(MessageHysteresisUpdate message, MessageContext ctx) {

        LogHelper.info("onMessage: " + message);

        if (ctx.side.isClient()) {
            TileEntity te = FMLClientHandler.instance().getWorldClient().getTileEntity(message.x, message.y, message.z);
            if (te instanceof TileEntityHysteresis)
                ((TileEntityHysteresis) te).handleMessageHysteresisUpdate(message);
        }

        return null;
    }

    @Override
    public String toString() {
        return String.format("MessageHysteresisUpdate - trigger:%d reset:%d triggerFunc:%s resetFunc:%s enabled:%d strength:%b sense:%b level:%d",
                this.trigger, this.reset, CompareFunc.getType(this.triggerFunc), CompareFunc.getType(this.resetFunc), this.enabled,
                this.strength, this.sense, this.level);
    }
}