package ipsis.wini.item;

import cofh.lib.util.position.BlockPosition;
import ipsis.oss.util.LogHelper;
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

        return count;
    }

    private boolean compactBlock(World world, BlockPosition p) {

        boolean changed = true;
        Block b = p.getBlock(world);
        if (isGravel((b)))
            world.setBlock(p.x, p.y, p.z, ModBlocks.blockCompacted, 1, 3);
        else if (isSand((b)))
            world.setBlock(p.x, p.y, p.z, ModBlocks.blockCompacted, 0, 3);
        else
            changed = false;

        return changed;
    }

    private boolean isGravel(Block b) {
        return b == Blocks.gravel;
    }

    private boolean isSand(Block b) {
        return b == Blocks.sand;
    }

    private boolean canCompact(Block b) {
        return (b != null && (isGravel(b) || isSand(b)));
    }

}
