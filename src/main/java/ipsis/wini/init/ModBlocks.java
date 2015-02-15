package ipsis.wini.init;

import com.mojang.realmsclient.gui.RealmsGenericErrorScreen;
import cpw.mods.fml.common.registry.GameRegistry;
import ipsis.wini.block.BlockPortachant;
import ipsis.wini.reference.Names;
import ipsis.wini.reference.Reference;

public class ModBlocks {

    public static void preInit() {

        blockPortachant = new BlockPortachant();

        GameRegistry.registerBlock(blockPortachant, Names.Blocks.BLOCK_PORTACHANT);
    }

    public static void initialize() {

    }

    public static void postInit() {

    }

    public static BlockPortachant blockPortachant;
}
