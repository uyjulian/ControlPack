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

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

import org.lwjgl.input.Keyboard;

public class GuiMeasure extends GuiScreen
{

	public GuiMeasure(String defaultDist)
	{
		defaultDistance = defaultDist;
	}

	@Override
	public void initGui()
	{
		int i = height / 4 + 48;
		buttonList.add(new GuiButton(0, width / 2 - 100, i, "Go"));
		measureDistance = new GuiTextField(1, fontRendererObj, width / 2 - 25, i - 35, 50, 20);
		measureDistance.setText("0".equalsIgnoreCase(defaultDistance) ? "" : defaultDistance);
		measureDistance.setFocused(true);
		measureDistance.setMaxStringLength(7);
	}
	
	@Override
	protected void keyTyped(char c, int i) throws IOException {
		if(measureDistance.isFocused() && ((c >= '0' && c <= '9') || i == Keyboard.KEY_BACK)) {
			measureDistance.textboxKeyTyped(c, i);
		}
		if(c == '\r') {
			actionPerformed(buttonList.get(0));
		}
		super.keyTyped(c, i);
	}    
	
	@Override
	protected void mouseClicked(int i, int j, int k) throws IOException {
		super.mouseClicked(i, j, k);
		measureDistance.mouseClicked(i, j, k);
	}    

	@Override
	public void drawScreen(int i, int j, float f) {
		measureDistance.drawTextBox();
		super.drawScreen(i, j, f);
	}
	
	@Override
	protected void actionPerformed(GuiButton guibutton)
	{
		if(guibutton.id == 0)
		{
			int distance = 0;
			try {
				distance = Integer.parseInt(measureDistance.getText());
			}
			catch(NumberFormatException ex) {}
			
			if (distance > 0) {
				ControlPackMain.instance.moveByDistance(distance);
			}
			mc.displayGuiScreen(null);
			mc.setIngameFocus();
		}
	}
	
	private GuiTextField measureDistance;
	private String defaultDistance;
}
