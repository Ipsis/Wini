package ipsis.wini.item;

import cofh.lib.util.position.BlockPosition;
import ipsis.wini.registry.CompactorRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;

/**
 * Damage
 *
 * itemDamage = 0 (undamaged)
 * itemDamage = maxDamage (fully damaged)
 *
 * durability bar displays (default) itemDamageForDisplay/getMaxDamage
 * durability displays the "inverse" of the damage value
 */

public class ItemToolCompactor extends ItemWini {

    protected Item.ToolMaterial material;

    public ItemToolCompactor(Item.ToolMaterial m, String name) {

        this.material = m;
        this.maxStackSize = 1;
        this.setMaxDamage(m.getMaxUses());

        this.setUnlocalizedName(name);
        this.setNoRepair();
    }

    @Override
    public boolean getIsRepairable(ItemStack p_82789_1_, ItemStack p_82789_2_) {
        return false;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {

        if (world.isRemote || player.isSneaking())
            return false;

        if (!player.canPlayerEdit(x, y, z, side, stack))
            return true;

        /* Check a 3x3x2 area centered on the hit block */
        ForgeDirection dir = ForgeDirection.getOrientation(side).getOpposite();

        BlockPosition pos = new BlockPosition(x, y, z, dir);
        Block originBlock = world.getBlock(x, y, z);
        List<BlockPosition> blockList = getBlockList(pos);

        int cost = CompactorRegistry.canCompactBlock(originBlock) ? 1 : 2;
        int usesLeft = stack.getMaxDamage() - stack.getItemDamage();
        if (player.capabilities.isCreativeMode || cost <= usesLeft) {
            int count = compactBlocks(world, blockList);
            if (count > 0) {
                /* We changed blocks so make one sound, based off the block that was clicked */
                world.playSoundEffect((double) ((float) x + 0.5F), (double) ((float) y + 0.5F), (double) ((float) z + 0.5F),
                        originBlock.stepSound.func_150496_b(), (originBlock.stepSound.getVolume() + 1.0F) / 2.0F, originBlock.stepSound.getPitch() * 0.8F);
                stack.damageItem(cost, player);
            }
        }

        return true;
    }

    private int compactBlocks(World world, List<BlockPosition> blockList) {

        int count = 0;
        for (BlockPosition blockPos : blockList)
            count += (CompactorRegistry.compactBlock(world, blockPos) == true ? 1 : 0);

        return count;
    }

    private List<BlockPosition> getBlockList(BlockPosition origin) {

        List<BlockPosition> blocks = new ArrayList<BlockPosition>();
        for (int i = 0; i < 2; i++) {
            origin.moveForwards(i);

            blocks.add(new BlockPosition(origin));
            blocks.add(new BlockPosition(origin).moveLeft(1));
            blocks.add(new BlockPosition(origin).moveLeft(1).moveUp(1));
            blocks.add(new BlockPosition(origin).moveLeft(1).moveDown(1));
            blocks.add(new BlockPosition(origin).moveUp(1));
            blocks.add(new BlockPosition(origin).moveDown(1));
            blocks.add(new BlockPosition(origin).moveRight(1));
            blocks.add(new BlockPosition(origin).moveRight(1).moveUp(1));
            blocks.add(new BlockPosition(origin).moveRight(1).moveDown(1));
        }

        return blocks;
    }
}
