package ctrlpack.litemod;

import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.gui.GuiKeyBindingList;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntityFurnace;

import com.mumfrey.liteloader.InitCompleteListener;
import com.mumfrey.liteloader.LiteMod;
import com.mumfrey.liteloader.RenderListener;
import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.transformers.event.EventInfo;
import com.mumfrey.liteloader.transformers.event.ReturnEventInfo;

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
		if (!(currentScreen == null))
		{
			ControlPackMain.instance.tickInGui(currentScreen);
		}
	}

	@Override
	public void onInitCompleted(Minecraft minecraft, LiteLoader loader) {
		ControlPackMain.instance.postMCInit();
	}
	
    @SuppressWarnings("unchecked")
	public static void guiFurnaceMouseClick(EventInfo<GuiContainer> eventinfo, int row, int col, int mouseButton) {
    	GuiContainer currentGuiFurnacez = eventinfo.getSource();
    	if (currentGuiFurnacez instanceof GuiFurnace) {
    		GuiFurnace currentGuiFurnace = (GuiFurnace) currentGuiFurnacez;
	    	IInventory tileFurnace = (IInventory) ControlPackPrivateFields.GuiFurnace_tileFurnace.get(currentGuiFurnace);
	    	int xSize = (Integer) ControlPackPrivateFields.GuiContainer_xSize.get(currentGuiFurnace);
	    	int ySize = (Integer) ControlPackPrivateFields.GuiContainer_ySize.get(currentGuiFurnace);
			boolean currentStatus = ControlPackMain.instance.furnaceMouseClicked(currentGuiFurnace, tileFurnace, currentGuiFurnace.inventorySlots, row, col, mouseButton, currentGuiFurnace.width, currentGuiFurnace.height, xSize, ySize);
			if (!currentStatus) {
				eventinfo.cancel();
			}
    	}
    }
    
    public static void keyBindingSetKeyBindState(EventInfo<KeyBinding> eventinfo, int p_74510_0_, boolean p_74510_1_) {
    	//methodhead
		ControlPackMain.improvedChatCompat(p_74510_0_, p_74510_1_);
        // mod_controlpack
        // called on every mouse AND keyboard event (mouse button - 100 to avoid colliding numbers)
        if (ControlPackMain.instance.handleInputEvent(p_74510_0_, p_74510_1_)) {
            eventinfo.cancel();
        }
        
        // mod_controlpack
        // ensure the correct keys are in the down state
        if (ControlPackMain.instance != null) {
            ControlPackMain.instance.resetPlayerKeyState();
        }
    }

    public static void keyBindingUnpressAllKeys(EventInfo<KeyBinding> eventinfo) {
    	//head
        // mod_controlpack
        // Called to 'reset player keystate'.
        if (ControlPackMain.instance != null) {
            ControlPackMain.instance.resetPlayerKeyState();
        }
    }
    
    public static void keyBindingResetKeybindingArrayAndHash(EventInfo<KeyBinding> eventinfo) {
    	//methodhead
        // mod_controlpack
        // Called to 'reset player keystate'.
        if (ControlPackMain.instance != null) {
            ControlPackMain.instance.resetPlayerKeyState();
        }	
    }
    
    public static void keyBindingIsPressed(ReturnEventInfo<KeyBinding,Boolean> eventinfo) {
    	KeyBinding currentkb = eventinfo.getSource();
		// mod_controlpack
        if (ControlPackMain.instance != null) {
            if (currentkb == ControlPackMain.instance.mc.gameSettings.keyBindInventory) {
                ControlPackMain.instance.runAutoTool(false);
            }
        }
    }
    
    @SuppressWarnings("unchecked")
	public static void guiControlsMouseReleased(EventInfo<GuiControls> eventinfo, int p_146286_1_, int p_146286_2_, int p_146286_3_) {
    	GuiKeyBindingList currentgc = (GuiKeyBindingList) ControlPackPrivateFields.GuiControls_keyBindingList.get(eventinfo.getSource());
    	try
    	{
    		currentgc.mouseReleased(p_146286_1_, p_146286_2_, p_146286_3_);
    	}
    	catch(Exception z)
    	{
    		eventinfo.cancel();
    	}
    }
//    
//    public static void guiListExtendedDrawSlot(EventInfo<GuiListExtended> eventinfo, int p_148126_1_, int p_148126_2_, int p_148126_3_, int p_148126_4_, Tessellator p_148126_5_, int p_148126_6_, int p_148126_7_) {
//    	if (eventinfo.getSource().getListEntry(p_148126_1_) == null) 
//    	{
//    		eventinfo.cancel();
//    	}
//    }
    
	@Override
	public void upgradeSettings(String version, File configPath, File oldConfigPath) {}

	@Override
	public void onRender() {}

	@Override
	public void onSetupCameraTransform() {}

}
