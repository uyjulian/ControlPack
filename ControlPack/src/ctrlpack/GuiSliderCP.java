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

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import org.lwjgl.opengl.GL11;

public class GuiSliderCP extends GuiButton
{

    public GuiSliderCP(int i, int j, int k, ControlPackEnumOptions option, String s, float f, float max)
    {
        super(i, j, k, 150, 20, s);
        sliderValue = 1.0F;
        dragging = false;
        idFloat = null;
        idFloat = option;
        sliderValue = f;
        maxValue = max;
    }

    @Override
	public int getHoverState(boolean flag)
    {
        return 0;
    }

    @Override
	protected void mouseDragged(Minecraft minecraft, int i, int j)
    {
        if(!visible)
        {
            return;
        }
        if(dragging)
        {
            sliderValue = (float)(i - (xPosition + 4)) / (float)(width - 8);
            sliderValue = sliderValue * maxValue;
            if(sliderValue < 0.0F)
            {
                sliderValue = 0.0F;
            }
            if(sliderValue > maxValue)
            {
                sliderValue = maxValue;
            }
            ControlPackMain.instance.floatOptions.put(idFloat, sliderValue);
            ControlPackMain.instance.saveOptions();
            displayString = ControlPackMain.instance.getOptionDesc(idFloat);
        }
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexturedModalRect(xPosition + (int)((sliderValue/maxValue) * (width - 8)), yPosition, 0, 66, 4, 20);
        drawTexturedModalRect(xPosition + (int)((sliderValue/maxValue) * (width - 8)) + 4, yPosition, 196, 66, 4, 20);
    }

    @Override
	public boolean mousePressed(Minecraft minecraft, int i, int j)
    {
        if(super.mousePressed(minecraft, i, j))
        {
            sliderValue = (float)(i - (xPosition + 4)) / (float)(width - 8);
            sliderValue = sliderValue * maxValue;
            if(sliderValue < 0.0F)
            {
                sliderValue = 0.0F;
            }
            if(sliderValue > maxValue)
            {
                sliderValue = maxValue;
            }
            ControlPackMain.instance.floatOptions.put(idFloat, sliderValue);
            ControlPackMain.instance.saveOptions();
            displayString = ControlPackMain.instance.getOptionDesc(idFloat);
            dragging = true;
            return true;
        } else
        {
            return false;
        }
    }

    @Override
	public void mouseReleased(int i, int j)
    {
        dragging = false;
    }

    public float sliderValue;
    public float maxValue;
    public boolean dragging;
    private ControlPackEnumOptions idFloat;
}
