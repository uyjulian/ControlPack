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
import net.minecraft.client.gui.GuiTextField;


//import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class GuiMeasure extends GuiScreen
{

    public GuiMeasure(String defaultDist)
    {
    	defaultDistance = defaultDist;
    }

    @SuppressWarnings("unchecked")
	@Override
	public void initGui()
    {
        int i = height / 4 + 48;
        buttonList.add(new GuiButton(0, width / 2 - 100, i, "Go"));
        measureDistance = new GuiTextField(fontRendererObj, width / 2 - 25, i - 35, 50, 20);
		measureDistance.setText("0".equalsIgnoreCase(defaultDistance) ? "" : defaultDistance);
        measureDistance.setFocused(true);
        measureDistance.setMaxStringLength(7);
    }
    
    @Override
	protected void keyTyped(char c, int i) {
        if(measureDistance.isFocused() && ((c >= '0' && c <= '9') || i == Keyboard.KEY_BACK)) {
        	measureDistance.textboxKeyTyped(c, i);
        }
        if(c == '\r') {
            actionPerformed((GuiButton)buttonList.get(0));
        }
        super.keyTyped(c, i);
    }    
    
    @Override
	protected void mouseClicked(int i, int j, int k) {
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
