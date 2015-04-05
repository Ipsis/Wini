package ipsis.wini.item;

import ipsis.wini.Wini;
import ipsis.wini.inventory.ItemInventoryTorchPouch;
import ipsis.wini.reference.Gui;
import ipsis.wini.reference.Names;
import ipsis.wini.reference.Nbt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;


public class ItemTorchPouch extends ItemWini {

    public ItemTorchPouch() {

        super();
        this.setMaxDamage(0);
        this.setUnlocalizedName(Names.Items.ITEM_TORCH_POUCH);
    }

    private void openGui(ItemStack itemStack, EntityPlayer entityPlayer) {

        entityPlayer.openGui(Wini.instance, Gui.Ids.TORCH_POUCH, entityPlayer.worldObj,
                (int)entityPlayer.posX, (int)entityPlayer.posY, (int)entityPlayer.posZ);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {

        if (!world.isRemote && entityPlayer.isSneaking())
            openGui(itemStack, entityPlayer);

        return itemStack;
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {

        if (world.isRemote || !itemStack.hasTagCompound())
            return true;

        /* Try to operate the same as normal torches */
        ItemInventoryTorchPouch inv = new ItemInventoryTorchPouch(itemStack);
        for (int slot = 0; slot < inv.getSizeInventory(); slot++) {
            ItemStack s = inv.getStackInSlot(slot);
            if (s != null && s.stackSize != 0) {
                /* we assume if it is in here it is a torch */
                Item item = s.getItem();
                if (item.onItemUse(s, entityPlayer, world, x, y, z, side, hitX, hitY, hitZ)) {
                    inv.saveInventoryToStack(entityPlayer.getHeldItem());
                    break;
                }
            }
        }

        return true;
    }

    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean showAdvanced) {
        super.addInformation(itemStack, player, info, showAdvanced);

        if (itemStack == null)
            return;

        short count = 0;
        if (itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey(Nbt.TORCH_POUCH_COUNT)) {
            info.add(itemStack.getTagCompound().getShort(Nbt.TORCH_POUCH_COUNT) + "/" + 5 * 64);

        }
    }
}
