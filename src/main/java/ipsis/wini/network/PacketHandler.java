package ipsis.wini.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import ipsis.wini.network.message.MessageGuiFixedProgressBar;
import ipsis.wini.network.message.MessageGuiWidget;
import ipsis.wini.network.message.MessageTileEntityWini;
import ipsis.wini.reference.Reference;

public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID.toLowerCase());

    public static void init() {

        INSTANCE.registerMessage(MessageGuiWidget.class, MessageGuiWidget.class, 0, Side.SERVER);
        INSTANCE.registerMessage(MessageGuiFixedProgressBar.class, MessageGuiFixedProgressBar.class, 1, Side.CLIENT);
        INSTANCE.registerMessage(MessageTileEntityWini.class, MessageTileEntityWini.class, 2, Side.CLIENT);
    }
}
