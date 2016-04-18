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

import java.io.IOException;
import java.util.List;





import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;



//import net.minecraft.client.Minecraft;
import org.lwjgl.input.*;

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

    @SuppressWarnings("unchecked")
	@Override
	public void initGui()
    {
        screenTitle = ControlPackMain.translate("controlPack.waypointsTitle");
        
        isNether = ControlPackMain.instance.mc.theWorld != null && (ControlPackMain.instance.mc.theWorld.provider.getDimensionName() == "Nether");

        ControlPackEnumOptions options[] = isNether ? ControlPackMain.instance.waypointNetherOptions : ControlPackMain.instance.waypointOptions;
        for(int k = 0; k < options.length; k++) {
            ControlPackEnumOptions option = options[k];
            ControlPackEnumOptions nameOption = isNether ? ControlPackMain.instance.waypointNetherNameOptions[k] : ControlPackMain.instance.waypointNameOptions[k];
            ControlPackEnumOptions showHUDOption = isNether ? ControlPackMain.instance.waypointNetherHUDOptions[k] : ControlPackMain.instance.waypointHUDOptions[k];
            
            String location = ControlPackMain.instance.stringOptions.get(option);
            String name = ControlPackMain.instance.stringOptions.get(nameOption);
            Boolean showHUD = ControlPackMain.instance.booleanOptions.get(showHUDOption);
            
            // width = 75 + 125 + 50 + 50 = 300 + 10x3 = 330
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
            String oldPoint = ControlPackMain.instance.stringOptions.get(option);
            String newPoint = ControlPackMain.instance.getLocation(false);
            if (oldPoint != null && oldPoint.equals(newPoint)) {
                point = "";
            }
            else {
                point = newPoint;
            }
        }
        ControlPackMain.instance.stringOptions.put(option, point);
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
            setWaypoint(isNether ? ControlPackMain.instance.waypointNetherOptions[i] : ControlPackMain.instance.waypointOptions[i], location);
            
            String name = waypointNames[i].getText();
            name = name == null ? "" : name;
            ControlPackMain.instance.stringOptions.put(isNether ? ControlPackMain.instance.waypointNetherNameOptions[i] : ControlPackMain.instance.waypointNameOptions[i], name);
        }
        ControlPackMain.instance.saveOptions();
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
                    Boolean value = ControlPackMain.instance.booleanOptions.get(cpOption);
                    ControlPackMain.instance.booleanOptions.put(cpOption, !value);
                    guibutton.displayString = ControlPackMain.instance.getOptionDesc(cpOption);
                }
                else {
                    // it was a SET button
                    String location = setWaypoint(cpOption, null);
                    // ex, 211 == 1
                    int wayPointNum = (guibutton.id - (200 - 1)) / 10;
                    waypointLocations[wayPointNum-1].setText(location);
                }

                ControlPackMain.instance.saveOptions();
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
