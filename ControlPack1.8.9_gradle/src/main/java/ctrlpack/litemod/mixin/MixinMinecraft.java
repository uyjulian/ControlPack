package ctrlpack.litemod.mixin;

import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import ctrlpack.litemod.IMinecraft;
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
