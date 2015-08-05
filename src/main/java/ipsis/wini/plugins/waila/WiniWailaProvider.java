package ipsis.wini.plugins.waila;

import cofh.lib.util.helpers.StringHelper;
import ipsis.wini.reference.Lang;
import ipsis.wini.tileentity.TileEntityHysteresis;
import ipsis.wini.tileentity.TileEntityHysteresisFluid;
import ipsis.wini.tileentity.TileEntityHysteresisRf;
import ipsis.wini.tileentity.TileEntityStepdown;
import ipsis.wini.utils.CompareFunc;
import ipsis.wini.utils.IRedstoneOutput;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import java.util.List;

/**
 * Best class name EVER!
 */
public class WiniWailaProvider implements IWailaDataProvider {

    /* Waila API formatting values from waila/api/SpecialChars.java */
    private static String WailaStyle     = "\u00A4";
    private static String TAB         = WailaStyle + WailaStyle +"a";
    private static String ALIGNRIGHT  = WailaStyle + WailaStyle +"b";
    private static String ALIGNCENTER = WailaStyle + WailaStyle +"c";

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {

        if (!accessor.getPlayer().isSneaking())
            return currenttip;

        if (accessor.getTileEntity() instanceof TileEntityHysteresis)
            currenttip = handleTileEntityHysteresis((TileEntityHysteresis) accessor.getTileEntity(), currenttip);
        else if (accessor.getTileEntity() instanceof TileEntityStepdown)
            currenttip = handleTileEntityStepdown((TileEntityStepdown)accessor.getTileEntity(), currenttip);

        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public NBTTagCompound getNBTData(TileEntity te, NBTTagCompound tag, World world, int x, int y, int z) {
        return null;
    }

    public static void callbackRegister(IWailaRegistrar registrar) {

        registrar.registerBodyProvider(new WiniWailaProvider(), TileEntityHysteresis.class);
        registrar.registerBodyProvider(new WiniWailaProvider(), TileEntityStepdown.class);
    }

    private List<String> handleTileEntityHysteresis(TileEntityHysteresis te, List<String> currenttip) {
        
        int on_rs, off_rs;
        CompareFunc on_f, off_f;
        String units;

        if (te instanceof TileEntityHysteresisRf)
            units = StringHelper.localize(Lang.Gui.TITLE_HYSTERICAL_RF + "_units");
        else if (te instanceof TileEntityHysteresisFluid)
            units = StringHelper.localize(Lang.Gui.TITLE_HYSTERICAL_FLUID + "_units");
        else
            units = StringHelper.localize(Lang.Gui.TITLE_HYSTERICAL_INV + "_units");

        if (te.getRedstoneSense() == IRedstoneOutput.Sense.NORMAL) {
            on_rs = te.getTriggerLevel();
            on_f = te.getTriggerFunc();
            off_rs = te.getResetLevel();
            off_f = te.getResetFunc();
        } else {
            on_rs = te.getResetLevel();
            on_f = te.getResetFunc();
            off_rs = te.getTriggerLevel();
            off_f = te.getTriggerFunc();
        }

        currenttip.add(String.format(
                EnumChatFormatting.GREEN + "Redstone On  : %s%s %d %s", TAB + ALIGNRIGHT, on_f.toString(), on_rs, units));
        currenttip.add(String.format(EnumChatFormatting.RED + "Redstone Off : %s%s %d %s", TAB + ALIGNRIGHT, off_f.toString(), off_rs, units));
        currenttip.add(String.format("Output Level : %s %d", te.getRedstoneStrength().toString(), te.getRedstoneLevel()));

        return currenttip;
    }

    private List<String> handleTileEntityStepdown(TileEntityStepdown te, List<String> currenttip) {

        currenttip.add(String.format("Stepdown Level : %s%d", TAB + ALIGNRIGHT, te.getOutputLevel()));
        return currenttip;
    }
}
