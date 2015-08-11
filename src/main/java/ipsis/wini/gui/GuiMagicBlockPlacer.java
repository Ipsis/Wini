package ipsis.wini.gui;

import ipsis.wini.inventory.ContainerMagicBlockPlacer;
import ipsis.wini.reference.Textures;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiMagicBlockPlacer extends GuiBaseWini {

    private static final String TEXTURE_STR = Textures.Gui.MAGIC_BLOCK_PLACER;
    private static final ResourceLocation TEXTURE = new ResourceLocation(TEXTURE_STR);


    public GuiMagicBlockPlacer(InventoryPlayer invPlayer, ContainerMagicBlockPlacer containerMagicBlockPlacer) {

        super(containerMagicBlockPlacer, TEXTURE);
        xSize = 176;
        ySize = 133;
    }
}
