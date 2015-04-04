package ipsis.wini.handler;

import cpw.mods.fml.common.network.IGuiHandler;
import ipsis.wini.gui.GuiTorchPouch;
import ipsis.wini.gui.GuiVoidBag;
import ipsis.wini.inventory.ContainerTorchPouch;
import ipsis.wini.inventory.ContainerVoidBag;
import ipsis.wini.inventory.InventoryTorchPouch;
import ipsis.wini.inventory.InventoryVoidBag;
import ipsis.wini.reference.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer entityPlayer, World world, int x, int y, int z) {

        if (ID == Gui.Ids.TORCH_POUCH) {
            return new ContainerTorchPouch(entityPlayer, new InventoryTorchPouch(entityPlayer.getHeldItem()));
        } else if (ID == Gui.Ids.VOID_BAG) {
            return new ContainerVoidBag(entityPlayer, entityPlayer.getHeldItem());
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer entityPlayer, World world, int x, int y, int z) {

        if (ID == Gui.Ids.TORCH_POUCH) {
            return new GuiTorchPouch(entityPlayer, new InventoryTorchPouch(entityPlayer.getHeldItem()));
        }  else if (ID == Gui.Ids.VOID_BAG) {
            return new GuiVoidBag(entityPlayer, entityPlayer.getHeldItem());
        }
        return null;
    }
}
