package controlpack;

import java.util.List;




import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;



//import net.minecraft.client.Minecraft;
import org.lwjgl.input.*;

public class GuiControlPackVolume extends GuiScreen
{
//SoundManager PlaySound note: this.sndSystem.setVolume(var8, par5 * this.options.soundVolume * (ControlPackMain.instance == null ? 1F : ControlPackMain.instance.getSoundVolume(par1Str)));
    public GuiControlPackVolume(GuiScreen parent)
    {
        screenTitle = "Control Pack";
		parentScreen = parent;
    }

    private int func_20080_j()
    {
        return width / 2 - 155;
    }
    
    // lifted from gameoptions.java
    public String getKeyBindingDescription(int i) {
        return ControlPackMain.translate(ControlPackMain.instance.keyBindings[i].getKeyDescription());
    }
    
    // end lifted
    
    @SuppressWarnings("unchecked")
	@Override
	public void initGui()
    {
        screenTitle = ControlPackMain.translate("controlPack.volumeTitle");

        int i = func_20080_j();
        int j = 0;
        //for(; j < mod_ControlPack.instance.keyBindings.length; j++) {
			//buttonList.add(new GuiSmallButton(j, i + (j % 2) * 160, (height / 9 + 17 * (j >> 1)) - 10, 70, 20, getOptionDisplayString(j)));
        //}

        ControlPackEnumOptions enumoptions[] = ControlPackMain.instance.volumeOptions;
        for(int k = 0; k < enumoptions.length; k++) {
            ControlPackEnumOptions option = enumoptions[k];
            if(!option.getIsFloat()) {
//                buttonList.add(new GuiSmallButtonCP(100 + option.getOrdinal(), i + (j % 2) * 160, height / 6 + 24 * (j >> 1), option, mod_ControlPack.instance.getOptionDesc(option)));
                //buttonList.add(new GuiSmallButtonCP(100 + option.getOrdinal(), i + (j % 2) * 160, height / 9 + 20 * (j >> 1), option, mod_ControlPack.instance.getOptionDesc(option)));
				//buttonList.add(new GuiSmallButtonCP(100 + option.getOrdinal(), i + (j % 2) * 160, height / 9 + 18 * (j >> 1), option, mod_ControlPack.instance.getOptionDesc(option)));
				buttonList.add(new GuiSmallButtonCP(100 + option.getOrdinal(), i + (j % 2) * 160, (height / 9 + 17 * (j >> 1)) - 10, option, ControlPackMain.instance.getOptionDesc(option)));
            }
            else {
//                buttonList.add(new GuiSliderCP(100 + option.getOrdinal(), i + (j % 2) * 160, height / 6 + 24 * (j >> 1), option, mod_ControlPack.instance.getOptionDesc(option), mod_ControlPack.instance.floatOptions.get(option)));
                //buttonList.add(new GuiSliderCP(100 + option.getOrdinal(), i + (j % 2) * 160, height / 9 + 20 * (j >> 1), option, mod_ControlPack.instance.getOptionDesc(option), mod_ControlPack.instance.floatOptions.get(option)));
				//buttonList.add(new GuiSliderCP(100 + option.getOrdinal(), i + (j % 2) * 160, height / 9 + 18 * (j >> 1), option, mod_ControlPack.instance.getOptionDesc(option), mod_ControlPack.instance.floatOptions.get(option)));
				buttonList.add(new GuiSliderCP(100 + option.getOrdinal(), i + (j % 2) * 160, (height / 9 + 24 * (j >> 1)), option, ControlPackMain.instance.getOptionDesc(option), ControlPackMain.instance.floatOptions.get(option), 2F));
            }
            j++;
        }

        //buttonList.add(new GuiButton(200, width / 2 - 100, height / 6 + 168, stringtranslate.translateKey("gui.done")));
        buttonList.add(new GuiButton(200, width / 2 - 100, height / 9 + 190, ControlPackMain.translate("gui.done")));
    }
    
