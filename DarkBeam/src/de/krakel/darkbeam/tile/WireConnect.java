/**
 * Dark Beam
 * WireConnect.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.tile;

import net.minecraft.block.Block;
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

	private int indirectEdge( World world, ChunkPosition pos, int indirect) {
		for (AreaType edge : AreaType.valuesEdge()) {
			if (isValidEdgeCon( edge)) {
				int x = pos.x + edge.mDx;
				int y = pos.y + edge.mDy;
				int z = pos.z + edge.mDz;
				if (isWired( edge.sideA())) {
					indirect = Math.max( indirectSideWeak( world, x, y, z, edge.sideA().ordinal()), indirect);
				}
				if (isWired( edge.sideB())) {
					indirect = Math.max( indirectSideWeak( world, x, y, z, edge.sideB().ordinal()), indirect);
				}
			}
		}
		return indirect;
	}

	private int indirectPower( World world, ChunkPosition pos) {
		if (InsulateLib.UNKNOWN == mInsu) {
			int indirect = 0;
			indirect = indirectSide( world, pos, indirect);
			indirect = indirectEdge( world, pos, indirect);
			return indirect;
		}
		return 0;
	}

	private int indirectSide( World world, ChunkPosition pos, int indirect) {
		for (AreaType side : AreaType.valuesSide()) {
			if (isValidSideCon( side)) {
//				LogHelper.info( "powerSide: %s, %s", side.name(), toString());
				int x = pos.x + side.mDx;
				int y = pos.y + side.mDy;
				int z = pos.z + side.mDz;
				indirect = Math.max( indirectSideWeak( world, x, y, z, side.ordinal()), indirect);
				if (indirect >= 15) {
					break;
				}
			}
		}
		return indirect;
	}

	private int indirectSideStrong( World world, int x, int y, int z, int side) {
		int id = world.getBlockId( x, y, z);
		if (DarkLib.isWireBlock( id)) {
			return 0;
		}
		Block blk = Block.blocksList[id];
		if (blk == null) {
			return 0;
		}
		return blk.isProvidingStrongPower( world, x, y, z, side);
	}

	private int indirectSideWeak( World world, int x, int y, int z, int side) {
		int id = world.getBlockId( x, y, z);
		if (DarkLib.isWireBlock( id)) {
			return 0;
		}
		Block blk = Block.blocksList[id];
		if (blk == null) {
			return 0;
		}
		if (blk.isBlockNormalCube( world, x, y, z)) {
			return indirectWeak( world, x, y, z);
		}
		return blk.isProvidingWeakPower( world, x, y, z, side);
	}

	private int indirectWeak( World world, int x0, int y0, int z0) {
		int pwr = 0;
		for (AreaType side : AreaType.valuesSide()) {
			int x = x0 + side.mDx;
			int y = y0 + side.mDy;
			int z = z0 + side.mDz;
			pwr = Math.max( pwr, indirectSideStrong( world, x, y, z, side.ordinal()));
			if (pwr >= 15) {
				break;
			}
		}
		return pwr;
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
		int power = mPower;
		int cable = PowerSearch.cablePower( this, world, pos, mInsu);
		int indirect = indirectPower( world, pos);
		if (needPowerChange( power, cable, indirect)) {
			if (cable > indirect || cable > power) {
				power = Math.max( 0, cable - 1);
			}
			else if (power <= indirect) {
				power = indirect;
			}
			else {
				power = 0;
			}
			mPower = power;
			PowerSearch.addUpdateBlock( pos);
			PowerSearch.addSearchBlocks( pos, this);
		}
	}

	@Override
	public void writeToNBT( NBTTagCompound nbt) {
		super.writeToNBT( nbt);
		nbt.setInteger( NBT_POWER, mPower);
	}
}
