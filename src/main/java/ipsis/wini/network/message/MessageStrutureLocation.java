package ipsis.wini.network.message;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import ipsis.oss.util.LogHelper;
import ipsis.wini.registry.LocationRegistry;
import net.minecraft.entity.player.EntityPlayer;

public class MessageStrutureLocation implements IMessage, IMessageHandler<MessageStrutureLocation, IMessage> {

    public int posX;
    public int posY;
    public int posZ;

    public MessageStrutureLocation() { }

    public MessageStrutureLocation(int x, int y, int z) {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.posX = buf.readInt();
        this.posY = buf.readInt();
        this.posZ = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.posX);
        buf.writeInt(this.posY);
        buf.writeInt(this.posZ);
    }

    @Override
    public IMessage onMessage(MessageStrutureLocation message, MessageContext ctx) {

        LogHelper.info("onMessage: " + message.posX + " " + message.posY + " " + message.posZ);
        LocationRegistry.getInstance().addLocation(message.posX, message.posY, message.posZ);
        return null;
    }
}
