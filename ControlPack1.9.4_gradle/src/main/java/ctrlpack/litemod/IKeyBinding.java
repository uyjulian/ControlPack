package ctrlpack.litemod;

public interface IKeyBinding {
	public abstract boolean isDown();
	public abstract void reset();
	public abstract void applyToggle();
	public abstract void toggle(boolean state);
	public abstract void toggle();
	public abstract int getPressTime();
	public abstract void setPressTime(int vp);
	public abstract boolean getToggled();
}
