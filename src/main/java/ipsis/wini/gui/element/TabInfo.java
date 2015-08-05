package ipsis.wini.gui.element;

import cofh.lib.gui.GuiBase;
import cofh.lib.util.helpers.StringHelper;
import ipsis.oss.util.TabScrolledText;
import ipsis.wini.reference.Lang;
import ipsis.wini.reference.Textures;

/**
 * This is based off CoFHCore/src/main/java/cofh/core/gui/element/TabInfo.java
 */
public class TabInfo extends TabScrolledText {

    public TabInfo(GuiBase gui, int side, String infoString) {
        super(gui, side, infoString);
        backgroundColor = Textures.Gui.INFO_TAB_BACKGROUND;
    }

    @Override
    public String getIcon() {
        return "Icon_Info";
    }

    @Override
    public String getTitle() {
        return StringHelper.localize(Lang.Gui.TAB_INFO);
    }
}
