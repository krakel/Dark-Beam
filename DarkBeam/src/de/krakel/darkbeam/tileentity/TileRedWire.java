/**
 * Dark Beam
 * TileRedWire.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

public class TileRedWire extends TileEntity {
	private boolean mPowered;

	public TileRedWire() {
	}

	public boolean isConnected( ForgeDirection dir) {
		return false;
	}

	public boolean isPowered() {
		return mPowered;
	}

	@Override
	public void readFromNBT( NBTTagCompound nbt) {
		super.readFromNBT( nbt);
	}

	public void updateOnNeighbor() {
	}

	public void updateOnPlace() {
	}

	@Override
	public void writeToNBT( NBTTagCompound nbt) {
		super.writeToNBT( nbt);
	}
}
