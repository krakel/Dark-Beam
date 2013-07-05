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
import de.krakel.darkbeam.core.ISection;

abstract class AConnect implements IConnectable {
	private static final int INVALID_WE = AreaType.toMask( AreaType.WEST, AreaType.EAST);
	private static final int INVALID_NS = AreaType.toMask( AreaType.NORTH, AreaType.SOUTH);
	private static final int INVALID_DU = AreaType.toMask( AreaType.DOWN, AreaType.UP);
	private static final String NBT_POWER = "pwr";
	protected ASectionWire mWire;
	protected int mArea;
	private int mPower;
	private int mAngledConn;
	private int mAngledBlock;
	private int mNeighborConn;
	private int mNeighborBlock;
	private int mInnerConn;
	private int mInnerBlock;

//	private int mWireMeta;
	protected AConnect( ASectionWire wire) {
		mWire = wire;
	}

	private boolean canConnect( IConnectable other) {
		if (this == other) {
			return true;
		}
		int diff = getLevel() - other.getLevel();
		return diff == 1 || diff == -1;
	}

	@Override
	public void delete( AreaType area) {
		mArea &= ~area.mMask;
	}

	private int getConnections() {
		return mInnerBlock & (mInnerConn | mNeighborBlock & (mNeighborConn | mAngledBlock & mAngledConn));
	}

	@Override
	public int getLevel() {
		return mWire.getLevel();
	}

	@Override
	public boolean isAllowed( ISection sec) {
		return mWire.equals( sec);
	}

	private boolean isCompatible( ISection sec) {
		return sec.getLevel() >= mWire.getLevel();
	}

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
		return (mNeighborBlock & mAngledBlock & mAngledConn & edge.mMask) != 0;
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
	public int isProvidingStrongPower( AreaType side) {
		if (isPowerd() && isWired( side)) {
			return 15;
		}
		return 0;
	}

	@Override
	public int isProvidingWeakPower( AreaType side) {
		if (isPowerd() && isWired( side)) {
			return 15;
		}
		return 0;
	}

	@Override
	public boolean isValid( int value) {
		return (mArea & value) != 0;
	}

	@Override
	public boolean isWired( AreaType area) {
		return (mArea & area.mMask) != 0;
	}

	@SuppressWarnings( "static-method")
	private void powerAngled( TileStage tile) {
		for (AreaType edge : AreaType.edges()) {
			int x = tile.xCoord + edge.mDx;
			int y = tile.yCoord + edge.mDy;
			int z = tile.zCoord + edge.mDz;
			TileStage tn = DarkLib.getTileEntity( tile.worldObj, x, y, z, TileStage.class);
			if (tn != null) {
			}
			else {
			}
		}
	}

	@SuppressWarnings( "static-method")
	private void powerNeighbor( TileStage tile) {
		for (AreaType side : AreaType.sides()) {
			int x = tile.xCoord + side.mDx;
			int y = tile.yCoord + side.mDy;
			int z = tile.zCoord + side.mDz;
			TileStage tn = DarkLib.getTileEntity( tile.worldObj, x, y, z, TileStage.class);
			if (tn != null) {
			}
			else {
			}
		}
	}

	@Override
	public void readFromNBT( NBTTagCompound nbt) {
		mPower = nbt.getInteger( NBT_POWER);
	}

	@Override
	public void refresh( TileStage tile) {
		reset();
		refreshWire( tile);
		if (isEmpty()) {
			return;
		}
		refreshInner( tile);
		refreshNeighbor( tile);
		refreshAngled( tile);
		powerNeighbor( tile);
		powerAngled( tile);
	}

	private void refreshAngled( TileStage tile) {
		for (AreaType edge : AreaType.edges()) {
			int x = tile.xCoord + edge.mDx;
			int y = tile.yCoord + edge.mDy;
			int z = tile.zCoord + edge.mDz;
			TileStage tn = DarkLib.getTileEntity( tile.worldObj, x, y, z, TileStage.class);
			if (tn != null) {
				refreshAngled( tn, edge);
			}
			else {
				int id = tile.worldObj.getBlockId( x, y, z);
				if (DarkLib.canPowered( id)) {
					mAngledConn |= edge.mMask;
				}
				else {
					Block blk = Block.blocksList[id];
					if (blk != null && blk.canProvidePower()) {
						mAngledConn |= edge.mMask;
					}
				}
			}
		}
	}

