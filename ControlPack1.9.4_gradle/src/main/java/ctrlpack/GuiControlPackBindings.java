/* Copyright (c) 2014, Julian Uy
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package ctrlpack;

import java.io.IOException;
import java.util.List;






import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.SoundEvents;
import net.minecraft.stats.StatisticsManager;
import net.minecraft.util.ResourceLocation;
//import net.minecraft.client.Minecraft;
import org.lwjgl.input.*;

public class GuiControlPackBindings extends GuiScreen
{

    public GuiControlPackBindings(GuiScreen parent)
    {
        screenTitle = "Control Pack " + ControlPackMain.instance.currentVersion();
        buttonId = -1;
        parentScreen = parent;
    }

    private int func_20080_j()
    {
        return width / 2 - 155;
    }
    
    // lifted from gameoptions.java
    public String getKeyBindingDescription(int i) {
		return ControlPackMain.translate(ControlPackMain.instance.keyBindings[i].getKeyDescription());
    }
    
    public String getOptionDisplayString(int i)
    {
        int j = ControlPackMain.instance.keyBindings[i].getKeyCode();
        if(j < 0)
        {
            return I18n.format("key.mouseButton", new Object[] {
                Integer.valueOf(j + 101)
            });
        }
		else if (j < 99999)
        {
            return Keyboard.getKeyName(j);
        }
		else
		{
			return "   ";
		}
    }
    // end lifted
    
    @SuppressWarnings("unchecked")
	@Override
	public void initGui()
    {
        screenTitle = ControlPackMain.translate("controlPack.bindingsTitle");

        int i = func_20080_j();
        int j = 0;
        for(; j < ControlPackMain.instance.keyBindings.length; j++) {
            //buttonList.add(new GuiSmallButton(j, i + (j % 2) * 160, height / 6 + 24 * (j >> 1), 70, 20, getOptionDisplayString(j)));
			buttonList.add(new GuiSmallButtonCP(j, i + (j % 2) * 160, height / 6 + 20 * (j >> 1), 70, 20, getOptionDisplayString(j)));
        }

        buttonList.add(new GuiButton(200, width / 2 - 100, height / 9 + 190, 200, 20, ControlPackMain.translate("gui.done")));
    }
    
    @Override
	protected void actionPerformed(GuiButton guibutton) {
        for(int i = 0; i < ControlPackMain.instance.keyBindings.length; i++) {
            ((GuiButton)buttonList.get(i)).displayString = getOptionDisplayString(i);
        }

		if(guibutton.id == 200) {
            mc.displayGuiScreen(parentScreen);
        }
        else if (guibutton.id < 100) {
            buttonId = guibutton.id;
            guibutton.displayString = (new StringBuilder()).append("> ").append(getOptionDisplayString(guibutton.id)).append(" <").toString();
        }
    }

    @Override
	protected void mouseClicked(int i, int j, int k) throws IOException {
        if(buttonId >= 0 && buttonId < 100) {
            ControlPackMain.instance.keyBindings[buttonId].setKeyCode(-100 + k);
            ControlPackMain.instance.saveOptions();

            ((GuiButton)buttonList.get(buttonId)).displayString = getOptionDisplayString(buttonId);
            buttonId = -1;
        }
        else {
			if (k == 1) /* right click */
			{
				for (int ci = 0; ci < buttonList.size(); ci++)
				{
					GuiButton guibutton = (GuiButton)buttonList.get(ci);

					if (guibutton.mousePressed(mc, i, j))
					{
						ControlPackMain.instance.keyBindings[guibutton.id].setKeyCode(99999);
						ControlPackMain.instance.saveOptions();

						((GuiButton)buttonList.get(guibutton.id)).displayString = getOptionDisplayString(guibutton.id);
						buttonId = -1;
						this.mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
						return;
					}
				}
			}		
		
            super.mouseClicked(i, j, k);
        }
    }

    @Override
	protected void keyTyped(char c, int i) throws IOException {
        if(buttonId >= 0 && buttonId < 100) {
            ControlPackMain.instance.keyBindings[buttonId].setKeyCode(i);
            ControlPackMain.instance.saveOptions();
            
            ((GuiButton)buttonList.get(buttonId)).displayString = getOptionDisplayString(buttonId);
            buttonId = -1;
        }
        else {
            super.keyTyped(c, i);
        }
    }

    @Override
	public void drawScreen(int i, int j, float f) {
        drawDefaultBackground();
        //drawCenteredString(fontRenderer, screenTitle, width / 2, 5, 0xffffff);
		drawCenteredString(fontRendererObj, screenTitle, width / 2, 2, 0xffffff);
		//drawCenteredString(fontRenderer, "You may RIGHT CLICK to disable a command.", width / 2, 25, 0x5555ff);
		drawCenteredString(fontRendererObj, "You may RIGHT CLICK to disable a command.", width / 2, 15, 0x5555ff);
        
        int k = func_20080_j();
        
        for(int l = 0; l < ControlPackMain.instance.keyBindings.length; l++) {
            boolean flag = false;
            if (ControlPackMain.instance.keyBindings[l] != ControlPackMain.instance.keyBindSayLocation) { // has its own "context"
                for(int i1 = 0; i1 < ControlPackMain.instance.keyBindings.length; i1++) {
                    if(i1 != l && ControlPackMain.instance.keyBindings[l].getKeyCode() == ControlPackMain.instance.keyBindings[i1].getKeyCode()) {
                        if (ControlPackMain.instance.keyBindings[i1] != ControlPackMain.instance.keyBindSayLocation) { // has its own "context"
                            flag = true;
                        }
                    }
                }
            }

            int j1 = l;
            if(buttonId == l) {
                ((GuiButton)buttonList.get(j1)).displayString = "\247f> \247e??? \247f<";
            }
            else if(flag) {
                ((GuiButton)buttonList.get(j1)).displayString = (new StringBuilder()).append("\247c").append(getOptionDisplayString(j1)).toString();
            }
            else {
                ((GuiButton)buttonList.get(j1)).displayString = getOptionDisplayString(j1);
            }
            //drawString(fontRenderer, getKeyBindingDescription(l), k + (l % 2) * 160 + 70 + 6, height / 6 + 24 * (l >> 1) + 7, -1);
			drawString(fontRendererObj, getKeyBindingDescription(l), k + (l % 2) * 160 + 70 + 6, height / 6 + 20 * (l >> 1) + 7, -1);
        }

        super.drawScreen(i, j, f);
    }

    private GuiScreen parentScreen;
    protected String screenTitle;
    private int buttonId;
}
