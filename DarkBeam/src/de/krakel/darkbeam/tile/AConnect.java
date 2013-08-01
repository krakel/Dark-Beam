/**
 * Dark Beam
 * AConnect.java
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

abstract class AConnect implements IConnectable {
	private static final int INVALID_WE = AreaType.toMask( AreaType.WEST, AreaType.EAST);
	private static final int INVALID_NS = AreaType.toMask( AreaType.NORTH, AreaType.SOUTH);
	private static final int INVALID_DU = AreaType.toMask( AreaType.DOWN, AreaType.UP);
	private static final String NBT_POWER = "pwr";
	private static final String NBT_INNER = "ci";
	private static final String NBT_SIDED = "cs";
	private static final String NBT_EDGED = "ce";
	protected ASectionWire mWire;
	private int mArea;
	private int mPower;
	private int mInnerEdge;
	private int mSidedEdge;
	private int mEdgedEdge;

	protected AConnect( ASectionWire wire) {
		mWire = wire;
	}

	protected abstract boolean canConnect( IConnectable other);

	@Override
	public void delete( AreaType area) {
		mArea &= ~area.mMask;
	}

	@Override
	public int getLevel() {
		return mWire.getLevel();
	}

	@Override
	public int getPower() {
		return mPower;
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

	private int getValidEdges() {
		return mInnerEdge | mSidedEdge | mEdgedEdge;
	}

	@Override
	public int indirectPower( World world, ChunkPosition pos) {
		return PowerSearch.indirectPower( this, world, pos);
	}

	@Override
	public abstract boolean isAllowed( ISection sec, IMaterial mat);

	@Override
	public boolean isConnected( AreaType side) {
		return (getValidEdges() & side.offEdges()) != 0;
	}

	@Override
	public boolean isEmpty() {
		return mArea == 0;
	}

	@Override
	public boolean isIllegal() {
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
	public boolean isValid( int value) {
		return (mArea & value) != 0;
	}

	@Override
	public boolean isValidEdge( AreaType edge) {
		return (getValidEdges() & edge.mMask) != 0;
	}

	@Override
	public boolean isValidEdgeCon( AreaType edge) {
		return (mEdgedEdge & edge.mMask) != 0;
	}

	@Override
	public boolean isValidSideCon( AreaType side) {
		return (mSidedEdge & side.offEdges()) != 0 && !isWired( side);
	}

	@Override
	public boolean isWired( AreaType area) {
		return (mArea & area.mMask) != 0;
	}

	@Override
	public void power( TileStage tile) {
		PowerSearch.update( tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord);
	}

	@Override
	public void readFromNBT( NBTTagCompound nbt) {
		mPower = nbt.getInteger( NBT_POWER);
		mInnerEdge = nbt.getInteger( NBT_INNER);
		mSidedEdge = nbt.getInteger( NBT_SIDED);
		mEdgedEdge = nbt.getInteger( NBT_EDGED);
	}

	@Override
	public void refresh( TileStage tile) {
		reset();
		refreshEdge( tile);
		refreshSide( tile);
		refreshInner( tile);
	}

	private void refreshEdge( TileStage tile) {
		for (AreaType edge : AreaType.valuesEdge()) {
			int x = tile.xCoord + edge.mDx;
			int y = tile.yCoord + edge.mDy;
			int z = tile.zCoord + edge.mDz;
			int id = tile.worldObj.getBlockId( x, y, z);
			if (id != 0) {
				TileStage other = DarkLib.getTileEntity( tile.worldObj, x, y, z, TileStage.class);
				if (other != null) {
					if (canConnect( other.getConnect()) && !other.isUsed( edge.anti())) {
						refreshEdge( other, edge);
					}
				}
				else if (DarkLib.canPowered( id)) {
					mEdgedEdge |= edge.mMask;
				}
				else if (DarkLib.canProvidePower( id)) {
					mEdgedEdge |= edge.mMask;
				}
			}
		}
	}

	private void refreshEdge( TileStage other, AreaType edge) {
		int breaked = 0;
		AreaType anti = edge.anti();
		AreaType sideA = anti.sideA();
		AreaType sideB = anti.sideB();
		if (other.isUsed( sideA)) {
			if (other.isAllowed( sideA)) {
				mEdgedEdge |= edge.mMask;
			}
			else {
				breaked |= edge.mMask;
			}
		}
		if (other.isUsed( sideB)) {
			if (other.isAllowed( sideB)) {
				mEdgedEdge |= edge.mMask;
			}
			else {
				breaked |= edge.mMask;
			}
		}
		mEdgedEdge &= ~breaked;
	}

	private void refreshInner( TileStage tile) {
		int breaked = 0;
		int temp = 0;
		for (AreaType side : AreaType.valuesSide()) {
			if (tile.isUsed( side)) {
				int edges = side.offEdges();
				if (tile.isAllowed( side)) {
					mInnerEdge |= temp & edges;
					temp |= edges;
				}
				else {
					breaked |= edges;
				}
			}
		}
		for (AreaType edge : AreaType.valuesEdge()) {
			if (tile.isUsed( edge)) {
				breaked |= edge.mMask;
			}
		}
		mInnerEdge &= ~breaked;
		mSidedEdge &= ~breaked;
		mEdgedEdge &= ~breaked;
	}

	private void refreshSide( TileStage tile) {
		int breaked = 0;
		for (AreaType side : AreaType.valuesSide()) {
			int x = tile.xCoord + side.mDx;
			int y = tile.yCoord + side.mDy;
			int z = tile.zCoord + side.mDz;
			int id = tile.worldObj.getBlockId( x, y, z);
			if (id != 0) {
				TileStage other = DarkLib.getTileEntity( tile.worldObj, x, y, z, TileStage.class);
				if (other != null) {
					if (other.isUsed( side.anti())) {
						breaked |= side.offEdges();
					}
					else {
						breaked |= refreshSide( other, side, canConnect( other.getConnect()));
					}
				}
				else if (DarkLib.canPowered( id)) {
					mSidedEdge |= side.offEdges();
				}
				else if (DarkLib.canProvidePower( id)) {
					mSidedEdge |= side.offEdges();
				}
				else if (DarkLib.canBreakPower( id) && !isWired( side)) {
					mEdgedEdge &= ~side.offEdges();
				}
			}
		}
		mSidedEdge &= ~breaked;
		mEdgedEdge &= ~breaked;
	}

	private int refreshSide( TileStage other, AreaType side, boolean connect) {
		int breaked = 0;
		AreaType anti = side.anti();
		for (AreaType border : anti.sides()) {
			if (other.isUsed( anti.edge( border))) {
				breaked |= side.edge( border).mMask;
			}
			else if (other.isUsed( border)) {
				if (connect && other.isAllowed( border)) {
					mSidedEdge |= side.edge( border).mMask;
				}
				else {
					breaked |= side.edge( border).mMask;
				}
			}
		}
		return breaked;
	}

	private void reset() {
		mEdgedEdge = 0;
		mSidedEdge = 0;
		mInnerEdge = 0;
//		mPower = 0;
	}

	@Override
	public void set( AreaType area) {
		mArea |= area.mMask;
	}

	@Override
	public String toString() {
		return DarkLib.format( "AConnect=[%d, 0x%04X, 0x%04X, 0x%04X, 0x%04X]", mPower, mArea, mInnerEdge, mSidedEdge, mEdgedEdge);
	}

	@Override
	public void updatePower( World world, ChunkPosition pos) {
		mPower = PowerSearch.updatePower( this, world, pos);
	}

	@Override
	public void writeToNBT( NBTTagCompound nbt) {
		nbt.setInteger( NBT_POWER, mPower);
		nbt.setInteger( NBT_INNER, mInnerEdge);
		nbt.setInteger( NBT_SIDED, mSidedEdge);
		nbt.setInteger( NBT_EDGED, mEdgedEdge);
	}
}
