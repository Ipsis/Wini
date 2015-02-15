package ipsis.wini.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ipsis.wini.reference.Names;
import ipsis.wini.reference.Textures;
import ipsis.wini.tileentity.TEPortachant;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * This is an extension of the vanilla Enchantment Table
 */
public class BlockPortachant extends BlockContainerWini {

    public BlockPortachant() {
        super(Material.rock);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
        this.setLightOpacity(0);
        this.setHardness(5.0F);
        this.setResistance(2000.0F);
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {
        return new TEPortachant();
    }

    /**
     * Icons
     */
    @SideOnly(Side.CLIENT)
    IIcon sideIcon;
    IIcon topIcon;
    IIcon bottomIcon;

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister ir) {

        sideIcon = ir.registerIcon(Textures.RESOURCE_PREFIX + Names.Blocks.BLOCK_PORTACHANT + ".Side");
        topIcon = ir.registerIcon(Textures.RESOURCE_PREFIX + Names.Blocks.BLOCK_PORTACHANT + ".Top");
        bottomIcon = ir.registerIcon(Textures.RESOURCE_PREFIX + Names.Blocks.BLOCK_PORTACHANT + ".Bottom");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta) {

        if (side == ForgeDirection.UP.ordinal())
            return topIcon;
        else if (side == ForgeDirection.DOWN.ordinal())
            return bottomIcon;
        else
            return sideIcon;
    }
}
