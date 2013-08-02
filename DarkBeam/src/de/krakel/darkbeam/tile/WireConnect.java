/**
 * Dark Beam
 * WireConnect.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

import de.krakel.darkbeam.core.ASectionWire;
import de.krakel.darkbeam.core.AreaType;
import de.krakel.darkbeam.core.DarkLib;
import de.krakel.darkbeam.core.IMaterial;
import de.krakel.darkbeam.core.ISection;
import de.krakel.darkbeam.core.InsulateLib;

public class WireConnect extends AConnect {
	private static final String NBT_POWER = "pwr";
	private IMaterial mInsu;
	private int mPower;

	public WireConnect( ASectionWire wire, IMaterial insu) {
		super( wire);
		mInsu = insu;
	}

	@Override
	protected boolean canConnect( IConnectable other) {
		if (other.isAllowed( mWire, mInsu)) {
			return true;
		}
		return mWire.canDock( other.getWire());
	}

	@Override
	public int colorMultiplier() {
		if (mPower > 0) {
			return 0xFFFFFF;
		}
		return 0;
	}

	@Override
	public int getPower( IMaterial insu) {
		if (mInsu == insu) {
			return mPower;
		}
		if (mInsu == InsulateLib.UNKNOWN) {
			return mPower;
		}
		if (insu == InsulateLib.UNKNOWN) {
			return mPower;
		}
		return 0;
	}

	@Override
	public int getProvidingStrongPower( AreaType side) {
		if (isValidSideCon( side)) {
			return mPower;
		}
		return 0;
	}

	@Override
	public int getProvidingWeakPower( AreaType side) {
		if (isValidSideCon( side)) {
			return mPower;
		}
		return 0;
	}

	@Override
	public int indirectPower( World world, ChunkPosition pos) {
		if (InsulateLib.UNKNOWN == mInsu) {
			return PowerSearch.indirectPower( this, world, pos);
		}
		return 0;
	}

	@Override
	public boolean isAllowed( ISection sec, IMaterial mat) {
		return mWire.equals( sec) && mInsu.equals( mat);
	}

	@Override
	public void readFromNBT( NBTTagCompound nbt) {
		super.readFromNBT( nbt);
		mPower = nbt.getInteger( NBT_POWER);
	}

	@Override
	public String toString() {
		return DarkLib.format( "WireConnect=[%d, 0x%04X, 0x%04X, 0x%04X, 0x%04X]", mPower, mArea, mInnerEdge, mSidedEdge, mEdgedEdge);
	}

	@Override
	public void updatePower( World world, ChunkPosition pos) {
		mPower = PowerSearch.updatePower( this, world, pos, mInsu);
	}

	@Override
	public void writeToNBT( NBTTagCompound nbt) {
		super.writeToNBT( nbt);
		nbt.setInteger( NBT_POWER, mPower);
	}
}
