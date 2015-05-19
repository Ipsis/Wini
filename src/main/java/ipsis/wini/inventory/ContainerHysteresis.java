package ipsis.wini.inventory;

import ipsis.wini.network.PacketHandler;
import ipsis.wini.network.message.MessageHysteresisCfg;
import ipsis.wini.network.message.MessageHysteresisUpdate;
import ipsis.wini.network.message.MessageRedstoneOutputCfg;
import ipsis.wini.tileentity.TileEntityHysteresis;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ICrafting;

public class ContainerHysteresis extends ContainerWini {

    private TileEntityHysteresis te;

    public ContainerHysteresis(EntityPlayer entityPlayer, TileEntityHysteresis te) {
        super();
        this.te = te;
        /* No slots in this gui */
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer) {
        return true; /** TODO fix canInteractWith */
    }

    @Override
    public void addCraftingToCrafters(ICrafting iCrafting) {
        super.addCraftingToCrafters(iCrafting);

        PacketHandler.INSTANCE.sendTo(new MessageHysteresisUpdate(this.te), (EntityPlayerMP) iCrafting);
    }
}
