package org.jglrxavpok.shady.gui;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

import org.jglrxavpok.shady.cpalette.ColorPalette;
import org.jglrxavpok.shady.shaders.passes.DummyPass;
import org.jglrxavpok.shady.shaders.passes.LowResPass;

public class GuiUserBatch extends GuiScreen
{

    public static final int BACK_BUTTON   = 0;
    public static final int ADD_BUTTON    = 1;
    public static final int EDIT_BUTTON   = 2;
    public static final int REMOVE_BUTTON = 3;
    private GuiScreen       parent;
    private GuiShaderList   passesList;
    private GuiButton       addButton;
    private GuiButton       editButton;
    private GuiButton       removeButton;

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

        addButton = new GuiButton(ADD_BUTTON, 10, height - 20 - 50, I18n.format("shady.addPass"));
        editButton = new GuiButton(EDIT_BUTTON, width - 10 - 200, height - 20 - 50, I18n.format("shady.editPass"));
        removeButton = new GuiButton(REMOVE_BUTTON, width - 10 - 200, height - 20 - 25, I18n.format("shady.removePass"));

        buttonList.add(addButton);
        buttonList.add(editButton);
        buttonList.add(removeButton);
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
        else if(button.id == ADD_BUTTON)
        {
            passesList.addEntry(new ShaderPassEntry(fontRendererObj, new DummyPass()));
            passesList.setSelected(passesList.getSize() - 1);
            openEditGui();
        }
        else if(button.id == EDIT_BUTTON)
        {
            openEditGui();
        }
        else if(button.id == REMOVE_BUTTON)
        {
            ;
        }
        else
        {
            passesList.actionPerformed(button);
        }
    }

    private void openEditGui()
    {
        if(passesList.getSelectedIndex() != -1)
        {
            ShaderPassEntry entry = passesList.getSelected();
            mc.displayGuiScreen(new GuiEditPass(this, entry));
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        drawDefaultBackground();
        passesList.drawScreen(mouseX, mouseY, partialTicks);
        super.drawScreen(mouseX, mouseY, partialTicks);
        drawCenteredString(fontRendererObj, I18n.format("shady.userbatch"), width / 2, 10, 0xFFFFFFFF);
    }

    public void updateScreen()
    {
        super.updateScreen();
        boolean slotSelected = passesList.getSelectedIndex() != -1;
        addButton.enabled = slotSelected;
        editButton.enabled = slotSelected;
        removeButton.enabled = slotSelected;
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        if(mouseButton != 0 || !this.passesList.mouseClicked(mouseX, mouseY, mouseButton))
        {
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
