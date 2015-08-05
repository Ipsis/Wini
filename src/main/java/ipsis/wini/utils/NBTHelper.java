package ipsis.wini.utils;

import ipsis.wini.reference.Nbt;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.UUID;

public class NBTHelper {

    public static void setUUID(ItemStack itemStack) {

        if (itemStack == null)
            return;

        if (itemStack.getTagCompound() == null)
            itemStack.setTagCompound(new NBTTagCompound());

        if (!itemStack.stackTagCompound.hasKey(Nbt.UUID_MS) && !itemStack.stackTagCompound.hasKey(Nbt.UUID_LS)) {
            UUID uuid = UUID.randomUUID();
            itemStack.stackTagCompound.setLong(Nbt.UUID_MS, uuid.getMostSignificantBits());
            itemStack.stackTagCompound.setLong(Nbt.UUID_LS, uuid.getLeastSignificantBits());
        }
    }

    public static UUID getUUID(ItemStack itemStack) {

        if (!hasUUID(itemStack))
            return null;

        return new UUID(itemStack.stackTagCompound.getLong(Nbt.UUID_MS), itemStack.stackTagCompound.getLong(Nbt.UUID_LS));
    }

    public static boolean hasUUID(ItemStack itemStack) {

        if (itemStack == null || itemStack.getTagCompound() == null || !itemStack.stackTagCompound.hasKey(Nbt.UUID_MS) || !itemStack.stackTagCompound.hasKey(Nbt.UUID_LS))
            return false;

        return true;
    }
}
