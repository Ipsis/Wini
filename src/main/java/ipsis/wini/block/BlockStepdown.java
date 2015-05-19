package ipsis.wini.block;

import cofh.lib.util.helpers.MathHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ipsis.wini.reference.Names;
import ipsis.wini.reference.Textures;
import ipsis.wini.tileentity.TileEntityStepdown;
import ipsis.wini.tileentity.TileEntityWini;
import ipsis.wini.utils.BlockHelper;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import javax.xml.soap.Text;

public class BlockStepdown extends BlockWini implements ITileEntityProvider {

    public BlockStepdown() {
        super(Material.ground);
        this.setBlockName(Names.Blocks.BLOCK_STEPDOWN);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
    }

    @SideOnly(Side.CLIENT)
    private IIcon inputIcon;
    private IIcon outputIcon;

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        blockIcon = iconRegister.registerIcon(Textures.RESOURCE_PREFIX + Names.Blocks.BLOCK_STEPDOWN + "_side");
        inputIcon = iconRegister.registerIcon(Textures.RESOURCE_PREFIX + Names.Blocks.BLOCK_STEPDOWN + "_input");
        outputIcon = iconRegister.registerIcon(Textures.RESOURCE_PREFIX + Names.Blocks.BLOCK_STEPDOWN + "_output");
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if (side == ForgeDirection.SOUTH.ordinal())
                return inputIcon;

        return blockIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess iblockaccess, int x, int y, int z, int side) {
        int meta = iblockaccess.getBlockMetadata(x, y, z);
        TileEntity te = iblockaccess.getTileEntity(x, y, z);
        if (te != null && te instanceof TileEntityWini) {
            TileEntityWini teh = (TileEntityWini)te;
            if (side == teh.getFacing().ordinal())
                return inputIcon;
            else if (side == teh.getFacing().getOpposite().ordinal())
                return outputIcon;
        }

        return blockIcon;
    }

    /**
     * ITileEntityProvider
     */
    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityStepdown();
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack) {
        if (!world.isRemote) {
            if (world.getTileEntity(x, y, z) instanceof TileEntityWini) {
                int d = BlockHelper.determineXYZPlaceFacing(world, x, y, z, entityLiving);
                TileEntityWini te = (TileEntityWini) world.getTileEntity(x, y, z);
                te.setFacing(ForgeDirection.getOrientation(d));
                world.markBlockForUpdate(x, y, z);
            }
        }
    }
}
