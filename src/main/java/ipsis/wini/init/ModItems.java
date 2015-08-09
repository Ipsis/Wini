package ipsis.wini.init;

import cpw.mods.fml.common.registry.GameRegistry;
import ipsis.wini.item.*;
import ipsis.wini.reference.Names;
import net.minecraft.item.Item;

public class ModItems {

    public static void preInit() {

        itemCompactorStone = new ItemToolCompactor(Item.ToolMaterial.STONE, Names.Items.ITEM_COMPACTOR + "_" + Names.Items.ITEM_COMPACTOR_SUBTYPES[0]);
        itemCompactorIron = new ItemToolCompactor(Item.ToolMaterial.IRON, Names.Items.ITEM_COMPACTOR + "_" + Names.Items.ITEM_COMPACTOR_SUBTYPES[1]);
        itemCompactorDiamond = new ItemToolCompactor(Item.ToolMaterial.EMERALD, Names.Items.ITEM_COMPACTOR + "_" + Names.Items.ITEM_COMPACTOR_SUBTYPES[2]);
        /* Emerald is diamond! */

        itemTorchPouch = new ItemTorchPouch();
        itemVoidBag = new ItemVoidBag(ItemVoidBag.BagSize.SMALL);
        itemVoidBagBig = new ItemVoidBag(ItemVoidBag.BagSize.LARGE);
        itemMagicBlockPlacer = new ItemMagicBlockPlacer();

        GameRegistry.registerItem(itemCompactorStone, "item." + Names.Items.ITEM_COMPACTOR + "_" + Names.Items.ITEM_COMPACTOR_SUBTYPES[0]);
        GameRegistry.registerItem(itemCompactorIron, "item." + Names.Items.ITEM_COMPACTOR + "_" + Names.Items.ITEM_COMPACTOR_SUBTYPES[1]);
        GameRegistry.registerItem(itemCompactorDiamond, "item." + Names.Items.ITEM_COMPACTOR + "_" + Names.Items.ITEM_COMPACTOR_SUBTYPES[2]);

        GameRegistry.registerItem(itemTorchPouch, "item." + Names.Items.ITEM_TORCH_POUCH);
        GameRegistry.registerItem(itemVoidBag, "item." + Names.Items.ITEM_VOID_BAG);
        GameRegistry.registerItem(itemVoidBagBig, "item." + Names.Items.ITEM_VOID_BAG_BIG);
        GameRegistry.registerItem(itemMagicBlockPlacer, "item." + Names.Items.ITEM_MAGIC_BLOCK_PLACER);
    }

    public static void initialize() {

    }

    public static void postInit() {

    }

    public static ItemWini itemCompactorStone;
    public static ItemWini itemCompactorIron;
    public static ItemWini itemCompactorDiamond;
    public static ItemWini itemTorchPouch;
    public static ItemWini itemVoidBag;
    public static ItemWini itemVoidBagBig;
    public static ItemWini itemMagicBlockPlacer;
}
