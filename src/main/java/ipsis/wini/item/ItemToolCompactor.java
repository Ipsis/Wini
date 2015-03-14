package ipsis.wini.item;

import cofh.lib.util.position.BlockPosition;
import ipsis.wini.block.BlockCompacted;
import ipsis.wini.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;

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
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {

        if (world.isRemote || player.isSneaking())
            return false;

        if (!player.canPlayerEdit(x, y, z, side, stack))
            return true;

        int count = compactBlocks(world, x, y, z, side);
        if (count > 0)
            stack.damageItem(1, player);
        return true;
    }

    private int compactBlocks(World world, int x, int y, int z, int side) {

        /* Check a 3x3x2 area centered on the hit block */
        ForgeDirection dir = ForgeDirection.getOrientation(side).getOpposite();

        BlockPosition pos = new BlockPosition(x, y, z, dir);
        Block originBlock = world.getBlock(x, y, z);

        List<BlockPosition> blocks = new ArrayList<BlockPosition>();
        for (int i = 0; i < 2; i++) {
            pos.moveForwards(i);

            blocks.add(new BlockPosition(pos));
            blocks.add(new BlockPosition(pos).moveLeft(1));
            blocks.add(new BlockPosition(pos).moveLeft(1).moveUp(1));
            blocks.add(new BlockPosition(pos).moveLeft(1).moveDown(1));
            blocks.add(new BlockPosition(pos).moveUp(1));
            blocks.add(new BlockPosition(pos).moveDown(1));
            blocks.add(new BlockPosition(pos).moveRight(1));
            blocks.add(new BlockPosition(pos).moveRight(1).moveUp(1));
            blocks.add(new BlockPosition(pos).moveRight(1).moveDown(1));
        }

        int count = 0;
        for (BlockPosition blockPos : blocks) {
            count += (compactBlock(world, blockPos) == true ? 1 : 0);
        }

        if (count > 0) {
            /* We changed blocks so make one sound, based off the block that was clicked */
            world.playSoundEffect((double) ((float) x + 0.5F), (double) ((float) y + 0.5F), (double) ((float) z + 0.5F),
                    originBlock.stepSound.func_150496_b(), (originBlock.stepSound.getVolume() + 1.0F) / 2.0F, originBlock.stepSound.getPitch() * 0.8F);

        }

        return count;
    }

    private boolean compactBlock(World world, BlockPosition p) {

        int newmeta = 0;
        boolean changed = false;
        Block b = p.getBlock(world);

        if (b == Blocks.gravel) {
            newmeta = BlockCompacted.META_GRAVEL;
            changed = true;
        } else if (b == Blocks.sand) {
            newmeta = (world.getBlockMetadata(p.x, p.y, p.z) == 0 ? BlockCompacted.META_SAND : BlockCompacted.META_RED_SAND);
            changed = true;
        }

        if (changed == true) {
            world.setBlock(p.x, p.y, p.z, ModBlocks.blockCompacted, newmeta, 3);
        }

        return changed;
    }
}
