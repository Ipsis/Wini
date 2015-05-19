package ipsis.wini.init;

import cpw.mods.fml.common.registry.GameRegistry;
import ipsis.wini.reference.Names;
import ipsis.wini.tileentity.TileEntityHysteresisFluid;
import ipsis.wini.tileentity.TileEntityHysteresisInventory;
import ipsis.wini.tileentity.TileEntityHysteresisRf;
import ipsis.wini.tileentity.TileEntityStepdown;

public class ModTileEntities {

    public static void preInit() {

    }

    public static void initialize() {
        GameRegistry.registerTileEntity(TileEntityHysteresisFluid.class, "tile." + Names.Blocks.BLOCK_HYSTERESIS + "Fluid");
        GameRegistry.registerTileEntity(TileEntityHysteresisInventory.class, "tile." + Names.Blocks.BLOCK_HYSTERESIS + "Inv");
        GameRegistry.registerTileEntity(TileEntityHysteresisRf.class, "tile." + Names.Blocks.BLOCK_HYSTERESIS + "Rf");
        GameRegistry.registerTileEntity(TileEntityStepdown.class, "tile." + Names.Blocks.BLOCK_STEPDOWN);
    }

    public static void postInit() {

    }
}

