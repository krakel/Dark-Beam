/**
 * Dark Beam
 * TileUnit.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileUnits extends TileEntity {
	public TileUnits() {
	}

	@Override
	public void readFromNBT( NBTTagCompound nbt) {
		super.readFromNBT( nbt);
	}

	public boolean tryAddUnit( int subHit, int i) {
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
