package ipsis.wini.network.message;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import ipsis.wini.gui.IGuiMessageHandler;
import net.minecraft.entity.player.EntityPlayer;

public class MessageGuiWidget implements IMessage, IMessageHandler<MessageGuiWidget, IMessage> {

    public int guiId;
    public byte ctrlType;
    public byte ctrlId;
    public int data1;
    public int data2;

    public MessageGuiWidget() { }

    public MessageGuiWidget(int guiId, int ctrlType, int ctrlId, int data1, int data2) {
        this.guiId = guiId;
        this.ctrlType = (byte)ctrlType;
        this.ctrlId = (byte)ctrlId;
        this.data1 = data1;
        this.data2 = data2;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.guiId = buf.readInt();
        this.ctrlType = buf.readByte();
        this.ctrlId = buf.readByte();
        this.data1 = buf.readInt();
        this.data2 = buf.readInt();
    }
    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(guiId);
        buf.writeByte(this.ctrlType);
        buf.writeByte(this.ctrlId);
        buf.writeInt(this.data1);
        buf.writeInt(this.data2);
    }

    @Override
    public IMessage onMessage(MessageGuiWidget message, MessageContext ctx) {
        EntityPlayer player = ctx.getServerHandler().playerEntity;

        if (player != null && player.openContainer != null && player.openContainer instanceof IGuiMessageHandler)
            ((IGuiMessageHandler) player.openContainer).handleGuiWidget(message);
        return null;
    }

    @Override
    public String toString() {
        return String.format("MessageGuiWidget - guiId:%d type:%d id:%d d1:%d d2:%d",
                this.guiId, this.ctrlType, this.ctrlId, this.data1, this.data2);
    }
}
