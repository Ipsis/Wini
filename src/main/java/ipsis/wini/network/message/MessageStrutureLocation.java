package ipsis.wini.network.message;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import ipsis.wini.registry.LocationRegistry;

public class MessageStrutureLocation implements IMessage, IMessageHandler<MessageStrutureLocation, IMessage> {

    public int posX;
    public int posY;
    public int posZ;
    public boolean valid;

    public MessageStrutureLocation() { }

    public MessageStrutureLocation(int x, int y, int z, boolean valid) {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.valid = valid;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.posX = buf.readInt();
        this.posY = buf.readInt();
        this.posZ = buf.readInt();
        this.valid = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.posX);
        buf.writeInt(this.posY);
        buf.writeInt(this.posZ);
        buf.writeBoolean(this.valid);
    }

    @Override
    public IMessage onMessage(MessageStrutureLocation message, MessageContext ctx) {

        if (message.valid)
            LocationRegistry.getInstance().setLocation(message.posX, message.posY, message.posZ, message.valid);
        else
            LocationRegistry.getInstance().clearLocation();
        return null;
    }
}
