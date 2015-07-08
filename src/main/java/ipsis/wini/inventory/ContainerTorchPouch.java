package ipsis.wini.inventory;

import cofh.lib.gui.slot.ISlotValidator;
import cofh.lib.gui.slot.SlotAcceptValid;
import cofh.lib.gui.slot.SlotLocked;
import cofh.lib.gui.slot.SlotValidated;
import cofh.lib.util.helpers.InventoryHelper;
import ipsis.wini.item.ItemTorchPouch;
import ipsis.wini.utils.ItemHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ContainerTorchPouch extends ContainerInventoryItemWini implements ISlotValidator {

    public ContainerTorchPouch(ItemStack itemStack, InventoryPlayer invPlayer) {

        super(itemStack, invPlayer);

        /* Assuming in a straight line */
        for (int i = 0; i < ItemTorchPouch.SLOT_COUNT; i++)
            this.addSlotToContainer(new SlotValidated(this, this.containerWrapper, i, 44 + (i * 18), 20));

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

    @Override
    public boolean isItemValid(ItemStack itemStack) {

        return ItemHelper.isTorch(itemStack);
    }
}
