package ipsis.wini.registry;

import cofh.lib.util.position.BlockPosition;
import ipsis.wini.block.BlockCompacted;
import ipsis.wini.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class CompactorRegistry {

    public static boolean canCompactBlock(Block b) {
        return b != null && (b == Blocks.sand || b == Blocks.gravel);
    }

    public static boolean compactBlock(World world, BlockPosition pos) {

        int newmeta = 0;
        boolean changed = false;
        Block b = pos.getBlock(world);

        if (b == Blocks.gravel) {
            newmeta = BlockCompacted.META_GRAVEL;
            changed = true;
        } else if (b == Blocks.sand) {
            newmeta = (world.getBlockMetadata(pos.x, pos.y, pos.z) == 0 ? BlockCompacted.META_SAND : BlockCompacted.META_RED_SAND);
            changed = true;
        }

        if (changed == true)
            world.setBlock(pos.x, pos.y, pos.z, ModBlocks.blockCompacted, newmeta, 3);

        return changed;
    }
}
