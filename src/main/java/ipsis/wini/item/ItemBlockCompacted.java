package ipsis.wini.item;

import ipsis.wini.init.ModBlocks;
import ipsis.wini.reference.Names;
import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;

public class ItemBlockCompacted extends ItemMultiTexture {

    public ItemBlockCompacted(Block b) {
        super(ModBlocks.blockCompacted, ModBlocks.blockCompacted, Names.Blocks.BLOCK_COMPACTED_SUBTYPES);
    }

}
