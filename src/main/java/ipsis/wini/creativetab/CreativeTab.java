package ipsis.wini.creativetab;

import cofh.lib.util.helpers.StringHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ipsis.wini.init.ModItems;
import ipsis.wini.reference.Lang;
import ipsis.wini.reference.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTab {

    public static final CreativeTabs WINI_TAB = new CreativeTabs(Reference.MOD_ID) {

        @Override
        public Item getTabIconItem() {

            return ModItems.itemVoidBagBig;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public String getTranslatedTabLabel() {

            return StringHelper.localize(Lang.TAG_TAB);
        }
    };
}
