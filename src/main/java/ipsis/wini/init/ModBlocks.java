package ipsis.wini.init;

import cpw.mods.fml.common.registry.GameRegistry;
import ipsis.wini.block.BlockCompacted;
import ipsis.wini.block.BlockHysteresis;
import ipsis.wini.block.BlockWini;
import ipsis.wini.item.ItemBlockCompacted;
import ipsis.wini.item.ItemBlockHysteresis;
import ipsis.wini.reference.Names;

public class ModBlocks {

    public static void preInit() {

        blockCompacted = new BlockCompacted();
        blockHysteresis = new BlockHysteresis();

        GameRegistry.registerBlock(blockCompacted, ItemBlockCompacted.class, Names.Blocks.BLOCK_COMPACTED);
        GameRegistry.registerBlock(blockHysteresis, ItemBlockHysteresis.class, Names.Blocks.BLOCK_HYSTERESIS);
    }

    public static void initialize() {

    }

    public static void postInit() {

    }

    public static BlockWini blockCompacted;
    public static BlockWini blockHysteresis;

}
