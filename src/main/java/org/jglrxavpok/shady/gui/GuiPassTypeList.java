package org.jglrxavpok.shady.gui;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListExtended;

import com.google.common.collect.Lists;

public class GuiPassTypeList extends GuiListExtended
{

    private ArrayList<PassTypeEntry> entries;
    private int                      selected;

    public GuiPassTypeList(Minecraft mc, int x, int y, int width, int height, int slotHeight)
    {
        super(mc, width, height, y, height - y - 40, slotHeight);
        entries = Lists.newArrayList();
        selected = -1;
    }

    public void addEntry(PassTypeEntry entry)
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

    public int getSelectedIndex()
    {
        return selected;
    }

    public PassTypeEntry getSelected()
    {
        if(selected != -1)
            return (PassTypeEntry) getListEntry(selected);
        return null;
    }

}
