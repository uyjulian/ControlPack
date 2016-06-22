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

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.world.World;

// used to hook a little bit into the EntityRenderer process. Provides the hook I need to make 3rd person and Look Behind work.

public class ControlPackEntity extends EntityPlayerSP {
	public ControlPackEntity(Minecraft mcIn, World worldIn, NetHandlerPlayClient p_i46278_3_, StatFileWriter p_i46278_4_) {
		super(mcIn, worldIn, p_i46278_3_, p_i46278_4_);
	}
	
	public void updatePos() {
		this.lastTickPosX = ControlPackMain.instance.wrappedEntity.lastTickPosX;
		this.posX = ControlPackMain.instance.wrappedEntity.posX;
		this.prevPosX = ControlPackMain.instance.wrappedEntity.prevPosX;
		this.lastTickPosY = ControlPackMain.instance.wrappedEntity.lastTickPosY;
		this.posY = ControlPackMain.instance.wrappedEntity.posY;
		this.prevPosY = ControlPackMain.instance.wrappedEntity.prevPosY;
		this.lastTickPosZ = ControlPackMain.instance.wrappedEntity.lastTickPosZ;
		this.posZ = ControlPackMain.instance.wrappedEntity.posZ;
		this.prevPosZ = ControlPackMain.instance.wrappedEntity.prevPosZ;
		this.prevRotationYaw = ControlPackMain.instance.wrappedEntity.prevRotationYaw;
		this.rotationYaw = ControlPackMain.instance.wrappedEntity.rotationYaw;
		this.prevRotationPitch = ControlPackMain.instance.wrappedEntity.prevRotationPitch;
		this.rotationPitch = ControlPackMain.instance.wrappedEntity.rotationPitch;
		
		if (ControlPackMain.instance.wrappedEntity instanceof EntityPlayer) {
			this.capabilities = ((EntityPlayer)ControlPackMain.instance.wrappedEntity).capabilities;
		}
	}
	
	// @Override 
	// public int getBrightnessForRender(float par1) {
		// int value = mod_ControlPack.instance.booleanOptions.get(ControlPackEnumOptions.VOIDFOG) ? mod_ControlPack.instance.wrappedEntity.getBrightnessForRender(par1) : 1;
		// return value;
	// }

	@Override
	public boolean isPlayerSleeping() {
		// this hook works because isPlayerSleeping is called by EntityRenderer when rendering, first
		// in orientCamera. EntityRendererProxy replaces the renderViewEntity with this thing so we can detect that.
		if (ControlPackMain.instance.renderingWorld) {
		
			StackTraceElement[] stackFrames = Thread.currentThread().getStackTrace();
			for(int i = 0; i < stackFrames.length; i++) {
				String name = stackFrames[i].getMethodName();
				if (name.equals("orientCamera") || name.equals("g") || name.equals("func_78467_g")) { //note: update from methods.csv
					ControlPackMain.instance.renderingWorld = false;
//mod_ControlPack.instance.mc.thePlayer.addChatMessage("orienting " + name);
					ControlPackMain.instance.orientCamera(0);
					// restore renderViewEntity, as it being a different instance messes with rendering entities
					// since it thinks YOU are not YOURSELF and renders YOU even in 1st person view, teehee
					// this is what made it too hard for me to make void fog not require changes to EntityRenderer :(
					ControlPackMain.mc.setRenderViewEntity(ControlPackMain.instance.wrappedEntity);
					break;
				}
				else {
					//System.out.println("stack = " + name);
				}
			}
			//System.out.println("===");
		}
		return ControlPackMain.instance.wrappedEntity.isPlayerSleeping();
	}
	
	/*
	@Override
	public int getMaxHealth() {
		// just passing it through... one of the super constructors of this class will be calling this,
		// which is why it is important that wrappedEntity be set before this class is created.
		return mod_ControlPack.instance.wrappedEntity.getMaxHealth();
	}*/
}
