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
	protected ASectionWire mWire;
	private int mArea;
	private int mPower;
	private int mAngledConn;
	private int mAngledBlock;
	private int mNeighborConn;
	private int mNeighborBlock;
	private int mInnerConn;
	private int mInnerBlock;

	protected AConnect( ASectionWire wire) {
		mWire = wire;
	}

	protected abstract boolean canConnect( IConnectable other);

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
	public int getProvidingPower( AreaType side) {
		int off = AreaType.offEdges( side);
		int neighbor = mInnerBlock & mNeighborBlock & mNeighborConn;
		if ((neighbor & off) != 0) {
			return mPower;
		}
		return 0;
	}

	@Override
	public abstract boolean isAllowed( ISection sec, IMaterial mat);

	@Override
	public boolean isAngled( AreaType edge) {
		int angled = mNeighborBlock & mAngledBlock & mAngledConn;
		return (angled & edge.mMask) != 0;
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
			int neighbor = mInnerBlock & mNeighborBlock & mNeighborConn;
			if ((neighbor & side.mMask) != 0 && other.getConnect().isPowerd()) {
				mPower |= 15;
			}
		}
	}

	@Override
	public void readFromNBT( NBTTagCompound nbt) {
		mPower = nbt.getInteger( NBT_POWER);
	}

	@Override
	public void refresh( TileStage tile) {
		int old = mPower;
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
		LogHelper.info( "refresh: %d -> %d", old, mPower);
//		tile.worldObj.markBlockForRenderUpdate( tile.xCoord, tile.yCoord, tile.zCoord);
		if (old != mPower) {
//			tile.updateAll();
		}
	}

	private void refreshAngled( TileStage tile) {
		for (AreaType edge : AreaType.edges()) {
			int x = tile.xCoord + edge.mDx;
			int y = tile.yCoord + edge.mDy;
			int z = tile.zCoord + edge.mDz;
			TileStage other = DarkLib.getTileEntity( tile.worldObj, x, y, z, TileStage.class);
			if (other != null) {
				refreshAngled( other, edge);
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

	private void refreshAngled( TileStage other, AreaType edge) {
		if (canConnect( other.getConnect())) {
			AreaType sideA = AreaType.sideA( edge).anti();
			if (other.isUsed( sideA)) {
				if (other.isAllowed( sideA)) {
					mAngledConn |= edge.mMask;
				}
				else {
					mAngledBlock &= ~edge.mMask;
				}
			}
			AreaType sideB = AreaType.sideB( edge).anti();
			if (other.isUsed( sideB)) {
				if (other.isAllowed( sideB)) {
					mAngledConn |= edge.mMask;
				}
				else {
					mAngledBlock &= ~edge.mMask;
				}
			}
		}
		if (other.isUsed( edge.anti())) {
			mAngledBlock &= ~edge.mMask;
		}
	}

	private void refreshInner( TileStage tile) {
		int temp = 0;
		for (AreaType side : AreaType.sides()) {
			if (tile.isUsed( side)) {
				int edges = AreaType.offEdges( side);
				if (tile.isAllowed( side)) {
					mInnerConn |= temp & edges;
					temp |= edges;
				}
				else {
					mInnerBlock &= ~edges;
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
			TileStage other = DarkLib.getTileEntity( tile.worldObj, x, y, z, TileStage.class);
			if (other != null) {
				refreshNeighbor( other, side);
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

	private void refreshNeighbor( TileStage other, AreaType side) {
		AreaType anti = side.anti();
		if (canConnect( other.getConnect())) {
			for (AreaType sideB : AreaType.sides( anti)) {
				if (other.isUsed( sideB)) {
					if (other.isAllowed( sideB)) {
						mNeighborConn |= AreaType.edge( side, sideB).mMask;
					}
					else {
						mNeighborBlock &= ~AreaType.edge( side, sideB).mMask;
					}
				}
				if (other.isUsed( AreaType.edge( anti, sideB))) {
					mNeighborBlock &= ~AreaType.edge( side, sideB).mMask;
				}
			}
		}
		if (other.isUsed( anti)) {
			mNeighborBlock &= ~AreaType.offEdges( side);
		}
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
		mAngledConn = 0;
		mAngledBlock = -1;
		mNeighborConn = 0;
		mNeighborBlock = -1;
		mInnerConn = 0;
		mInnerBlock = -1;
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
	}
}
