package org.jglrxavpok.shady.gui;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListExtended;

import com.google.common.collect.Lists;

public class GuiShaderList extends GuiListExtended
{

    private ArrayList<ShaderPassEntry> entries;
    private int                        selected;

    public GuiShaderList(Minecraft mc, int x, int y, int width, int height, int slotHeight)
    {
        super(mc, width, height, y, height - y - 30, slotHeight);
        entries = Lists.newArrayList();
    }

    public void addEntry(ShaderPassEntry entry)
    {
        entries.add(entry);
        entry.list = this;
    }

    public void setSelected(int slotIndex)
    {
        selected = slotIndex;
    }

    public boolean isSelected(int slotIndex)
    {
        return selected == slotIndex;
    }

    @Override
    public IGuiListEntry getListEntry(int index)
    {
        return entries.get(index);
    }

    @Override
    protected int getSize()
    {
        return entries.size();
    }

}
