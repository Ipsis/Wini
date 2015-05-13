package ipsis.wini.proxy;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ipsis.oss.util.IconRegistry;
import net.minecraftforge.client.event.TextureStitchEvent;

public class ClientProxy extends CommonProxy {

    @SubscribeEvent
    public void registerIcons(TextureStitchEvent.Pre event) {

        if (event.map.getTextureType() == 1) {
            /* Register our gui icons */
            IconRegistry.addIcon("Icon_Energy", event.map.registerIcon("wini:icons/Icon_Energy"));
            IconRegistry.addIcon("Icon_Info", event.map.registerIcon("wini:icons/Icon_Info"));
            IconRegistry.addIcon("Icon_Tank", event.map.registerIcon("wini:icons/Icon_Tank"));
            IconRegistry.addIcon("Icon_Redstone", event.map.registerIcon("wini:icons/Icon_Tank"));

            IconRegistry.addIcon("Icon_Up_Active", event.map.registerIcon("wini:icons/Icon_Up_Active"));
            IconRegistry.addIcon("Icon_Up_Inactive", event.map.registerIcon("wini:icons/Icon_Up_Inactive"));
            IconRegistry.addIcon("Icon_Dn_Active", event.map.registerIcon("wini:icons/Icon_Dn_Active"));
            IconRegistry.addIcon("Icon_Dn_Inactive", event.map.registerIcon("wini:icons/Icon_Dn_Inactive"));

            IconRegistry.addIcon("Icon_Dec", event.map.registerIcon("wini:icons/Icon_Dec"));
            IconRegistry.addIcon("Icon_Inc", event.map.registerIcon("wini:icons/Icon_Inc"));
            IconRegistry.addIcon("Icon_RS_Strong", event.map.registerIcon("wini:icons/Icon_RS_Strong"));
            IconRegistry.addIcon("Icon_RS_Weak", event.map.registerIcon("wini:icons/Icon_RS_Weak"));
            IconRegistry.addIcon("Icon_RS_Normal", event.map.registerIcon("wini:icons/Icon_RS_Normal"));
            IconRegistry.addIcon("Icon_RS_Invert", event.map.registerIcon("wini:icons/Icon_RS_Invert"));

            IconRegistry.addIcon("Icon_Button", event.map.registerIcon("wini:icons/Icon_Button"));
            IconRegistry.addIcon("Icon_ButtonHighlight", event.map.registerIcon("wini:icons/Icon_ButtonHighlight"));
            IconRegistry.addIcon("Icon_ButtonInactive", event.map.registerIcon("wini:icons/Icon_ButtonInactive"));
        }
    }
}
