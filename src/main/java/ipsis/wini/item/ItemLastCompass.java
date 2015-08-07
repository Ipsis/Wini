package ipsis.wini.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ipsis.oss.util.LogHelper;
import ipsis.wini.handler.TextureEventHandlers;
import ipsis.wini.network.PacketHandler;
import ipsis.wini.network.message.MessageStrutureLocation;
import ipsis.wini.reference.Names;
import ipsis.wini.registry.LocationRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

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

        if (!world.isRemote && entityPlayer.isSneaking()) {
            ChunkPosition chunkposition = world.findClosestStructure(LocationRegistry.StructureType.STRONGHOLD.name, (int) entityPlayer.posX, (int) entityPlayer.posY, (int) entityPlayer.posZ);
            /* Forge Docs - as long as you are on the server you can safely cast any EntityPlayer to EntityPlayerMP */
            if (chunkposition != null)
                PacketHandler.INSTANCE.sendTo(new MessageStrutureLocation(chunkposition.chunkPosX, chunkposition.chunkPosY, chunkposition.chunkPosZ), (EntityPlayerMP)entityPlayer);
        }

        return itemStack;
    }
}
