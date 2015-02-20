package org.jglrxavpok.shady.cpalette;

import java.io.IOException;

import org.jglrxavpok.shady.ShadyMod;

import net.minecraft.util.ResourceLocation;

public class DefaultColorPalettes
{

    public static ColorPalette NES;
    public static ColorPalette GB;
    public static ColorPalette GB_GRAY;
    public static ColorPalette DOOM;
    public static ColorPalette CGA;
    public static ColorPalette COMMODORE64;
    
    public static void init()
    {
        try
        {
            NES = ColorPalette.create("NES", new ResourceLocation(ShadyMod.MODID, "textures/palettes/nes.png"));
            GB = ColorPalette.create("GBA", new ResourceLocation(ShadyMod.MODID, "textures/palettes/gameboy.png"));
            GB_GRAY = ColorPalette.create("GBA_GRAY", new ResourceLocation(ShadyMod.MODID, "textures/palettes/gameboy_gray.png"));
            DOOM = ColorPalette.create("DOOM", new ResourceLocation(ShadyMod.MODID, "textures/palettes/doom.png"));
            CGA = ColorPalette.create("CGA", new ResourceLocation(ShadyMod.MODID, "textures/palettes/cga.png"));
            COMMODORE64 = ColorPalette.create("COMMODORE64", new ResourceLocation(ShadyMod.MODID, "textures/palettes/c64.png"));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
