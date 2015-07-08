package ipsis.wini.inventory;

import cofh.lib.gui.container.ContainerInventoryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerInventoryItemWini extends ContainerInventoryItem {

    public ContainerInventoryItemWini(ItemStack itemStack, InventoryPlayer invPlayer) {
        super(itemStack, invPlayer);
    }

    public void addPlayerInventory(EntityPlayer entityPlayer, int startSlot, int xOffset, int yOffset) {

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++)
                this.addSlotToContainer(new Slot(entityPlayer.inventory, x + y * 9 + startSlot, xOffset + x * 18, yOffset + y * 18));
        }
    }

    public void addPlayerHotBar(EntityPlayer entityPlayer, int startSlot, int xOffset, int yOffset) {

        for (int x = 0; x < 9; x++)
            this.addSlotToContainer(new Slot(entityPlayer.inventory, startSlot, xOffset + x * 18, yOffset));
    }
}