	private void refreshAngled( TileStage tile, AreaType edge) {
		AreaType sideA = AreaType.anti( AreaType.sideA( edge));
		if (tile.isUsed( sideA)) {
			if (isCompatible( tile.getSection( sideA))) {
				mAngledConn |= edge.mMask;
			}
			else {
				mAngledBlock &= ~edge.mMask;
			}
		}
		AreaType sideB = AreaType.anti( AreaType.sideB( edge));
		if (tile.isUsed( sideB)) {
			if (isCompatible( tile.getSection( sideB))) {
				mAngledConn |= edge.mMask;
			}
			else {
				mAngledBlock &= ~edge.mMask;
			}
		}
		if (tile.isUsed( AreaType.anti( edge))) {
			mAngledBlock &= ~edge.mMask;
		}
	}

	private void refreshInner( TileStage tile) {
		int temp = 0;
		for (AreaType side : AreaType.sides()) {
			if (tile.isUsed( side)) {
				if (isAllowed( tile.getSection( side))) {
					int edges = AreaType.offEdges( side);
					mInnerConn |= temp & edges;
					temp |= edges;
				}
				else {
					mInnerBlock &= ~AreaType.offEdges( side);
				}
			}
		}
		for (AreaType edge : AreaType.edges()) {
			if (tile.isUsed( edge)) {
				mInnerBlock &= ~edge.mMask;
			}
		}
	}

	private void refreshNeighbor( TileStage tile) {
		for (AreaType side : AreaType.sides()) {
			int x = tile.xCoord + side.mDx;
			int y = tile.yCoord + side.mDy;
			int z = tile.zCoord + side.mDz;
			TileStage tn = DarkLib.getTileEntity( tile.worldObj, x, y, z, TileStage.class);
			if (tn != null) {
				refreshNeighbor( tn, side);
			}
			else if (!tile.isUsed( side)) {
				int id = tile.worldObj.getBlockId( x, y, z);
				if (DarkLib.canPowered( id)) {
					mNeighborConn |= AreaType.offEdges( side);
				}
				else {
					Block blk = Block.blocksList[id];
					if (blk != null) {
						if (blk.canProvidePower()) {
							mNeighborConn |= AreaType.offEdges( side);
						}
						else if (blk.blockMaterial.isOpaque() && blk.renderAsNormalBlock()) {
							mAngledBlock &= ~AreaType.offEdges( side);
						}
					}
				}
			}
		}
	}

	private void refreshNeighbor( TileStage tile, AreaType side) {
		AreaType[] sides = AreaType.sides( AreaType.anti( side));
		for (AreaType sideB : sides) {
			if (tile.isUsed( sideB) && canConnect( tile.getConnect())) {
				if (!isCompatible( tile.getSection( sideB))) {
					mNeighborBlock &= ~AreaType.edge( side, sideB).mMask;
				}
				else if (tile.isUsed( sideB) && isCompatible( tile.getSection( sideB))) {
					mNeighborConn |= AreaType.edge( side, sideB).mMask;
				}
			}
			if (tile.isUsed( AreaType.edge( AreaType.anti( side), sideB))) {
				mNeighborBlock &= ~AreaType.edge( side, sideB).mMask;
			}
		}
		if (tile.isUsed( AreaType.anti( side))) {
			mNeighborBlock &= ~AreaType.offEdges( side);
		}
	}

	private void refreshWire( TileStage tile) {
		for (AreaType side : AreaType.sides()) {
			ISection sec = tile.getSection( side);
			if (isAllowed( sec)) {
				set( side);
			}
		}
	}

	private void reset() {
		mAngledConn = 0;
		mAngledBlock = -1;
		mNeighborConn = 0;
		mNeighborBlock = -1;
		mInnerConn = 0;
		mInnerBlock = -1;
	}

	@Override
	public void set( AreaType area) {
		mArea |= area.mMask;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer( "AConnect[");
		sb.append( mPower);
		sb.append( "]");
		return sb.toString();
	}

	@Override
	public void writeToNBT( NBTTagCompound nbt) {
		nbt.setInteger( NBT_POWER, mPower);
	}
}
