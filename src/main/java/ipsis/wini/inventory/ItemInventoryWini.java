package ipsis.wini.inventory;

import ipsis.wini.reference.Nbt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

public class ItemInventoryWini implements IInventory {

    private ItemStack[] inv;
    private ItemStack parentStack; /* The itemstack we created the inventory from */
    private int stackLimit; /* InventoryStackLimit */

    private ItemInventoryWini() { }
    public ItemInventoryWini(int size, ItemStack parentStack, int stackLimit) {

        inv = new ItemStack[size];
        this.parentStack = parentStack;
        this.stackLimit = stackLimit;
        if (!parentStack.hasTagCompound())
            parentStack.setTagCompound(new NBTTagCompound());
        loadInventory();
    }

    private boolean isValidSlotNum(int slotNum) {
        return slotNum >= 0 && slotNum < getSizeInventory();
    }

    /**
     * NBT
     */
    private void readFromNBT(NBTTagCompound compound) {

        if (compound == null)
            return;

        NBTTagList taglist = compound.getTagList(Nbt.ITEM_INV_ITEMS, Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < taglist.tagCount(); i++) {
            NBTTagCompound tmpCompound = taglist.getCompoundTagAt(i);
            int slot = tmpCompound.getByte(Nbt.ITEM_INV_SLOT) & 0xff;
            if (slot >= 0 && slot < inv.length)
                setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(tmpCompound));
        }
    }

    private void writeToNBT(NBTTagCompound compound) {

        if (compound == null)
            return;

        NBTTagList taglist = new NBTTagList();
        for (int i = 0 ; i < inv.length; i++) {
            ItemStack itemStack = getStackInSlot(i);

            if (itemStack == null)
                continue;

            NBTTagCompound tmpCompound = new NBTTagCompound();
            tmpCompound.setByte(Nbt.ITEM_INV_SLOT, (byte)i);
            itemStack.writeToNBT(tmpCompound);
            taglist.appendTag(tmpCompound);
        }

        compound.setTag(Nbt.ITEM_INV_ITEMS, taglist);
    }

    public void loadInventory() {
        readFromNBT(parentStack.getTagCompound());
    }

    public void saveInventoryToStack(ItemStack itemStack) {
        if (!itemStack.hasTagCompound())
            itemStack.setTagCompound(new NBTTagCompound());

        writeToNBT(itemStack.getTagCompound());
    }

    /**
     * How many items are in the inventory regardless of type
     * @return
     */
    public short getItemCount() {

        short count = 0;
        for (int i = 0; i < getSizeInventory(); i++)
            count += (getStackInSlot(i) != null ? getStackInSlot(i).stackSize : 0);

        return count;
    }

    /**
     * IInventory
     */
    @Override
    public int getSizeInventory() {
        return inv.length;
    }

    @Override
    public ItemStack getStackInSlot(int slotNum) {
        return isValidSlotNum(slotNum) ? inv[slotNum] : null;
    }

    @Override
    public ItemStack decrStackSize(int slotNum, int count) {
        if (!isValidSlotNum(slotNum))
            return null;

        if (inv[slotNum] == null)
            return null;

        ItemStack newStack = null;
        if (inv[slotNum].stackSize <= count) {
            newStack = inv[slotNum];
            inv[slotNum] = null;
        } else {
            newStack = inv[slotNum].splitStack(count);
            if (inv[slotNum].stackSize <= 0)
                inv[slotNum] = null;
        }

        markDirty();
        return newStack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slotNum) {
        if (isValidSlotNum(slotNum)) {
            ItemStack itemStack = inv[slotNum];
            inv[slotNum] = null;
            return itemStack;
        }

        return null;
    }

    @Override
    public void setInventorySlotContents(int slotNum, ItemStack itemStack) {
        if (isValidSlotNum(slotNum)) {
            inv[slotNum] = itemStack;
            if (itemStack != null && itemStack.stackSize > getInventoryStackLimit())
                itemStack.stackSize = getInventoryStackLimit();

            markDirty();;
        }
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
        return this.stackLimit;
    }

    @Override
    public void markDirty() {
        /* NOOP */
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityPlayer) {
        /* You are holding it, you must be close enough */
        return true;
    }

    @Override
    public void openInventory() { }

    @Override
    public void closeInventory() { }

    @Override
    public boolean isItemValidForSlot(int slotNum, ItemStack itemStack) {
        if (isValidSlotNum(slotNum)) {
            return true;
        }

        return false;
    }
}
