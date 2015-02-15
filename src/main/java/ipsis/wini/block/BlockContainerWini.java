package ipsis.wini.block;

import ipsis.wini.creativetab.CreativeTab;
import ipsis.wini.reference.Reference;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;

public abstract class BlockContainerWini extends BlockContainer {

    public BlockContainerWini(Material m) {
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
}
