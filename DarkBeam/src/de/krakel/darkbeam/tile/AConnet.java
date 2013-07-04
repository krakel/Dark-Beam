/**
 * Dark Beam
 * AConnet.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.tile;

import net.minecraft.nbt.NBTTagCompound;

import de.krakel.darkbeam.core.ASectionWire;
import de.krakel.darkbeam.core.AreaType;
import de.krakel.darkbeam.core.ISection;

abstract class AConnet implements IConnetable {
	private static final String NBT_POWER = "pwr";
	protected ASectionWire mWire;
	protected int mArea;
	private int mPower;

	protected AConnet( ASectionWire wire) {
		mWire = wire;
	}

	@Override
	public void add( AreaType area) {
		mArea |= area.mMask;
	}

	@Override
	public boolean isAllowed( ISection sec) {
		return mWire.equals( sec);
	}

	@Override
	public boolean isEmpty() {
		return mArea == 0;
	}

	public boolean isPowerd() {
		return mPower > 0;
	}

	@Override
	public void readFromNBT( NBTTagCompound nbt) {
		mPower = nbt.getInteger( NBT_POWER);
	}

	@Override
	public void remove( AreaType area) {
		mArea &= ~area.mMask;
	}

	@Override
	public void writeToNBT( NBTTagCompound nbt) {
		nbt.setInteger( NBT_POWER, mPower);
	}
}
