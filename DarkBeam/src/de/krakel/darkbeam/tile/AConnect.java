/**
 * Dark Beam
 * AConnect.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.tile;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;

import de.krakel.darkbeam.core.ASectionWire;
import de.krakel.darkbeam.core.AreaType;
import de.krakel.darkbeam.core.DarkLib;
import de.krakel.darkbeam.core.IMaterial;
import de.krakel.darkbeam.core.ISection;
import de.krakel.darkbeam.core.helper.LogHelper;

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
	public int getProvidingPower( AreaType side) {
		int off = AreaType.offEdges( side);
		if ((mSidedConn & off) != 0) {
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
		int offs = AreaType.offEdges( area);
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
	public boolean isValid( int value) {
		return (mArea & value) != 0;
	}

	@Override
	public boolean isWired( AreaType area) {
		return (mArea & area.mMask) != 0;
	}

	private void powerAngled( TileStage tile) {
		for (AreaType edge : AreaType.edges()) {
			int x = tile.xCoord + edge.mDx;
			int y = tile.yCoord + edge.mDy;
			int z = tile.zCoord + edge.mDz;
			TileStage other = DarkLib.getTileEntity( tile.worldObj, x, y, z, TileStage.class);
			if (other != null) {
				powerAngled( other, edge);
			}
			else {
			}
		}
	}

	private void powerAngled( TileStage other, AreaType edge) {
	}

	private void powerNeighbor( TileStage tile) {
		for (AreaType side : AreaType.sides()) {
			int x = tile.xCoord + side.mDx;
			int y = tile.yCoord + side.mDy;
			int z = tile.zCoord + side.mDz;
			TileStage other = DarkLib.getTileEntity( tile.worldObj, x, y, z, TileStage.class);
			if (other != null) {
				powerNeighbor( other, side);
			}
			else if (tile.worldObj.getIndirectPowerLevelTo( x, y, z, side.ordinal()) > 0) {
				mPower |= 15;
			}
			if (mPower >= 15) {
				return;
			}
		}
	}

	private void powerNeighbor( TileStage other, AreaType side) {
		if (canConnect( other.getConnect())) {
			if ((mSidedConn & side.mMask) != 0 && other.getConnect().isPowerd()) {
				mPower |= 15;
			}
		}
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
		int old = mPower;
		reset();
		refreshWire( tile);
		if (isEmpty()) {
			return;
		}
		refreshEdged( tile);
		refreshSided( tile);
		refreshInner( tile);
		powerAngled( tile);
		powerNeighbor( tile);
		LogHelper.info( "refresh: %d -> %d", old, mPower);
//		tile.worldObj.markBlockForRenderUpdate( tile.xCoord, tile.yCoord, tile.zCoord);
		if (old != mPower) {
//			tile.updateAll();
		}
	}

	private void refreshEdged( TileStage tile) {
		for (AreaType edge : AreaType.edges()) {
			int x = tile.xCoord + edge.mDx;
			int y = tile.yCoord + edge.mDy;
			int z = tile.zCoord + edge.mDz;
			TileStage other = DarkLib.getTileEntity( tile.worldObj, x, y, z, TileStage.class);
			if (other != null) {
				refreshEdged( other, edge);
			}
			else {
				int id = tile.worldObj.getBlockId( x, y, z);
				if (DarkLib.canPowered( id)) {
					mEdgedConn |= edge.mMask;
				}
				else {
					Block blk = Block.blocksList[id];
					if (blk != null && blk.canProvidePower()) {
						mEdgedConn |= edge.mMask;
					}
				}
			}
		}
	}

	private void refreshEdged( TileStage other, AreaType edge) {
		int blocked = 0;
		if (canConnect( other.getConnect())) {
			AreaType sideA = AreaType.sideA( edge).anti();
			if (other.isUsed( sideA)) {
				if (other.isAllowed( sideA)) {
					mEdgedConn |= edge.mMask;
				}
				else {
					blocked |= edge.mMask;
				}
			}
			AreaType sideB = AreaType.sideB( edge).anti();
			if (other.isUsed( sideB)) {
				if (other.isAllowed( sideB)) {
					mEdgedConn |= edge.mMask;
				}
				else {
					blocked |= edge.mMask;
				}
			}
		}
		if (other.isUsed( edge.anti())) {
			blocked |= edge.mMask;
		}
		mEdgedConn &= ~blocked;
	}

	private void refreshInner( TileStage tile) {
		int blocked = 0;
		int temp = 0;
		for (AreaType side : AreaType.sides()) {
			if (tile.isUsed( side)) {
				int edges = AreaType.offEdges( side);
				if (tile.isAllowed( side)) {
					mInnerConn |= temp & edges;
					temp |= edges;
				}
				else {
					blocked |= edges;
				}
			}
		}
		for (AreaType edge : AreaType.edges()) {
			if (tile.isUsed( edge)) {
				blocked |= edge.mMask;
			}
		}
		mInnerConn &= ~blocked;
		mSidedConn &= ~blocked;
		mEdgedConn &= ~blocked;
	}

	private void refreshSided( TileStage tile) {
		for (AreaType side : AreaType.sides()) {
			int x = tile.xCoord + side.mDx;
			int y = tile.yCoord + side.mDy;
			int z = tile.zCoord + side.mDz;
			TileStage other = DarkLib.getTileEntity( tile.worldObj, x, y, z, TileStage.class);
			if (other != null) {
				refreshSided( other, side);
			}
			else if (!tile.isUsed( side)) {
				int id = tile.worldObj.getBlockId( x, y, z);
				if (DarkLib.canPowered( id)) {
					mSidedConn |= AreaType.offEdges( side);
				}
				else {
					Block blk = Block.blocksList[id];
					if (blk != null) {
						if (blk.canProvidePower()) {
							mSidedConn |= AreaType.offEdges( side);
						}
						else if (blk.blockMaterial.isOpaque() && blk.renderAsNormalBlock()) {
							mEdgedConn &= ~AreaType.offEdges( side);
						}
					}
				}
			}
		}
	}

	private void refreshSided( TileStage other, AreaType side) {
		int blocked = 0;
		AreaType anti = side.anti();
		if (canConnect( other.getConnect())) {
			for (AreaType sideB : AreaType.sides( anti)) {
				if (other.isUsed( sideB)) {
					if (other.isAllowed( sideB)) {
						mSidedConn |= AreaType.edge( side, sideB).mMask;
					}
					else {
						blocked |= AreaType.edge( side, sideB).mMask;
					}
				}
				if (other.isUsed( AreaType.edge( anti, sideB))) {
					blocked |= AreaType.edge( side, sideB).mMask;
				}
			}
		}
		if (other.isUsed( anti)) {
			blocked |= AreaType.offEdges( side);
		}
		mSidedConn &= ~blocked;
		mEdgedConn &= ~blocked;
	}

	private void refreshWire( TileStage tile) {
		for (AreaType side : AreaType.sides()) {
			ISection sec = tile.getSection( side);
			IMaterial mat = tile.getMaterial( side);
			if (isAllowed( sec, mat)) {
				set( side);
			}
		}
	}

	private void reset() {
		mEdgedConn = 0;
		mSidedConn = 0;
		mInnerConn = 0;
		mPower = 0;
	}

	@Override
	public void set( AreaType area) {
		mArea |= area.mMask;
	}

	@Override
	public String toString() {
		return DarkLib.format( "AConnect=[%d, 0x%04X, 0x%04X]", mPower, mArea, getConnections());
	}

	@Override
	public void writeToNBT( NBTTagCompound nbt) {
		nbt.setInteger( NBT_POWER, mPower);
		nbt.setInteger( NBT_INNER, mInnerConn);
		nbt.setInteger( NBT_SIDED, mSidedConn);
		nbt.setInteger( NBT_EDGED, mEdgedConn);
	}
}
