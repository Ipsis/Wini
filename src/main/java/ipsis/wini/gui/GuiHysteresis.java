package ipsis.wini.gui;

import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementButton;
import cofh.lib.gui.element.ElementTextFieldFiltered;
import cofh.lib.gui.element.TabBase;
import ipsis.wini.gui.element.TabInfo;
import ipsis.wini.gui.element.TabRedstoneOutput;
import ipsis.wini.inventory.ContainerHysteresis;
import ipsis.wini.reference.Lang;
import ipsis.wini.reference.Textures;
import ipsis.wini.tileentity.TileEntityHysteresis;
import ipsis.wini.utils.CompareFunc;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import scala.Int;

import java.util.regex.Pattern;

public class GuiHysteresis extends GuiBaseWini {

    private static final String TEXTURE_STR = new String(Textures.Gui.HYSTERESIS);
    private static final ResourceLocation TEXTURE = new ResourceLocation(TEXTURE_STR);

    private TileEntityHysteresis te;

    private ElementButton triggerBtn, resetBtn;
    private ElementButton triggerSaveBtn, resetSaveBtn;
    private ElementButton runningBtn;
    private ElementTextFieldFiltered triggerTextField, resetTextField;

    static final String BTN_TRIGGER_FUNC_STR = "TriggerFunc";
    static final String BTN_RESET_FUNC_STR = "ResetFunc";
    static final String BTN_TRIGGER_SAVE_STR = "TriggerSave";
    static final String BTN_RESET_SAVE_STR = "ResetSave";
    static final String BTN_RUNNING_STR = "Running";

    public GuiHysteresis(EntityPlayer entityPlayer, TileEntityHysteresis te) {

        super(new ContainerHysteresis(entityPlayer, te), TEXTURE);
        this.te = te;
        this.name = "Some TEst";
        xSize = 176;
        ySize = 166;
        drawInventory = false;
    }

    @Override
    public void initGui() {
        super.initGui();

        triggerBtn = new ElementButton(this, 10, 40, BTN_TRIGGER_FUNC_STR, 0, 166, 0, 186, 0, 206, 20, 20, TEXTURE_STR);
        resetBtn = new ElementButton(this, 10, 90, BTN_RESET_FUNC_STR, 0, 166, 0, 186, 0, 206, 20, 20, TEXTURE_STR);
        triggerSaveBtn = new ElementButton(this, 140, 40, BTN_TRIGGER_SAVE_STR, 176, 60, 196, 60, 20, 20, TEXTURE_STR);
        resetSaveBtn = new ElementButton(this, 140, 90, BTN_RESET_SAVE_STR, 176, 60, 196, 60, 176, 40, 20, 20, TEXTURE_STR);
        runningBtn = new ElementButton(this, 152, 142, BTN_RUNNING_STR, 176, 0, 176, 20, 176, 40, 20, 20, TEXTURE_STR);

        triggerTextField = new ElementTextFieldFiltered(this, 40, 44, 60, 12).setFilter(Pattern.compile("[0-9]"), false);
        resetTextField = new ElementTextFieldFiltered(this, 40, 94, 60, 12).setFilter(Pattern.compile("[0-9]"), false);

        addElement(triggerBtn);
        addElement(resetBtn);
        addElement(triggerSaveBtn);
        addElement(resetSaveBtn);
        addElement(runningBtn);
        addElement(triggerTextField);
        addElement(resetTextField);

        triggerTextField.setText(Integer.toString(te.getTriggerLevel()));
        resetTextField.setText(Integer.toString(te.getResetLevel()));

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
        fontRendererObj.drawString("Items", 108, 46, 4210752);
        fontRendererObj.drawString("Items", 108, 96, 4210752);
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

        boolean txUpdate = true;
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
                txUpdate = false;
            }
        } else if (s.equals(BTN_RESET_SAVE_STR)) {
            int val;
            try {
                val = Integer.parseInt(this.resetTextField.getText());
                te.setResetLevel(val);
                GuiBase.playSound("random.click", 1.0F, 0.4F);
            } catch (NumberFormatException e) {
                txUpdate = false;
            }
        }

        /* Update the server */
        if (txUpdate)
            this.te.sendMessageHysteresisCfgServer();
    }
}
