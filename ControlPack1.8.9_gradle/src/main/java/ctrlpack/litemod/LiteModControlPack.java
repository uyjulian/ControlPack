/* Copyright (c) 2011-2016 Julian Uy, Dave Reed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package ctrlpack.litemod;

import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import com.mumfrey.liteloader.InitCompleteListener;
import com.mumfrey.liteloader.LiteMod;
import com.mumfrey.liteloader.core.LiteLoader;
import ctrlpack.ControlPackMain;

public class LiteModControlPack implements LiteMod, InitCompleteListener {

	@Override
	public String getName() {
		return "ControlPack";
	}

	@Override
	public String getVersion() {
		return "5.93-beta1";
	}
	
	public static void regKeys(KeyBinding[] keyArray) {
		for (KeyBinding currentKey : keyArray) {
			if (currentKey != null) {
				LiteLoader.getInput().registerKeyBinding(currentKey);
			}
		}
	}

	@Override
	public void init(File configPath) {
		try {
			new ControlPackMain();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onTick(Minecraft minecraft, float partialTicks, boolean inGame, boolean clock) {
		if (inGame == true) {
			ControlPackMain.instance.tickInGame();
		}
	}

	@Override
	public void onInitCompleted(Minecraft minecraft, LiteLoader loader) {
		ControlPackMain.instance.postMCInit();
	}
	
	@Override
	public void upgradeSettings(String version, File configPath, File oldConfigPath) {}

}
