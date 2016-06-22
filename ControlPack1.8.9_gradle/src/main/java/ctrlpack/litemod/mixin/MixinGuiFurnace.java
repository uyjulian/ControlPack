package ctrlpack.litemod.mixin;

import java.io.IOException;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import ctrlpack.ControlPackMain;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;

@Mixin(GuiFurnace.class)
public abstract class MixinGuiFurnace extends GuiContainer {

	@Shadow private IInventory tileFurnace;
	public MixinGuiFurnace(Container inventorySlotsIn) {
		super(inventorySlotsIn);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void mouseClicked(int row, int col, int mouseButton) throws IOException {
    	GuiContainer currentGuiFurnacez = (GuiFurnace)(Object)this;
    	if (currentGuiFurnacez instanceof GuiFurnace) {
    		GuiFurnace currentGuiFurnace = (GuiFurnace) currentGuiFurnacez;
	    	IInventory tileFurnace = this.tileFurnace;
	    	int xSize = this.xSize;
	    	int ySize = this.ySize;
			boolean currentStatus = ControlPackMain.instance.furnaceMouseClicked(currentGuiFurnace, tileFurnace, currentGuiFurnace.inventorySlots, row, col, mouseButton, currentGuiFurnace.width, currentGuiFurnace.height, xSize, ySize);
			if (!currentStatus) {
				return;
			}
    	}
    	super.mouseClicked(row, col, mouseButton);
	}

}
