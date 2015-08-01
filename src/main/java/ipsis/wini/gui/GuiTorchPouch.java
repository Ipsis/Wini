package ipsis.wini.gui;

import ipsis.wini.inventory.ContainerTorchPouch;
import ipsis.wini.reference.Textures;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiTorchPouch extends GuiBaseWini {

    private static final String TEXTURE_STR = Textures.Gui.TORCH_POUCH;
    private static final ResourceLocation TEXTURE = new ResourceLocation(TEXTURE_STR);


    public GuiTorchPouch(InventoryPlayer invPlayer, ContainerTorchPouch containerTorchPouch) {

        super(containerTorchPouch, TEXTURE);
        xSize = 176;
        ySize = 133;
    }
}
