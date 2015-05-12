package ipsis.wini.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import ipsis.wini.network.message.*;
import ipsis.wini.reference.Reference;

public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID.toLowerCase());

    public static void init() {

        INSTANCE.registerMessage(MessageGuiWidget.class, MessageGuiWidget.class, 0, Side.SERVER);
        INSTANCE.registerMessage(MessageGuiFixedProgressBar.class, MessageGuiFixedProgressBar.class, 1, Side.CLIENT);
        INSTANCE.registerMessage(MessageTileEntityWini.class, MessageTileEntityWini.class, 2, Side.CLIENT);
        INSTANCE.registerMessage(MessageHysteresisCfg.class, MessageHysteresisCfg.class, 3, Side.SERVER);
        INSTANCE.registerMessage(MessageHysteresisCfg.class, MessageHysteresisCfg.class, 4, Side.CLIENT);
        INSTANCE.registerMessage(MessageRedstoneOutputCfg.class, MessageRedstoneOutputCfg.class, 5, Side.SERVER);
        INSTANCE.registerMessage(MessageRedstoneOutputCfg.class, MessageRedstoneOutputCfg.class, 6, Side.CLIENT);
    }
}
