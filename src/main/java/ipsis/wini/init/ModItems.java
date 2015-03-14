package ipsis.wini.init;

import cpw.mods.fml.common.registry.GameRegistry;
import ipsis.wini.item.ItemToolCompactor;
import ipsis.wini.item.ItemWini;
import ipsis.wini.reference.Names;
import net.minecraft.item.Item;

public class ModItems {

    public static void preInit() {

        itemCompactorStone = new ItemToolCompactor(Item.ToolMaterial.STONE, Names.Items.ITEM_COMPACTOR + "_" + Names.Items.ITEM_COMPACTOR_SUBTYPES[0]);
        itemCompactorIron = new ItemToolCompactor(Item.ToolMaterial.IRON, Names.Items.ITEM_COMPACTOR + "_" + Names.Items.ITEM_COMPACTOR_SUBTYPES[1]);
        itemCompactorDiamond = new ItemToolCompactor(Item.ToolMaterial.EMERALD, Names.Items.ITEM_COMPACTOR + "_" + Names.Items.ITEM_COMPACTOR_SUBTYPES[2]);
        /* Emerald is diamond! */

        GameRegistry.registerItem(itemCompactorStone, "item." + Names.Items.ITEM_COMPACTOR + "_" + Names.Items.ITEM_COMPACTOR_SUBTYPES[0]);
        GameRegistry.registerItem(itemCompactorIron, "item." + Names.Items.ITEM_COMPACTOR + "_" + Names.Items.ITEM_COMPACTOR_SUBTYPES[1]);
        GameRegistry.registerItem(itemCompactorDiamond, "item." + Names.Items.ITEM_COMPACTOR + "_" + Names.Items.ITEM_COMPACTOR_SUBTYPES[2]);
    }

    public static void initialize() {

    }

    public static void postInit() {

    }

    public static ItemWini itemCompactorStone;
    public static ItemWini itemCompactorIron;
    public static ItemWini itemCompactorDiamond;
}
