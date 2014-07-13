package ctrlpack.litemod;

import com.mumfrey.liteloader.client.util.PrivateFields;
import com.mumfrey.liteloader.core.runtime.Obf;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.tileentity.TileEntityFurnace;

@SuppressWarnings("rawtypes")
public class ControlPackPrivateFields<P,T> extends PrivateFields {

	@SuppressWarnings("unchecked")
	protected ControlPackPrivateFields(Class owner, Obf obf) {
		super(owner, obf);
	}
	
	public static final ControlPackPrivateFields<EntityRenderer, Float> EntityRenderer_debugCamPitch = new ControlPackPrivateFields<EntityRenderer, Float>(EntityRenderer.class, ControlPackObfTable.EntityRenderer_debugCamPitch);
	
	public static final ControlPackPrivateFields<GuiFurnace, TileEntityFurnace> GuiFurnace_tileFurnace = new ControlPackPrivateFields<GuiFurnace, TileEntityFurnace>(GuiFurnace.class, ControlPackObfTable.GuiFurnace_tileFurnace);
	public static final ControlPackPrivateFields<GuiContainer, Integer> GuiContainer_xSize = new ControlPackPrivateFields<GuiContainer, Integer>(GuiContainer.class, ControlPackObfTable.GuiContainer_xSize);
	public static final ControlPackPrivateFields<GuiContainer, Integer> GuiContainer_ySize = new ControlPackPrivateFields<GuiContainer, Integer>(GuiContainer.class, ControlPackObfTable.GuiContainer_ySize);
}