    @Override
	protected void actionPerformed(GuiButton guibutton) {
        //for(int i = 0; i < mod_ControlPack.instance.keyBindings.length; i++) {
            //((GuiButton)buttonList.get(i)).displayString = getOptionDisplayString(i);
        //}

        if(guibutton.id == 200) {
            mc.displayGuiScreen(parentScreen);
        }
        else {
            //if (guibutton instanceof GuiSmallButtonCP) {
              //  GuiSmallButtonCP optionButton = (GuiSmallButtonCP)guibutton;
                //Boolean value = mod_ControlPack.instance.booleanOptions.get(optionButton.getOption());
                //mod_ControlPack.instance.booleanOptions.put(optionButton.getOption(), !value);
                //mod_ControlPack.instance.saveOptions();
                //guibutton.displayString = mod_ControlPack.instance.getOptionDesc(optionButton.getOption());
            //}
            //else if (guibutton.id < 100) {
              //  buttonId = guibutton.id;
                //guibutton.displayString = (new StringBuilder()).append("> ").append(getOptionDisplayString(guibutton.id)).append(" <").toString();
            //}
        }
    }
/*
    protected void mouseClicked(int i, int j, int k) {
        if(buttonId >= 0 && buttonId < 100) {
            mod_ControlPack.instance.keyBindings[buttonId].keyCode = -100 + k;
            mod_ControlPack.instance.saveOptions();

            ((GuiButton)buttonList.get(buttonId)).displayString = getOptionDisplayString(buttonId);
            buttonId = -1;
        }
        else {
            super.mouseClicked(i, j, k);
        }
    }

    protected void keyTyped(char c, int i) {
        if(buttonId >= 0 && buttonId < 100) {
            mod_ControlPack.instance.keyBindings[buttonId].keyCode = i;
            mod_ControlPack.instance.saveOptions();
            
            ((GuiButton)buttonList.get(buttonId)).displayString = getOptionDisplayString(buttonId);
            buttonId = -1;
        }
        else {
            super.keyTyped(c, i);
        }
    }
*/
    @Override
	public void drawScreen(int i, int j, float f) {
        drawDefaultBackground();
        //drawCenteredString(fontRenderer, screenTitle, width / 2, 15, 0xffffff);
        drawCenteredString(fontRendererObj, screenTitle, width / 2, 5, 0xffffff);
        
		/*
        int k = func_20080_j();
        
        for(int l = 0; l < mod_ControlPack.instance.keyBindings.length; l++) {
            boolean flag = false;
            for(int i1 = 0; i1 < mod_ControlPack.instance.keyBindings.length; i1++) {
                if(i1 != l && mod_ControlPack.instance.keyBindings[l].keyCode == mod_ControlPack.instance.keyBindings[i1].keyCode) {
                    flag = true;
                }
            }

            int j1 = l;
            if(buttonId == l) {
                ((GuiButton)buttonList.get(j1)).displayString = "\247f> \247e??? \247f<";
            }
            else if(flag) {
                ((GuiButton)buttonList.get(j1)).displayString = (new StringBuilder()).append("\247c").append(getOptionDisplayString(j1)).toString();
            }
            else {
                ((GuiButton)buttonList.get(j1)).displayString = getOptionDisplayString(j1);
            }
            //drawString(fontRenderer, getKeyBindingDescription(l), k + (l % 2) * 160 + 70 + 6, height / 6 + 24 * (l >> 1) + 7, -1);
            //drawString(fontRenderer, getKeyBindingDescription(l), k + (l % 2) * 160 + 70 + 6, height / 9 + 20 * (l >> 1) + 7, -1);
			//drawString(fontRenderer, getKeyBindingDescription(l), k + (l % 2) * 160 + 70 + 6, height / 9 + 18 * (l >> 1) + 7, -1);
			drawString(fontRenderer, getKeyBindingDescription(l), k + (l % 2) * 160 + 70 + 6, (height / 9 + 17 * (l >> 1) + 7) - 10, -1);
        }*/

        super.drawScreen(i, j, f);
    }

    protected String screenTitle;
	private GuiScreen parentScreen;
}
