package controlpack.litemod;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import net.minecraft.client.settings.KeyBinding;

import com.mumfrey.liteloader.transformers.Obfuscated;

public abstract class KeyBindingOverlay implements IKeyBinding {
	
	@SuppressWarnings("unused")
	private static KeyBinding __TARGET;
	
	// TODO Minecraft 1.7.10
	@Obfuscated({"field_74512_d", "d"}) private int keyCode;
	@Obfuscated({"field_74513_e", "e"}) private boolean pressed;
	@Obfuscated({"field_151474_i","i"}) private int pressTime;
	
	// Mod stuff
	public boolean toggled;

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
