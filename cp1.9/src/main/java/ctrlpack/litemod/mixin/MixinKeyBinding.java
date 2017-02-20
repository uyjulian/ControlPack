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

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import ctrlpack.ControlPackMain;
import ctrlpack.IKeyBinding;
import net.minecraft.client.settings.KeyBinding;

@Mixin(KeyBinding.class)
public abstract class MixinKeyBinding implements Comparable<KeyBinding>, IKeyBinding {
	@Shadow private int keyCode;
	@Shadow private boolean pressed;
	@Shadow private int pressTime;
	public boolean toggled;
	
	@Inject(method="setKeyBindState", at=@At("HEAD"), cancellable=true)
	private static void onSetKeyBindState(int p_74510_0_, boolean p_74510_1_, CallbackInfo ci) {
		//methodhead

		// mod_controlpack
		// called on every mouse AND keyboard event (mouse button - 100 to avoid colliding numbers)
		if (ControlPackMain.instance.handleInputEvent(p_74510_0_, p_74510_1_)) {
			ci.cancel();
		}
		
		// mod_controlpack
		// ensure the correct keys are in the down state
		if (ControlPackMain.instance != null) {
			ControlPackMain.instance.resetPlayerKeyState();
		}
	}
	
	@Inject(method="unPressAllKeys", at=@At("HEAD"))
	private static void onUnPressAllKeys(CallbackInfo ci) {
		//head
		// mod_controlpack
		// Called to 'reset player keystate'.
		if (ControlPackMain.instance != null) {
			ControlPackMain.instance.resetPlayerKeyState();
		}
	}
	
	@Inject(method="resetKeyBindingArrayAndHash", at=@At("HEAD"))
	private static void onResetKeyBindingArrayAndHash(CallbackInfo ci) {
		//methodhead
		// mod_controlpack
		// Called to 'reset player keystate'.
		if (ControlPackMain.instance != null) {
			ControlPackMain.instance.resetPlayerKeyState();
		}	
	}
	
	@Inject(method="isPressed", at=@At("HEAD"))
	private void onIsPressed(CallbackInfoReturnable<Boolean> ci) {
		KeyBinding currentkb = ((KeyBinding)(Object)this);
		// mod_controlpack
		if (ControlPackMain.instance != null) {
			if (currentkb == ControlPackMain.mc.gameSettings.keyBindInventory) {
				ControlPackMain.instance.runAutoTool(false);
			}
		}
	}
	
	@Override
	public boolean isDown() {
		if (keyCode >= 0) {
			return Keyboard.isKeyDown(keyCode);
		}
		else {
			return Mouse.isButtonDown(keyCode + 100);
		}
	}
	@Override
	public void reset() {
		pressTime = 0;
		pressed = false;
	}
	@Override
	public void applyToggle() {
		pressed = pressed || toggled;
	}
	@Override
	public void toggle(boolean state) {
		if (toggled != state) {
			toggled = state;
			if (!toggled) {
				pressed = false;
				pressTime = 0;
			}
		}
	}
	@Override
	public void toggle() {
		toggle(!toggled);
	}	
	@Override
	public int getPressTime(){
		return this.pressTime;
	}
	@Override
	public void setPressTime(int vp){
		this.pressTime = vp;
	}
	@Override
	public boolean getToggled() {
		return this.toggled;
	}
}
