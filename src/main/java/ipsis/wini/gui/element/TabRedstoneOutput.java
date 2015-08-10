package ipsis.wini.gui.element;

import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.TabBase;
import cofh.lib.util.helpers.StringHelper;
import ipsis.wini.reference.Lang;
import ipsis.wini.reference.Textures;
import ipsis.wini.utils.IRedstoneOutput;
import org.lwjgl.opengl.GL11;

import java.util.List;

/**
 * Based off TabRedstone from CoFHCore
 */
public class TabRedstoneOutput extends TabBase {

    IRedstoneOutput myContainer;

    public TabRedstoneOutput(GuiBase gui, IRedstoneOutput container) {
        this(gui, 1, container);
    }

    public TabRedstoneOutput(GuiBase gui, int side, IRedstoneOutput container) {
        super(gui, side);

        backgroundColor = Textures.Gui.REDSTONE_TAB_BACKGROUND;
        if (side == LEFT)
            this.setTexture(Textures.RESOURCE_PREFIX + Textures.Gui.TAB_LEFT, 256, 256);
        else
            this.setTexture(Textures.RESOURCE_PREFIX + Textures.Gui.TAB_RIGHT, 256, 256);

        maxHeight = 116;
        maxWidth = 120;
        myContainer = container;
    }

    @Override
    public void draw() {

        drawBackground();
        drawTabIcon("Icon_Redstone");
        if (!isFullyOpened()) {
            return;
        }

        getFontRenderer().drawStringWithShadow("Redstone Strength", posXOffset() + 20, posY + 6, headerColor);
        getFontRenderer().drawStringWithShadow("Redstone Sense", posXOffset() + 20, posY + 42, headerColor);
        getFontRenderer().drawStringWithShadow("Redstone Value", posXOffset() + 20, posY + 78, headerColor);

        if (myContainer.getRedstoneStrength() == IRedstoneOutput.Strength.STRONG) {
            gui.drawButton("Icon_RS_Strong", posX() + 38, posY + 20, 1, 0);
            gui.drawButton("Icon_RS_Weak", posX() + 58, posY + 20, 1, 2);
        } else {
            gui.drawButton("Icon_RS_Strong", posX() + 38, posY + 20, 1, 2);
            gui.drawButton("Icon_RS_Weak", posX() + 58, posY + 20, 1, 0);
        }

        if (myContainer.getRedstoneSense() == IRedstoneOutput.Sense.NORMAL) {
            gui.drawButton("Icon_RS_Normal", posX() + 38, posY + 56, 1, 0);
            gui.drawButton("Icon_RS_Invert", posX() + 58, posY + 56, 1, 2);
        } else {
            gui.drawButton("Icon_RS_Normal", posX() + 38, posY + 56, 1, 2);
            gui.drawButton("Icon_RS_Invert", posX() + 58, posY + 56, 1, 0);
        }

        gui.drawButton("Icon_Dec", posX() + 28, posY + 92, 1, 0);
        gui.drawButton("Icon_Inc", posX() + 68, posY + 92, 1, 0);

        getFontRenderer().drawString(Integer.toString(myContainer.getRedstoneLevel()), posXOffset() + 48, posY + 96, textColor);

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    protected void drawBackground() {

        super.drawBackground();

        if (!isFullyOpened()) {
            return;
        }

        float colorR = (backgroundColor >> 16 & 255) / 255.0F * 0.6F;
        float colorG = (backgroundColor >> 8 & 255) / 255.0F * 0.6F;
        float colorB = (backgroundColor & 255) / 255.0F * 0.6F;
        GL11.glColor4f(colorR, colorG, colorB, 1.0F);
        gui.drawTexturedModalRect(posX() + 24, posY + 16, 16, 20, 64, 24);
        gui.drawTexturedModalRect(posX() + 24, posY + 52, 16, 20, 64, 24);
        gui.drawTexturedModalRect(posX() + 24, posY + 88, 16, 20, 64, 24);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public boolean onMousePressed(int mouseX, int mouseY, int mouseButton) {

        if (!isFullyOpened()) {
            return false;
        }
        if (side == LEFT) {
            mouseX += currentWidth;
        }
        mouseX -= currentShiftX;
        mouseY -= currentShiftY;

        if (mouseX < 28 || mouseX > 110 || mouseY < 20 || mouseY > 106)
            return false;

        if (mouseX >= 38 && mouseX <= 54 && mouseY >= 20 && mouseY <= 36) {
            if (myContainer.getRedstoneStrength() == IRedstoneOutput.Strength.WEAK) {
                myContainer.setRedstoneStrength(IRedstoneOutput.Strength.STRONG);
                GuiBase.playSound("random.click", 1.0F, 0.4F);
            }
        } else if (mouseX >= 58 && mouseX <= 74 && mouseY >= 20 && mouseY <= 36) {
            if (myContainer.getRedstoneStrength() == IRedstoneOutput.Strength.STRONG) {
                myContainer.setRedstoneStrength(IRedstoneOutput.Strength.WEAK);
                GuiBase.playSound("random.click", 1.0F, 0.4F);
            }
        } else if (mouseX >= 38 && mouseX <= 54 && mouseY >= 56 && mouseY <= 72) {
            if (myContainer.getRedstoneSense() == IRedstoneOutput.Sense.INVERTED) {
                myContainer.setRedstoneSense(IRedstoneOutput.Sense.NORMAL);
                GuiBase.playSound("random.click", 1.0F, 0.4F);
            }
        } else if (mouseX >= 58 && mouseX <= 74 && mouseY >= 56 && mouseY <= 72) {
            if (myContainer.getRedstoneSense() == IRedstoneOutput.Sense.NORMAL) {
                myContainer.setRedstoneSense(IRedstoneOutput.Sense.INVERTED);
                GuiBase.playSound("random.click", 1.0F, 0.4F);
            }
        } else if (mouseX >= 28 && mouseX <= 44 && mouseY >= 90 && mouseY <= 106) {
            myContainer.decRedstoneLevel();
            GuiBase.playSound("random.click", 1.0F, 0.4F);
        } else if (mouseX >= 68 && mouseX <= 84 && mouseY >= 90 && mouseY <= 106) {
            myContainer.incRedstoneLevel();
            GuiBase.playSound("random.click", 1.0F, 0.4F);
        }

        return true;
    }

    @Override
    public void addTooltip(List<String> list) {

        int x = gui.getMouseX() - currentShiftX;
        int y = gui.getMouseY() - currentShiftY;

        if (x >= 38 && x <= 54 && y >= 20 && y <= 36) {
            list.add(StringHelper.localize(Lang.Gui.TIP_STRONG_RS));
        } else if (x >= 58 && x <= 74 && y >= 20 && y <= 36) {
            list.add(StringHelper.localize(Lang.Gui.TIP_WEAK_RS));
        } else if (x >= 38 && x <= 54 && y >= 56 && y <= 72) {
            list.add(StringHelper.localize(Lang.Gui.TIP_NORM_SENSE));
        } else if (x >= 58 && x <= 74 && y >= 56 && y <= 72) {
            list.add(StringHelper.localize(Lang.Gui.TIP_INV_SENSE));
        } else if (x >= 28 && x <= 44 && y >= 90 && y <= 106) {
            list.add(StringHelper.localize(Lang.Gui.TIP_DEC_RS));
        } else if (x >= 68 && x <= 84 && y >= 90 && y <= 106) {
            list.add(StringHelper.localize(Lang.Gui.TIP_INC_RS));
        }
    }
}
