package ipsis.wini.item;

import cofh.lib.util.helpers.StringHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ipsis.wini.creativetab.CreativeTab;
import ipsis.wini.reference.Reference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public abstract class ItemWini extends Item {

    private String tooltip;

    public ItemWini()
    {
        super();
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTab.WINI_TAB);
        this.setNoRepair();
        this.tooltip = null;
    }

    protected void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    private boolean hasTooltip() { return this.tooltip != null; }

    @Override
    public String getUnlocalizedName()
    {
        /* Item item.<name> -> item.<mod>:<name> */
        return String.format("item.%s%s", Reference.MOD_ID + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        /* Item item.<name> -> item.<mod>:<name> */
        return String.format("item.%s%s", Reference.MOD_ID + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        /* item.<name> -> <name> */
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        itemIcon = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".") + 1));
    }

    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean showAdvanced) {
        super.addInformation(itemStack, player, info, showAdvanced);

        if (hasTooltip())
            info.add(StringHelper.localize(this.tooltip));
    }
}
