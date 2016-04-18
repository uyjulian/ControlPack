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

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;

import org.lwjgl.opengl.GL11;

public class GuiTextFieldCP extends GuiTextField {
	public ControlPackEnumOptions option;
	public String label;
	private int height;
	private int xpos;
	private int ypos;
	private FontRenderer fontRenderer;
	public boolean isIdList;
	
    public GuiTextFieldCP(FontRenderer thefontRenderer, String thelabel, int x, int y, int width, int h, ControlPackEnumOptions opt) {
		super(1, thefontRenderer, x + 80, y, width - 80, h);
		xpos = x;
		ypos = y;
		label = thelabel;
		option = opt;
		fontRenderer = thefontRenderer;
		height = h;
    }
	
	@Override
	public void drawTextBox() {
		super.drawTextBox();
		fontRenderer.drawStringWithShadow(label, xpos, ypos + height/2, 0xffffffff);
	}
}
