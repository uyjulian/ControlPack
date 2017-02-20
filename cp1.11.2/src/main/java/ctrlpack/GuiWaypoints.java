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

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class GuiWaypoints extends GuiScreen
{
	private GuiTextField[] waypointNames = new GuiTextField[10]; // note: increase from 10 if more than 10 waypoints supported
	private GuiTextField[] waypointLocations = new GuiTextField[10];
	private boolean isNether;
	
	public GuiWaypoints(GuiScreen parent)
	{
		parentScreen = parent;
		screenTitle = "Control Pack " + ControlPackMain.instance.currentVersion();
	}

	@Override
	public void initGui()
	{
		screenTitle = ControlPackMain.translate("controlPack.waypointsTitle");
		
		isNether = ControlPackMain.mc.world != null && (ControlPackMain.mc.world.provider.getDimensionType().getName() == "Nether");

		ControlPackEnumOptions options[] = isNether ? ControlPackOptions.waypointNetherOptions : ControlPackOptions.waypointOptions;
		for(int k = 0; k < options.length; k++) {
			ControlPackEnumOptions option = options[k];
			ControlPackEnumOptions nameOption = isNether ? ControlPackOptions.waypointNetherNameOptions[k] : ControlPackOptions.waypointNameOptions[k];
			ControlPackEnumOptions showHUDOption = isNether ? ControlPackOptions.waypointNetherHUDOptions[k] : ControlPackOptions.waypointHUDOptions[k];
			
			String location = ControlPackOptions.stringOptions.get(option);
			String name = ControlPackOptions.stringOptions.get(nameOption);

			int x = width / 2 - (330 / 2);
			int y = 100 + k * 25;
			
			// name
			waypointNames[k] = new GuiTextField(1, fontRendererObj, x, y, 75, 20);
			waypointNames[k].setText(name);
			waypointNames[k].setMaxStringLength(12);
			x+= 85;
			// location
			waypointLocations[k] = new GuiTextField(1, fontRendererObj, x, y, 125, 20);
			waypointLocations[k].setText(location);
			waypointLocations[k].setMaxStringLength("-1000000, -1000000, -999".length());
			x += 135;
			// hud on/off
			buttonList.add(new GuiSmallButtonCP(200 + (k+1)*10 + 0, x, y, 50, 20, showHUDOption, ControlPackMain.instance.getOptionDesc(showHUDOption)));
			x += 60;
			// set it
			buttonList.add(new GuiSmallButtonCP(200 + (k+1)*10 + 1, x, y, 50, 20, option, ControlPackMain.translate("options.set"))); 
		}

		buttonList.add(new GuiButton(200, width / 2 - 100, height / 9 + 195, 200, 20, ControlPackMain.translate("gui.done"))); 
	}
	
	private String setWaypoint(ControlPackEnumOptions option, String value) {
		String point;
		if (value != null) {
			point = value;
		}
		else {
			String oldPoint = ControlPackOptions.stringOptions.get(option);
			String newPoint = ControlPackMain.instance.getLocation(false);
			if (oldPoint != null && oldPoint.equals(newPoint)) {
				point = "";
			}
			else {
				point = newPoint;
			}
		}
		ControlPackOptions.stringOptions.put(option, point);
		return point;
	}

	@Override
	protected void keyTyped(char c, int code) throws IOException {
		if(c == '\r') {
			closeGui();
		}
		for (int i = 0; i < waypointNames.length; i++) {
			if (waypointNames[i] != null && waypointNames[i].isFocused()) {
				keyTyped(waypointNames[i], c, code);
			}
			else if (waypointLocations[i] != null && waypointLocations[i].isFocused()) {
				keyTyped(waypointLocations[i], c, code);
			}
		}
		super.keyTyped(c, code);
	}  
	
	@Override
	protected void mouseClicked(int i, int j, int k) throws IOException {
		super.mouseClicked(i, j, k);
		for (int l = 0; l < waypointNames.length; l++) {
			if (waypointNames[l] != null) {
				waypointNames[l].mouseClicked(i, j, k);
				waypointLocations[l].mouseClicked(i, j, k);
			}
		}
	}    

	private void keyTyped(GuiTextField field, char c, int code) {
		if (c == ',' || c == '_' || c == '-' || c == ' ' || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9') || code == Keyboard.KEY_BACK) {
			field.textboxKeyTyped(c, code);
		}
	}
	
	private void closeGui() {
		for (int i = 0; i < waypointNames.length; i++) {
			if (waypointNames[i] == null) continue;
			String location = waypointLocations[i].getText();
			location = location == null ? "" : location;
			setWaypoint(isNether ? ControlPackOptions.waypointNetherOptions[i] : ControlPackOptions.waypointOptions[i], location);
			
			String name = waypointNames[i].getText();
			name = name == null ? "" : name;
			ControlPackOptions.stringOptions.put(isNether ? ControlPackOptions.waypointNetherNameOptions[i] : ControlPackOptions.waypointNameOptions[i], name);
		}
		ControlPackOptions.saveOptions();
		mc.displayGuiScreen(parentScreen);
	}
	
	@Override
	protected void actionPerformed(GuiButton guibutton) {
		if(guibutton.id == 200) {
			closeGui();
		}
		else {
			if (guibutton instanceof GuiSmallButtonCP) {
				GuiSmallButtonCP optionButton = (GuiSmallButtonCP)guibutton;
				ControlPackEnumOptions cpOption = optionButton.getOption();
				if (guibutton.id % 10 == 0) {
					// it was a HUD button
					Boolean value = ControlPackOptions.booleanOptions.get(cpOption);
					ControlPackOptions.booleanOptions.put(cpOption, !value);
					guibutton.displayString = ControlPackMain.instance.getOptionDesc(cpOption);
				}
				else {
					// it was a SET button
					String location = setWaypoint(cpOption, null);
					// ex, 211 == 1
					int wayPointNum = (guibutton.id - (200 - 1)) / 10;
					waypointLocations[wayPointNum-1].setText(location);
				}

				ControlPackOptions.saveOptions();
			}
		}
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();
		drawCenteredString(fontRendererObj, screenTitle + (isNether ? " (Nether)" : ""), width / 2, 5, 0xffffff);
		
		drawCenteredString(fontRendererObj, "Click 'Set' to set a waypoint to your current position.", width / 2, 35, 0x6666ff);
		drawCenteredString(fontRendererObj, "Click it again to clear it.", width / 2, 45, 0x6666ff);
		drawCenteredString(fontRendererObj, "Or just type coordinates in X, Z, Y format.", width / 2, 65, 0x666666);
		
		for (int k = 0; k < waypointNames.length; k++) {
			GuiTextField field = waypointNames[k];
			if (field != null) {
				field.drawTextBox();
				waypointLocations[k].drawTextBox();
			}
		}
		super.drawScreen(i, j, f);
	}

	private GuiScreen parentScreen;
	protected String screenTitle;
}
