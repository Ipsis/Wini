package ipsis.wini.block;

import cofh.lib.util.helpers.MathHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ipsis.wini.reference.Names;
import ipsis.wini.reference.Textures;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;
import java.util.Random;

public class BlockCompacted extends BlockWini {

    public static final int META_SAND = 0;
    public static final int META_GRAVEL = 1;
    public static final int META_RED_SAND = 2;

    public BlockCompacted() {
        super(Material.ground);
        this.setBlockName(Names.Blocks.BLOCK_COMPACTED);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
    }

    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.icons = new IIcon[Names.Blocks.BLOCK_COMPACTED_SUBTYPES.length];

        for (int i = 0; i < Names.Blocks.BLOCK_COMPACTED_SUBTYPES.length; i++)
            this.icons[i] = iconRegister.registerIcon(Textures.RESOURCE_PREFIX + Names.Blocks.BLOCK_COMPACTED + "_" + Names.Blocks.BLOCK_COMPACTED_SUBTYPES[i]);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        meta = MathHelper.clampI(meta, 0, Names.Blocks.BLOCK_COMPACTED_SUBTYPES.length - 1);
        return this.icons[meta];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {

        for (int i = 0; i < Names.Blocks.BLOCK_COMPACTED_SUBTYPES.length; i++)
            list.add(new ItemStack(item, 1, i));
    }

    @Override
    public int damageDropped(int meta) {
        return MathHelper.clampI(meta, 0, Names.Blocks.BLOCK_COMPACTED_SUBTYPES.length - 1);
    }

    @Override
    public int quantityDropped(Random p_149745_1_) {
        return 0;
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        /* This doesn't drop anything */
        return null;
    }
}
