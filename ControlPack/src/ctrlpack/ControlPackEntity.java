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

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.*;
import java.util.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Session;
import net.minecraft.world.World;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.*;
import org.lwjgl.opengl.*;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;

import ctrlpack.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

// used to hook a little bit into the EntityRenderer process. Provides the hook I need to make 3rd person and Look Behind work.

public class ControlPackEntity extends EntityPlayerSP {
	public ControlPackEntity(Minecraft par1Minecraft, World par2World, Session par3Session, int par4) {
		super(par1Minecraft, par2World, par3Session, par4);
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
					ControlPackMain.instance.mc.renderViewEntity = ControlPackMain.instance.wrappedEntity;
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
