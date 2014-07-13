package controlpack.litemod;

import com.mumfrey.liteloader.transformers.event.Event;
import com.mumfrey.liteloader.transformers.event.EventInjectionTransformer;
import com.mumfrey.liteloader.transformers.event.MethodInfo;
import com.mumfrey.liteloader.transformers.event.inject.MethodHead;

public class ControlPackEventTransformer extends EventInjectionTransformer {

	@Override
	protected void addEvents() {
		//GuiFurnace
		this.addEvent(Event.getOrCreate("ControlPack_GuiFurnace", true), new MethodInfo(ControlPackObfTable.GuiContainer, ControlPackObfTable.GuiScreen_mouseClicked, Void.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE), new MethodHead()).addListener(new MethodInfo("controlpack.litemod.LiteModControlPack", "guiFurnaceMouseClick"));

		//KeyBinding
		this.addEvent(Event.getOrCreate("ControlPack_KeyBinding_setKeyBindState", true), new MethodInfo(ControlPackObfTable.KeyBinding, ControlPackObfTable.KeyBinding_setKeyBindState, Void.TYPE, Integer.TYPE, Boolean.TYPE), new MethodHead()).addListener(new MethodInfo("controlpack.litemod.LiteModControlPack", "keyBindingSetKeyBindState"));
		this.addEvent(Event.getOrCreate("ControlPack_KeyBinding_unPressAllKeys"), new MethodInfo(ControlPackObfTable.KeyBinding, ControlPackObfTable.KeyBinding_unPressAllKeys, Void.TYPE), new MethodHead()).addListener(new MethodInfo("controlpack.litemod.LiteModControlPack", "keyBindingUnpressAllKeys"));
		this.addEvent(Event.getOrCreate("ControlPack_KeyBinding_resetKeyBindingArrayAndHash"), new MethodInfo(ControlPackObfTable.KeyBinding, ControlPackObfTable.KeyBinding_resetKeyBindingArrayAndHash, Void.TYPE), new MethodHead()).addListener(new MethodInfo("controlpack.litemod.LiteModControlPack", "keyBindingResetKeybindingArrayAndHash"));
		this.addEvent(Event.getOrCreate("ControlPack_KeyBinding_isPressed"), new MethodInfo(ControlPackObfTable.KeyBinding, ControlPackObfTable.KeyBinding_isPressed, Void.TYPE), new MethodHead()).addListener(new MethodInfo("controlpack.litemod.LiteModControlPack", "keyBindingIsPressed"));
	}

}
