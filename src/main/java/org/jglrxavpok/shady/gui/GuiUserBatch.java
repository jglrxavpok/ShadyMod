package org.jglrxavpok.shady.gui;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

import org.jglrxavpok.shady.cpalette.ColorPalette;
import org.jglrxavpok.shady.shaders.passes.LowResPass;

public class GuiUserBatch extends GuiScreen
{

    public static final int BACK_BUTTON = 0;
    private GuiScreen       parent;
    private GuiShaderList   passesList;

    public GuiUserBatch(GuiScreen parent)
    {
        this.parent = parent;
    }

    @SuppressWarnings("unchecked")
    public void initGui()
    {
        buttonList.clear();
        passesList = new GuiShaderList(mc, 20, 30, width, height, 30);
        passesList.addEntry(new ShaderPassEntry(fontRendererObj, new LowResPass()));
        for(ColorPalette palette : ColorPalette.getPalettes().values())
        {
            passesList.addEntry(new ShaderPassEntry(fontRendererObj, palette));
        }

        buttonList.add(new GuiButton(BACK_BUTTON, width / 2 - 100, height - 20, I18n.format("gui.back")));
    }

    public void handleMouseInput() throws IOException
    {
        super.handleMouseInput();
        this.passesList.handleMouseInput();
    }

    public void actionPerformed(GuiButton button)
    {
        if(button.id == BACK_BUTTON)
        {
            mc.displayGuiScreen(parent);
        }
        else
        {
            passesList.actionPerformed(button);
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        drawDefaultBackground();
        passesList.drawScreen(mouseX, mouseY, partialTicks);
        super.drawScreen(mouseX, mouseY, partialTicks);
        drawCenteredString(fontRendererObj, I18n.format("shady.userbatch"), width / 2, 10, 0xFFFFFFFF);
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        if(mouseButton != 0 || !this.passesList.mouseClicked(mouseX, mouseY, mouseButton))
        {
            passesList.setSelected(-1);
            super.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    protected void mouseReleased(int mouseX, int mouseY, int state)
    {
        if(state != 0 || !this.passesList.mouseReleased(mouseX, mouseY, state))
        {
            super.mouseReleased(mouseX, mouseY, state);
        }
    }
}
