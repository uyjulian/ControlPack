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

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiControlPackVolume extends GuiScreen
{
	protected String screenTitle;
	private GuiScreen parentScreen;
	
	public GuiControlPackVolume(GuiScreen parent)
	{
		screenTitle = "Control Pack";
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
	
	// end lifted
	
	@Override
	public void initGui()
	{
		screenTitle = ControlPackMain.translate("controlPack.volumeTitle");

		int i = func_20080_j();
		int j = 0;
 
		ControlPackEnumOptions enumoptions[] = ControlPackOptions.volumeOptions;
		for(int k = 0; k < enumoptions.length; k++) {
			ControlPackEnumOptions option = enumoptions[k];
			if(!option.getIsFloat()) {
				buttonList.add(new GuiSmallButtonCP(100 + option.getOrdinal(), i + (j % 2) * 160, (height / 9 + 17 * (j >> 1)) - 10, option, ControlPackMain.instance.getOptionDesc(option)));
			}
			else {
				buttonList.add(new GuiSliderCP(100 + option.getOrdinal(), i + (j % 2) * 160, (height / 9 + 24 * (j >> 1)), option, ControlPackMain.instance.getOptionDesc(option), ControlPackOptions.floatOptions.get(option), 2F));
			}
			j++;
		}

		buttonList.add(new GuiButton(200, width / 2 - 100, height / 9 + 190, ControlPackMain.translate("gui.done")));
	}
	
	@Override
	protected void actionPerformed(GuiButton guibutton) {
		if(guibutton.id == 200) {
			mc.displayGuiScreen(parentScreen);
		}
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();
		drawCenteredString(fontRenderer, screenTitle, width / 2, 5, 0xffffff);

		super.drawScreen(i, j, f);
	}

}
