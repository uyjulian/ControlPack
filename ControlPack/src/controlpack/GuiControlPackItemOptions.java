package controlpack;

import java.util.List;

import net.minecraft.client.gui.GuiScreen;

//import net.minecraft.client.Minecraft;
import org.lwjgl.input.*;

public class GuiControlPackItemOptions extends GuiControlPackOptions {
    public GuiControlPackItemOptions(GuiScreen parent) {
		super(parent);
		options = ControlPackMain.instance.itemOptions;
		screenTitle = ControlPackMain.translate("controlPack.itemOptionsTitle");
    }
}
