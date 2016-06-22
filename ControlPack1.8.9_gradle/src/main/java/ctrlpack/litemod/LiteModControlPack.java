package ctrlpack.litemod;

import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import com.mumfrey.liteloader.InitCompleteListener;
import com.mumfrey.liteloader.LiteMod;
import com.mumfrey.liteloader.RenderListener;
import com.mumfrey.liteloader.core.LiteLoader;
import ctrlpack.ControlPackMain;

public class LiteModControlPack implements LiteMod, RenderListener, InitCompleteListener {

	@Override
	public String getName() {
		return "ControlPack";
	}

	@Override
	public String getVersion() {
		return "5.91";
	}
	
	public static void regKeys(KeyBinding[] lolz) {
		for (KeyBinding currentKey : lolz) {
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
	public void onRenderGui(GuiScreen currentScreen) {
		if (currentScreen != null)
		{
			ControlPackMain.instance.tickInGui(currentScreen);
		}
	}

	@Override
	public void onInitCompleted(Minecraft minecraft, LiteLoader loader) {
		ControlPackMain.instance.postMCInit();
	}
    
	@Override
	public void upgradeSettings(String version, File configPath, File oldConfigPath) {}

	@Override
	public void onRender() {}

	@Override
	public void onSetupCameraTransform() {}

}
