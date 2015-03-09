package ctrlpack.litemod;

import com.mumfrey.liteloader.core.runtime.Obf;
import com.mumfrey.liteloader.util.PrivateFields;

import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.gui.GuiKeyBindingList;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntityFurnace;

@SuppressWarnings("rawtypes")
public class ControlPackPrivateFields<P,T> extends PrivateFields {

	@SuppressWarnings("unchecked")
	protected ControlPackPrivateFields(Class owner, Obf obf) {
		super(owner, obf);
	}
	
	public static final ControlPackPrivateFields<EntityRenderer, Float> EntityRenderer_debugCamPitch = new ControlPackPrivateFields<EntityRenderer, Float>(EntityRenderer.class, ControlPackObfTable.EntityRenderer_debugCamPitch);
	
	public static final ControlPackPrivateFields<GuiFurnace, IInventory> GuiFurnace_tileFurnace = new ControlPackPrivateFields<GuiFurnace, IInventory>(GuiFurnace.class, ControlPackObfTable.GuiFurnace_tileFurnace);
	public static final ControlPackPrivateFields<GuiContainer, Integer> GuiContainer_xSize = new ControlPackPrivateFields<GuiContainer, Integer>(GuiContainer.class, ControlPackObfTable.GuiContainer_xSize);
	public static final ControlPackPrivateFields<GuiContainer, Integer> GuiContainer_ySize = new ControlPackPrivateFields<GuiContainer, Integer>(GuiContainer.class, ControlPackObfTable.GuiContainer_ySize);
	public static final ControlPackPrivateFields<GuiControls, GuiKeyBindingList> GuiControls_keyBindingList = new ControlPackPrivateFields<GuiControls, GuiKeyBindingList>(GuiControls.class, ControlPackObfTable.GuiScreen_mouseReleased);
}
