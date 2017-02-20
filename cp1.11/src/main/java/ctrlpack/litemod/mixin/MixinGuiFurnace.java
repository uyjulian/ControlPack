/* Copyright (c) 2011-2016 Julian Uy, Dave Reed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package ctrlpack.litemod.mixin;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import ctrlpack.ControlPackEnumOptions;
import ctrlpack.ControlPackMain;
import ctrlpack.ControlPackOptions;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraft.tileentity.TileEntityFurnace;

@Mixin(GuiFurnace.class)
public abstract class MixinGuiFurnace extends GuiContainer {

	@Shadow private IInventory tileFurnace;
	public MixinGuiFurnace(Container inventorySlotsIn) {
		super(inventorySlotsIn);
		// TODO Auto-generated constructor stub
	}

	private void putItBack(ItemStack stack, Slot emptySlot) {
		if (stack != null && emptySlot != null) {
			addToSendQueue(emptySlot.slotNumber, 0, null);
		}
	}
	
	@Override
	protected void mouseClicked(int row, int col, int mouseButton) throws IOException {
		if (ControlPackMain.instance == null || !ControlPackOptions.booleanOptions.get(ControlPackEnumOptions.SMARTFURNACE)) {
			super.mouseClicked(row, col, mouseButton);
			return;
		}
		if (mouseButton != 0 ||
				(!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && !Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) ||
				getSlotAtPosition(row, col) == null ||
				getSlotAtPosition(row, col).getStack() == null) {
			super.mouseClicked(row, col, mouseButton);
			return;
		}
		ItemStack currentItemStack = mc.player.inventory.getItemStack();
		Slot emptySlot = null;
		// temporarily drop off anything currently being held
		if (currentItemStack != null) {
			// they have something held already. See if we can drop it off temporarily.
			emptySlot = findEmptySlot();
			if (emptySlot == null) {
				super.mouseClicked(row, col, mouseButton);
				return;
			}
			// drop it there
			addToSendQueue(emptySlot.slotNumber, 0, null);
		}
		Slot slot = getSlotAtPosition(row, col);
		ItemStack itemStack = slot.getStack();
		// if they shift clicked a furnace item, dump it into inventory
		if (slot.slotNumber < 3) {
			// dump it into the inventory
			dumpLoad(slot);
			putItBack(currentItemStack, emptySlot);
			return;
		}
		
		// 1. If they clicked on a smeltable material, put it into the to-bake slot as much as we can,
		//    unless there's already something there that is a different type.
		// 2. If they clicked on a burnable material, put it into the bottom slot as much as it takes
		//    to burn # of items in top slot, unless bottom slot already has a different material in it.
		
		// slots: 0 = top, 1 = fuel, 2 = result
		ItemStack fuelStack = tileFurnace.getStackInSlot(1); 
		ItemStack burningStack = tileFurnace.getStackInSlot(0);
		// can smelt if it has a recipe...
		boolean canSmelt = itemStack != null && FurnaceRecipes.instance().getSmeltingResult(itemStack) != null;
		// and the burningStack is empty, or it has the same item type and subtype in it.
		canSmelt = canSmelt && (burningStack == null || (Item.getIdFromItem(burningStack.getItem()) == Item.getIdFromItem(itemStack.getItem()) && (!itemStack.getItem().getHasSubtypes() || burningStack.getItemDamage() == itemStack.getItemDamage())));
		if (canSmelt) {
			// fill it up as much as we can
			if (burningStack == null) {
				// No burning stack yet, dropping the entire thing.
				tileFurnace.setInventorySlotContents(0, itemStack.copy());
				slot.putStack(null);
				// pick it up
				addToSendQueue(slot.slotNumber, 0, itemStack);
				// and drop it
				addToSendQueue(0, 0, null);
				
				putItBack(currentItemStack, emptySlot);
			}
			else if (burningStack.getCount() + itemStack.getCount() <= itemStack.getMaxStackSize()) {
				// Burning stack has stuff on it, and all of it will fit, so, dropping it all.
				ItemStack burningStackCopy = burningStack.copy();
				burningStack.grow(itemStack.getCount());
				tileFurnace.setInventorySlotContents(0, burningStack);
				slot.putStack(null);
				// pick it up
				addToSendQueue(slot.slotNumber, 0, itemStack);
				// and drop it
				addToSendQueue(0, 0, burningStackCopy);
				
				putItBack(currentItemStack, emptySlot);
			}
			else {
				// Burning stack has stuff on it, but wont fit all of it, so filling to max size.
				int remainingSizeLeft = itemStack.getMaxStackSize() - burningStack.getCount();
				ItemStack burningStackcopy = burningStack.copy();
				ItemStack itemStackCopy = itemStack.copy();
				burningStack.setCount(itemStack.getMaxStackSize());
				tileFurnace.setInventorySlotContents(0, burningStack);
				slot.decrStackSize(remainingSizeLeft);
				// pick it up
				addToSendQueue(slot.slotNumber, 0, itemStackCopy);
				// and drop it on the furnace slot
				addToSendQueue(0, 0, burningStackcopy);
				// then put it back down in the inventory
				addToSendQueue(slot.slotNumber, 0, null);
				
				putItBack(currentItemStack, emptySlot);
			}
		}
		else {
			int burnTime = TileEntityFurnace.getItemBurnTime(itemStack);
			if (burnTime > 0) {
				// see if the existing fuel is the same type
				if (fuelStack != null && (Item.getIdFromItem(fuelStack.getItem()) != Item.getIdFromItem(itemStack.getItem()) || fuelStack.getItemDamage() != itemStack.getItemDamage())) {
					putItBack(currentItemStack, emptySlot);
					super.mouseClicked(row, col, mouseButton);
					return;
				}
				float smeltsPerItem = (burnTime) / 200F;
				int stacksNeeded = burningStack == null ? 0 : (int)Math.ceil(burningStack.getCount() / smeltsPerItem);
				int existingFuelSize = fuelStack == null ? 0 : fuelStack.getCount();
				// need stacksNeeded, have existingFuelSize, stacksize = itemStack.getCount()
				if (existingFuelSize < stacksNeeded) {
					int toDrop = Math.min(itemStack.getCount(), stacksNeeded - existingFuelSize);
					if (fuelStack == null) {
						// No fuel yet, so creating new fuel stack.
						ItemStack itemStackCopy = itemStack.copy();
						ItemStack fuelStackCopy = itemStack.copy();
						fuelStackCopy.setCount(0);
						fuelStack = itemStack.splitStack(toDrop);
						tileFurnace.setInventorySlotContents(1, fuelStack);
						// pick it up
						addToSendQueue(slot.slotNumber, 0, itemStackCopy);
						if (itemStack.getCount() <= 0) {
							// all of the picked up fuel was dropped
							// local client, clear clicked slot
							slot.putStack(null);
							// remote, drop it all on the furnace slot (null=there wasnt any there already)
							addToSendQueue(1, 0, null);
						}
						else {
							// drop the needed item count one at a time with right clicks
							addToSendQueueDrop1(1, toDrop, fuelStackCopy);
							// drop remaining amount back into the inventory
							addToSendQueue(slot.slotNumber, 0, null);
						}
					}
					else {
						toDrop = Math.min(toDrop, fuelStack.getMaxStackSize() - fuelStack.getCount());
						// Existing fuel, so increase size
						ItemStack itemStackCopy = itemStack.copy();
						ItemStack fuelStackCopy = fuelStack.copy();
						// client side
						fuelStack.grow(toDrop);
						slot.decrStackSize(toDrop);
						// server side
						// pick it up
						addToSendQueue(slot.slotNumber, 0, itemStackCopy);
						if (slot.getStack() != null) {
							// e.g. We need 4 more, but we're holding 5. Right click 4 of them onto
							// the slot, then drop the remaining 1 in inventory where we got it from
							// drop the needed item count one at a time with right clicks
							addToSendQueueDrop1(1, toDrop, fuelStackCopy);
							// drop remaining amount back into the inventory, if any
							addToSendQueue(slot.slotNumber, 0, null);
						}
						else {
							// drop all we are holding
							// e.g. We need 4 more, but we're only holding 3. Just left click drop them.
							addToSendQueue(1, 0, itemStackCopy);
						}
					}
				}
				else {
					// note: We could TAKE fuel away since it has too much, but naw, might not be a place to put it..
					// would have to look for unfilled existing fuel slots, then empty slots, then give up, or a combo of all of these.
				}
				putItBack(currentItemStack, emptySlot);
			}
			else {
				putItBack(currentItemStack, emptySlot);
				super.mouseClicked(row, col, mouseButton);
			}
		}
		slot.onSlotChanged();
		tileFurnace.markDirty();
		inventorySlots.detectAndSendChanges();
	}
	
	private Slot findEmptySlot() {
		for (int i = 3; i < inventorySlots.inventorySlots.size(); i++) {
			Slot slot = inventorySlots.getSlot(i);
			if (slot != null && !slot.getHasStack()) {
				return slot;
			}
		}
		return null;
	}
	
	private void dumpLoad(Slot fromSlot) {
		ItemStack stack = fromSlot.getStack();
		// first look for non-empty slots of the same type with room to add stuff to
		boolean holdingStack = false;
		for (int i = 3; i < inventorySlots.inventorySlots.size(); i++) {
			Slot slot = inventorySlots.getSlot(i);
			if (slot == null) {
				continue;
			}
			ItemStack existingStack = slot.getStack();
			if (existingStack == null || Item.getIdFromItem(existingStack.getItem()) != Item.getIdFromItem(stack.getItem()) || existingStack.getItemDamage() != stack.getItemDamage()) {
				continue;
			}
			int canDrop = existingStack.getMaxStackSize() - existingStack.getCount();
			if (canDrop > 0) {
				if (stack.getCount() <= canDrop) {
					// there's enough room to drop everything
					ItemStack existingStackCopy = existingStack.copy();
					ItemStack stackCopy = stack.copy();
					// client:
					existingStack.grow(stack.getCount());
					stack.setCount(0);
					fromSlot.putStack(null);
					// server:
					if (!holdingStack) {
						// pick it up
						addToSendQueue(fromSlot.slotNumber, 0, stackCopy);
						holdingStack = true;
					}
					// and drop it
					addToSendQueue(slot.slotNumber, 0, existingStackCopy);
					return;
				}
				else {
					// not enough room for everything, drop as much as we can
					ItemStack existingStackCopy = existingStack.copy();
					ItemStack stackCopy = stack.copy();
					// client:
					existingStack.grow(canDrop);
					stack.shrink(canDrop);
					// server:
					if (!holdingStack) {
						// 	pick it up
						addToSendQueue(fromSlot.slotNumber, 0, stackCopy);
						holdingStack = true;
					}
					// and drop as much as we can onto it
					addToSendQueue(slot.slotNumber, 0, existingStackCopy);
				}
			}
		}
		// then look for empty slots to add the rest to
		for (int i = 3; i < inventorySlots.inventorySlots.size(); i++) {
			Slot slot = inventorySlots.getSlot(i);
			if (slot == null) {
				continue;
			}
			ItemStack existingStack = slot.getStack();
			if (existingStack != null) {
				continue;
			}
			// drop it all
			ItemStack stackCopy = stack.copy();
			// client:
			slot.putStack(stack.copy());
			stack.setCount(0);
			fromSlot.putStack(null);
			// server:
			if (!holdingStack) {
				// pick it up
				addToSendQueue(fromSlot.slotNumber, 0, stackCopy);
				holdingStack = true;
			}
			// and drop it off
			addToSendQueue(slot.slotNumber, 0, null);
			return;
		}
	}
	
	private void addToSendQueue(int slotNumber, int mouseButton, ItemStack stack) {
		//if (mc.isMultiplayerWorld()) {
			short actionnum = mc.player.openContainer.getNextTransactionID(mc.player.inventory);
			// i = windowid, slot num, mouse, shift, itemstack, action
			mc.getConnection().sendPacket(new CPacketClickWindow(inventorySlots.windowId, slotNumber, mouseButton, ClickType.PICKUP, stack, actionnum));
		//}
	}
	
	private void addToSendQueueDrop1(int slotNumber, int count, ItemStack stack) {
		for (int i = 0; i < count; i++) {
			ItemStack copy = stack.copy();
			addToSendQueue(slotNumber, 1, copy.getCount() == 0 ? null : copy);
			stack.grow(1);
		}
	}
	
	private Slot getSlotAtPosition(int i, int j) {
		for(int k = 0; k < inventorySlots.inventorySlots.size(); k++) {
			Slot slot = inventorySlots.inventorySlots.get(k);
			if(getIsMouseOverSlot(slot, i, j)) {
				return slot;
			}
		}

		return null;
	}
	
	private boolean getIsMouseOverSlot(Slot slot, int i, int j)
	{
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		i -= k;
		j -= l;
		return i >= slot.xPos - 1 && i < slot.xPos + 16 + 1 && j >= slot.yPos - 1 && j < slot.yPos + 16 + 1;
	}	

}
