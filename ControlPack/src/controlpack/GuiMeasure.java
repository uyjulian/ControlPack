// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package controlpack;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;


//import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class GuiMeasure extends GuiScreen
{

    public GuiMeasure(String defaultDist)
    {
    	defaultDistance = defaultDist;
    }

    @SuppressWarnings("unchecked")
	@Override
	public void initGui()
    {
        int i = height / 4 + 48;
        buttonList.add(new GuiButton(0, width / 2 - 100, i, "Go"));
        measureDistance = new GuiTextField(fontRendererObj, width / 2 - 25, i - 35, 50, 20);
		measureDistance.setText("0".equalsIgnoreCase(defaultDistance) ? "" : defaultDistance);
        measureDistance.setFocused(true);
        measureDistance.setMaxStringLength(7);
    }
    
    @Override
	protected void keyTyped(char c, int i) {
        if(measureDistance.isFocused() && ((c >= '0' && c <= '9') || i == Keyboard.KEY_BACK)) {
        	measureDistance.textboxKeyTyped(c, i);
        }
        if(c == '\r') {
            actionPerformed((GuiButton)buttonList.get(0));
        }
        super.keyTyped(c, i);
    }    
    
    @Override
	protected void mouseClicked(int i, int j, int k) {
        super.mouseClicked(i, j, k);
        measureDistance.mouseClicked(i, j, k);
    }    

    @Override
	public void drawScreen(int i, int j, float f) {
    	measureDistance.drawTextBox();
    	super.drawScreen(i, j, f);
    }
    
    @Override
	protected void actionPerformed(GuiButton guibutton)
    {
        if(guibutton.id == 0)
        {
        	int distance = 0;
        	try {
        		distance = Integer.parseInt(measureDistance.getText());
        	}
        	catch(NumberFormatException ex) {}
        	
        	if (distance > 0) {
        		ControlPackMain.instance.moveByDistance(distance);
        	}
            mc.displayGuiScreen(null);
            mc.setIngameFocus();
        }
    }
    
    private GuiTextField measureDistance;
    private String defaultDistance;
}
