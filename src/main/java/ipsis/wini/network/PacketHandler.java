package ipsis.wini.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import ipsis.wini.network.message.*;
import ipsis.wini.reference.Reference;

public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID.toLowerCase());

    private enum ModMessage {
        GUI_WIDGET(MessageGuiWidget.class, Side.SERVER),
        GUI_FIXED_PROGRESS_BASE(MessageGuiFixedProgressBar.class, Side.CLIENT),
        TE_DESCRIPTION(MessageTileEntityWini.class, Side.CLIENT),
        HYST_CFG(MessageHysteresisCfg.class, Side.SERVER),
        RS_OUTPUT_CFG(MessageRedstoneOutputCfg.class, Side.SERVER),
        HYST_UPDATE(MessageHysteresisUpdate.class, Side.CLIENT);

        private Class clazz;
        private Side side;
        ModMessage(Class clazz, Side side) {
            this.clazz = clazz;
            this.side = side;
        }

        public Class getClazz() { return this.clazz; }
        public Side getSide() { return this.side; }

    }

    public static void init() {

        for (ModMessage m : ModMessage.values())
            INSTANCE.registerMessage(m.getClazz(), m.getClazz(), m.ordinal(), m.getSide());
    }

}
