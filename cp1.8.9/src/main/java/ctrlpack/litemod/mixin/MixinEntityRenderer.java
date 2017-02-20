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

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import ctrlpack.ControlPackMain;
import ctrlpack.IEntityRenderer;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer implements IResourceManagerReloadListener, IEntityRenderer {
	
	@Shadow private double cameraPitch;
	@Shadow private double cameraYaw;
	
	@Inject(method="orientCamera", at=@At("HEAD"))
	private void onOrientCamera(float partialTicks, CallbackInfo ci){
		Entity entity = ControlPackMain.mc.getRenderViewEntity();
		if (entity instanceof EntityLivingBase) {
			if (ControlPackMain.instance.renderingWorld) {
				ControlPackMain.instance.renderingWorld = false;
				ControlPackMain.instance.orientCamera(0);
			}
		}
	}
	@Inject(method="updateCameraAndRender", at=@At("HEAD"))
	private void onUpdateCameraAndRenderHEAD(float partialTicks, long nanoTime, CallbackInfo ci) {
		ControlPackMain.instance.setupRenderHook();
		//ControlPackMain.instance.updateCameraAngle();
	}
	
	@Inject(method="updateCameraAndRender", at=@At("RETURN"))
	private void onUpdateCameraAndRenderRETURN(float partialTicks, long nanoTime, CallbackInfo ci) {
		ControlPackMain.instance.updateCameraAngle();
	}
	
	@Override
	public void setCameraPitch(float val) {
		cameraPitch = val;
	}
	
	@Override
	public void setCameraYaw(float val) {
		cameraYaw = val;
	}
}
