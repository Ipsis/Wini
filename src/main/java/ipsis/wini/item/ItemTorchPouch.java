package ipsis.wini.item;

import cofh.api.item.IInventoryContainerItem;
import cofh.lib.gui.container.InventoryContainerItemWrapper;
import ipsis.wini.Wini;
import ipsis.wini.inventory.ContainerTorchPouch;
import ipsis.wini.reference.Gui;
import ipsis.wini.reference.Names;
import ipsis.wini.reference.Nbt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

import java.util.List;


public class ItemTorchPouch extends ItemWini implements IInventoryContainerItem {

    public static final int SLOT_COUNT = 5;

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

        if (world.isRemote || itemStack == null || !itemStack.hasTagCompound())
            return true;

        int invSize = ((IInventoryContainerItem)itemStack.getItem()).getSizeInventory(itemStack);

        boolean found = false;
        for (int i = 0; i < invSize && !found; i++) {

            if (!itemStack.stackTagCompound.hasKey("Slot" + i))
                continue;

            ItemStack currStack = ItemStack.loadItemStackFromNBT(itemStack.stackTagCompound.getCompoundTag("Slot" + i));
            if (currStack != null && currStack.stackSize > 0) {
                if (currStack.getItem().onItemUse(currStack, entityPlayer, world, x, y, z, side, hitX, hitY, hitZ)) {
                    if (currStack.stackSize == 0)
                        itemStack.stackTagCompound.setTag("Slot" + i, new NBTTagCompound());
                    else
                        itemStack.stackTagCompound.setTag("Slot" + i, currStack.writeToNBT(new NBTTagCompound()));

                    found = true;
                }
            }
        }

        return true;
    }

    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List info, boolean showAdvanced) {
        super.addInformation(itemStack, entityPlayer, info, showAdvanced);

        if (itemStack == null || itemStack.hasTagCompound() == false)
            return;

        int count = 0;
        int invSize = ((IInventoryContainerItem)itemStack.getItem()).getSizeInventory(itemStack);

        for (int i = 0; i < invSize; i++) {

            if (!itemStack.stackTagCompound.hasKey("Slot" + i))
                continue;

            ItemStack currStack = ItemStack.loadItemStackFromNBT(itemStack.stackTagCompound.getCompoundTag("Slot" + i));
            count += (currStack != null ? currStack.stackSize : 0);
        }

        info.add(count + " torches");
    }

    @Override
    public int getSizeInventory(ItemStack itemStack) {
        return SLOT_COUNT;
    }

}
