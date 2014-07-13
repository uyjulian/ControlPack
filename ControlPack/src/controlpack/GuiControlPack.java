package controlpack;

import java.util.List;



import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;


//import net.minecraft.client.Minecraft;
import org.lwjgl.input.*;


public class GuiControlPack extends GuiScreen
{
    public GuiControlPack()
    {
        screenTitle = "Control Pack " + ControlPackMain.instance.currentVersion();
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public void initGui()
    {
		screenTitle = ControlPackMain.translate("controlPack.title");

		buttonList.add(new GuiButton(204, width / 2 - 75, height / 9 + 80, 150, 20, ControlPackMain.translate("options.cpwaypoints")));
		buttonList.add(new GuiButton(203, width / 2 - 75, height / 9 + 105, 150, 20, ControlPackMain.translate("options.cpoptions")));
		buttonList.add(new GuiButton(202, width / 2 - 75, height / 9 + 130, 150, 20, ControlPackMain.translate("options.cpbindings")));
		buttonList.add(new GuiButton(201, width / 2 - 75, height / 9 + 155, 150, 20, ControlPackMain.translate("options.volume")));
		buttonList.add(new GuiButton(205, width / 2 - 75, height / 9 + 180, 150, 20, ControlPackMain.translate("options.itemSettings")));

        buttonList.add(new GuiButton(200, width / 2 - 100, height / 9 + 205, 200, 20, ControlPackMain.translate("gui.done")));
    }
    
    @Override
	protected void actionPerformed(GuiButton guibutton) {
        if(guibutton.id == 205) {
            mc.displayGuiScreen(new GuiControlPackItemOptions(this));
        }
        if(guibutton.id == 204) {
            mc.displayGuiScreen(new GuiWaypoints(this));
        }
        if(guibutton.id == 203) {
            mc.displayGuiScreen(new GuiControlPackOptions(this));
        }
        else if(guibutton.id == 202) {
            mc.displayGuiScreen(new GuiControlPackBindings(this));
        }
		else if(guibutton.id == 201) {
			mc.displayGuiScreen(new GuiControlPackVolume(this));
		}
		else if(guibutton.id == 200) {
            mc.displayGuiScreen(null);
        }
    }

    @Override
	public void drawScreen(int i, int j, float f) {
        drawDefaultBackground();
        //drawCenteredString(fontRenderer, screenTitle, width / 2, 15, 0xffffff);
        drawCenteredString(fontRendererObj, screenTitle, width / 2, 5, 0xffffff);

        drawCenteredString(fontRendererObj, "Here you can configure all of ControlPacks key bindings, turn", width / 2, 30, 0xffffff);
        drawCenteredString(fontRendererObj, "features on and off, and adjust sound effect volume levels.", width / 2, 45, 0xffffff);
        
        drawCenteredString(fontRendererObj, "***** ALSO READ \"README.TXT\" THAT SHIPS WITH THE MOD! *****", width / 2, 70, 0xff0000);
        drawCenteredString(fontRendererObj, "It contains great information about the features that you", width / 2, 85, 0xaaaaaa);
        drawCenteredString(fontRendererObj, " WILL NOT figure out on your own!", width / 2, 100, 0xaaaaaa);
        super.drawScreen(i, j, f);
    }

    protected String screenTitle;
}
