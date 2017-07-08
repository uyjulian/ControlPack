/* Copyright (c) 2011-2016 Julian Uy, Dave Reed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package ctrlpack;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiControlPackOptions extends GuiScreen
{

	protected ControlPackEnumOptions options[];
	protected List<GuiTextFieldCP> textFields;

	public GuiControlPackOptions(GuiScreen parent)
	{
		textFields = new ArrayList<>();
		parentScreen = parent;
		screenTitle = ControlPackMain.translate("controlPack.optionsTitle");
		options = ControlPackOptions.allOptions;
	}

	private int func_20080_j()
	{
		return width / 2 - 155;
	}
	
	@Override
	public void initGui()
	{
		int i = func_20080_j();
		int j = 0;

		for (ControlPackEnumOptions option : options) {
			String optionDesc = ControlPackMain.instance.getOptionDesc(option);
			if (option.getIsString()) {
				//buttonList.add(new GuiSmallButtonCP(100 + option.getOrdinal(), i + (j % 2) * 160, height / 6 + 24 * (j >> 1), option, mod_ControlPack.instance.getOptionDesc(option)));
				GuiTextFieldCP field = new GuiTextFieldCP(fontRenderer, ControlPackMain.instance.getOptionDesc(option), i, height / 6 + 24 * j - 30, 300, 20, option);
				field.setText(ControlPackOptions.stringOptions.get(option));
				textFields.add(field);
				if (option == ControlPackEnumOptions.ITEM_SWORDS) {
					field.isIdList = true;
				}
			} else if (!option.getIsFloat()) {
				buttonList.add(new GuiSmallButtonCP(100 + option.getOrdinal(), i + (j % 2) * 160, height / 6 + 24 * (j >> 1) - 30, option, optionDesc));
			} else {
				//buttonList.add(new GuiSliderCP(100 + option.getOrdinal(), i + (j % 2) * 160, height / 6 + 24 * (j >> 1), option, optionDesc, mod_ControlPack.instance.floatOptions.get(option)));
			}
			j++;
		}

		buttonList.add(new GuiButton(200, width / 2 - 100, height / 9 + 215, 200, 20, ControlPackMain.translate("gui.done")));
	}
	
	private void saveFields() {
		// text fields don't update automatically
		for (GuiTextFieldCP tf : textFields) {
			ControlPackEnumOptions option = tf.option;
			//String value = ControlPackMain.instance.stringOptions.get(option);
			ControlPackOptions.stringOptions.put(option, tf.getText());
		}
		ControlPackOptions.saveOptions();
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
					Boolean value = ControlPackOptions.booleanOptions.get(cpOption);
					ControlPackOptions.booleanOptions.put(cpOption, !value);
				}
				else {
					Integer value = ControlPackOptions.intOptions.get(cpOption);
					Integer maxValue = ControlPackOptions.intOptionsMaxValue.get(cpOption);
					value++;
					if (value > maxValue) {
						value = 0;
					}
					ControlPackOptions.intOptions.put(cpOption, value);
				}
				ControlPackOptions.saveOptions();
				guibutton.displayString = ControlPackMain.instance.getOptionDesc(optionButton.getOption());
			}
		}
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();
		drawCenteredString(fontRenderer, screenTitle, width / 2, 5, 0xffffff);
		super.drawScreen(i, j, f);
		for (GuiTextFieldCP tf : textFields) {
			tf.drawTextBox();
		}
	}
	
	@Override
	protected void mouseClicked(int i, int j, int k) throws IOException {
		super.mouseClicked(i, j, k);
		for (GuiTextFieldCP textField : textFields) {
			if (textField != null) {
				textField.mouseClicked(i, j, k);
			}
		}
	}  

	@Override
	protected void keyTyped(char c, int code) throws IOException {
		for (GuiTextFieldCP textField : textFields) {
			if (textField != null && textField.isFocused()) {
				keyTyped(textField, c, code);
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
