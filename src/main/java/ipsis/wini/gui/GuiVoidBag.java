package ipsis.wini.gui;

import ipsis.wini.inventory.ContainerVoidBag;
import ipsis.wini.inventory.InventoryVoidBag;
import ipsis.wini.reference.Textures;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiVoidBag extends GuiBaseWini {

    private static final String TEXTURE_STR = new String(Textures.Gui.VOID_BAG);
    private static final ResourceLocation TEXTURE = new ResourceLocation(TEXTURE_STR);

    public GuiVoidBag(EntityPlayer entityPlayer, ItemStack equippedItemStack) {

        super(new ContainerVoidBag(entityPlayer, equippedItemStack), TEXTURE);
        xSize = 176;
        ySize = 133;
    }
}
