// mod_controlpack

package ctrlpack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import org.lwjgl.opengl.GL11;

public class GuiSliderCP extends GuiButton
{

    public GuiSliderCP(int i, int j, int k, ControlPackEnumOptions option, String s, float f, float max)
    {
        super(i, j, k, 150, 20, s);
        sliderValue = 1.0F;
        dragging = false;
        idFloat = null;
        idFloat = option;
        sliderValue = f;
        maxValue = max;
    }

    @Override
	public int getHoverState(boolean flag)
    {
        return 0;
    }

    @Override
	protected void mouseDragged(Minecraft minecraft, int i, int j)
    {
        if(!visible)
        {
            return;
        }
        if(dragging)
        {
            sliderValue = (float)(i - (xPosition + 4)) / (float)(width - 8);
            sliderValue = sliderValue * maxValue;
            if(sliderValue < 0.0F)
            {
                sliderValue = 0.0F;
            }
            if(sliderValue > maxValue)
            {
                sliderValue = maxValue;
            }
            ControlPackMain.instance.floatOptions.put(idFloat, sliderValue);
            ControlPackMain.instance.saveOptions();
            displayString = ControlPackMain.instance.getOptionDesc(idFloat);
        }
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexturedModalRect(xPosition + (int)((sliderValue/maxValue) * (width - 8)), yPosition, 0, 66, 4, 20);
        drawTexturedModalRect(xPosition + (int)((sliderValue/maxValue) * (width - 8)) + 4, yPosition, 196, 66, 4, 20);
    }

    @Override
	public boolean mousePressed(Minecraft minecraft, int i, int j)
    {
        if(super.mousePressed(minecraft, i, j))
        {
            sliderValue = (float)(i - (xPosition + 4)) / (float)(width - 8);
            sliderValue = sliderValue * maxValue;
            if(sliderValue < 0.0F)
            {
                sliderValue = 0.0F;
            }
            if(sliderValue > maxValue)
            {
                sliderValue = maxValue;
            }
            ControlPackMain.instance.floatOptions.put(idFloat, sliderValue);
            ControlPackMain.instance.saveOptions();
            displayString = ControlPackMain.instance.getOptionDesc(idFloat);
            dragging = true;
            return true;
        } else
        {
            return false;
        }
    }

    @Override
	public void mouseReleased(int i, int j)
    {
        dragging = false;
    }

    public float sliderValue;
    public float maxValue;
    public boolean dragging;
    private ControlPackEnumOptions idFloat;
}
