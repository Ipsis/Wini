package ipsis.wini.item;

import cofh.api.item.IInventoryContainerItem;
import cofh.lib.util.position.BlockPosition;
import ipsis.wini.Wini;
import ipsis.wini.reference.Gui;
import ipsis.wini.reference.Names;
import net.minecraft.block.Block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

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

        if (world.isRemote || itemStack == null || !itemStack.hasTagCompound())
            return itemStack;

        if (entityPlayer.isSneaking()) {
            openGui(itemStack, entityPlayer);
        } else {
            if (itemStack.stackTagCompound.hasKey("Slot0")) {
                ItemStack currStack = ItemStack.loadItemStackFromNBT(itemStack.stackTagCompound.getCompoundTag("Slot0"));
                if (currStack != null && currStack.stackSize > 0) {
                    placeInAir(currStack, world, entityPlayer);
                    if (currStack.stackSize == 0)
                        itemStack.stackTagCompound.setTag("Slot0", new NBTTagCompound());
                    else
                        itemStack.stackTagCompound.setTag("Slot0", currStack.writeToNBT(new NBTTagCompound()));
                }
            }
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

            /* dmg/count acts as vanilla onPlayerRightClick */
            int dmg = itemStack.getItemDamage();
            int count  = itemStack.stackSize;
            itemStack.tryPlaceItemIntoWorld(entityPlayer, world, pos.x, pos.y, pos.z, 0, 0.5F, 0.5F, 0.5F);
            if (entityPlayer.capabilities.isCreativeMode) {
                itemStack.setItemDamage(dmg);
                itemStack.stackSize = count;
            }
        }
    }

    public static BlockPosition getSelectedBlock(World world, EntityPlayer entityPlayer) {

        Vec3 lookVec = entityPlayer.getLookVec();

        /**
         * Forge forums
         * http://www.minecraftforge.net/forum/index.php?topic=29895.0
         * Yes, before 1.8 the server references entities by origin, the client references them by eyes.
         * You need to add the eye-height on the server
         */
        Vec3 playerVec;
        if (world.isRemote)
            playerVec = Vec3.createVectorHelper(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ);
        else
            playerVec = Vec3.createVectorHelper(entityPlayer.posX, entityPlayer.posY + entityPlayer.getEyeHeight(), entityPlayer.posZ);

        Vec3 targetVec = playerVec.addVector(lookVec.xCoord * 2, lookVec.yCoord * 2, lookVec.zCoord * 2);

        int x = MathHelper.floor_double(targetVec.xCoord);
        int y = MathHelper.floor_double(targetVec.yCoord);
        int z = MathHelper.floor_double(targetVec.zCoord);


        return new BlockPosition(x, y, z);
    }
}
