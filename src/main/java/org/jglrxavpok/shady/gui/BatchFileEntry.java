package org.jglrxavpok.shady.gui;

import java.io.File;

import joptsimple.internal.Strings;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiListExtended.IGuiListEntry;

public class BatchFileEntry implements IGuiListEntry
{

    private FontRenderer      font;
    public GuiBatchesFileList list;
    private File              file;
    private String            filepath;

    public BatchFileEntry(FontRenderer font, File file)
    {
        this.font = font;
        this.file = file;
        String[] hierarchy = file.getAbsolutePath().split("\\\\");
        int maxDepth = 5;
        maxDepth = Math.min(maxDepth, hierarchy.length);

        String[] pieces = new String[maxDepth];
        for(int i = hierarchy.length - maxDepth; i < hierarchy.length; i++ )
        {
            pieces[i - (hierarchy.length - maxDepth)] = hierarchy[i];
        }
        this.filepath = Strings.join(pieces, "/");
    }

    public File getFile()
    {
        return file;
    }

    @Override
    public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_)
    {
        ;
    }

    @Override
    public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected)
    {
        font.drawString(file.getName().replace(".batch", ""), x, y, 0xFFFFFFFF);
        font.drawString(filepath, x + 10, y + 11, 0xFF707070);
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

}
