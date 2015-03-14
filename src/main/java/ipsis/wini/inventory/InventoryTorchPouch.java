package ipsis.wini.inventory;

import ipsis.wini.reference.Nbt;
import ipsis.wini.reference.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

import java.util.UUID;

/**
 * Based off Pahimar's InventoryAlchemicalBag.java
 */
public class InventoryTorchPouch implements IInventory {

    public ItemStack parentItemStack;
    protected ItemStack[] inventory;

    public InventoryTorchPouch(ItemStack itemStack) {

        parentItemStack = itemStack;
        inventory = new ItemStack[5];

        /** Load the inventory from that stored in the itemStack */
        readFromNBT(itemStack.getTagCompound());
    }

    private void readFromNBT(NBTTagCompound compound) {

        if (compound == null)
            return;

        NBTTagList taglist = compound.getTagList(Nbt.TORCH_POUCH_ITEMS, Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < taglist.tagCount(); i++) {
            NBTTagCompound tmpCompound = taglist.getCompoundTagAt(i);
            int slot = tmpCompound.getByte(Nbt.TORCH_POUCH_SLOT) & 0xff;
            if (slot >= 0 && slot < inventory.length)
                setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(tmpCompound));
        }
    }

    private void writeToNBT(NBTTagCompound compound) {

        if (compound == null)
            return;

        NBTTagList taglist = new NBTTagList();
        for (int i = 0 ; i < inventory.length; i++) {
            ItemStack itemStack = getStackInSlot(i);

            if (itemStack == null)
                continue;

            NBTTagCompound tmpCompound = new NBTTagCompound();
            tmpCompound.setByte(Nbt.TORCH_POUCH_SLOT, (byte)i);
            itemStack.writeToNBT(tmpCompound);
            taglist.appendTag(tmpCompound);
        }

        compound.setTag(Nbt.TORCH_POUCH_ITEMS, taglist);
    }

    private void saveTorchPouch() {

        if (parentItemStack == null)
            return;

        NBTTagCompound compound = parentItemStack.getTagCompound();
        if (compound == null) {
            UUID uuid = UUID.randomUUID();
            compound.setLong(Reference.UUID_MS, uuid.getMostSignificantBits());
            compound.setLong(Reference.UUID_LS, uuid.getLeastSignificantBits());
        }

        writeToNBT(compound);
        parentItemStack.setTagCompound(compound);
    }

    /**
     * Search the players inventory for the matching parent stack
     */
    private ItemStack findParentItemStack(EntityPlayer entityPlayer) {
        /* Can only find the parent if the parent currently has a UUID */
        if (parentItemStack.hasTagCompound() && parentItemStack.getTagCompound().hasKey(Reference.UUID_MS) && parentItemStack.getTagCompound().hasKey(Reference.UUID_LS)) {
            UUID parentUUID = new UUID(parentItemStack.getTagCompound().getLong(Reference.UUID_MS), parentItemStack.getTagCompound().getLong(Reference.UUID_LS));
            for (int i = 0; i < entityPlayer.inventory.getSizeInventory(); i++)
            {
                ItemStack itemStack = entityPlayer.inventory.getStackInSlot(i);
                if (itemStack != null && itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey(Reference.UUID_MS) && itemStack.getTagCompound().hasKey(Reference.UUID_LS)) {
                    /* This is the matching parent stack */
                    if (itemStack.getTagCompound().getLong(Reference.UUID_MS) == parentUUID.getMostSignificantBits() &&
                            itemStack.getTagCompound().getLong(Reference.UUID_LS) == parentUUID.getLeastSignificantBits()) {
                        return itemStack;
                    }
                }
            }
        }
        return null;
    }


    public void onGuiSaved(EntityPlayer entityPlayer) {

        parentItemStack = findParentItemStack(entityPlayer);
        if (parentItemStack != null)
            saveTorchPouch();
    }

    /**
     * IInventory
     */

    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {

        return inventory[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int count) {

        if (inventory[slot] == null)
            return null;
        if (inventory[slot].stackSize <= count) {
            ItemStack s = inventory[slot];
            inventory[slot] = null;
            markDirty();
            return s;
        }
        ItemStack s = inventory[slot].splitStack(count);
        if (inventory[slot].stackSize <= 0)
            inventory[slot] = null;
        markDirty();
        return s;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {

        ItemStack s = inventory[slot];
        if (s != null)
            inventory[slot] = null;
        return s;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {

        inventory[slot] = stack;
        if (stack != null && stack.stackSize > getInventoryStackLimit())
            stack.stackSize = getInventoryStackLimit();
        markDirty();
    }

    @Override
    public String getInventoryName() {
        return null;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markDirty() {

    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
        return false;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemStack) {

        if (itemStack == null)
            return false;

        return itemStack.getItem() == Item.getItemFromBlock(Blocks.torch);
    }
}
