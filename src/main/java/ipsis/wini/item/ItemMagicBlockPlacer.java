package ipsis.wini.item;

import cofh.api.item.IInventoryContainerItem;
import cofh.lib.util.position.BlockPosition;
import ipsis.oss.util.LogHelper;
import ipsis.wini.Wini;
import ipsis.wini.reference.Gui;
import ipsis.wini.reference.Names;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import sun.rmi.runtime.Log;

public class ItemMagicBlockPlacer extends ItemWini implements IInventoryContainerItem {

    public ItemMagicBlockPlacer() {
        super();
        this.setMaxDamage(0);
        this.setUnlocalizedName(Names.Items.ITEM_MAGIC_BLOCK_PLACER);
    }

    private void openGui(ItemStack itemStack, EntityPlayer entityPlayer) {
        entityPlayer.openGui(Wini.instance, Gui.Ids.MAGIC_BLOCK_PLACER, entityPlayer.worldObj,
                (int)entityPlayer.posX, (int)entityPlayer.posY, (int)entityPlayer.posZ);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {

        if (world.isRemote)
            return itemStack;

        if (entityPlayer.isSneaking()) {
            openGui(itemStack, entityPlayer);
        } else {
            placeInAir(itemStack, world, entityPlayer);
        }

        return itemStack;
    }


    @Override
    public int getSizeInventory(ItemStack itemStack) {
        return 1;
    }

    private void placeInAir(ItemStack itemStack, World world, EntityPlayer entityPlayer) {

        if (world.isRemote)
            return;

        BlockPosition pos = getSelectedBlock(world, entityPlayer);
        Block block = world.getBlock(pos.x, pos.y, pos.z);

        if (block == Blocks.air) {
            world.setBlock(pos.x, pos.y, pos.z, Blocks.cobblestone);
            if (!entityPlayer.capabilities.isCreativeMode)
                --itemStack.stackSize;
        }
    }

    public static BlockPosition getSelectedBlock(World world, EntityPlayer entityPlayer) {

        Vec3 lookVec = entityPlayer.getLookVec();
        Vec3 playerVec = Vec3.createVectorHelper(entityPlayer.posX, entityPlayer.posY + (entityPlayer.getEyeHeight() - entityPlayer.getDefaultEyeHeight()), entityPlayer.posZ);
        Vec3 targetVec = playerVec.addVector(lookVec.xCoord * 2, lookVec.yCoord * 2, lookVec.zCoord * 2);

        int x = MathHelper.floor_double(targetVec.xCoord);
        int y = MathHelper.floor_double(targetVec.yCoord) + 1;
        int z = MathHelper.floor_double(targetVec.zCoord);

        return new BlockPosition(x, y, z);
    }
}
