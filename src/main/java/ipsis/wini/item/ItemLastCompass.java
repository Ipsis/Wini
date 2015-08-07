package ipsis.wini.item;

import cofh.lib.util.helpers.StringHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ipsis.oss.util.LogHelper;
import ipsis.wini.handler.TextureEventHandlers;
import ipsis.wini.network.PacketHandler;
import ipsis.wini.network.message.MessageStrutureLocation;
import ipsis.wini.reference.Lang;
import ipsis.wini.reference.Names;
import ipsis.wini.registry.LocationRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import tv.twitch.chat.ChatMessage;

import java.util.List;

/**
 * Compass spins when it has no location
 * Shift-rightclick to set to the nearest stronghold
 */
public class ItemLastCompass extends ItemWini {

    public ItemLastCompass() {
        super();
        this.setUnlocalizedName(Names.Items.ITEM_LAST_COMPASS);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = TextureEventHandlers.lastCompass;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {

        if (world.isRemote)
            return itemStack;

        if (!entityPlayer.isSneaking()) {
            /* Search */
            LocationRegistry.StructureType type = LocationRegistry.StructureType.getStructureType(itemStack.getItemDamage());
            ChunkPosition chunkPosition = LocationRegistry.getLocationChunkPosition(type, world,
                    (int) entityPlayer.posX, (int) entityPlayer.posY, (int) entityPlayer.posZ);

            /* Forge Docs - as long as you are on the server you can safely cast any EntityPlayer to EntityPlayerMP */
            if (chunkPosition != null) {
                entityPlayer.addChatComponentMessage(new ChatComponentText("Found " + type.displayName));
                PacketHandler.INSTANCE.sendTo(new MessageStrutureLocation(chunkPosition.chunkPosX, chunkPosition.chunkPosY, chunkPosition.chunkPosZ, true), (EntityPlayerMP) entityPlayer);
            } else {
                entityPlayer.addChatComponentMessage(new ChatComponentText("Cannot find " + type.displayName));
                PacketHandler.INSTANCE.sendTo(new MessageStrutureLocation(0, 0, 0, false), (EntityPlayerMP) entityPlayer);
            }

        } else {
            /* Change destination */
            LocationRegistry.StructureType type = LocationRegistry.StructureType.getStructureType(itemStack.getItemDamage());
            type = type.getNext();
            itemStack.setItemDamage(type.ordinal());
            entityPlayer.addChatComponentMessage(new ChatComponentText("Target: " + type.displayName));

            /* Invalidate the current location */
            PacketHandler.INSTANCE.sendTo(new MessageStrutureLocation(0, 0, 0, false), (EntityPlayerMP) entityPlayer);
        }

        return itemStack;
    }

    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean showAdvanced) {
        super.addInformation(itemStack, player, info, showAdvanced);

        LocationRegistry.StructureType type = LocationRegistry.StructureType.getStructureType(itemStack.getItemDamage());
        info.add("Target: "  + type.displayName);
    }
}
