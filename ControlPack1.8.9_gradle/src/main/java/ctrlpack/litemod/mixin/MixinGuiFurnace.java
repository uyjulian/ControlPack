/* Copyright (c) 2011-2016 Julian Uy, Dave Reed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package ctrlpack.litemod.mixin;

import java.io.IOException;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import ctrlpack.ControlPackMain;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;

@Mixin(GuiFurnace.class)
public abstract class MixinGuiFurnace extends GuiContainer {

	@Shadow private IInventory tileFurnace;
	public MixinGuiFurnace(Container inventorySlotsIn) {
		super(inventorySlotsIn);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void mouseClicked(int row, int col, int mouseButton) throws IOException {
    	GuiContainer currentGuiFurnacez = (GuiFurnace)(Object)this;
    	if (currentGuiFurnacez instanceof GuiFurnace) {
    		GuiFurnace currentGuiFurnace = (GuiFurnace) currentGuiFurnacez;
	    	IInventory tileFurnace = this.tileFurnace;
	    	int xSize = this.xSize;
	    	int ySize = this.ySize;
			boolean currentStatus = ControlPackMain.instance.furnaceMouseClicked(currentGuiFurnace, tileFurnace, currentGuiFurnace.inventorySlots, row, col, mouseButton, currentGuiFurnace.width, currentGuiFurnace.height, xSize, ySize);
			if (!currentStatus) {
				return;
			}
    	}
    	super.mouseClicked(row, col, mouseButton);
	}

}
