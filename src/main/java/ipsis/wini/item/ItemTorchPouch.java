package ipsis.wini.item;

import ipsis.wini.Wini;
import ipsis.wini.inventory.InventoryTorchPouch;
import ipsis.wini.reference.Gui;
import ipsis.wini.reference.Names;
import ipsis.wini.reference.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.UUID;

public class ItemTorchPouch extends ItemWini {

    public ItemTorchPouch() {

        super();
        this.setMaxDamage(0);
        this.setUnlocalizedName(Names.Items.ITEM_TORCH_POUCH);
    }

    private void openGui(ItemStack itemStack, EntityPlayer entityPlayer) {

        /** Add a UUID */
        if (!itemStack.hasTagCompound())
            itemStack.stackTagCompound = new NBTTagCompound();

        if (!itemStack.getTagCompound().hasKey(Reference.UUID_MS) || !itemStack.getTagCompound().hasKey(Reference.UUID_LS)) {

            UUID uuid = UUID.randomUUID();
            itemStack.getTagCompound().setLong(Reference.UUID_MS, uuid.getMostSignificantBits());
            itemStack.getTagCompound().setLong(Reference.UUID_LS, uuid.getLeastSignificantBits());
        }

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
        InventoryTorchPouch inv = new InventoryTorchPouch(itemStack);
        for (int slot = 0; slot < inv.getSizeInventory(); slot++) {
            ItemStack s = inv.getStackInSlot(slot);
            if (s != null && s.stackSize != 0) {
                /* we assume if it is in here it is a torch */
                Item item = s.getItem();
                if (item.onItemUse(s, entityPlayer, world, x, y, z, side, hitX, hitY, hitZ)) {
                    inv.onGuiSaved(entityPlayer);
                    break;
                }
            }
        }

        return true;
    }
}
