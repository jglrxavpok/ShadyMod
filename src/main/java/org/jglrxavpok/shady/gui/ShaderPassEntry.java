package org.jglrxavpok.shady.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiListExtended.IGuiListEntry;

import org.jglrxavpok.shady.shaders.ShaderPass;

public class ShaderPassEntry implements IGuiListEntry
{

    private ShaderPass   pass;
    private FontRenderer font;
    public GuiShaderList list;

    public ShaderPassEntry(FontRenderer font, ShaderPass pass)
    {
        this.font = font;
        this.pass = pass;
    }

    public ShaderPass getPass()
    {
        return pass;
    }

    @Override
    public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_)
    {
        ;
    }

    @Override
    public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected)
    {
        font.drawString(pass.getName(), x, y, 0xFFFFFFFF);
    }

    @Override
    public boolean mousePressed(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY)
    {
        return false;
    }

    @Override
    public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY)
    {
        if(isMouseOver(slotIndex, x, y))
            list.setSelected(slotIndex);
    }

    private boolean isMouseOver(int slotIndex, int x, int y)
    {
        return list.getSlotIndexFromScreenCoords(x, y) == slotIndex && list.isMouseYWithinSlotBounds(y);
    }

}
