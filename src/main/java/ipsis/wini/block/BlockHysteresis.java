package ipsis.wini.block;

import cofh.lib.util.helpers.MathHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ipsis.oss.util.LogHelper;
import ipsis.wini.helper.MonitorType;
import ipsis.wini.reference.Names;
import ipsis.wini.reference.Textures;
import ipsis.wini.tileentity.*;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class BlockHysteresis extends BlockWini implements ITileEntityProvider {

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
    public IIcon getIcon(IBlockAccess iblockaccess, int x, int y, int z, int side) {

        int meta = iblockaccess.getBlockMetadata(x, y, z);
        TileEntity te = iblockaccess.getTileEntity(x, y, z);
        if (te != null && te instanceof TileEntityHysteresis) {
            TileEntityHysteresis teh = (TileEntityHysteresis)te;
            if (side == teh.getFacing().ordinal()) {
                if (teh.isStateConnected())
                    return connectedIcon;
                else
                    return unconnectedIcon;
            } else if (side == teh.getStateOutputFace().ordinal()) {
                if (teh.isStateEmittingRedstone())
                    return activeIcon;
                else
                    return inactiveIcon;
            }
        }

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

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack) {
        if (!world.isRemote) {
            if (world.getTileEntity(x, y, z) instanceof TileEntityWini) {

                /* 6 way placement of block */
                int d = BlockPistonBase.determineOrientation(world, x, y, z, entityLiving);
                TileEntityHysteresis te = (TileEntityHysteresis) world.getTileEntity(x, y, z);
                te.setFacing(ForgeDirection.getOrientation(d));
                te.setRedstoneOutputFace(ForgeDirection.getOrientation(d).getOpposite());
                world.markBlockForUpdate(x, y, z);
            }
        }
    }

    /**
     * Rotation
     */
    @Override
    public boolean rotateBlock(World worldObj, int x, int y, int z, ForgeDirection axis) {

        if (!worldObj.isRemote) {
            TileEntity te = worldObj.getTileEntity(x, y, z);
            if (te instanceof TileEntityHysteresis)
                ((TileEntityHysteresis) te).rotateBlock(axis);
        }

        return true;
    }

    /**
     * ITileEntityProvider
     */
    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {

        if (metadata == MonitorType.FLUID.ordinal())
            return new TileEntityHysteresisFluid();
        else if (metadata == MonitorType.RF.ordinal())
            return new TileEntityHysteresisRf();
        else if (metadata == MonitorType.INVENTORY.ordinal())
            return new TileEntityHysteresisInventory();
        else
            return null;
    }

    /**
     * Redstone
     */
    @Override
    public boolean canProvidePower() {
        return true;
    }

    @Override
    public boolean canConnectRedstone(IBlockAccess iblockaccess, int x, int y, int z, int side) {
        boolean canConnect = false;
        TileEntity te = iblockaccess.getTileEntity(x, y, z);
        if (te != null && te instanceof TileEntityHysteresis)
            canConnect = ((TileEntityHysteresis) te).isRedstoneOutputFace(ForgeDirection.getOrientation(side));

        return canConnect;
    }

    @Override
    public int isProvidingStrongPower(IBlockAccess iblockaccess, int x, int y, int z, int side) {
        int power = 0;
        TileEntity te = iblockaccess.getTileEntity(x, y, z);
        if (te != null && te instanceof TileEntityHysteresis)
                power = (((TileEntityHysteresis) te).isEmittingStrongRedstoneSignal() ? ((TileEntityHysteresis) te).getCurrentRedstoneLevel() : 0);

        return power;
    }

    @Override
    public int isProvidingWeakPower(IBlockAccess iblockaccess, int x, int y, int z, int side) {
        int power = 0;
        TileEntity te = iblockaccess.getTileEntity(x, y, z);
        if (te != null && te instanceof TileEntityHysteresis)
                power = (((TileEntityHysteresis) te).isEmittingWeakRedstoneSignal() ? ((TileEntityHysteresis) te).getCurrentRedstoneLevel() : 0);

        return power;
    }
}
