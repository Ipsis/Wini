package ipsis.wini.item;

import ipsis.wini.init.ModBlocks;
import ipsis.wini.reference.Names;
import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;

public class ItemBlockHysteresis extends ItemMultiTexture {

    public ItemBlockHysteresis(Block b) {
        super(ModBlocks.blockHysteresis, ModBlocks.blockHysteresis, Names.Blocks.BLOCK_HYSTERESIS_SUBSTYPES);
    }
}
