package org.jglrxavpok.shady.shaders;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.util.JsonException;
import net.minecraft.util.ResourceLocation;

import org.jglrxavpok.shady.ShadyMod;
import org.jglrxavpok.shady.ShadyResManager;
import org.jglrxavpok.shady.VirtualResource;

import com.google.common.collect.Lists;
import com.google.gson.stream.JsonWriter;

public class ShaderBatch
{

    private static int       NUM = 0;
    private List<ShaderPass> passes;
    private int              id;
    private ResourceLocation location;
    private String[]         targets;

    public ShaderBatch()
    {
        this.id = NUM++ ;
        this.location = new ResourceLocation(ShadyMod.MODID, "shaders/post/virtual" + id + ".json");
        passes = Lists.newArrayList();
    }

    public List<ShaderPass> getPasses()
    {
        return passes;
    }

    public void addPass(ShaderPass pass)
    {
        passes.add(pass);
    }

    public void init()
    {
        targets = new String[passes.size() == 1 ? 1 : 2];
        for(int i = 0; i < targets.length; i++ )
            targets[i] = "swap_" + i;
        ShadyResManager resManager = ShadyMod.instance.getResourceManager();
        for(ShaderPass pass : getPasses())
        {
            pass.init();
            pass.registerVirtuals(resManager);
        }

        try
        {
            StringWriter stringWriter = new StringWriter();
            JsonWriter writer = new JsonWriter(stringWriter);
            writer.setIndent("    ");
            writer.beginObject();
            {
                writer.name("targets");
                writer.beginArray();
                {
                    for(String target : targets)
                        writer.value(target);
                }
                writer.endArray();

                writer.name("passes");
                writer.beginArray();
                {
                    int passIndex = 0;
                    for(ShaderPass pass : passes)
                    {
                        writer.beginObject();
                        writer.name("name");
                        writer.value(pass.getProgram());
                        writer.name("intarget");
                        writer.value(getInTarget(passIndex));

                        writer.name("outtarget");
                        writer.value(getOutTarget(passIndex));
                        writer.endObject();

                        passIndex++ ;
                    }

                    writer.beginObject();
                    writer.name("name");
                    writer.value("blit");
                    writer.name("intarget");
                    writer.value(getInTarget(passIndex));

                    writer.name("outtarget");
                    writer.value("minecraft:main");
                    writer.endObject();
                }
                writer.endArray();
            }
            writer.endObject();
            writer.flush();
            writer.close();
            stringWriter.flush();
            stringWriter.close();
            String string = stringWriter.getBuffer().toString();
            System.out.println(string);
            resManager.register(new VirtualResource(getResourceLocation(), string.getBytes()));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private String getOutTarget(int passIndex)
    {
        return getInTarget(passIndex + 1);
    }

    private String getInTarget(int target)
    {
        if(target == 0)
        {
            return "minecraft:main";
        }
        return targets[target - 1];
    }

    public ResourceLocation getResourceLocation()
    {
        return location;
    }

    public ShaderGroup toShaderGroup(ShadyResManager resManager) throws JsonException
    {
        return new ShaderGroup(Minecraft.getMinecraft().renderEngine, resManager, Minecraft.getMinecraft().getFramebuffer(), getResourceLocation());
    }
}
