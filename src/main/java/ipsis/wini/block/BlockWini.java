package ipsis.wini.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ipsis.wini.creativetab.CreativeTab;
import ipsis.wini.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

public abstract class BlockWini extends Block {

    public BlockWini(Material m) {
        super(m);
        this.setCreativeTab(CreativeTab.WINI_TAB);
        this.setHardness(3.5F);
    }

    @Override
    public String getUnlocalizedName()
    {
        /* Block is tile.<name> -> tile.<mod>:<name> */
        return String.format("tile.%s%s", Reference.MOD_ID + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        /* tile.<name> -> <name> */
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon(String.format("%s", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
    }
}
