package ctrlpack.litemod;

import com.mumfrey.liteloader.core.runtime.Obf;

public class ControlPackObfTable extends Obf {
	public static ControlPackObfTable GuiFurnace = new ControlPackObfTable("net/minecraft/client/gui/inventory/GuiFurnace","bgf");
	public static ControlPackObfTable GuiScreen = new ControlPackObfTable("net/minecraft/client/gui/GuiScreen","bez");
	public static ControlPackObfTable GuiContainer = new ControlPackObfTable("net/minecraft/client/gui/inventory/GuiContainer","bfr");
	public static ControlPackObfTable GuiControls = new ControlPackObfTable("net/minecraft/client/gui/GuiControls","bfp");
	public static ControlPackObfTable GuiListExtended = new ControlPackObfTable("net/minecraft/client/gui/GuiListExtended","bdj");
	public static ControlPackObfTable Tessellator = new ControlPackObfTable("net/minecraft/client/renderer/Tessellator","bnr");
	
	public static ControlPackObfTable KeyBinding = new ControlPackObfTable("net/minecraft/client/settings/KeyBinding","bca");
	public static ControlPackObfTable KeyBinding_setKeyBindState = new ControlPackObfTable("func_74510_a","a", "setKeyBindState");
	public static ControlPackObfTable KeyBinding_unPressAllKeys = new ControlPackObfTable("func_74506_a","b", "unPressAllKeys");
	public static ControlPackObfTable KeyBinding_resetKeyBindingArrayAndHash = new ControlPackObfTable("func_74508_b","c", "resetKeyBindingArrayAndHash");
	public static ControlPackObfTable KeyBinding_isPressed = new ControlPackObfTable("func_151468_f","g", "isPressed");
	
	public static ControlPackObfTable GuiFurnace_tileFurnace = new ControlPackObfTable("field_147086_v", "w", "tileFurnace");
	public static ControlPackObfTable GuiScreen_mouseClicked = new ControlPackObfTable("func_73864_a", "a", "mouseClicked");
	public static ControlPackObfTable GuiScreen_mouseReleased = new ControlPackObfTable("func_146286_b", "b", "mouseReleased");
	public static ControlPackObfTable GuiContainer_xSize = new ControlPackObfTable("field_146999_f", "f", "xSize");
	public static ControlPackObfTable GuiContainer_ySize = new ControlPackObfTable("field_147000_g", "g", "ySize");
	public static ControlPackObfTable GuiListExtended_drawSlot = new ControlPackObfTable("func_180791_a", "a", "drawSlot");
	
	public static ControlPackObfTable EntityRenderer_debugCamPitch = new ControlPackObfTable("func_78467_g", "f", "orientCamera");
	
	protected ControlPackObfTable(String seargeName, String obfName, String mcpName) {
		super(seargeName, obfName, mcpName);
	}

	protected ControlPackObfTable(String seargeName, String obfName) {
		super(seargeName, obfName);
	}

}
