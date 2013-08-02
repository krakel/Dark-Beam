/**
 * Dark Beam
 * CableConnect.java
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

public class CableConnect extends AConnect {
	private static final String NBT_POWERS = "pwrs";
	private int mPower[] = new int[16];

	public CableConnect( ASectionWire wire) {
		super( wire);
	}

	@Override
	protected boolean canConnect( IConnectable other) {
		if (other.isAllowed( mWire, InsulateLib.UNKNOWN)) {
			return true;
		}
		return mWire.canDock( other.getWire());
	}

	@Override
	public int colorMultiplier() {
		return 0;
	}

	@Override
	public int getPower( IMaterial insu) {
		try {
			return mPower[insu.getID()];
		}
		catch (IndexOutOfBoundsException ex) {
			return 0;
		}
	}

	@Override
	public int getProvidingStrongPower( AreaType side) {
		return 0;
	}

	@Override
	public int getProvidingWeakPower( AreaType side) {
		return 0;
	}

	@Override
	public boolean isAllowed( ISection sec, IMaterial mat) {
		return mWire.equals( sec);
	}

	@Override
	public void readFromNBT( NBTTagCompound nbt) {
		super.readFromNBT( nbt);
		byte[] arr = nbt.getByteArray( NBT_POWERS);
		int max = Math.min( arr.length, mPower.length);
		for (int i = 0; i < max; ++i) {
			mPower[i] = arr[i] & 0xFF;
		}
	}

	@Override
	public String toString() {
		return DarkLib.format( "CableConnect=[%d, 0x%04X, 0x%04X, 0x%04X, 0x%04X]", mPower, mArea, mInnerEdge, mSidedEdge, mEdgedEdge);
	}

	@Override
	public void updatePower( World world, ChunkPosition pos) {
		boolean update = false;
		for (int i = 0; i < mPower.length; ++i) {
			IMaterial insu = InsulateLib.get( i);
			int power = mPower[i];
			int cable = PowerSearch.cablePower( this, world, pos, insu);
			if (needPowerChange( power, cable, 0)) {
				if (cable > 0 || cable > power) {
					power = Math.max( 0, cable - 1);
				}
				else if (power < 0) {
					power = 0;
				}
				else {
					power = 0;
				}
				mPower[i] = power;
				update = true;
			}
		}
		if (update) {
			PowerSearch.addUpdateBlock( pos);
			PowerSearch.addSearchBlocks( pos, this);
		}
	}

	@Override
	public void writeToNBT( NBTTagCompound nbt) {
		super.writeToNBT( nbt);
		int max = mPower.length;
		byte[] arr = new byte[max];
		for (int i = 0; i < max; ++i) {
			arr[i] = (byte) mPower[i];
		}
		nbt.setByteArray( NBT_POWERS, arr);
	}
}
