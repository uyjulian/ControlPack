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

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;


public class GuiControlPack extends GuiScreen
{
    public GuiControlPack()
    {
        screenTitle = "Control Pack " + ControlPackMain.instance.currentVersion();
    }
    
    @Override
	public void initGui()
    {
		screenTitle = ControlPackMain.translate("controlPack.title");

		buttonList.add(new GuiButton(204, width / 2 - 75, height / 9 + 80, 150, 20, ControlPackMain.translate("options.cpwaypoints")));
		buttonList.add(new GuiButton(203, width / 2 - 75, height / 9 + 105, 150, 20, ControlPackMain.translate("options.cpoptions")));
		buttonList.add(new GuiButton(202, width / 2 - 75, height / 9 + 130, 150, 20, ControlPackMain.translate("options.cpbindings")));
		buttonList.add(new GuiButton(201, width / 2 - 75, height / 9 + 155, 150, 20, ControlPackMain.translate("options.volume")));
		buttonList.add(new GuiButton(205, width / 2 - 75, height / 9 + 180, 150, 20, ControlPackMain.translate("options.itemSettings")));

        buttonList.add(new GuiButton(200, width / 2 - 100, height / 9 + 205, 200, 20, ControlPackMain.translate("gui.done")));
    }
    
    @Override
	protected void actionPerformed(GuiButton guibutton) {
        if(guibutton.id == 205) {
            mc.displayGuiScreen(new GuiControlPackItemOptions(this));
        }
        if(guibutton.id == 204) {
            mc.displayGuiScreen(new GuiWaypoints(this));
        }
        if(guibutton.id == 203) {
            mc.displayGuiScreen(new GuiControlPackOptions(this));
        }
        else if(guibutton.id == 202) {
            mc.displayGuiScreen(new GuiControlPackBindings(this));
        }
		else if(guibutton.id == 201) {
			mc.displayGuiScreen(new GuiControlPackVolume(this));
		}
		else if(guibutton.id == 200) {
            mc.displayGuiScreen(null);
        }
    }

    @Override
	public void drawScreen(int i, int j, float f) {
        drawDefaultBackground();
        //drawCenteredString(fontRenderer, screenTitle, width / 2, 15, 0xffffff);
        drawCenteredString(fontRendererObj, screenTitle, width / 2, 5, 0xffffff);

        drawCenteredString(fontRendererObj, "Here you can configure all of ControlPacks key bindings, turn", width / 2, 30, 0xffffff);
        drawCenteredString(fontRendererObj, "features on and off, and adjust sound effect volume levels.", width / 2, 45, 0xffffff);
        
        drawCenteredString(fontRendererObj, "***** ALSO READ \"README.TXT\" THAT SHIPS WITH THE MOD! *****", width / 2, 70, 0xff0000);
        drawCenteredString(fontRendererObj, "It contains great information about the features that you", width / 2, 85, 0xaaaaaa);
        drawCenteredString(fontRendererObj, " WILL NOT figure out on your own!", width / 2, 100, 0xaaaaaa);
        super.drawScreen(i, j, f);
    }

    protected String screenTitle;
}
