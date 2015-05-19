package ipsis.wini.network.message;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import ipsis.oss.util.LogHelper;
import ipsis.wini.network.PacketHandler;
import ipsis.wini.tileentity.TileEntityHysteresis;
import ipsis.wini.utils.CompareFunc;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;

public class MessageHysteresisCfg implements IMessage, IMessageHandler<MessageHysteresisCfg, IMessage> {

    public int x, y, z;
    public int trigger;
    public int reset;
    public byte triggerFunc;
    public byte resetFunc;
    public byte enabled;

    public MessageHysteresisCfg() { }

    public MessageHysteresisCfg(TileEntityHysteresis te) {
        this.x = te.xCoord;
        this.y = te.yCoord;
        this.z = te.zCoord;
        this.trigger = te.getTriggerLevel();
        this.reset = te.getResetLevel();
        this.triggerFunc = (byte)te.getTriggerFunc().ordinal();
        this.resetFunc = (byte)te.getResetFunc().ordinal();
        this.enabled = (byte)(te.isEnabled() ? 1 : 0);
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
    }

    @Override
    public IMessage onMessage(MessageHysteresisCfg message, MessageContext ctx) {
        if (ctx.side.isServer()) {
            if (ctx.getServerHandler().playerEntity != null) {
                TileEntity te = ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.x, message.y, message.z);
                if (te instanceof TileEntityHysteresis)
                    ((TileEntityHysteresis) te).handleMessageHysteresisCfg(message, ctx.getServerHandler().playerEntity);
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return String.format("MessageHysteresisCfg - trigger:%d reset:%d triggerFunc:%s resetFunc:%s enabled:%d",
                this.trigger, this.reset, CompareFunc.getType(this.triggerFunc), CompareFunc.getType(this.resetFunc), this.enabled);
    }
}