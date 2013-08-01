/**
 * Dark Beam
 * AConnect.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.tile;

import net.minecraft.nbt.NBTTagCompound;

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
	private int mInnerConn;
	private int mSidedConn;
	private int mEdgedConn;

	protected AConnect( ASectionWire wire) {
		mWire = wire;
	}

	protected abstract boolean canConnect( IConnectable other);

	@Override
	public void delete( AreaType area) {
		mArea &= ~area.mMask;
	}

	private int getConnections() {
		return mInnerConn | mSidedConn | mEdgedConn;
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
		if (isSided( side.offEdges())) {
			return mPower;
		}
		return 0;
	}

	@Override
	public int getProvidingWeakPower( AreaType side) {
		if (isSided( side.offEdges())) {
			return mPower;
		}
		return 0;
	}

	@Override
	public abstract boolean isAllowed( ISection sec, IMaterial mat);

	@Override
	public boolean isConnected( AreaType edge) {
		return (getConnections() & edge.mMask) != 0;
	}

	@Override
	public boolean isConnection( AreaType area) {
		int offs = area.offEdges();
		return (getConnections() & offs) != 0;
	}

	@Override
	public boolean isEdged( AreaType edge) {
		return (mEdgedConn & edge.mMask) != 0;
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
	public boolean isSided( int value) {
		return (mSidedConn & value) != 0;
	}

	@Override
	public boolean isValid( int value) {
		return (mArea & value) != 0;
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
		mInnerConn = nbt.getInteger( NBT_INNER);
		mSidedConn = nbt.getInteger( NBT_SIDED);
		mEdgedConn = nbt.getInteger( NBT_EDGED);
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
					mEdgedConn |= edge.mMask;
				}
				else if (DarkLib.canProvidePower( id)) {
					mEdgedConn |= edge.mMask;
				}
			}
		}
	}

	private void refreshEdge( TileStage other, AreaType edge) {
		int breaked = 0;
		AreaType anti = edge.anti();
		AreaType sideA = anti.sideA();
		if (other.isUsed( sideA)) {
			if (other.isAllowed( sideA)) {
				mEdgedConn |= edge.mMask;
			}
			else {
				breaked |= edge.mMask;
			}
		}
		AreaType sideB = anti.sideB();
		if (other.isUsed( sideB)) {
			if (other.isAllowed( sideB)) {
				mEdgedConn |= edge.mMask;
			}
			else {
				breaked |= edge.mMask;
			}
		}
		mEdgedConn &= ~breaked;
	}

	private void refreshInner( TileStage tile) {
		int breaked = 0;
		int temp = 0;
		for (AreaType side : AreaType.valuesSide()) {
			if (tile.isUsed( side)) {
				int edges = side.offEdges();
				if (tile.isAllowed( side)) {
					mInnerConn |= temp & edges;
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
		mInnerConn &= ~breaked;
		mSidedConn &= ~breaked;
		mEdgedConn &= ~breaked;
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
					mSidedConn |= side.offEdges();
				}
				else if (DarkLib.canProvidePower( id)) {
					mSidedConn |= side.offEdges();
				}
				else if (DarkLib.canBreakPower( id) && !isWired( side)) {
					mEdgedConn &= ~side.offEdges();
				}
			}
		}
		mSidedConn &= ~breaked;
		mEdgedConn &= ~breaked;
	}

	private int refreshSide( TileStage other, AreaType side, boolean connect) {
		int breaked = 0;
		AreaType anti = side.anti();
		for (AreaType sideB : anti.sides()) {
			if (other.isUsed( anti.edge( sideB))) {
				breaked |= side.edge( sideB).mMask;
			}
			else if (other.isUsed( sideB)) {
				if (connect && other.isAllowed( sideB)) {
					mSidedConn |= side.edge( sideB).mMask;
				}
				else {
					breaked |= side.edge( sideB).mMask;
				}
			}
		}
		return breaked;
	}

	private void reset() {
		mEdgedConn = 0;
		mSidedConn = 0;
		mInnerConn = 0;
//		mPower = 0;
	}

	@Override
	public void set( AreaType area) {
		mArea |= area.mMask;
	}

	@Override
	public void setPower( int power) {
		mPower = power;
	}

	@Override
	public String toString() {
		return DarkLib.format( "AConnect=[%d, 0x%04X, 0x%04X, 0x%04X, 0x%04X]", mPower, mArea, mInnerConn, mSidedConn, mEdgedConn);
	}

	@Override
	public void writeToNBT( NBTTagCompound nbt) {
		nbt.setInteger( NBT_POWER, mPower);
		nbt.setInteger( NBT_INNER, mInnerConn);
		nbt.setInteger( NBT_SIDED, mSidedConn);
		nbt.setInteger( NBT_EDGED, mEdgedConn);
	}
}
