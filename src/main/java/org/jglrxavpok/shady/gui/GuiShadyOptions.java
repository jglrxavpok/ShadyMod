package org.jglrxavpok.shady.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

import org.jglrxavpok.shady.ShadyMod;

public class GuiShadyOptions extends GuiScreen
{

    public static final int BACK_BUTTON   = 0;
    public static final int ENABLE_BUTTON = 1;
    private GuiScreen       parent;
    private GuiButton       enableButton;

    public GuiShadyOptions(GuiScreen parent)
    {
        this.parent = parent;
    }

    @SuppressWarnings("unchecked")
    public void initGui()
    {
        buttonList.clear();
        buttonList.add(new GuiButton(BACK_BUTTON, width / 2 - 100, height - 20, I18n.format("gui.back")));

        enableButton = new GuiButton(ENABLE_BUTTON, width / 2 - 100, height / 8, I18n.format("shady.enable." + (ShadyMod.instance.isEnabled() ? "on" : "off")));
        buttonList.add(enableButton);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTick)
    {
        drawDefaultBackground();
        drawCenteredString(fontRendererObj, I18n.format("shady.configuration"), width / 2, 10, 0xFFFFFFFF);
        super.drawScreen(mouseX, mouseY, partialTick);
    }

    public void actionPerformed(GuiButton button)
    {
        if(button.id == BACK_BUTTON)
        {
            mc.displayGuiScreen(parent);
        }
        else if(button.id == ENABLE_BUTTON)
        {
            ShadyMod.instance.setEnabled(!ShadyMod.instance.isEnabled());
            enableButton.displayString = I18n.format("shady.enable." + (ShadyMod.instance.isEnabled() ? "on" : "off"));
        }
    }
}
