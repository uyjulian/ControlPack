/* Copyright (c) 2011-2017 Julian Uy, Dave Reed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package ctrlpack;

import ctrlpack.litemod.LiteModControlPack;
import net.minecraft.block.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import java.util.Properties;

public class ControlPackMain implements Runnable {
	public static ControlPackMain instance;
	public static Minecraft mc;
	private int toggleCounter = 10;
	
	public ControlPackMain() throws Exception {
		instance = this;
		mc = Minecraft.getMinecraft();
		
		keyBindAlternateLeft = new KeyBinding("key.ctrlpack.altleft", Keyboard.KEY_LCONTROL, "ControlPack");
		keyBindAlternateRight = new KeyBinding("key.ctrlpack.altright", Keyboard.KEY_GRAVE, "ControlPack");
		keyBindToggleSneak = new KeyBinding("key.ctrlpack.toggleSneak", Keyboard.KEY_CAPITAL, "ControlPack");
		keyBindToggleRun = new KeyBinding("key.ctrlpack.autoRun", Keyboard.KEY_R, "ControlPack");
		keyBindToggleJump = new KeyBinding("key.ctrlpack.autoJump", Keyboard.KEY_J, "ControlPack");
		keyBindToggleMine = new KeyBinding("key.ctrlpack.toggleMine", Keyboard.KEY_M, "ControlPack");
		keyBindToggleUse = new KeyBinding("key.ctrlpack.toggleUse", Keyboard.KEY_N, "ControlPack");
		keyBindWalkDistance = new KeyBinding("key.ctrlpack.walkDistance", Keyboard.KEY_EQUALS, "ControlPack");
		keyBindLookBehind = new KeyBinding("key.ctrlpack.lookBehind", 2-100, "ControlPack");
		keyBindToggleGamma = new KeyBinding("key.ctrlpack.toggleGamma", Keyboard.KEY_B, "ControlPack");
		keyBindTorch = new KeyBinding("key.ctrlpack.placeTorch", Keyboard.KEY_V, "ControlPack");
		keyBindEat = new KeyBinding("key.ctrlpack.eatFood", Keyboard.KEY_HOME, "ControlPack");
		keyBindWaypoints = new KeyBinding("key.ctrlpack.waypoints", Keyboard.KEY_PERIOD, "ControlPack");
		keyBindSayLocation = new KeyBinding("key.ctrlpack.saylocation", Keyboard.KEY_INSERT, "ControlPack");
		keyBindings = new KeyBinding[] {
			keyBindTorch,
			keyBindAlternateLeft, keyBindAlternateRight,
			keyBindEat,
			keyBindToggleSneak, keyBindToggleRun, keyBindToggleJump, keyBindToggleMine, keyBindToggleUse,
			keyBindWalkDistance, keyBindLookBehind, keyBindToggleGamma, keyBindSayLocation, keyBindWaypoints
		};		
		LiteModControlPack.regKeys(keyBindings);
		
		ControlPackOptions.loadOptions();
		volumeSettingsProperties = new Properties();
		volumeSettingsProperties.load(getClass().getResourceAsStream("/assets/ctrlpack/map/volumesettings.map"));
 
	}
	@Override
	public void run() {
		do {
			if (mc.currentScreen != null) {
				altKey = false;
			}
			checkGame();
			try {
				Thread.sleep(1000L);
			}
			catch(Exception ex) {
			}
		}
		while(true);
	}
	
	public void postMCInit() {
		applyLastWindowSize();        
		
		Thread thread = new Thread(this, "ControlPack Listener Thread");
		thread.start();
	}
	
	public String currentVersion() {
		return "5.94.3";
	}
	
	private void startUpdateChecker() {
		new Thread(new ControlPackUpdateChecker()).start();
	}
	
	
	private boolean firstTick = false;
	public void tickInGame() {
		if (!firstTick) {
			firstTick = true;
			if (ControlPackOptions.booleanOptions.get(ControlPackEnumOptions.UPDATECHECK)) {
				startUpdateChecker();
			}
		}


		setDeathWaypoint();
		
		if (!mc.gameSettings.showDebugInfo) {
			int coordLocation = ControlPackOptions.intOptions.get(ControlPackEnumOptions.COORDINATESLOCATION);
			int statusLocation = ControlPackOptions.intOptions.get(ControlPackEnumOptions.STATUSLOCATION);
			int lineNum = 0;
			
			if (coordLocation != 4) {
				DrawString(getLocation(true), coordLocation, lineNum, 0xdddddd, null);
				lineNum++;

				//drawArrow(10, 10, 100, 100);
				//drawArrow(200, 200, 100, 175);
				
				boolean isNether = mc.world != null && mc.world.provider.getDimensionType().getName().equals("Nether");

				for (int i = 0; i < (isNether ? ControlPackOptions.waypointNetherOptions.length : ControlPackOptions.waypointOptions.length); i++) {
					ControlPackEnumOptions locationOption = isNether ? ControlPackOptions.waypointNetherOptions[i] : ControlPackOptions.waypointOptions[i];
					ControlPackEnumOptions nameOption = isNether ? ControlPackOptions.waypointNetherNameOptions[i] : ControlPackOptions.waypointNameOptions[i];
					ControlPackEnumOptions hudOption = isNether ? ControlPackOptions.waypointNetherHUDOptions[i] : ControlPackOptions.waypointHUDOptions[i];
					
					String location = ControlPackOptions.stringOptions.get(locationOption);
					String name = ControlPackOptions.stringOptions.get(nameOption);
					Boolean hud = ControlPackOptions.booleanOptions.get(hudOption);
					
					if (!hud || location == null || location.length() == 0) {
						continue;
					}

					String[] parts = location.split(",");
					if (parts.length == 3) {
						try {
							double wpX = Integer.parseInt(parts[0].trim()) + 0.5;
							double wpZ = Integer.parseInt(parts[1].trim()) + 0.5;
							double wpY = Integer.parseInt(parts[2].trim()) + 0.5;
							Vec3d v_waypoint = new Vec3d(wpX, 0, wpZ);
							BlockPos v_currents = mc.player.getPosition();
							BlockPos v_current = new BlockPos(v_currents.getX(),0,v_currents.getZ());

							int currentX = (int) wpX;
							int currentY = (int) wpY;
							int currentZ = (int) wpZ;
							location = ControlPackOptions.stringOptions.get(ControlPackEnumOptions.COORDINATE_FORMAT).replace("{X}", ""+currentX).replace("{Y}", ""+currentY).replace("{Z}", ""+currentZ);
							
							Vec3d v_directionFromHere = v_waypoint.subtract(v_current.getX(), v_current.getY(), v_current.getZ());
							if (v_directionFromHere.lengthVector() <= 0.5) {
								DrawString((name == null || name.length() == 0) ? location : (name + ": " + location), coordLocation, lineNum, 0xbbbbbb, null, true);
							}
							else {
								v_directionFromHere.rotateYaw((mc.player.rotationYaw * 0.01745329F));
								v_directionFromHere = v_directionFromHere.normalize();
								DrawString((name == null || name.length() == 0) ? location : (name + ": " + location), coordLocation, lineNum, 0xbbbbbb, v_directionFromHere);
							}
							lineNum++;
						}
						catch(Exception ex) {
						}
					}
				}
				
			}
			if (statusLocation != 4) {
				String status = getToggleStatus();
				
				if (statusLocation != coordLocation) {
					lineNum = 0;
				}
				
				if (status != null && status.length() > 0) {
					DrawString(status, statusLocation, lineNum, 0xdddddd, null);
					lineNum++;
				}
				
				if (!mc.playerController.isInCreativeMode() && ControlPackOptions.booleanOptions.get(ControlPackEnumOptions.USECOUNT)) {
					ItemStack currentStack = mc.player.inventory.mainInventory.get(mc.player.inventory.currentItem);
					if (currentStack != null) {
						int maxdmg = currentStack.getMaxDamage();
						if (maxdmg > 0) {
							int dmg = currentStack.getItemDamage();
							int remaining = (maxdmg - dmg + 1);
							String text = "Uses: " + remaining;
							// bow? show arrow count
							if (currentStack.getItem() instanceof ItemBow) {
								int count = 0;
								for (int i = 0; i < mc.player.inventory.mainInventory.size(); i++)
								{
									if (mc.player.inventory.mainInventory.get(i) != null && mc.player.inventory.mainInventory.get(i) == new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("minecraft:arrow"))))
									{
										count += mc.player.inventory.mainInventory.get(i).getCount();
									}
								}
								text += " / Arrows: " + count;
							}

							if (remaining <= 10) {
								DrawString(text, statusLocation, lineNum, 0xdd0000, null);
							}
							else {
								DrawString(text, statusLocation, lineNum, 0xdddddd, null);
							}
						}
					}
				}
			}
		}
		
		if (previouslyPlacedBlock && previouslyPlacedBlockID != -1) {
			previouslyPlacedBlock = false;
			if (ControlPackOptions.booleanOptions.get(ControlPackEnumOptions.AUTOBLOCK)) {
				runAutoBlockOnExpend();
			}
		}
		
		if (keyBindingGetToggled(mc.gameSettings.keyBindUseItem)) {
			// they are auto right clicking
			previouslyPlacedBlock = true;
			ItemStack stack = mc.player.inventory.getCurrentItem();
			previouslyPlacedBlockID = stack == null ? -1 : Item.getIdFromItem(stack.getItem());
		 }
		 
		if (ControlPackOptions.booleanOptions.get(ControlPackEnumOptions.HOLDTOATTACK) && mc.gameSettings.keyBindAttack.isKeyDown()) {
			if (--toggleCounter <= 0) {
				// holding down attach should also mean toggle attack!
				keyBindingSetPressTime(mc.gameSettings.keyBindAttack,1);
				toggleCounter = 10;
			}
		}
		else {
			toggleCounter = 10;
		}
		
		if (!nagged) {
			nagged = true;
			if (ControlPackOptions.booleanOptions.get(ControlPackEnumOptions.WELCOMENAG)) {
				chatMsg("Thanks for using ControlPack. Hit ALT+C to configure it.         I will nag you until you do :)");
			}
		}
		if (swapBack) {
			swapBack = false;
			mc.player.inventory.currentItem = swapBackTo;
		}

		// lookbehind animation progress
		if (lookBehind && mc.player.isDead) {
			lookBehind = false;
			lookBehindProgress = 0;
			lookBehindProgressTicks = 0;
			turnOffToggles();
		}
		
		// turn off auto-use item if a dialog comes up, or else you have
		// a hard time getting out of a furnace, crafting table, etc!
		if (mc.currentScreen != null) {
			keyBindingToggle(mc.gameSettings.keyBindUseItem,false);
			// turn off auto mining cuz it doesn't work while a screen is up anyway,
			// and it does not start back up after chatting
			keyBindingToggle(mc.gameSettings.keyBindAttack,false);
		}
		
		
		if ((lookBehind && lookBehindProgress < 1) || (!lookBehind && lookBehindProgress > 0)) {
			float startProgress = lookBehindProgress;
			
			float direction = (lookBehind ? 1F : -1F);
			long ticks = System.currentTimeMillis();
			lookBehindProgressTicks += direction * (ticks - lookBehindProgressTicksLast);
			lookBehindProgressTicksLast = ticks;
			lookBehindProgress = lookBehindProgressTicks / lookBehindAnimationLength;
			if (direction > 0) {
				lookBehindProgress = Math.min(1, lookBehindProgress);
				lookBehindProgressTicks = lookBehindAnimationLength * lookBehindProgress;
			}
			else {
				lookBehindProgress = Math.max(0, lookBehindProgress);
				lookBehindProgressTicks = lookBehindAnimationLength * lookBehindProgress;
			}
			
			if (lookBehindProgress > 0 && startProgress == 0) {
				ControlPackMain.mc.gameSettings.hideGUI = true;
			}
			else if (lookBehindProgress == 0 && startProgress > 0) {
				ControlPackMain.mc.gameSettings.hideGUI = false; // todo: restore from settings
			}
		}

		// handle measure distance mode
		if (measureDistanceState) {
			BlockPos currentPos = mc.player.getPosition();
			int currentX = (int) (currentPos.getX() < 0 ? Math.ceil(currentPos.getX()) : Math.floor(currentPos.getX()));
			//int currentY = (int) (currentPos.getY() < 0 ? Math.ceil(currentPos.getY()) : Math.floor(currentPos.getY()));
			int currentZ = (int) (currentPos.getZ() < 0 ? Math.ceil(currentPos.getZ()) : Math.floor(currentPos.getZ()));

			double traveled = Math.max(Math.abs(measureDistanceStartX - currentX), Math.abs(measureDistanceStartZ - currentZ));
			measureDistanceRemaining = lastMeasureDistance - traveled;
			if (traveled >= lastMeasureDistance) {
				cancelMeasureDistance();
			}
			else if (!measureDistanceStateMoving) {
				measureDistanceStateMoving = true;
				resetPlayerKeyState();
			}
		}

		// 3rd person mode rotation
		if (mc.gameSettings.debugCamEnable) {
			if ((Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL))) {
				if (Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode())) {
					frontView_rotationPitch += pitchSpeed;
					syncThirdPersonRotation();
					//pitchSpeed += 0.1;
				}
				else if (Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode())) {
					frontView_rotationPitch -= pitchSpeed;
					syncThirdPersonRotation();
					//pitchSpeed += 0.1;
				}
				if (Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode())) {
					frontView_rotationYaw += yawSpeed;
					syncThirdPersonRotation();
					//yawSpeed += 0.1;
				}
				else if (Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode())) {
					frontView_rotationYaw -= yawSpeed;
					syncThirdPersonRotation();
					//yawSpeed += 0.1;
				}
			}
		}
	}
	
	public static String translate(String key) {
		return I18n.format(key);
	}
	
	public void applyLastWindowSize() {
		if (ControlPackOptions.booleanOptions.get(ControlPackEnumOptions.WINDOWRESTORE) && ControlPackOptions.booleanOptions.get(ControlPackEnumOptions.LASTPOSITIONEXISTS)) {
			try {
				if (ControlPackOptions.booleanOptions.get(ControlPackEnumOptions.LASTFULLSCREEN)) {
					if (!Display.isFullscreen()) {
						mc.toggleFullscreen();
					}
				}
				else {					
					Display.setLocation(ControlPackOptions.intOptions.get(ControlPackEnumOptions.LASTBOUNDSX), ControlPackOptions.intOptions.get(ControlPackEnumOptions.LASTBOUNDSY));
					Display.setDisplayMode(new DisplayMode(ControlPackOptions.intOptions.get(ControlPackEnumOptions.LASTBOUNDSW), ControlPackOptions.intOptions.get(ControlPackEnumOptions.LASTBOUNDSH)));
					IMinecraft imc = (IMinecraft)mc;
					imc.pubCheckWindowResize();
					Display.setResizable(true);
				}
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public void checkGame() {
		try {
			int displayX = Display.getX(); 
			int displayY = Display.getY();
			int displayW = Display.getWidth(); 
			int displayH = Display.getHeight();
			if (ControlPackOptions.intOptions.get(ControlPackEnumOptions.LASTBOUNDSX) != displayX || ControlPackOptions.intOptions.get(ControlPackEnumOptions.LASTBOUNDSY) != displayY || ControlPackOptions.intOptions.get(ControlPackEnumOptions.LASTBOUNDSW) != displayW || ControlPackOptions.intOptions.get(ControlPackEnumOptions.LASTBOUNDSH) != displayH || (ControlPackOptions.booleanOptions.get(ControlPackEnumOptions.LASTFULLSCREEN) != Display.isFullscreen())) {
				ControlPackOptions.booleanOptions.put(ControlPackEnumOptions.LASTFULLSCREEN, Display.isFullscreen());
				if (!ControlPackOptions.booleanOptions.get(ControlPackEnumOptions.LASTFULLSCREEN)) {
					ControlPackOptions.intOptions.put(ControlPackEnumOptions.LASTBOUNDSX, displayX);
					ControlPackOptions.intOptions.put(ControlPackEnumOptions.LASTBOUNDSY, displayY);
					ControlPackOptions.intOptions.put(ControlPackEnumOptions.LASTBOUNDSW, displayW);
					ControlPackOptions.intOptions.put(ControlPackEnumOptions.LASTBOUNDSH, displayH);
				}
				ControlPackOptions.booleanOptions.put(ControlPackEnumOptions.LASTPOSITIONEXISTS, true);
				ControlPackOptions.saveOptions();
			}
		}
		catch(Exception ex) {
			System.out.println("Unable to check window state. " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	

		
	public void resetPlayerKeyState() {
		keyBindingApplyToggle(mc.gameSettings.keyBindAttack);
		keyBindingApplyToggle(mc.gameSettings.keyBindUseItem);
		// simulate an actual clicking for use item command
		if(keyBindingGetToggled(mc.gameSettings.keyBindUseItem) && keyBindingGetPressTime(mc.gameSettings.keyBindUseItem) == 0) {
			keyBindingSetPressTime(mc.gameSettings.keyBindUseItem,1);
		}
		
		keyBindingApplyToggle(mc.gameSettings.keyBindJump);
		keyBindingApplyToggle(mc.gameSettings.keyBindForward);
		keyBindingApplyToggle(mc.gameSettings.keyBindBack);	
		keyBindingApplyToggle(mc.gameSettings.keyBindSneak);
	}

	public void turnOffToggles() {
		keyBindingToggle(mc.gameSettings.keyBindAttack,false);
		keyBindingToggle(mc.gameSettings.keyBindUseItem,false);
		keyBindingToggle(mc.gameSettings.keyBindJump,false);
		keyBindingToggle(mc.gameSettings.keyBindForward,false);
		keyBindingToggle(mc.gameSettings.keyBindBack,false);
		keyBindingToggle(mc.gameSettings.keyBindSneak,false);
	}

	private void cancelMeasureDistance() {
		if (measureDistanceState) {
			measureDistanceState = false;
			if (measureDistanceStateMoving) {
				measureDistanceStateMoving = false;
				keyBindingToggle(mc.gameSettings.keyBindForward,false);
				keyBindingToggle(mc.gameSettings.keyBindBack,false);
				resetPlayerKeyState();
			}
		}
	}

	public float getSoundVolume(String name) {
		try {
			String setting = volumeSettingsProperties.getProperty(name);
			if (setting == null) { return 1F; }
			ControlPackEnumOptions option = ControlPackEnumOptions.getOption(setting);
			if (option == null) { return 1F; }
			return ControlPackOptions.floatOptions.get(option);
		}
		catch(Exception ex) {
			return 1F;
		}
	}

	private boolean isTool(Item item) {
		return (item != null) && (item instanceof ItemTool || item instanceof ItemShears);
	}

	private float blockStrength(Block block, Item item) {
		// adapted from block.java
		if(block.getBlockHardness(block.getDefaultState(), mc.world, new BlockPos(1, 1, 1)) < 0.0F) {
			return 0.0F;
		}
		if(item == null || !canHarvestBlock(item, block) || (getIsHarvestable(block) && item.getDestroySpeed(new ItemStack(block), block.getDefaultState()) == 1.0F)) {
			return 1.0F / block.getBlockHardness(block.getDefaultState(), mc.world, new BlockPos(1, 1, 1)) / 100F;
		}
		else {
			return item.getDestroySpeed(new ItemStack(block), block.getDefaultState()) / block.getBlockHardness(block.getDefaultState(), mc.world, new BlockPos(1, 1, 1)) / 30F;
		}
	}	
	
	private boolean canHarvestBlock(Item item, Block block) {
		return canHarvestBlock(item, block, false);
	}
	
	private boolean canHarvestBlock(Item item, Block block, boolean forReals) {
		if (!forReals && getIsHarvestable(block)) return true;
		boolean canHarvest = item.canHarvestBlock(block.getDefaultState());
		if (canHarvest) return true;

		// shears are wierd.. they dont say they can harvest vines, and their str vs vines is 1,
		// and vines are harvestable even though they arent. arg!
		return item instanceof ItemShears && (block instanceof BlockVine || block instanceof BlockLeaves || block instanceof BlockColored);
	}
	
	private boolean getIsHarvestable(Block block) {
		return block.getMaterial(block.getDefaultState()).isToolNotRequired() && !(block instanceof BlockVine || block instanceof BlockLeaves);
	}
	
	private boolean isSword(Item item) {
		if (item instanceof ItemSword) return true;
		String swordIdList = "," + ControlPackOptions.stringOptions.get(ControlPackEnumOptions.ITEM_SWORDS) + ",";
		return swordIdList.contains("," + Item.getIdFromItem(item) + ",");
	}
	
	public void ensureSwordSelected() {
		for(int i = 0; i < 9; i++) {
			ItemStack stack = mc.player.inventory.mainInventory.get(i);
			if (stack != null) {
				if (isSword(stack.getItem())) {
					if (i != mc.player.inventory.currentItem) {
						mc.player.inventory.currentItem = i;
					}
					return;
				}
			}
		}
		// no sword.. at least make sure we're not holding a tool
		ItemStack currentStack = mc.player.inventory.mainInventory.get(mc.player.inventory.currentItem);
		if (currentStack != null && isTool(currentStack.getItem())) {
			swapToHand();
		}
	}
	
	private boolean swapToHand() {
		// look for an empty slot
		for(int i = 0; i < 9; i++) {
			ItemStack stack = mc.player.inventory.mainInventory.get(i);
			if (stack == null) {
				mc.player.inventory.currentItem = i;
				return true;
			}
		}
		// no empty slots so look for a lame item instead
		for(int i = 0; i < 9; i++) {
			ItemStack stack = mc.player.inventory.mainInventory.get(i);
			if (stack != null) {
				Item item = stack.getItem();
				if (item != null && (!isSword(item) && !isTool(item))) {
					mc.player.inventory.currentItem = i;
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean isBetterTool(Block block, Item tool, Item testTool) {
		if (!getIsHarvestable(block)) {
			// this tool can't harvest it, so forget about it...
			if (!canHarvestBlock(testTool, block)) {
				return false;
			}
			// the current item can't harvest it, and the new one can.
			if (tool == null || !canHarvestBlock(tool, block)) {
				return true;
			}
			
			// both items can harvest it, so it's better if it has greater strength against the block (block has lower strength rating with it)
			float testStrength = blockStrength(block, testTool);
			float existingStrength = blockStrength(block, tool);
			Integer mode = ControlPackOptions.intOptions.get(ControlPackEnumOptions.AUTOTOOLMODE);
			if (mode == 0) { // weakest
				if (testStrength < existingStrength) {
					return true;
				}
				else if (testStrength == existingStrength) {
					// if they have equal strength. It's better if it has a lower 'level' (e.g. wood pick on a furance vs. stone pick -- equal str but pick wood)
					// cheap way to determine that is to look at the max uses.. less is lower level :)
					if (testTool.getMaxDamage() < tool.getMaxDamage()) {
						return true;
					}
				}
			}
			else { // strongest (the first/last wont even call this method)
				if (testStrength > existingStrength) {
					return true;
				}
				else if (testStrength == existingStrength) {
					// if they have equal strength. It's better if it has a higher 'level' (e.g. wood pick on a furance vs. stone pick -- equal str but pick stone)
					// cheap way to determine that is to look at the max uses.. less is lower level :)
					if (testTool.getMaxDamage() > tool.getMaxDamage()) {
						return true;
					}
				}
			}
		}
		else {
			float testStrength = blockStrength(block, testTool);
			float existingStrength = blockStrength(block, tool);
			float handStrength = blockStrength(block, null);
			//mc.player.addChatMessage(testTool.getItemName() + "=" + testStrength + ". hand=" + handStrength + ". exist=" + existingStrength);

			// dont even consider tools that have the same strength as hand-based strength
			if (testStrength <= handStrength) {
				return false;
			}
			
			// tool vs no-null (and the tool offers better strength than no tool)
			if (tool == null || existingStrength == handStrength) {
				// any tool is better than no tool, or than a tool that has no advantage
				return true;
			}
			
			// tool vs tool (both good ones)
			Integer mode = ControlPackOptions.intOptions.get(ControlPackEnumOptions.AUTOTOOLMODE);
			if (mode == 0) { // weakest
				if (testStrength < existingStrength) {
					return true;
				}
				else if (testStrength == existingStrength) {
					if (testTool.getMaxDamage() < tool.getMaxDamage()) {
						return true;
					}
				}
			}
			else { // strongest
				if (testStrength > existingStrength) {
					return true;
				}
				else if (testStrength == existingStrength) {
					if (testTool.getMaxDamage() < tool.getMaxDamage()) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private boolean isBetterSword(Item tool, Item testTool) {
		// any sword is better than no sword
		if (tool == null) {
			return true;
		}
		
		Integer mode = ControlPackOptions.intOptions.get(ControlPackEnumOptions.AUTOTOOLMODE);
		if (mode == 0) { // weakest
			if (testTool.getMaxDamage() < tool.getMaxDamage()) {
				return true;
			}
		}
		else { 
			if (testTool.getMaxDamage() > tool.getMaxDamage()) {
				return true;
			}
		}
		
		return false;
	}	
	
	private boolean swapToFirstTool(Block block, Item currentItem) {
		for(int i = 0; i < 9; i++) {
			ItemStack stack = mc.player.inventory.mainInventory.get(i);
			if (stack != null) {
				Item item = stack.getItem();
				if (item == null || !isTool(item)) {
					continue;
				}
				// found a good tool to use as long as it offers better strength than no-tool strength
				if (blockStrength(block, item) > blockStrength(block, null)) {
					mc.player.inventory.currentItem = i;
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean swapToFirstSword() {
		for(int i = 0; i < 9; i++) {
			ItemStack stack = mc.player.inventory.mainInventory.get(i);
			if (stack != null) {
				Item item = stack.getItem();
				if (item == null || !isSword(item)) {
					continue;
				}
				mc.player.inventory.currentItem = i;
				return true;
			}
		}
		return false;
	}	
	
	private boolean swapToLastTool(Block block, Item currentItem) {
		for(int i = 8; i >= 0; i--) {
			ItemStack stack = mc.player.inventory.mainInventory.get(i);
			if (stack != null) {
				Item item = stack.getItem();
				if (item == null || !isTool(item)) {
					continue;
				}
				// found a good tool to use as long as it offers better strength than no-tool strength
				if (blockStrength(block, item) > blockStrength(block, null)) {
					mc.player.inventory.currentItem = i;
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean swapToLastSword() {
		for(int i = 8; i >= 0; i--) {
			ItemStack stack = mc.player.inventory.mainInventory.get(i);
			if (stack != null) {
				Item item = stack.getItem();
				if (item == null || !isSword(item)) {
					continue;
				}
				mc.player.inventory.currentItem = i;
				return true;
			}
		}
		return false;
	}	
	
	private boolean shouldUseSword(Block block) {
		return block instanceof BlockWeb;
	}
	
	private boolean swapToBestSword() {
		Integer mode = ControlPackOptions.intOptions.get(ControlPackEnumOptions.AUTOTOOLMODE);

		if (mode == 2) {
			// first
			return swapToFirstSword();
		}
		else if (mode == 3) {
			// last
			return swapToLastSword();
		}

		int currentItemIndex = -1;
		Item currentItem = null;
		
		for(int i = 0; i < 9; i++) {
			ItemStack stack = mc.player.inventory.mainInventory.get(i);
			if (stack != null) {
				Item item = stack.getItem();
				if (item == null || !isSword(item)) {
					continue;
				}
				if (isBetterSword(currentItem, item)) {
					currentItem = item;
					currentItemIndex = i;
				}
			}
		}
		if (currentItemIndex != -1) {
			mc.player.inventory.currentItem = currentItemIndex;
			return true;
		}
		return false;
	}
	
	private boolean swapToBestTool(Block block, Item currentItem, int currentItemIndex) {
		Integer mode = ControlPackOptions.intOptions.get(ControlPackEnumOptions.AUTOTOOLMODE);

		if (mode == 2) {
			// first
			return swapToFirstTool(block, currentItem);
		}
		else if (mode == 3) {
			// last
			return swapToLastTool(block, currentItem);
		}

		int originalIndex = currentItemIndex;
		
		for(int i = 0; i < 9; i++) {
			ItemStack stack = mc.player.inventory.mainInventory.get(i);
			if (stack != null) {
				Item item = stack.getItem();
				if (item == null || !isTool(item)) {
					continue;
				}
				if (isBetterTool(block, currentItem, item)) {
					currentItem = item;
					currentItemIndex = i;
				}
			}
		}
		if (currentItemIndex != originalIndex) {
			mc.player.inventory.currentItem = currentItemIndex;
			return true;
		}
		return false;
	}
	
	public void ensureCorrectToolSelected(Block block) {
//System.out.println("");
		// inputs:
		// 	HARVESTABLE. block is always harvestable (e.g. dirt and sand = true)
		//  EASYBREAK. block is easily breakable even if not harvestable (tall grass, vines, leaves)
		//  HAS - SWORD, HAND/LAME, TOOL. current item 
		//  CURRENTCANHARVEST. current tool can harvest block
		//  HASGREATERSTR. player has a tool of greater strength than current strength (consider that 0 str if sword, unharvesting tool, lame, etc)
		// output:
		//  S. stay with current item/tool/hand/whatever
		//  T. swap to tool with greater strength
		//  H. swap to hand or lame item if not already on hand or lame item
		/*
		HARVESTABLE | EASYBREAK |   HAS   | CURRENTCANHARVEST | HASGREATERSTR | OUTPUT
		====================================================================================================================
			 T            T          -             -                 -            H

			 T			  F         HAND           -                 T            T
			 T			  F         HAND           -                 F            S
			 F            T         HAND           -                 T            T
			 F            T         HAND           -                 F            S
			 F            F         HAND           -                 T            T
			 F            F         HAND           -                 F            S
			 
			 T			  F         SWORD          -                 T            T
			 T			  F         SWORD          -                 F            H
			 F            F         SWORD          -                 T            T
			 F            F         SWORD          -                 F            H
			 F            T         SWORD          -                 T            T
			 F            T         SWORD          -                 F            H

			 T			  F         TOOL           -                 T            T
			 F            T         TOOL           T                 T            T
			 F            T         TOOL           F                 T            T
			 F            F         TOOL           T                 T            T
			 F            F         TOOL           F                 T            T
			 
			 T			  F         TOOL           -                 F            S
			 F            T         TOOL           T                 F            S
			 F            F         TOOL           T                 F            S
			 
			 F            T         TOOL           F                 F            H
			 F            F         TOOL           F                 F            H
			 
		*/
		int inventoryIndex = mc.player.inventory.currentItem;
		ItemStack currentStack = mc.player.inventory.getCurrentItem();
		Item currentItem = currentStack == null ? null : currentStack.getItem();
		boolean easilyBreakable = block.getBlockHardness(block.getDefaultState(), mc.world, new BlockPos(1, 1, 1)) <= 0.20001F; // leaves are 0.2. Saplings, redstone dust, etc are 0 (1 hit).
		boolean harvestable = getIsHarvestable(block);
		boolean hasSword = currentItem != null && isSword(currentItem);
		boolean hasTool = isTool(currentItem);
		boolean hasHandOrItem = currentItem == null || (!hasSword && !hasTool);
		
		// redstone dust, etc
		// dont need a tool, shouldn't have one.
		if (easilyBreakable && harvestable) {
			if (!hasHandOrItem) {
//System.out.println("swap to hand...");
				swapToHand();
			}
			return;
		}
		// need a tool. It's either not harvestable w/o a tool or it is but it's not easily breakable
		
		if (hasHandOrItem) {
			swapToBestTool(block, null, -1);
		}
		else if (hasSword) {
			if (!swapToBestTool(block, null, -1)) {
				swapToHand();
			}
		}
		else if (hasTool) {
			// only swap if we can find a better one
			if (!swapToBestTool(block, currentItem, inventoryIndex)) {
				// cant find a better one... maybe we already have the best one
				if (!canHarvestBlock(currentItem, block, true)) {
					// ok, the tool doesnt say it can harvest the block.
					// but the block might be naturally harvestable, so this could be either a shove or a pick against dirt, for example.
					if (getIsHarvestable(block)) {
						// ok.. so lets determine if its a good tool based on its strength. It's NO good if its strength
						// is the same as the no-tool strength.
						if (blockStrength(block, currentItem) == blockStrength(block, null)) {
//System.out.println(currentItem.itemID + " not a good tool because str = same as no tool str, so swap to hand");
							swapToHand();
						}
					}
					else {
//System.out.println(currentItem.itemID + " not a good tool so swap to hand");
						swapToHand();
					}
				}
			}
		}
		
		// after all this if we still don't have a tool, and it's a sword whitelisted block
		// then try to select a sword.
		if (shouldUseSword(block)) {
			currentStack = mc.player.inventory.getCurrentItem();
			currentItem = currentStack == null ? null : currentStack.getItem();
			hasSword = currentItem != null && isSword(currentItem);
			hasTool = isTool(currentItem);
			hasHandOrItem = currentItem == null || (!hasSword && !hasTool);
			if (!hasSword && hasHandOrItem) {
				swapToBestSword();
			}
		}
	}
	
	public void runAutoTool(boolean proactive) {
		// proactive means we're doing this before the keybinding is even logged yet.
		// currently mining? and not using swap command?
		if (swappedInventoryState == 0 && (proactive || mc.gameSettings.keyBindAttack.isKeyDown()) && mc.objectMouseOver != null) {
			// get the block being targetted
			if (!mc.playerController.isInCreativeMode() && ControlPackOptions.booleanOptions.get(ControlPackEnumOptions.AUTOTOOL) && mc.objectMouseOver.typeOfHit == Type.BLOCK) {
				Block block = mc.world.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock();
				if (block != null) {
					ensureCorrectToolSelected(block);
				}
			}
			else if (ControlPackOptions.booleanOptions.get(ControlPackEnumOptions.AUTOSWORD) && (mc.objectMouseOver.typeOfHit == Type.ENTITY && mc.objectMouseOver.entityHit instanceof EntityLivingBase)) {
				ensureSwordSelected();
			}
			// fyi change item: player.inventory.changeCurrentItem(k);, or player.inventory.currentItem = i;
			// mc.objectMouseOver
			// objectMouseOver.typeOfHit == EnumMovingObjectType.TILE
			// objectMouseOver.typeOfHit == EnumMovingObjectType.ENTITY
			// objectMouseOver.blockX,Y,Z
			// world.getBlockId(i,j,k)
			// Block.blocksList[id]
		}
	}
	
	public boolean swapToFood() {
		for (int i = 0; i < 9; i++) {
			ItemStack possibleBlock = mc.player.inventory.mainInventory.get(i);
			if (possibleBlock != null && possibleBlock.getItem() instanceof ItemFood) {
				//ItemFood food = (ItemFood) possibleBlock.getItem();
				//int healAmount = food.getHealAmount();
				swapBackTo = mc.player.inventory.currentItem;
				mc.player.inventory.currentItem = i;
				return true;
			}
		}
		return false;
	}
	
	public void placeTorch() {
		for (int i = 0; i < 9; i++) {
			ItemStack possibleBlock = mc.player.inventory.mainInventory.get(i);
			if (possibleBlock != null && (Block.getBlockFromItem(possibleBlock.getItem()) instanceof BlockTorch)) { // torch block id = 50: http://www.minecraftwiki.net/wiki/Data_values#Block_IDs_.28Minecraft_Beta.29
				swapBack = true;
				swapBackTo = mc.player.inventory.currentItem;
				mc.player.inventory.currentItem = i;
				// make MC think they did a right click..
				KeyBinding.onTick(mc.gameSettings.keyBindUseItem.getKeyCode());
				return;
			}
		}
		// no torch... try a redstone torch instead
		for (int i = 0; i < 9; i++) {
			ItemStack possibleBlock = mc.player.inventory.mainInventory.get(i);
			if (possibleBlock != null && ((Block.getBlockFromItem(possibleBlock.getItem()) instanceof BlockRedstoneTorch))) { // redstone torch block id = 75 or 76 (on or off)
				swapBack = true;
				swapBackTo = mc.player.inventory.currentItem;
				mc.player.inventory.currentItem = i;
				// make MC think they did a right click..
				KeyBinding.onTick(mc.gameSettings.keyBindUseItem.getKeyCode());
				return;
			}
		}
	}

	public void runAutoBlockOnExpend() {
		ItemStack currentItem = mc.player.inventory.getCurrentItem();
		if (currentItem == null || currentItem.getCount() <= 0) {
			// they just placed the last item in a stack, switch to another stack of the same item
			for (int i = 0; i < 9; i++) {
				ItemStack possibleBlock = mc.player.inventory.mainInventory.get(i);
				if (possibleBlock != null && Item.getIdFromItem(possibleBlock.getItem()) == previouslyPlacedBlockID) {
					mc.player.inventory.currentItem = i;
					break;
				}
			}
		}
	}    
	
	public void runAutoBlock() {
		// See if they have a tool currently selected, which won't do anything.
		ItemStack currentItem = mc.player.inventory.getCurrentItem();
		if (currentItem == null || isTool(currentItem.getItem())) {
			Integer mode = ControlPackOptions.intOptions.get(ControlPackEnumOptions.AUTOBLOCKMODE);
			//int foundBlockId = -1;
			if (mode == 0) { // leftmost
				for (int i = 0; i < 9; i++) {
					ItemStack possibleBlock = mc.player.inventory.mainInventory.get(i);
					if (possibleBlock != null && possibleBlock.getItem() instanceof ItemBlock) {
						mc.player.inventory.currentItem = i;
						break;
					}
				}
			}
			else if (mode == 1) { // rightmost
				for (int i = 8; i >= 0; i--) {
					ItemStack possibleBlock = mc.player.inventory.mainInventory.get(i);
					if (possibleBlock != null && possibleBlock.getItem() instanceof ItemBlock) {
						mc.player.inventory.currentItem = i;
						break;
					}
				}
			}
			else if (mode < 11) { // slot #
				ItemStack possibleBlock = mc.player.inventory.mainInventory.get(mode - 2);
				if (possibleBlock != null && possibleBlock.getItem() instanceof ItemBlock) {
					mc.player.inventory.currentItem = mode - 2;
				}
			}
		}
	}
	
	private String getToggleStatus() {
		String s = "";
		if (keyBindingGetToggled(mc.gameSettings.keyBindForward)) {
			if (measureDistanceState) {
				s += "[Run=" + ((int) Math.floor(measureDistanceRemaining)) + "]";
			}
			else {
				s += "[Run]";
			}
		}
		if (keyBindingGetToggled(mc.gameSettings.keyBindBack)) {
			s += "[Run Back]";
		}
		
		if (keyBindingGetToggled(mc.gameSettings.keyBindSneak)) {
			s += "[Sneak]";
		}
		if (keyBindingGetToggled(mc.gameSettings.keyBindAttack)) {
			s += "[Mine]";
		}
		if (keyBindingGetToggled(mc.gameSettings.keyBindJump)) {
			s += "[Jump]";
		}
		if (keyBindingGetToggled(mc.gameSettings.keyBindUseItem)) {
			s += "[Use]";
		}
		if (swappedInventoryState != 0) {
			if (swappedInventoryState == -1) {
				s += "[Swap L]";
			}
			else {
				s += "[Swap R]";
			}
		}
		return s;
	}
	
	public String getLocation(boolean shouldFormat) {
		if (mc == null || mc.player == null) return "";
		BlockPos currentPos = mc.player.getPosition();
		int currentX = (int) Math.ceil(currentPos.getX()) - 1;
		int currentY = (int) Math.ceil(currentPos.getY()) - 1;
		int currentZ = (int) Math.ceil(currentPos.getZ()) - 1;
		if (shouldFormat) {
			return ControlPackOptions.stringOptions.get(ControlPackEnumOptions.COORDINATE_FORMAT).replace("{X}", ""+currentX).replace("{Y}", ""+currentY).replace("{Z}", ""+currentZ);
		}
		else {
			return currentX + ", " + currentZ + ", " + currentY;
		}
	}
	
	public boolean chat_insertedPosition = false;

	
	private void drawSquare(double x, double y, int color) {
		int size = 7;
		float f = (color >> 16 & 0xff) / 255F;
		float f1 = (color >> 8 & 0xff) / 255F;
		float f2 = (color & 0xff) / 255F;
		float f3 = (color >> 24 & 0xff) / 255F;
		if(f3 == 0.0F) {
			f3 = 1.0F;
		}
		GL11.glColor4f(f, f1, f2, f3);
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glLineWidth(2); 
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x, (float)y, 0f);
		line(2, 2, size-2, 2);
		line(size-2, 2, size-2, size-2);
		line(size-2, size-2, 2, size-2);
		line(2, size-2, 2, 2);
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}
	
	private void drawDirectionalArrow(ScaledResolution sr, double x, double y, Vec3d v, int color) {
		double size = 7;
		double startx = x + (size / 2) - (size / 2) * v.x;
		double starty = y + (size / 2) - (size / 2) * v.z;
		double endx = x + (size / 2) + (size / 2) * v.x;
		double endy = y + (size / 2) + (size / 2) * v.z;
			
		int l = color & 0xff000000;
		int shadowColor = (color & 0xfcfcfc) >> 2;
		shadowColor += l;

		drawArrow(startx+1, starty+1, endx+1, endy+1, shadowColor);
		drawArrow(startx, starty, endx, endy, color);
	}
	
	private void drawArrow(double x1, double y1, double x2, double y2, int color) {
		float f = (color >> 16 & 0xff) / 255F;
		float f1 = (color >> 8 & 0xff) / 255F;
		float f2 = (color & 0xff) / 255F;
		float f3 = (color >> 24 & 0xff) / 255F;
		if(f3 == 0.0F) {
			f3 = 1.0F;
		}
		GL11.glColor4f(f, f1, f2, f3);

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glLineWidth(2); 
		GL11.glPushMatrix();
		GL11.glTranslatef(0F, 0F, 0F);
		line(x1, y1, x2, y2);
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x2, (float)y2, 0F);
		float a = (float)Math.atan2(x1-x2, y2-y1);
		GL11.glRotatef((float)Math.toDegrees(a), 0F, 0F, 1F);
		line(0, 0, -3, -3);
		line(0, 0, 3, -3);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}
	
	private void line(double x1, double y1, double x2, double y2) {
		GL11.glBegin(GL11.GL_LINES); 
		GL11.glVertex2f((float)x1, (float)y1); 
		GL11.glVertex2f((float)x2, (float)y2); 
		GL11.glEnd();
	}
	
	private void DrawString(String str, int position, int lineNum, int color, Vec3d arrow) {
		DrawString(str, position, lineNum, color, arrow, false);
	}
	
	private void DrawString(String str, int position, int lineNum, int color, Vec3d arrow, Boolean square) {
		FontRenderer fr = mc.fontRenderer;
		ScaledResolution sr = new ScaledResolution(mc);
		int xPos;
		int yPos;
		
		if (position == 0) {
			xPos = 1;
			yPos = 1;
		}
		else if (position == 1) {
			xPos = (int)sr.getScaledWidth_double() - fr.getStringWidth(str) - 1;
			yPos = 1;
		}
		else if (position == 2) {
			xPos = 1;
			yPos = (int)sr.getScaledHeight_double() - 10 - 40;
		}
		else {
			xPos = (int)sr.getScaledWidth_double() - fr.getStringWidth(str) - 1;
			yPos = (int)sr.getScaledHeight_double() - 10 - 40;
		}
		
		yPos += lineNum * 9;
		
		fr.drawStringWithShadow(str, xPos, yPos, color);
		
		if (arrow != null || square) {
			if (position == 0 || position == 2) {
				xPos += fr.getStringWidth(str);
			}
			else {
				xPos -= 10;
			}
			if (arrow != null) {
				drawDirectionalArrow(sr, xPos, yPos, arrow, color);
			}
			else if (square) {
				drawSquare(xPos, yPos, color);
			}
		}
	}
	
	private void addDeathWaypoint() {
		boolean isNether = ControlPackMain.mc.world != null && mc.world.provider.getDimensionType().getName().equals("Nether");
		ControlPackEnumOptions[] nameoptions = isNether ? ControlPackOptions.waypointNetherNameOptions : ControlPackOptions.waypointNameOptions;
		ControlPackEnumOptions[] locationoptions = isNether ? ControlPackOptions.waypointNetherOptions : ControlPackOptions.waypointOptions;
		ControlPackEnumOptions[] hudoptions = isNether ? ControlPackOptions.waypointNetherHUDOptions : ControlPackOptions.waypointHUDOptions;
		
		// find 'corpse' waypoint
		for (int i = 0; i < nameoptions.length; i++) {
			ControlPackEnumOptions locationOption = locationoptions[i];
			ControlPackEnumOptions nameoption = nameoptions[i];
			ControlPackEnumOptions hudoption = hudoptions[i];
			String name = ControlPackOptions.stringOptions.get(nameoption);
			if (name.equals("Corpse")) {
				String newPoint = this.getLocation(false);
				ControlPackOptions.stringOptions.put(locationOption, newPoint);
				ControlPackOptions.booleanOptions.put(hudoption, true);
				ControlPackOptions.saveOptions();
				return;
			}
		}
		
		// nope, so try to find unused waypoint
		for (int i = 0; i < nameoptions.length; i++) {
			ControlPackEnumOptions locationOption = locationoptions[i];
			ControlPackEnumOptions nameoption = nameoptions[i];
			ControlPackEnumOptions hudoption = hudoptions[i];
			String name = ControlPackOptions.stringOptions.get(nameoption);
			if (name == null || name.length() == 0) {
				String newPoint = this.getLocation(false);
				ControlPackOptions.stringOptions.put(nameoption, "Corpse");
				ControlPackOptions.stringOptions.put(locationOption, newPoint);
				ControlPackOptions.booleanOptions.put(hudoption, true);
				ControlPackOptions.saveOptions();
				return;
			}
		}
		
	}
	
	private boolean deathKnown;
	private void setDeathWaypoint() {
		if (ControlPackOptions.booleanOptions.get(ControlPackEnumOptions.CORPSELOCATION)) {
			if (!mc.player.isEntityAlive()) {
				if (!deathKnown) {
					addDeathWaypoint();
					deathKnown = true;
				}
			}
			else {
				deathKnown = false;
			}
		}
	}
	
	public void updateCameraAngle() {
		// called before game renders from my entity renderer proxy. The modloader OnTickInGame will be post rendering.
		if (ControlPackMain.mc.gameSettings.debugCamEnable) {
			if (Mouse.isButtonDown(2)) {
				int dx = Mouse.getDX();
				int dy = Mouse.getDY();
				ControlPackMain.instance.frontView_rotationYaw += dx / 2;
				ControlPackMain.instance.frontView_rotationPitch += dy / 2;
				ControlPackMain.instance.syncThirdPersonRotation();
			}
		}
	}
	
	public void setupRenderHook() {
		if (this.lookBehindProgress != 0 && ControlPackMain.mc.getRenderViewEntity() != null) {
			this.renderingWorld = true;
		}
	}
	
	public void syncThirdPersonRotation() {
		IEntityRenderer iEntityRenderer = (IEntityRenderer)ControlPackMain.mc.entityRenderer;
		iEntityRenderer.setCameraPitch(frontView_rotationPitch);
		iEntityRenderer.setCameraYaw(frontView_rotationYaw);
	}
	
	private void openGUIRunDistance() {
		GuiMeasure guiMeasure = new GuiMeasure(String.valueOf(lastMeasureDistance));
		mc.displayGuiScreen(guiMeasure);
	}
	
	public void moveByDistance(int numTiles) {
		lastMeasureDistance = numTiles;
		BlockPos pos = mc.player.getPosition();
		measureDistanceStartX = (int) (pos.getX() < 0 ? Math.ceil(pos.getX()) : Math.floor(pos.getX()));
		measureDistanceStartZ = (int) (pos.getZ() < 0 ? Math.ceil(pos.getZ()) : Math.floor(pos.getZ()));
		measureDistanceState = true;
		keyBindingToggle(mc.gameSettings.keyBindForward,true);
	}
	
	public void orientCamera(float f) {
		if (mc == null) return;
		if (lookBehindProgress != 0) {
			GL11.glRotatef(180 * lookBehindProgress, 0.1F, 1F, 0F); // rotate 180
		}
	}
	
	public void toggleThirdPersonView() {
		if (mc.gameSettings.thirdPersonView < 2) {
			// let MC do it
		}
		else {
			if (mc.gameSettings.debugCamEnable) {
				// in front-3rd view, need to go into normal view.
				mc.gameSettings.debugCamEnable = false;
				// let MC increment 3rd view field
			}
			else {
				mc.gameSettings.debugCamEnable = true;
				// trick MC into staying in the same mode
				mc.gameSettings.thirdPersonView = 1;
			}
		}
	}
	
	public boolean handleInputEvent(int code, boolean down) {
		// mouse or keyboard event
		if (mc.currentScreen != null) return false;
		
		if (code == Keyboard.KEY_LMENU || code == Keyboard.KEY_RMENU) {
			altKey = down;
		}
		
		if (down && code == mc.gameSettings.keyBindAttack.getKeyCode()) {
			// if they just hit the 'left click' button then proactively run autotool
			// so that it swaps items BEFORE processing the event.
			runAutoTool(true);
		}
		
		if (down && code == keyBindWaypoints.getKeyCode()) {
			GuiWaypoints gui = new GuiWaypoints(null);
			mc.displayGuiScreen(gui);
			return true;
		}
	   
		if (code == Keyboard.KEY_F5 && down && ControlPackOptions.booleanOptions.get(ControlPackEnumOptions.FRONTVIEW)) {
			toggleThirdPersonView();
			return true;
		}
		
		if (code == keyBindEat.getKeyCode()) {
			if (down) {
				if (swapToFood()) {
					isEating = mc.player.inventory.currentItem;
					keyBindingToggle(mc.gameSettings.keyBindUseItem,true);
				}
			}
			else {
				isEating = -1;
				swapBack = true;
				keyBindingToggle(mc.gameSettings.keyBindUseItem,false);
			}
		}
		else if (isEating != -1 && isEating != mc.player.inventory.currentItem) {
			isEating = -1;
			swapBack = true;
			keyBindingToggle(mc.gameSettings.keyBindUseItem,false);
		}
		
		
		if (down && code == keyBindTorch.getKeyCode()) {
			placeTorch();
		}

		// open controlpack options
		if (down && altKey && code == Keyboard.KEY_C) {
			// open controlpack options gui
			ControlPackOptions.booleanOptions.put(ControlPackEnumOptions.WELCOMENAG, false);
			ControlPackOptions.saveOptions();
			mc.displayGuiScreen(new GuiControlPack());
			altKey = false;
			return true;
		}

		if (down && altKey && code == Keyboard.KEY_T) {
			// toggle auto tool
			boolean enabled = !ControlPackOptions.booleanOptions.get(ControlPackEnumOptions.AUTOTOOL);
			ControlPackOptions.booleanOptions.put(ControlPackEnumOptions.AUTOTOOL, enabled);
			chatMsg("Auto Tool " + (enabled ? "ENABLED" : "DISABLED"));
			ControlPackOptions.saveOptions();
			return true;
		}
		if (down && altKey && code == Keyboard.KEY_S) {
			// toggle auto sword
			boolean enabled = !ControlPackOptions.booleanOptions.get(ControlPackEnumOptions.AUTOSWORD);
			ControlPackOptions.booleanOptions.put(ControlPackEnumOptions.AUTOSWORD, enabled);
			chatMsg("Auto Sword " + (enabled ? "ENABLED" : "DISABLED"));
			ControlPackOptions.saveOptions();
			return true;
		}
		if (down && altKey && code == Keyboard.KEY_B) {
			// toggle auto block
			boolean enabled = !ControlPackOptions.booleanOptions.get(ControlPackEnumOptions.AUTOBLOCK);
			ControlPackOptions. booleanOptions.put(ControlPackEnumOptions.AUTOBLOCK, enabled);
			chatMsg("Auto Block " + (enabled ? "ENABLED" : "DISABLED"));
			ControlPackOptions. saveOptions();
			return true;
		}		
		if (down && altKey && code == Keyboard.KEY_R) {
			// cycle auto tool mode
			int mode = ControlPackOptions.intOptions.get(ControlPackEnumOptions.AUTOTOOLMODE);
			mode++;
			int max = ControlPackOptions.intOptionsMaxValue.get(ControlPackEnumOptions.AUTOTOOLMODE);
			if (mode > max) {
				mode = 0;
			}
			ControlPackOptions.intOptions.put(ControlPackEnumOptions.AUTOTOOLMODE, mode);
			chatMsg("Auto Tool Mode = " + getIntOptionDesc(ControlPackEnumOptions.AUTOTOOLMODE, mode));
			
			ControlPackOptions.saveOptions();
			return true;
		}

		
		if (code == keyBindLookBehind.getKeyCode() && !ControlPackMain.mc.gameSettings.debugCamEnable && ControlPackMain.mc.gameSettings.thirdPersonView == 0) {
			lookBehind = down;
			lookBehindProgressTicksLast = System.currentTimeMillis();
			return true;
		}
		
		if (code == keyBindAlternateLeft.getKeyCode()) {
			if (down) {
				swappedInventoryState = -1;
				mc.player.inventory.changeCurrentItem(1);
			}
			else if (swappedInventoryState != 0) {
				mc.player.inventory.changeCurrentItem(swappedInventoryState);
				swappedInventoryState = 0;
			}
			return true;
		}
		if (code == keyBindAlternateRight.getKeyCode()) {
			if (down) {
				swappedInventoryState = 1;
				mc.player.inventory.changeCurrentItem(-1);
			}
			else if (swappedInventoryState != 0) {
				mc.player.inventory.changeCurrentItem(swappedInventoryState);
				swappedInventoryState = 0;
			}
			return true;
		}
		
		// if pressing forward and back at the same time, lookbehind
		if (mc.gameSettings.thirdPersonView == 0 && ControlPackOptions.booleanOptions.get(ControlPackEnumOptions.LOOKBEHINDBACK) && (code == mc.gameSettings.keyBindForward.getKeyCode() || code == mc.gameSettings.keyBindBack.getKeyCode())) {
			boolean wasLookBehind = lookBehind;
			lookBehind = keyBindingIsDown(mc.gameSettings.keyBindForward) && keyBindingIsDown(mc.gameSettings.keyBindBack);
			lookBehindProgressTicksLast = System.currentTimeMillis();
			if (down && (wasLookBehind != lookBehind)) {
				return true;
			}
		}

		if (measureDistanceState && (code == mc.gameSettings.keyBindForward.getKeyCode()) || (code == mc.gameSettings.keyBindBack.getKeyCode())) {
			cancelMeasureDistance();
		}

		if (down) {
			if (mc.gameSettings.debugCamEnable) {
				if ((Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL))) {
					if (code == mc.gameSettings.keyBindForward.getKeyCode() || code == mc.gameSettings.keyBindBack.getKeyCode() || code == mc.gameSettings.keyBindLeft.getKeyCode() || code == mc.gameSettings.keyBindRight.getKeyCode()) {
						return true;
					}
				}
			}
			
			if (ControlPackOptions.booleanOptions.get(ControlPackEnumOptions.AUTOBLOCK) && code == mc.gameSettings.keyBindUseItem.getKeyCode()) {
				// they right clicked.
				runAutoBlock();
				
				// they just 'used' the current item. remember what kind of item it was for autoblock expending feature.
				previouslyPlacedBlock = true;
				ItemStack stack = mc.player.inventory.getCurrentItem();
				previouslyPlacedBlockID = stack == null ? -1 : Item.getIdFromItem(stack.getItem());
			}
			
			if (code == keyBindToggleGamma.getKeyCode()) {
				if (toggleGammaState) {
					mc.gameSettings.gammaSetting = originalGamma;
				}
				else {
					originalGamma = mc.gameSettings.gammaSetting;
					if (originalGamma == 1F) {
						mc.gameSettings.gammaSetting = 0F;
					}
					else {
						mc.gameSettings.gammaSetting = 1F;
					}
				}
				toggleGammaState = !toggleGammaState;
				return true;
			}

			if (code == keyBindWalkDistance.getKeyCode()) {
				openGUIRunDistance();
				return true;
			}

			if (code == keyBindToggleMine.getKeyCode()) {
				keyBindingToggle(mc.gameSettings.keyBindAttack);
				return true;
			}

			if (code == keyBindToggleUse.getKeyCode()) {
				keyBindingToggle(mc.gameSettings.keyBindUseItem);
				return true;
			}

			// toggle sneak, run, jump
			if (code == keyBindToggleSneak.getKeyCode()) {
				keyBindingToggle(mc.gameSettings.keyBindSneak);
				//autoSneakState = !autoSneakState;
				return true;
			}
			if (code == keyBindToggleRun.getKeyCode()) {
				cancelMeasureDistance();
				// if they are already running backward then auto run will run backward...
				if (mc.gameSettings.keyBindBack.isKeyDown()) {
					keyBindingToggle(mc.gameSettings.keyBindBack);
				}
				else {
					keyBindingToggle(mc.gameSettings.keyBindForward);
				}
				//autoRunState = !autoRunState;
				return true;
			}
			if (code == keyBindToggleJump.getKeyCode()) {
				keyBindingToggle(mc.gameSettings.keyBindJump);
				//autoJumpState = !autoJumpState;
				return true;
			}
			
			// cancel mine, toggle sneak, run, jump
			if (code == mc.gameSettings.keyBindAttack.getKeyCode()) { // attack
				keyBindingToggle(mc.gameSettings.keyBindAttack,false);
				return false;
			}
			if (code == mc.gameSettings.keyBindUseItem.getKeyCode()) {
				keyBindingToggle(mc.gameSettings.keyBindUseItem,false);
				return false;
			}
			if (code == mc.gameSettings.keyBindSneak.getKeyCode()) {
				keyBindingToggle(mc.gameSettings.keyBindSneak,false);
				return false;
				//autoSneakState = false;
			}
			if (code == mc.gameSettings.keyBindJump.getKeyCode()) {
				keyBindingToggle(mc.gameSettings.keyBindJump,false);
				return false;
				//autoJumpState = false;
			}
			if (code == mc.gameSettings.keyBindForward.getKeyCode() || code == mc.gameSettings.keyBindBack.getKeyCode()) {
				keyBindingToggle(mc.gameSettings.keyBindForward,false);
				keyBindingToggle(mc.gameSettings.keyBindBack,false);	
				//autoRunState = false;
				cancelMeasureDistance();
				return false;
			}
		}
		return false;
	}	
	
	public String getOptionDesc(ControlPackEnumOptions option) {
		String s = (new StringBuilder()).append(translate(option.getLocKey())).append(": ").toString();
		if (option.getIsBool()) {
			boolean value = ControlPackOptions.booleanOptions.get(option);
			s += (value ? "ON" : "OFF");
		}
		else if (option.getIsFloat()) {
			float value = ControlPackOptions.floatOptions.get(option);
			s += ((int)(value * 100)) + "%";
		}
		else {
			Integer value = ControlPackOptions.intOptions.get(option);
			s += " " + getIntOptionDesc(option, value);
		}
		return s;
	}
	
	private String getIntOptionDesc(ControlPackEnumOptions option, Integer value) {
		if (option == ControlPackEnumOptions.AUTOTOOLMODE) {
			return value == 0 ? "Weakest" : (value == 1 ? "Strongest" : (value == 2 ? "Leftmost" : "Rightmost"));
		}
		else if (option == ControlPackEnumOptions.AUTOBLOCKMODE) {
			if (value == 0) {
				return "Leftmost";
			}
			if (value == 1) {
				return "Rightmost";
			}
			return "Slot #" + (value - 1);
		}
		else if (option == ControlPackEnumOptions.STATUSLOCATION || option == ControlPackEnumOptions.COORDINATESLOCATION) {
			if (value == 0) {
				return "Top Left";
			}
			if (value == 1) {
				return "Top Right";
			}
			if (value == 2) {
				return "Bottom Left";
			}
			if (value == 3) {
				return "Bottom Right";
			}
			if (value == 4) {
				return "OFF";
			}
		}
		return "";
	}
	
	public void chatMsg(String msg)
	{
		mc.ingameGUI.getChatGUI().printChatMessage(new TextComponentString("\u00A7c[ControlPack]\u00A7r " + msg));
	}
	
	public boolean compareItemType(Item i1, Item i2) {
		return (Item.getIdFromItem(i1) == Item.getIdFromItem(i2));
	}
	
	public boolean keyBindingIsDown(KeyBinding kb) {
		if (!(kb instanceof IKeyBinding)) {return false;}
		IKeyBinding kbs = (IKeyBinding) kb;
		return kbs.isDown();
	}
	
	public void keyBindingReset(KeyBinding kb) {
		if (!(kb instanceof IKeyBinding)) {return;}
		IKeyBinding kbs = (IKeyBinding) kb;
		kbs.reset();
	}
	
	public void keyBindingApplyToggle(KeyBinding kb) {
		if (!(kb instanceof IKeyBinding)) {return;}
		IKeyBinding kbs = (IKeyBinding) kb;
		kbs.applyToggle();
	}
	
	public void keyBindingToggle(KeyBinding kb, boolean bo) {
		if (!(kb instanceof IKeyBinding)) {return;}
		IKeyBinding kbs = (IKeyBinding) kb;
		kbs.toggle(bo);
	}
	
	public void keyBindingToggle(KeyBinding kb) {
		if (!(kb instanceof IKeyBinding)) {return;}
		IKeyBinding kbs = (IKeyBinding) kb;
		kbs.toggle();
	}
	
	public int keyBindingGetPressTime(KeyBinding kb) {
		if (!(kb instanceof IKeyBinding)) {return 0;}
		IKeyBinding kbs = (IKeyBinding) kb;
		return kbs.getPressTime();
	}
	
	public void keyBindingSetPressTime(KeyBinding kb, int vp) {
		if (!(kb instanceof IKeyBinding)) {return;}
		IKeyBinding kbs = (IKeyBinding) kb;
		kbs.setPressTime(vp);
	}
	
	public boolean keyBindingGetToggled(KeyBinding kb) {
		if (!(kb instanceof IKeyBinding)) {return false;}
		IKeyBinding kbs = (IKeyBinding) kb;
		return kbs.getToggled();
	}
	
	public KeyBinding keyBindings[];
	public KeyBinding keyBindAlternateLeft;
	public KeyBinding keyBindAlternateRight;
	public KeyBinding keyBindToggleSneak;
	public KeyBinding keyBindToggleRun;
	public KeyBinding keyBindToggleJump;
	public KeyBinding keyBindToggleMine;
	public KeyBinding keyBindToggleUse;
	public KeyBinding keyBindWalkDistance;
	public KeyBinding keyBindLookBehind;
	public KeyBinding keyBindToggleGamma;
	public KeyBinding keyBindTorch;
	public KeyBinding keyBindEat;
	public KeyBinding keyBindSayLocation;
	public KeyBinding keyBindWaypoints;
	
	public int lastMeasureDistance;
	public boolean measureDistanceState;
	public boolean measureDistanceStateMoving;
	public int measureDistanceStartX;
	public int measureDistanceStartZ;
	public double measureDistanceRemaining;
	
	public boolean lookBehind;
	public float lookBehindProgress;
	//public float lookBehindAnimationLength = 125F;
	// instant because of lag spikes :(
	public float lookBehindAnimationLength = 1F;
	public float lookBehindProgressTicks;
	public long lookBehindProgressTicksLast;
	
	public int swappedInventoryState;
	
	public float frontView_rotationPitch;
	public float frontView_rotationYaw;
	public float pitchSpeed = 0.2f;
	public float yawSpeed = 0.2f;
	
	private boolean toggleGammaState;
	private float originalGamma;
	
	private int isEating = -1;
	private boolean swapBack;
	private int swapBackTo;
	private boolean altKey;
	private boolean nagged;

	public boolean cameraStandMode;
	public boolean renderingWorld;

	private boolean previouslyPlacedBlock;
	private int previouslyPlacedBlockID;
	private Properties volumeSettingsProperties;
}

