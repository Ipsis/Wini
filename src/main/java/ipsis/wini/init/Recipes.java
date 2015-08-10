package ipsis.wini.init;

import cpw.mods.fml.common.registry.GameRegistry;
import ipsis.wini.reference.Settings;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class Recipes {

    public static void init() {

        initItemRecipes();
        initBlockRecipes();
    }

    private static void initItemRecipes() {

        GameRegistry.addRecipe(new ShapedOreRecipe(
                new ItemStack(ModItems.itemCompactorStone), "scs", " w ", " w ", 's', "slimeball", 'c', "cobblestone", 'w', "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                new ItemStack(ModItems.itemCompactorIron), "sis", " w ", " w ", 's', "slimeball", 'i', "ingotIron", 'w', "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                new ItemStack(ModItems.itemCompactorDiamond), "sds", " w ", " w ", 's', "slimeball", 'd', "gemDiamond", 'w', "stickWood"));

        GameRegistry.addRecipe(new ShapedOreRecipe(
                new ItemStack(ModItems.itemTorchPouch), "lll", "ltl", "lcl", 'l', Items.leather, 't', Blocks.torch, 'c', Blocks.chest));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                new ItemStack(ModItems.itemVoidBag), "lll", "lol", "lcl", 'l', Items.leather, 'o', Blocks.obsidian, 'c', Blocks.chest));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                new ItemStack(ModItems.itemVoidBagBig), "ldl", "lol", "lcl", 'd', "gemDiamond", 'l', Items.leather, 'o', Blocks.obsidian, 'c', Blocks.chest));

        if (Settings.blockSceptreEnabled) {
            GameRegistry.addRecipe(new ShapedOreRecipe(
                    new ItemStack(ModItems.itemMagicBlockPlacer),
                    "rgr", "eoe", "fsf",
                    'r', Blocks.redstone_block, 'g', "blockGold",
                    'e', Items.ender_eye, 'o', Blocks.obsidian,
                    'f', Items.feather, 's', Items.diamond_shovel));
        }
    }

    private static void initBlockRecipes() {

        GameRegistry.addRecipe(new ShapedOreRecipe(
                new ItemStack(ModBlocks.blockStepdown), "rss", "rcr", "rss", 'r', Items.redstone, 's', Blocks.stone, 'c', Items.comparator));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                new ItemStack(ModBlocks.blockHysteresis, 1, 0), "sss", "scs", "sbs", 's', Blocks.stone, 'c', Items.comparator, 'b', Items.bucket));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                new ItemStack(ModBlocks.blockHysteresis, 1, 1), "sss", "scs", "srs", 's', Blocks.stone, 'c', Items.comparator, 'r', Blocks.furnace));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                new ItemStack(ModBlocks.blockHysteresis, 1, 2), "sss", "scs", "sbs", 's', Blocks.stone, 'c', Items.comparator, 'b', Blocks.chest));
    }
}
