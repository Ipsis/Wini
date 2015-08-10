package ipsis.wini.registry;

import cofh.lib.util.position.BlockPosition;
import ipsis.wini.block.BlockCompacted;
import ipsis.wini.init.ModBlocks;
import ipsis.wini.init.ModItems;
import ipsis.wini.item.ItemToolCompactor;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
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

    public static int getDamageDropped(int type, int meta) {
        int dmg;

        if (type == 0) {
            dmg = meta;
        } else {
            if (meta == BlockCompacted.META_SAND)
                dmg = 0;
            else if (meta == BlockCompacted.META_RED_SAND)
                dmg = 1;
            else
                dmg = 0;
        }
        return dmg;
    }

    public static Item getItemDropped(int type, int meta) {

        Item item = null;
        if (type == 0) {
            item = Item.getItemFromBlock(ModBlocks.blockCompacted);
        } else {
            if (meta == BlockCompacted.META_SAND || meta == BlockCompacted.META_RED_SAND)
                item = Item.getItemFromBlock(Blocks.sand);
            else
                item = Item.getItemFromBlock(Blocks.gravel);
        }
        return item;
    }



}
