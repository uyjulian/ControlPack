/* Copyright (c) 2011-2017 Julian Uy, Dave Reed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package ctrlpack.litemod.mixin;

import ctrlpack.ControlPackMain;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiChat.class)
public abstract class MixinGuiChat extends GuiScreen {
	@Shadow protected GuiTextField inputField;

	@Inject(method="keyTyped", at=@At("HEAD"))
	private void onKeyTyped(char typedChar, int keyCode, CallbackInfo ci) {
		if (ControlPackMain.instance.keyBindSayLocation.getKeyCode() != 99999 && Keyboard.isKeyDown(ControlPackMain.instance.keyBindSayLocation.getKeyCode()) && !ControlPackMain.instance.chat_insertedPosition) {
			inputField.setText(inputField.getText() + " " + ControlPackMain.instance.getLocation(true) + " ");
		}
		else if (ControlPackMain.instance.keyBindSayLocation.getKeyCode() != 99999 && !Keyboard.isKeyDown(ControlPackMain.instance.keyBindSayLocation.getKeyCode())) {
			ControlPackMain.instance.chat_insertedPosition = false;
		}
	}
}
