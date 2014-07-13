package ctrlpack;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;

import org.lwjgl.opengl.GL11;

public class GuiTextFieldCP extends GuiTextField {
	public ControlPackEnumOptions option;
	public String label;
	private int height;
	private int xpos;
	private int ypos;
	private FontRenderer fontRenderer;
	public boolean isIdList;
	
    public GuiTextFieldCP(FontRenderer thefontRenderer, String thelabel, int x, int y, int width, int h, ControlPackEnumOptions opt) {
		super(thefontRenderer, x + 80, y, width - 80, h);
		xpos = x;
		ypos = y;
		label = thelabel;
		option = opt;
		fontRenderer = thefontRenderer;
		height = h;
    }
	
	@Override
	public void drawTextBox() {
		super.drawTextBox();
		fontRenderer.drawStringWithShadow(label, xpos, ypos + height/2, 0xffffffff);
	}
}
