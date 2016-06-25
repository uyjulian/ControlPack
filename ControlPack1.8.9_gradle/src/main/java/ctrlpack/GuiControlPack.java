/* Copyright (c) 2011-2016 Julian Uy, Dave Reed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package ctrlpack;

import java.net.URI;
import java.net.URISyntaxException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNoCallback;

public class GuiControlPack extends GuiScreen implements GuiYesNoCallback
{
    protected String screenTitle;
    private URI clickedURI;
    
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
		buttonList.add(new GuiButton(206, width / 2 - 75, height / 9 + 205, 150, 20, ControlPackMain.translate("options.cpopendocs")));

        buttonList.add(new GuiButton(200, width / 2 - 100, height / 9 + 230, 200, 20, ControlPackMain.translate("gui.done")));
    }
    
    @Override
	protected void actionPerformed(GuiButton guibutton) {
        if(guibutton.id == 206) {
			try {
				URI currentURI = new URI("https://sites.google.com/site/awertyb/minecraft-mods/controlpack");
                if (this.mc.gameSettings.chatLinksPrompt)
                {
                    this.clickedURI = currentURI;
                    this.mc.displayGuiScreen(new GuiConfirmOpenLink(this, "https://sites.google.com/site/awertyb/minecraft-mods/controlpack", 0, false));
                }
                else
                {
                    this.OpenURI(currentURI);
                }
			} 
			catch (URISyntaxException e) {
				e.printStackTrace();
			}
        }
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
        drawCenteredString(fontRendererObj, screenTitle, width / 2, 5, 0xffffff);

        drawCenteredString(fontRendererObj, "Here you can configure all of the key bindings used in ControlPack,", width / 2, 30, 0xffffff);
        drawCenteredString(fontRendererObj, "turn features on and off, and adjust sound effect volume levels.", width / 2, 45, 0xffffff);
        
        drawCenteredString(fontRendererObj, "***** ALSO CLICK OPEN DOCUMENTATION TO OPEN YOUR WEB BROWSER *****", width / 2, 70, 0xff0000);
        drawCenteredString(fontRendererObj, "It contains great information about the features that you", width / 2, 85, 0xaaaaaa);
        drawCenteredString(fontRendererObj, " WILL NOT figure out on your own!", width / 2, 100, 0xaaaaaa);
        super.drawScreen(i, j, f);
    }
    
    @Override
	public void confirmClicked(boolean doOpen, int status)
    {
        if (status == 0)
        {
            if (doOpen)
            {
                this.OpenURI(this.clickedURI);
            }

            this.clickedURI = null;
            this.mc.displayGuiScreen(this);
        }
    }
	

    private void OpenURI(URI uri)
    {
        try
        {
            Class<?> var2 = Class.forName("java.awt.Desktop");
            Object var3 = var2.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
            var2.getMethod("browse", new Class[] {URI.class}).invoke(var3, new Object[] {uri});
        }
        catch (Throwable var4)
        {}
    }

}
