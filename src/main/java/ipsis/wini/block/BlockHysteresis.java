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

public class BlockHysteresis extends BlockWini {

    public static final int META_FLUID = 0;
    public static final int META_RF = 1;
    public static final int META_INVENTORY = 2;

    public BlockHysteresis() {
        super(Material.ground);
        this.setBlockName(Names.Blocks.BLOCK_HYSTERESIS);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
    }

    @SideOnly(Side.CLIENT)
    private IIcon connectedIcon;
    private IIcon unconnectedIcon;
    private IIcon activeIcon;
    private IIcon inactiveIcon;
    private IIcon[] icons;


    @Override
    public boolean isNormalCube() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.icons = new IIcon[Names.Blocks.BLOCK_HYSTERESIS_SUBSTYPES.length];

        for (int i = 0; i < Names.Blocks.BLOCK_HYSTERESIS_SUBSTYPES.length; i++)
            this.icons[i] = iconRegister.registerIcon(Textures.RESOURCE_PREFIX + Names.Blocks.BLOCK_HYSTERESIS + "_" + Names.Blocks.BLOCK_HYSTERESIS_SUBSTYPES[i]);

        connectedIcon = iconRegister.registerIcon(Textures.RESOURCE_PREFIX + Names.Blocks.BLOCK_HYSTERESIS + "_connected");
        unconnectedIcon = iconRegister.registerIcon(Textures.RESOURCE_PREFIX + Names.Blocks.BLOCK_HYSTERESIS + "_unconnected");
        activeIcon = iconRegister.registerIcon(Textures.RESOURCE_PREFIX + Names.Blocks.BLOCK_HYSTERESIS + "_active");
        inactiveIcon = iconRegister.registerIcon(Textures.RESOURCE_PREFIX + Names.Blocks.BLOCK_HYSTERESIS + "_inactive");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        meta = MathHelper.clampI(meta, 0, Names.Blocks.BLOCK_HYSTERESIS_SUBSTYPES.length - 1);
        return this.icons[meta];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {

        for (int i = 0; i < Names.Blocks.BLOCK_HYSTERESIS_SUBSTYPES.length; i++)
            list.add(new ItemStack(item, 1, i));
    }

    @Override
    public int damageDropped(int meta) {
        return MathHelper.clampI(meta, 0, Names.Blocks.BLOCK_HYSTERESIS_SUBSTYPES.length - 1);
    }
}
