package ipsis.wini.item;

import cofh.lib.util.helpers.ItemHelper;
import cofh.lib.util.helpers.StringHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ipsis.oss.util.LogHelper;
import ipsis.wini.Wini;
import ipsis.wini.inventory.ItemInventoryVoidBag;
import ipsis.wini.reference.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemVoidBag extends ItemWini {

    public ItemVoidBag() {
        super();
        this.setMaxDamage(0);
        this.setUnlocalizedName(Names.Items.ITEM_VOID_BAG);
        this.setTooltip(StringHelper.localize(Lang.Tooltips.ITEM_VOID_BAG));
    }

    public boolean handleItem(EntityPlayer entityPlayer, ItemStack bagStack, ItemStack itemStack) {

        boolean handled = false;

        if (!isLocked(bagStack)) {
        /* If it matches an item in the inventory get rid of it */
            IInventory inventory = new ItemInventoryVoidBag(bagStack);
            for (int slot = 0; slot < inventory.getSizeInventory(); slot++) {

                ItemStack c = inventory.getStackInSlot(slot);
                if (c != null && ItemHelper.itemsIdentical(c, itemStack)) {
                    LogHelper.info("Delete Item");
                    handled = true;
                    break;
                }
            }
        }

        return handled;
    }

    private void openGui(ItemStack itemStack, EntityPlayer entityPlayer) {

        entityPlayer.openGui(Wini.instance, Gui.Ids.VOID_BAG, entityPlayer.worldObj,
                (int)entityPlayer.posX, (int)entityPlayer.posY, (int)entityPlayer.posZ);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {

        if (!world.isRemote) {
            if (entityPlayer.isSneaking())
                setLockedState(itemStack, !isLocked(itemStack));
            else
                openGui(itemStack, entityPlayer);
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
}
