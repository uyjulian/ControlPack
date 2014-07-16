/* Copyright (c) 2014, Julian Uy
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package ctrlpack;

import java.util.List;
import java.util.ArrayList;




import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;


import net.minecraft.client.gui.GuiTextField;

//import net.minecraft.client.Minecraft;
import org.lwjgl.input.*;

public class GuiControlPackOptions extends GuiScreen
{

	protected ControlPackEnumOptions options[];
	protected List<GuiTextFieldCP> textFields;

    public GuiControlPackOptions(GuiScreen parent)
    {
		textFields = new ArrayList<GuiTextFieldCP>();
        parentScreen = parent;
        screenTitle = ControlPackMain.translate("controlPack.optionsTitle");
		options = ControlPackMain.instance.allOptions;
    }

    private int func_20080_j()
    {
        return width / 2 - 155;
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public void initGui()
    {
        int i = func_20080_j();
        int j = 0;
        
        for(int k = 0; k < options.length; k++) {
            ControlPackEnumOptions option = options[k];
			String optionDesc = ControlPackMain.instance.getOptionDesc(option);
			if (option.getIsString()) {
				//buttonList.add(new GuiSmallButtonCP(100 + option.getOrdinal(), i + (j % 2) * 160, height / 6 + 24 * (j >> 1), option, mod_ControlPack.instance.getOptionDesc(option)));
				GuiTextFieldCP field = new GuiTextFieldCP(fontRendererObj, ControlPackMain.instance.getOptionDesc(option), i, height / 6 + 24 * j - 30, 300, 20, option);
				field.setText(ControlPackMain.instance.stringOptions.get(option));
				textFields.add(field);
				if (option == ControlPackEnumOptions.ITEM_SWORDS) {
					field.isIdList = true;
				}
			}
            else if(!option.getIsFloat()) {
                buttonList.add(new GuiSmallButtonCP(100 + option.getOrdinal(), i + (j % 2) * 160, height / 6 + 24 * (j >> 1) - 30, option, optionDesc));
            }
            else {
                //buttonList.add(new GuiSliderCP(100 + option.getOrdinal(), i + (j % 2) * 160, height / 6 + 24 * (j >> 1), option, optionDesc, mod_ControlPack.instance.floatOptions.get(option)));
            }
            j++;
        }

        buttonList.add(new GuiButton(200, width / 2 - 100, height / 9 + 190, 200, 20, ControlPackMain.translate("gui.done")));
    }
	
	private void saveFields() {
		// text fields don't update automatically
		for (int i = 0; i < textFields.size(); i++) {
			GuiTextFieldCP tf = textFields.get(i);
			ControlPackEnumOptions option = tf.option;
			String value = ControlPackMain.instance.stringOptions.get(option);
			ControlPackMain.instance.stringOptions.put(option, tf.getText());
		}
		ControlPackMain.instance.saveOptions();
	}
	
    @Override
	protected void actionPerformed(GuiButton guibutton) {
		if(guibutton.id == 200) {
			saveFields();
            mc.displayGuiScreen(parentScreen);
        }
        else {
            if (guibutton instanceof GuiSmallButtonCP) {
                GuiSmallButtonCP optionButton = (GuiSmallButtonCP)guibutton;
                ControlPackEnumOptions cpOption = optionButton.getOption();
                if (cpOption.getIsBool()) {
                    Boolean value = ControlPackMain.instance.booleanOptions.get(cpOption);
                    ControlPackMain.instance.booleanOptions.put(cpOption, !value);
                }
                else {
                    Integer value = ControlPackMain.instance.intOptions.get(cpOption);
                    Integer maxValue = ControlPackMain.instance.intOptionsMaxValue.get(cpOption);
                    value++;
                    if (value > maxValue) {
                        value = 0;
                    }
                    ControlPackMain.instance.intOptions.put(cpOption, value);
                }
                ControlPackMain.instance.saveOptions();
                guibutton.displayString = ControlPackMain.instance.getOptionDesc(optionButton.getOption());
            }
        }
    }

    @Override
	public void drawScreen(int i, int j, float f) {
        drawDefaultBackground();
        drawCenteredString(fontRendererObj, screenTitle, width / 2, 5, 0xffffff);
        super.drawScreen(i, j, f);
		for (int i2 = 0; i2 < textFields.size(); i2++) {
			GuiTextFieldCP tf = textFields.get(i2);
			tf.drawTextBox();
		}
    }
	
    @Override
	protected void mouseClicked(int i, int j, int k) {
        super.mouseClicked(i, j, k);
        for (int l = 0; l < textFields.size(); l++) {
            if (textFields.get(l) != null) {
                ((GuiTextField)textFields.get(l)).mouseClicked(i, j, k);
            }
        }
    }  

	@Override
	protected void keyTyped(char c, int code) {
        for (int i = 0; i < textFields.size(); i++) {
            if (textFields.get(i) != null && ((GuiTextField)(textFields.get(i))).isFocused()) {
                keyTyped(textFields.get(i), c, code);
            }
        }
        super.keyTyped(c, code);
    } 
	
    private void keyTyped(GuiTextField field, char c, int code) {
		if (field instanceof GuiTextFieldCP && ((GuiTextFieldCP)field).isIdList) {
			if (c == ',' || (c >= '0' && c <= '9') || code == Keyboard.KEY_BACK) {
				field.textboxKeyTyped(c, code);
			}
		}
		else {
			field.textboxKeyTyped(c, code);
		}
    }

    private GuiScreen parentScreen;
    protected String screenTitle;
}
