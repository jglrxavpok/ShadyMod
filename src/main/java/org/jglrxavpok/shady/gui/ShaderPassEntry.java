package org.jglrxavpok.shady.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiListExtended.IGuiListEntry;
import net.minecraft.client.resources.I18n;

import org.jglrxavpok.shady.shaders.PassRegistry;
import org.jglrxavpok.shady.shaders.ShaderPass;

public class ShaderPassEntry implements IGuiListEntry
{

    private ShaderPass   pass;
    private FontRenderer font;
    public GuiShaderList list;
    private String       name;

    public ShaderPassEntry(FontRenderer font, ShaderPass pass)
    {
        this(font, pass, pass.getName());
    }

    public ShaderPassEntry(FontRenderer font, ShaderPass pass, String name)
    {
        this.font = font;
        this.pass = pass;
        this.name = name;
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
        font.drawString(name, x, y, 0xFFFFFFFF);
        font.drawString(I18n.format("shady.pass.type." + PassRegistry.getID(pass)), x + 10, y + 10, 0xFF707070);
    }

    @Override
    public boolean mousePressed(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY)
    {
        if(isMouseOver(slotIndex, x, y))
            list.setSelected(slotIndex);
        return false;
    }

    @Override
    public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY)
    {
    }

    private boolean isMouseOver(int slotIndex, int x, int y)
    {
        return list.getSlotIndexFromScreenCoords(x, y) == slotIndex && list.isMouseYWithinSlotBounds(y);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setPass(ShaderPass pass)
    {
        this.pass = pass;
    }

}
