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
	private static final int INVALID_WE = AreaType.toMask( AreaType.WEST, AreaType.EAST);
	private static final int INVALID_NS = AreaType.toMask( AreaType.NORTH, AreaType.SOUTH);
	private static final int INVALID_DU = AreaType.toMask( AreaType.DOWN, AreaType.UP);
	private static final String NBT_POWER = "pwr";
	protected ASectionWire mWire;
	protected int mArea;
	private int mPower;

//	private int mWireMeta;
	protected AConnet( ASectionWire wire) {
		mWire = wire;
	}

	@Override
	public void delete( AreaType area) {
		mArea &= ~area.mMask;
	}

	@Override
	public int getCount() {
		return Integer.bitCount( mArea);
	}

	@Override
	public int getLevel() {
		return mWire.getLevel();
	}

	@Override
	public boolean isAllowed( ISection sec) {
		return mWire.equals( sec);
	}

	@Override
	public boolean isEmpty() {
		return mArea == 0;
	}

	@Override
	public boolean isInvalid() {
		if (mArea == INVALID_DU) {
			return true;
		}
		if (mArea == INVALID_NS) {
			return true;
		}
		if (mArea == INVALID_WE) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isPowerd() {
		return mPower > 0;
	}

	@Override
	public boolean isWired( AreaType area) {
		return (mArea & area.mMask) != 0;
	}

	@Override
	public void readFromNBT( NBTTagCompound nbt) {
		mPower = nbt.getInteger( NBT_POWER);
	}

	@Override
	public void set( AreaType area) {
		mArea |= area.mMask;
	}

	@Override
	public void writeToNBT( NBTTagCompound nbt) {
		nbt.setInteger( NBT_POWER, mPower);
	}
}
