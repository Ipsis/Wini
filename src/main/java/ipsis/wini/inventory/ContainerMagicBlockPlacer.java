package ipsis.wini.inventory;

import cofh.lib.gui.slot.SlotLocked;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMagicBlockPlacer extends ContainerInventoryItemWini {

    public ContainerMagicBlockPlacer(ItemStack itemStack, InventoryPlayer invPlayer) {

        super(itemStack, invPlayer);

        this.addSlotToContainer(new Slot(this.containerWrapper, 0, 80, 20));

         /* Player inventory */
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++)
                this.addSlotToContainer(new Slot(invPlayer, x + y * 9 + 9, 8 + x * 18, 51 + y * 18));
        }
        /* Player hotbar */
        for (int x = 0; x < 9; x++) {
            /* Dont allow the bag inventory item to be changed */
            if (x == invPlayer.currentItem)
                this.addSlotToContainer(new SlotLocked(invPlayer, x, 8 + x * 18, 109));
            else
                this.addSlotToContainer(new Slot(invPlayer, x, 8 + x * 18, 109));
        }
    }
}
