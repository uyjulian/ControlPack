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

import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import ctrlpack.IMinecraft;
import net.minecraft.client.Minecraft;
import net.minecraft.profiler.IPlayerUsage;
import net.minecraft.util.IThreadListener;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft implements IThreadListener, IPlayerUsage, IMinecraft {
	@Shadow public int displayWidth;
	@Shadow public int displayHeight;
	@Shadow private void resize(int width, int height) {};
	@Override
	public void pubCheckWindowResize() {
		int i = this.displayWidth;
		int j = this.displayHeight;
		this.displayWidth = Display.getWidth();
		this.displayHeight = Display.getHeight();

		if (this.displayWidth != i || this.displayHeight != j)
		{
			if (this.displayWidth <= 0)
			{
				this.displayWidth = 1;
			}

			if (this.displayHeight <= 0)
			{
				this.displayHeight = 1;
			}

			this.resize(this.displayWidth, this.displayHeight);
		}
	}
}
