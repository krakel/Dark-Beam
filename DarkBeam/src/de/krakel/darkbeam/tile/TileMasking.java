/**
 * Dark Beam
 * TileMasking.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileMasking extends TileEntity {
	public TileMasking() {
	}

	@Override
	public void readFromNBT( NBTTagCompound nbt) {
		super.readFromNBT( nbt);
	}

	public boolean tryAddMask( int subHit, int i) {
		return false;
	}

	@Override
	public void updateEntity() {
	}

	@Override
	public void writeToNBT( NBTTagCompound nbt) {
		super.writeToNBT( nbt);
	}
}
