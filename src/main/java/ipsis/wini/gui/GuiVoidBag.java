package ipsis.wini.gui;

import ipsis.wini.inventory.ContainerVoidBag;
import ipsis.wini.item.ItemVoidBag;
import ipsis.wini.reference.Textures;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiVoidBag extends GuiBaseWini {

    private static final String TEXTURE_STR = Textures.Gui.VOID_BAG;
    private static final String TEXTURE_BIG_STR = Textures.Gui.VOID_BAG_BIG;
    private static final ResourceLocation TEXTURE = new ResourceLocation(TEXTURE_STR);
    private static final ResourceLocation TEXTURE_BIG = new ResourceLocation(TEXTURE_BIG_STR);

    public GuiVoidBag(InventoryPlayer invPlayer, ContainerVoidBag containerVoidBag) {

        super(containerVoidBag, TEXTURE);

        ItemVoidBag voidBag = (ItemVoidBag)(invPlayer.getCurrentItem().getItem());
        if (voidBag.getBagSize() == ItemVoidBag.BagSize.LARGE)
            texture = TEXTURE_BIG;

        xSize = 176;
        ySize = 133;
    }
}
