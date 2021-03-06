package ipsis.wini.item;

import cofh.api.item.IInventoryContainerItem;
import cofh.lib.util.helpers.StringHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ipsis.wini.Wini;
import ipsis.wini.reference.Gui;
import ipsis.wini.reference.Lang;
import ipsis.wini.reference.Names;
import ipsis.wini.reference.Nbt;
import ipsis.wini.registry.VoidBagRegistry;
import ipsis.wini.utils.NBTHelper;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

public class ItemVoidBag extends ItemWini implements IInventoryContainerItem {

    public enum BagSize {
        SMALL(9),
        LARGE(18);

        private int size;
        BagSize(int size) {
            this.size = size;
        }

        public int getSize() { return this.size; }
    }

    private BagSize bagSize;

    public ItemVoidBag(BagSize bagSize) {
        super();
        this.setMaxDamage(0);

        if (bagSize == BagSize.SMALL) {
            this.setUnlocalizedName(Names.Items.ITEM_VOID_BAG);
            this.setTooltip(StringHelper.localize(Lang.Tooltips.ITEM_VOID_BAG));
        } else {
            this.setUnlocalizedName(Names.Items.ITEM_VOID_BAG_BIG);
            this.setTooltip(StringHelper.localize(Lang.Tooltips.ITEM_VOID_BAG_BIG));
        }

        this.bagSize = bagSize;
    }

    public BagSize getBagSize() {
        return this.bagSize;
    }

    private static void setDefaultTags(ItemStack itemStack) {
        if (itemStack.hasTagCompound() == false)
            itemStack.setTagCompound(new NBTTagCompound());

        NBTHelper.setUUID(itemStack);
    }

    private static boolean hasDefaultTags(ItemStack itemStack) {
        if (itemStack == null || itemStack.hasTagCompound() == false)
            return false;

        return NBTHelper.hasUUID(itemStack);
    }


    public static UUID getBagUUID(ItemStack itemStack) {
        if (itemStack == null || !(itemStack.getItem() instanceof ItemVoidBag))
            return null;

        if (!hasDefaultTags(itemStack))
            setDefaultTags(itemStack);

        return NBTHelper.getUUID(itemStack);
    }

    private void openGui(ItemStack itemStack, EntityPlayer entityPlayer) {

        entityPlayer.openGui(Wini.instance, Gui.Ids.VOID_BAG, entityPlayer.worldObj,
                (int)entityPlayer.posX, (int)entityPlayer.posY, (int)entityPlayer.posZ);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {

        if (!world.isRemote && itemStack != null) {

            setDefaultTags(itemStack);

            if (entityPlayer.isSneaking()) {
                setLockedState(itemStack, !isLocked(itemStack));
                VoidBagRegistry.getInstance().setBagLock(entityPlayer, itemStack);
                entityPlayer.addChatComponentMessage(new ChatComponentText(isLocked(itemStack) ? "Locked" : "Unlocked"));
            } else {
                openGui(itemStack, entityPlayer);
            }
        }
        return itemStack;
    }

    @SideOnly(Side.CLIENT)
    private IIcon itemIconLocked;

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        itemIcon = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".") + 1) + "_unlocked");
        itemIconLocked = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".") + 1) + "_locked");
    }

    public static boolean isLocked(ItemStack stack) {

        return stack != null && stack.hasTagCompound() && stack.getTagCompound().hasKey(Nbt.VOID_BAG_LOCKED) && stack.getTagCompound().getBoolean(Nbt.VOID_BAG_LOCKED);
    }

    public static void setLocked(ItemStack stack) {

        setLockedState(stack, true);
    }

    public static void setUnlocked(ItemStack stack) {

        setLockedState(stack, false);
    }

    private static void setLockedState(ItemStack stack, boolean state) {

        NBTTagCompound tag = stack.getTagCompound();
        if (tag == null)
            tag = new NBTTagCompound();

        tag.setBoolean(Nbt.VOID_BAG_LOCKED, state);
        stack.setTagCompound(tag);
    }

    @Override
    public IIcon getIconIndex(ItemStack stack) {
        return isLocked(stack) ? itemIconLocked : itemIcon;
    }

    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean showAdvanced) {
        super.addInformation(itemStack, player, info, showAdvanced);
        info.add(StringHelper.localize(isLocked(itemStack) ? Lang.Tooltips.ITEM_VOID_BAG_LOCKED : Lang.Tooltips.ITEM_VOID_BAG_UNLOCKED));
    }

    @Override
    public int getSizeInventory(ItemStack itemStack) {
        return getBagSize().getSize();
    }
}
