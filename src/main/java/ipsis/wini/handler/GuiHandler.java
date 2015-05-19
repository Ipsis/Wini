package ipsis.wini.handler;

import cpw.mods.fml.common.network.IGuiHandler;
import ipsis.wini.gui.GuiHysteresis;
import ipsis.wini.gui.GuiTorchPouch;
import ipsis.wini.gui.GuiVoidBag;
import ipsis.wini.inventory.ContainerHysteresis;
import ipsis.wini.inventory.ContainerTorchPouch;
import ipsis.wini.inventory.ContainerVoidBag;
import ipsis.wini.reference.Gui;
import ipsis.wini.tileentity.TileEntityHysteresis;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer entityPlayer, World world, int x, int y, int z) {

        if (ID == Gui.Ids.TORCH_POUCH) {
            return new ContainerTorchPouch(entityPlayer, entityPlayer.getHeldItem());
        } else if (ID == Gui.Ids.VOID_BAG) {
            return new ContainerVoidBag(entityPlayer, entityPlayer.getHeldItem());
        } else if (ID == Gui.Ids.HYSTERESIS) {
            return new ContainerHysteresis(entityPlayer, (TileEntityHysteresis)world.getTileEntity(x, y, z));
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer entityPlayer, World world, int x, int y, int z) {

        if (ID == Gui.Ids.TORCH_POUCH) {
            return new GuiTorchPouch(entityPlayer, entityPlayer.getHeldItem());
        }  else if (ID == Gui.Ids.VOID_BAG) {
            return new GuiVoidBag(entityPlayer, entityPlayer.getHeldItem());
        } else if (ID == Gui.Ids.HYSTERESIS) {
            return new GuiHysteresis(entityPlayer, (TileEntityHysteresis)world.getTileEntity(x, y, z));
        }
        return null;
    }
}
