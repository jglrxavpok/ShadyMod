package org.jglrxavpok.shady.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiSelectPassType extends GuiScreen
{

    private GuiEditPass parent;

    public GuiSelectPassType(GuiEditPass parent)
    {
        this.parent = parent;
    }

    public void initGui()
    {
        buttonList.clear();
    }

    public void actionPerformed(GuiButton button)
    {
        ;
    }

    public void drawScreen(int mx, int my, float partialTicks)
    {
        super.drawScreen(mx, my, partialTicks);
    }

}
