package ipsis.wini.gui;

import cofh.lib.gui.GuiBase;
import cofh.lib.util.helpers.StringHelper;
import ipsis.oss.util.IconRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.inventory.Container;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public class GuiBaseWini extends GuiBase {

    public GuiBaseWini(Container container, ResourceLocation resourceLocation) {
        super(container, resourceLocation);
    }

    @Override
    public IIcon getIcon(String s) {
        /**
        * This must NEVER return null, as the icon rendering is passed straight to the the
        * drawTexturedModelRectFromIcon function with no checking for the icon being null.
        * The vanilla path uses getIconSafe to turn a null IIcon into
        * the missing icon.
        * So we replicate that here.
        *
        * Of course you should never be calling the getIcon with a string that maps to no icon.
        * But this should prevent an actual rendering crash
        */
        IIcon icon = IconRegistry.getIcon(s);
        if (icon == null)
            icon = ((TextureMap) Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.locationBlocksTexture)).getAtlasSprite("missingno");
        return icon;
    }

    protected String buildInfoString(String baseTag) {
        StringBuilder infoString = new StringBuilder();
        if (!StringHelper.localize(baseTag).equals(baseTag)) {
        /* Only have one line */
            infoString.append(StringHelper.localize(baseTag));
        } else {
            int idx = 0;
            String tag = baseTag + "." + idx;
            String t = StringHelper.localize(baseTag + "." + idx);
            while (!t.equals(tag)) {
                infoString.append(t).append(" ");
                idx++;
                tag = baseTag + "." + idx;
                t = StringHelper.localize(tag);
                /* Someone might think they are funny and write a tome! */
                if (idx > 15)
                    break;
            }
        }
        return infoString.toString();
    }

    /**
     * From CoFHCore GuiBaseAdv
     */
    @Override
    public void drawButton(IIcon icon, int x, int y, int spriteSheet, int mode) {

        switch (mode) {
            case 0:
                drawIcon(getIcon("Icon_Button"), x, y, 1);
                break;
            case 1:
                drawIcon(getIcon("Icon_ButtonHighlight"), x, y, 1);
                break;
            default:
                drawIcon(getIcon("Icon_ButtonInactive"), x, y, 1);
                break;
        }
        drawIcon(icon, x, y, spriteSheet);
    }
}
