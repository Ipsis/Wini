package ipsis.wini.gui;

import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementButton;
import cofh.lib.gui.element.ElementTextFieldFiltered;
import cofh.lib.gui.element.TabBase;
import cofh.lib.util.helpers.StringHelper;
import ipsis.wini.gui.element.TabInfo;
import ipsis.wini.gui.element.TabRedstoneOutput;
import ipsis.wini.inventory.ContainerHysteresis;
import ipsis.wini.reference.Lang;
import ipsis.wini.reference.Textures;
import ipsis.wini.tileentity.TileEntityHysteresis;
import ipsis.wini.tileentity.TileEntityHysteresisFluid;
import ipsis.wini.tileentity.TileEntityHysteresisInventory;
import ipsis.wini.utils.CompareFunc;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import java.util.regex.Pattern;

public class GuiHysteresis extends GuiBaseWini {

    private static final String TEXTURE_STR = new String(Textures.Gui.HYSTERESIS);
    private static final ResourceLocation TEXTURE = new ResourceLocation(TEXTURE_STR);

    private TileEntityHysteresis te;

    private ElementButton triggerBtn, resetBtn;
    private ElementButton triggerSaveBtn, resetSaveBtn;
    private ElementButton runningBtn;
    private ElementTextFieldFiltered triggerTextField, resetTextField;
    private String units;

    static final String BTN_TRIGGER_FUNC_STR = "TriggerFunc";
    static final String BTN_RESET_FUNC_STR = "ResetFunc";
    static final String BTN_TRIGGER_SAVE_STR = "TriggerSave";
    static final String BTN_RESET_SAVE_STR = "ResetSave";
    static final String BTN_RUNNING_STR = "Running";

    public GuiHysteresis(EntityPlayer entityPlayer, TileEntityHysteresis te) {

        super(new ContainerHysteresis(entityPlayer, te), TEXTURE);
        this.te = te;
        this.name = getTitle(te);
        this.units = getUnits(te);

        xSize = 176;
        ySize = 166;
        drawInventory = false;
    }

    String getTitle(TileEntityHysteresis te) {
        if (te instanceof TileEntityHysteresisInventory)
            return StringHelper.localize(Lang.Gui.TITLE_HYSTERICAL_INV);
        else if (te instanceof TileEntityHysteresisFluid)
            return StringHelper.localize(Lang.Gui.TITLE_HYSTERICAL_FLUID);

        return StringHelper.localize(Lang.Gui.TITLE_HYSTERICAL_RF);
    }

    String getUnits(TileEntityHysteresis te) {
        if (te instanceof TileEntityHysteresisInventory)
            return StringHelper.localize(Lang.Gui.TITLE_HYSTERICAL_INV + "_units");
        else if (te instanceof TileEntityHysteresisFluid)
            return StringHelper.localize(Lang.Gui.TITLE_HYSTERICAL_FLUID + "_units");

        return StringHelper.localize(Lang.Gui.TITLE_HYSTERICAL_RF + "_units");
    }

    @Override
    public void initGui() {
        super.initGui();

        triggerBtn = new ElementButton(this, 60, 22, BTN_TRIGGER_FUNC_STR, 0, 166, 0, 186, 0, 206, 20, 20, TEXTURE_STR);
        resetBtn = new ElementButton(this, 60, 72, BTN_RESET_FUNC_STR, 0, 166, 0, 186, 0, 206, 20, 20, TEXTURE_STR);
        triggerSaveBtn = new ElementButton(this, 140, 44, BTN_TRIGGER_SAVE_STR, 176, 60, 196, 60, 20, 20, TEXTURE_STR);
        resetSaveBtn = new ElementButton(this, 140, 94, BTN_RESET_SAVE_STR, 176, 60, 196, 60, 176, 40, 20, 20, TEXTURE_STR);
        runningBtn = new ElementButton(this, 152, 142, BTN_RUNNING_STR, 176, 0, 176, 20, 176, 40, 20, 20, TEXTURE_STR);

        triggerTextField = new ElementTextFieldFiltered(this, 90, 48, 40, 12).setFilter(Pattern.compile("[0-9]"), false);
        resetTextField = new ElementTextFieldFiltered(this, 90, 98, 40, 12).setFilter(Pattern.compile("[0-9]"), false);

        addElement(triggerBtn);
        addElement(resetBtn);
        addElement(triggerSaveBtn);
        addElement(resetSaveBtn);
        addElement(runningBtn);
        addElement(triggerTextField);
        addElement(resetTextField);

        addTab(new TabInfo(this, TabBase.LEFT, buildInfoString(Lang.Gui.INFO_HYSTERICAL)));
        addTab(new TabRedstoneOutput(this, TabBase.RIGHT, te));
    }

    @Override
    protected void updateElementInformation() {
        super.updateElementInformation();

        setRunningBtn(te.isEnabled());
        setFuncBtn(this.triggerBtn, te.getTriggerFunc());
        setFuncBtn(this.resetBtn, te.getResetFunc());
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int i, int i1) {
        super.drawGuiContainerForegroundLayer(i, i1);

        fontRendererObj.drawString("Trigger" + " :", 10, 28, 4210752);
        fontRendererObj.drawString("Reset" + " :", 10, 78, 4210752);
        fontRendererObj.drawString(String.format("%d %s", te.getTriggerLevel(), units), 90, 28, 4210752);
        fontRendererObj.drawString(String.format("%d %s", te.getResetLevel(), units), 90, 78, 4210752);
    }

    void setRunningBtn(boolean b) {
        this.runningBtn.setSheetX(b ? 176 : 196);
        this.runningBtn.setDisabledX(b ? 176 : 196);
        this.runningBtn.setHoverX(b ? 176 : 196);
    }

    void setFuncBtn(ElementButton b, CompareFunc f) {
        int xOffset = 20 * f.ordinal();
        b.setSheetX(xOffset);
        b.setDisabledX(xOffset);
        b.setHoverX(xOffset);
    }

    @Override
    public void handleElementButtonClick(String s, int i) {

        if (s.equals(BTN_RUNNING_STR)) {
            te.setEnabled(!te.isEnabled());
            setRunningBtn(te.isEnabled());
            GuiBase.playSound("random.click", 1.0F, 0.4F);
        } else if (s.equals(BTN_TRIGGER_FUNC_STR)) {
            te.setTriggerFunc(te.getTriggerFunc().getNext());
            setFuncBtn(this.triggerBtn, te.getTriggerFunc());
            GuiBase.playSound("random.click", 1.0F, 0.4F);
        } else if (s.equals(BTN_RESET_FUNC_STR)) {
            te.setResetFunc(te.getResetFunc().getNext());
            setFuncBtn(this.resetBtn, te.getResetFunc());
            GuiBase.playSound("random.click", 1.0F, 0.4F);
        } else if (s.equals(BTN_TRIGGER_SAVE_STR)) {
            int val;
            try {
                val = Integer.parseInt(this.triggerTextField.getText());
                te.setTriggerLevel(val);
                GuiBase.playSound("random.click", 1.0F, 0.4F);
            } catch (NumberFormatException e) {
            }
        } else if (s.equals(BTN_RESET_SAVE_STR)) {
            int val;
            try {
                val = Integer.parseInt(this.resetTextField.getText());
                te.setResetLevel(val);
                GuiBase.playSound("random.click", 1.0F, 0.4F);
            } catch (NumberFormatException e) {
            }
        }
    }
}
