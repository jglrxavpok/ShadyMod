package org.jglrxavpok.shady.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

import org.jglrxavpok.shady.shaders.PassRegistry;

import com.mojang.realmsclient.gui.ChatFormatting;

public class GuiEditPass extends GuiScreen
{

    private static final int BACK_BUTTON      = 0;
    private static final int ADD_BUTTON       = 1;
    private static final int PASS_TYPE_BUTTON = 2;
    private ShaderPassEntry  entry;
    private GuiScreen        parent;
    private String           passName;
    private String           passType;

    public GuiEditPass(GuiScreen parent, ShaderPassEntry entry)
    {
        this.parent = parent;
        this.entry = entry;
        passName = entry.getPass().getName();
        passType = PassRegistry.getID(entry.getPass().getProvider());
    }

    @SuppressWarnings("unchecked")
    public void initGui()
    {
        buttonList.clear();

        buttonList.add(new GuiButton(PASS_TYPE_BUTTON, width / 2 - 200 - 5, 70, I18n.format("shady.select.passtype")));

        buttonList.add(new GuiButton(BACK_BUTTON, width / 2 + 5, height - height / 8, I18n.format("gui.back")));
        buttonList.add(new GuiButton(ADD_BUTTON, width / 2 - 200 - 5, height - height / 8, I18n.format("shady.create")));
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        drawDefaultBackground();
        drawCenteredString(fontRendererObj, I18n.format("shady.editPass.title"), width / 2, 10, 0xFFFFFFFF);

        drawCenteredString(fontRendererObj, ChatFormatting.UNDERLINE + I18n.format("shady.pass.name"), width / 2, 30, 0xFFFFFFFF);

        fontRendererObj.drawStringWithShadow(I18n.format("shady.currentPass") + ": " + I18n.format("shady.pass.type." + passType), width / 2 + 5, 75, 0xFFFFFFFF);
        fontRendererObj.drawStringWithShadow(passName, width / 2 - 200 - 5, 50, 0xFFFFFFFF);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void actionPerformed(GuiButton button)
    {
        if(button.id == BACK_BUTTON)
        {
            mc.displayGuiScreen(parent);
        }
        else if(button.id == ADD_BUTTON)
        {
            // TODO: Add changes to entry
            mc.displayGuiScreen(parent);
        }
        else if(button.id == PASS_TYPE_BUTTON)
        {
            mc.displayGuiScreen(new GuiSelectPassType(this));
        }
    }

    public void confirmType(String type)
    {
        this.passType = type;
    }
}
