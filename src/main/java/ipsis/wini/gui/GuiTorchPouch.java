package ipsis.wini.gui;

import ipsis.wini.inventory.ContainerTorchPouch;
import ipsis.wini.inventory.InventoryTorchPouch;
import ipsis.wini.reference.Textures;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiTorchPouch extends GuiBaseWini {

    private static final String TEXTURE_STR = new String(Textures.Gui.TORCH_POUCH);
    private static final ResourceLocation TEXTURE = new ResourceLocation(TEXTURE_STR);

    private final ItemStack parentItemStack;
    private final InventoryTorchPouch inv;

    public GuiTorchPouch(EntityPlayer entityPlayer, InventoryTorchPouch inv) {

        super(new ContainerTorchPouch(entityPlayer, inv), TEXTURE);

        xSize = 176;
        ySize = 133;

        this.inv = inv;
        this.parentItemStack = inv.parentItemStack;
    }
}
