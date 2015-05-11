package ipsis.wini.inventory;

import ipsis.wini.tileentity.TileEntityHysteresis;
import net.minecraft.entity.player.EntityPlayer;

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


}
