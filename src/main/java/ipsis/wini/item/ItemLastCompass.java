package ipsis.wini.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ipsis.wini.handler.TextureEventHandlers;
import ipsis.wini.reference.Names;
import net.minecraft.client.renderer.texture.IIconRegister;

public class ItemLastCompass extends ItemWini {

    public ItemLastCompass() {
        super();
        this.setUnlocalizedName(Names.Items.ITEM_LAST_COMPASS);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = TextureEventHandlers.lastCompass;
    }
}
