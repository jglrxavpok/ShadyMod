package org.jglrxavpok.shady;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.shader.ShaderLinkHelper;
import net.minecraft.client.util.JsonException;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent;
import net.minecraftforge.client.event.GuiScreenEvent.DrawScreenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.jglrxavpok.shady.cpalette.ColorPalette;
import org.jglrxavpok.shady.cpalette.DefaultColorPalettes;
import org.jglrxavpok.shady.gui.GuiIconButton;
import org.jglrxavpok.shady.shaders.ShaderBatch;
import org.jglrxavpok.shady.shaders.passes.LowResPass;

@Mod(modid = ShadyMod.MODID, version = ShadyMod.VERSION, name = "Shady")
public class ShadyMod
{
    public static final String MODID   = "shady";
    public static final String VERSION = "Pre-0.1";

    @Instance(MODID)
    public static ShadyMod     instance;
    private ShadyResManager    resManager;
    private Configuration      config;
    private String             paletteID;
    private boolean            enabled;
    private ShaderBatch        batch;
    private ColorPalette       palette;

    public ShadyResManager getResourceManager()
    {
        return resManager;
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        if(event.getSide().isServer())
        {
            throw new IllegalStateException("The Shady mod can't be loaded on servers");
        }

        resManager = new ShadyResManager(Minecraft.getMinecraft().getResourceManager());
        MinecraftForge.EVENT_BUS.register(this);
        config = new Configuration(event.getSuggestedConfigurationFile());
        enabled = config.getBoolean("enabled", Configuration.CATEGORY_GENERAL, true, "Are shaders enabled?");
        paletteID = config.getString("palette", Configuration.CATEGORY_GENERAL, "none", "The id of the palette");
        config.save();

        DefaultColorPalettes.init();
        setPalette(DefaultColorPalettes.NES);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        ShaderLinkHelper.setNewStaticShaderLinkHelper();
    }

    public void setPalette(ColorPalette palette)
    {
        this.palette = palette;
        if(palette != null)
        {
            paletteID = palette.getID();
            batch = new ShaderBatch();
            batch.addPass(palette); // TODO: Replace the palette, plz
            batch.addPass(new LowResPass());
            batch.init();
        }
        else
            paletteID = "none";
        updateConfig();
    }

    public void activatePalette()
    {
        enabled = true;
        updateConfig();

        if(batch != null)
        {
            if(OpenGlHelper.shadersSupported)
            {
                try
                {
                    ShaderGroup theShaderGroup = batch.toShaderGroup(resManager);
                    theShaderGroup.createBindFramebuffers(Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
                    ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, Minecraft.getMinecraft().entityRenderer, theShaderGroup, 51);
                    ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, Minecraft.getMinecraft().entityRenderer, true, 55);
                }
                catch(JsonException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public void disactivePalette()
    {
        enabled = false;
        updateConfig();
        ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, Minecraft.getMinecraft().entityRenderer, null, 51);
        ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, Minecraft.getMinecraft().entityRenderer, false, 55);
    }

    private void updateConfig()
    {
        config.get(Configuration.CATEGORY_GENERAL, "enabled", true).set(enabled);
        config.get(Configuration.CATEGORY_GENERAL, "palette", "none").set(paletteID);
        config.save();
    }

    @SubscribeEvent
    public void onActionPerformed(ActionPerformedEvent event)
    {
        if(event.gui instanceof GuiOptions)
        {
            if(event.button.id == 0x42)
            {
                //                Minecraft.getMinecraft().displayGuiScreen(new GuiSelectPalette());
                activatePalette();
            }
        }
    }

    @SubscribeEvent
    public void onGuiInit(DrawScreenEvent event)
    {
        if(event.gui instanceof GuiMainMenu)
        {
            if(!OpenGlHelper.shadersSupported)
            {
                String t = "you won't be able to enjoy ShadyMod and its children ;(";
                FontRenderer font = Minecraft.getMinecraft().fontRendererObj;
                Gui.drawRect(0, 0, font.getStringWidth(t), 20, 0x90000000);
                font.drawStringWithShadow("Shaders are not supported by your graphical card,", 0, 0, 0xFFFF0707);
                font.drawStringWithShadow(t, 0, 10, 0xFFFF0707);
            }
        }
    }

    @SuppressWarnings("unchecked")
    @SubscribeEvent
    public void onGuiInit(InitGuiEvent event)
    {
        if(event.gui instanceof GuiOptions)
        {
            int x = event.gui.width / 2 + 5 + 150 + 5;
            int y = event.gui.height / 6 + 48 - 6;
            event.buttonList.add(new GuiIconButton(0x42, x, y, new ResourceLocation(MODID, "textures/gui/palette.png")));
        }
    }
}
