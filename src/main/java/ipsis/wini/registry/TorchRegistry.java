package ipsis.wini.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import ipsis.oss.util.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TorchRegistry {

    private TorchRegistry() { }
    private static TorchRegistry registry;
    private static List<ItemStack> validItems = new ArrayList<ItemStack>();

    public static TorchRegistry getInstance() {
        if (registry == null)
            registry = new TorchRegistry();
        return registry;
    }

    public void addModItems() {

        validItems.add(new ItemStack(Blocks.torch));

        /** Tinkers torches */
        Block tinkersTorchBlock = GameRegistry.findBlock("TConstruct", "decoration.stonetorch");
        if (tinkersTorchBlock != null) {
            validItems.add(new ItemStack(tinkersTorchBlock));
            LogHelper.info("Added TConstruct stonetorch to torch registry");
        }
    }

    public boolean isValid(ItemStack itemStack) {

        if (itemStack == null)
            return false;

        for (ItemStack checkStack : validItems) {
            if (checkStack.isItemEqual(itemStack))
                return true;
        }

        return false;
    }
}
