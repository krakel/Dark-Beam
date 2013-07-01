/**
 * Dark Beam
 * TileSection.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.tile;

import java.util.Iterator;
import java.util.NoSuchElementException;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

import com.sun.istack.internal.NotNull;

import de.krakel.darkbeam.core.Cube;
import de.krakel.darkbeam.core.DarkLib;
import de.krakel.darkbeam.core.IArea;
import de.krakel.darkbeam.core.ISection;
import de.krakel.darkbeam.core.SectionLib;
import de.krakel.darkbeam.core.helper.LogHelper;
import de.krakel.darkbeam.lib.BlockType;

public class TileStage extends TileEntity implements Iterable<Integer> {
	private static final String NBT_AREAS = "as";
	private static final String NBT_SECTIONS = "ss";
	private static final String NBT_POWER = "pwr";
	private int mArea;
	private int mAngledConn;
	private int mAngledBlock;
	private int mInnerConn;
	private int mInnerBlock;
	private int mNeighborConn;
	private int mNeighborBlock;
	private int[] mArr = new int[32];
	private int mWireMeta;
	private boolean mNeedUpdate = true;
	private int mPower;

	public TileStage() {
	}

	private boolean canConnect( TileStage other) {
		if (mWireMeta == other.mWireMeta) {
			return true;
		}
		int diff = SectionLib.getForDmg( mWireMeta).getLevel() - SectionLib.getForDmg( other.mWireMeta).getLevel();
		return diff == 1 || diff == -1;
	}

	private boolean containeWire() {
		for (int side = IArea.MIN_SIDE; side < IArea.MAX_SIDE; ++side) {
			if (mArr[side] == mWireMeta) {
				return true;
			}
		}
		return false;
	}

	private int getAngled() {
		return mInnerBlock & mNeighborBlock & mAngledBlock & mAngledConn;
	}

	private int getConnections() {
		return mInnerBlock & (mInnerConn | mNeighborBlock & (mNeighborConn | mAngledBlock & mAngledConn));
	}

	public int getCount( int meta) {
		if (meta != mWireMeta) {
			return 1;
		}
		int n = 0;
		for (int side = IArea.MIN_SIDE; side < IArea.MAX_SIDE; ++side) {
			if (mArr[side] == meta) {
				++n;
			}
		}
		if (n != 2) {
			return 1;
		}
		if (mArr[IArea.SIDE_DOWN] == meta && mArr[IArea.SIDE_UP] == meta) {
			mArr[IArea.SIDE_DOWN] = 0;
			mArea &= ~IArea.D;
			mArr[IArea.SIDE_UP] = 0;
			mArea &= ~IArea.U;
			mWireMeta = 0;
			return 3;
		}
		if (mArr[IArea.SIDE_NORTH] == meta && mArr[IArea.SIDE_SOUTH] == meta) {
			mArr[IArea.SIDE_NORTH] = 0;
			mArea &= ~IArea.N;
			mArr[IArea.SIDE_SOUTH] = 0;
			mArea &= ~IArea.S;
			mWireMeta = 0;
			return 3;
		}
		if (mArr[IArea.SIDE_WEST] == meta && mArr[IArea.SIDE_EAST] == meta) {
			mArr[IArea.SIDE_WEST] = 0;
			mArea &= ~IArea.W;
			mArr[IArea.SIDE_EAST] = 0;
			mArea &= ~IArea.E;
			mWireMeta = 0;
			return 3;
		}
		return 1;
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT( nbt);
		return new Packet132TileEntityData( xCoord, yCoord, zCoord, 0, nbt);
	}

	public int getMeta( int area) {
		try {
			return mArr[area];
		}
		catch (IndexOutOfBoundsException ex) {
			return 0;
		}
	}

	public ISection getSection( int area) {
		int dmg = getMeta( area);
		return SectionLib.getForDmg( dmg);
	}

	public boolean isAngled( int area, int side) {
		int off = Cube.offEdge( area, side);
		return (getAngled() & off) != 0;
	}

	public boolean isConnected( int area) {
		int off = Cube.offEdges( area);
		return (getConnections() & off) != 0;
	}

	public boolean isConnected( int area, int side) {
		int off = Cube.offEdge( area, side);
		return (getConnections() & off) != 0;
	}

	public boolean isEmpty() {
		return mArea == 0;
	}

	public boolean isInner( int side) {
		int off = 1 << side;
		return (mInnerConn & off) != 0;
	}

	public boolean isInUse( int area) {
		int off = 1 << area;
		return (mArea & off) != 0;
	}

	public int isProvidingStrongPower( int side) {
		if (mPower > 0 && isWire( side)) {
			return 15;
		}
		return 0;
	}

	public int isProvidingWeakPower( int side) {
		if (mPower > 0 && isWire( side)) {
			return 15;
		}
		return 0;
	}

	public boolean isUsed( int value) {
		return (mArea & value) != 0;
	}

	public boolean isValid( int value) {
		return (mArea & value) == 0;
	}

	public boolean isWire( int area) {
		if (mWireMeta != 0) {
			try {
				return mArr[area] == mWireMeta;
			}
			catch (IndexOutOfBoundsException ex) {
			}
		}
		return false;
	}

	public boolean isWired() {
		return mWireMeta != 0;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new AreaIterator( mArea);
	}

	@Override
	public void onDataPacket( INetworkManager net, Packet132TileEntityData paket) {
		readFromNBT( paket.customParam1);
	}

	private void powerAngled() {
		for (int edge = IArea.MIN_EDGE; edge < IArea.MAX_EDGE; ++edge) {
			int x = xCoord + Cube.relX( edge);
			int y = yCoord + Cube.relY( edge);
			int z = zCoord + Cube.relZ( edge);
			TileStage tile = DarkLib.getTileEntity( worldObj, x, y, z, TileStage.class);
			if (tile != null) {
			}
			else {
			}
		}
	}

	private void powerNeighbor() {
		for (int side = IArea.MIN_SIDE; side < IArea.MAX_SIDE; ++side) {
			int x = xCoord + Cube.relX( side);
			int y = yCoord + Cube.relY( side);
			int z = zCoord + Cube.relZ( side);
			TileStage tile = DarkLib.getTileEntity( worldObj, x, y, z, TileStage.class);
			if (tile != null) {
			}
			else {
			}
		}
	}

	@Override
	public void readFromNBT( NBTTagCompound nbt) {
		LogHelper.info( "readFromNBT: %s", nbt);
		super.readFromNBT( nbt);
		int areas = nbt.getInteger( NBT_AREAS);
		byte[] arr = nbt.getByteArray( NBT_SECTIONS);
		int n = 0;
		for (int a = areas, i = 0; a != 0; a >>>= 1, ++i) {
			if ((a & 1) != 0) {
				int mat = arr[n++] & 0xFF;
				int sec = arr[n++] & 0xFF;
				mArr[i] = sec << 8 | mat;
			}
		}
		mArea |= areas;
		mPower = nbt.getInteger( NBT_POWER);
	}

	public void refresh() {
		mInnerConn = 0;
		mNeighborConn = 0;
		mAngledConn = 0;
		mInnerBlock = -1;
		mNeighborBlock = -1;
		mAngledBlock = -1;
		refreshWire();
		refreshInner();
		refreshNeighbor();
		refreshAngled();
		if (isEmpty()) {
			invalidate();
		}
		else {
			powerNeighbor();
			powerAngled();
		}
	}

	private void refreshAngled() {
		for (int edge = IArea.MIN_EDGE; edge < IArea.MAX_EDGE; ++edge) {
			int x = xCoord + Cube.relEdgeX( edge);
			int y = yCoord + Cube.relEdgeY( edge);
			int z = zCoord + Cube.relEdgeZ( edge);
			TileStage tile = DarkLib.getTileEntity( worldObj, x, y, z, TileStage.class);
			if (tile != null) {
				refreshAngled( tile, edge);
			}
			else {
				int id = worldObj.getBlockId( x, y, z);
				if (DarkLib.canPowered( id)) {
					mAngledConn |= 1 << edge;
				}
				else {
					Block blk = Block.blocksList[id];
					if (blk != null && blk.canProvidePower()) {
						mAngledConn |= 1 << edge;
					}
				}
			}
		}
	}

	private void refreshAngled( @NotNull TileStage tile, int edge) {
		int sideA = Cube.sideA( edge) ^ 1;
		if (tile.isUsed( 1 << sideA)) {
			if (tile.getSection( sideA).isWire()) {
				mAngledConn |= 1 << edge;
			}
			else {
				mAngledBlock &= ~(1 << edge);
			}
		}
		int sideB = Cube.sideB( edge) ^ 1;
		if (tile.isUsed( 1 << sideB)) {
			if (tile.getSection( sideB).isWire()) {
				mAngledConn |= 1 << edge;
			}
			else {
				mAngledBlock &= ~(1 << edge);
			}
		}
		if (tile.isUsed( Cube.offAnti( edge))) {
			mAngledBlock &= ~(1 << edge);
		}
	}

	private void refreshInner() {
		int temp = 0;
		for (int side = IArea.MIN_SIDE; side < IArea.MAX_SIDE; ++side) {
			if (isUsed( 1 << side)) {
				if (getSection( side).isWire()) {
					int edges = Cube.offEdges( side);
					mInnerConn |= temp & edges;
					temp |= edges;
				}
				else {
					mInnerBlock &= ~Cube.offEdges( side);
				}
			}
		}
		for (int edge = IArea.MIN_EDGE; edge < IArea.MAX_EDGE; ++edge) {
			int off = 1 << edge;
			if (isUsed( off)) {
				mInnerBlock &= ~off;
			}
		}
	}

	private void refreshNeighbor() {
		for (int side = IArea.MIN_SIDE; side < IArea.MAX_SIDE; ++side) {
			int x = xCoord + Cube.relX( side);
			int y = yCoord + Cube.relY( side);
			int z = zCoord + Cube.relZ( side);
			TileStage tile = DarkLib.getTileEntity( worldObj, x, y, z, TileStage.class);
			if (tile != null) {
				refreshNeighbor( tile, side);
			}
			else if (!isUsed( 1 << side)) {
				int id = worldObj.getBlockId( x, y, z);
				if (DarkLib.canPowered( id)) {
					mNeighborConn |= Cube.offEdges( side);
				}
				else {
					Block blk = Block.blocksList[id];
					if (blk != null) {
						if (blk.canProvidePower()) {
							mNeighborConn |= Cube.offEdges( side);
						}
						else if (blk.blockMaterial.isOpaque() && blk.renderAsNormalBlock()) {
							mAngledBlock &= ~Cube.offEdges( side);
						}
					}
				}
			}
		}
	}

	private void refreshNeighbor( @NotNull TileStage tile, int side) {
		int[] sides = Cube.sides( side ^ 1);
		for (int sideB : sides) {
			int offB = 1 << sideB;
			if (tile.isUsed( offB) && canConnect( tile)) {
				if (!tile.getSection( sideB).isWire()) {
					mNeighborBlock &= ~Cube.offEdge( side, sideB);
				}
				else if (isUsed( offB) && getSection( sideB).isWire()) {
					mNeighborConn |= Cube.offEdge( side, sideB);
				}
			}
			if (tile.isUsed( Cube.offEdge( side ^ 1, sideB))) {
				mNeighborBlock &= ~Cube.offEdge( side, sideB);
			}
		}
		if (tile.isUsed( 1 << (side ^ 1))) {
			mNeighborBlock &= ~Cube.offEdges( side);
		}
	}

	private void refreshWire() {
		for (int side = IArea.MIN_SIDE; side < IArea.MAX_SIDE; ++side) {
			int dmg = getMeta( side);
			if (SectionLib.getForDmg( dmg).isWire()) {
				mWireMeta = dmg;
				return;
			}
		}
		mWireMeta = 0;
	}

	public void setSectionBounds( int area, Block blk) {
		ISection sec = getSection( area);
		sec.setSectionBounds( area, blk, this);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer( "TileSection[");
		for (int a = mArea, i = 0; a != 0; a >>>= 1, ++i) {
			if (i > 0) {
				sb.append( ',');
			}
			if ((a & 1) != 0) {
				int value = mArr[i];
				sb.append( value >> 8 & 0xFF);
				sb.append( '|');
				sb.append( value & 0xFF);
			}
			else {
				sb.append( "-|-");
			}
		}
		sb.append( "]");
		return sb.toString();
	}

	public boolean tryAdd( int area, int meta) {
		try {
			int off = 1 << area;
			if (isValid( off)) {
				ISection sec = SectionLib.getForDmg( meta);
				if (sec.isWire()) {
					if (mWireMeta == 0) {
						mWireMeta = meta;
					}
					else if (mWireMeta != meta) {
						return false;
					}
				}
				mArr[area] = meta;
				mArea |= off;
				LogHelper.info( "tryAdd: %b, %s, %s", worldObj != null && worldObj.isRemote, Cube.toString( area), toString());
				return true;
			}
		}
		catch (IndexOutOfBoundsException ex) {
		}
		return false;
	}

	public int tryRemove( int area) {
		try {
			int off = 1 << area;
			if (isUsed( off)) {
				int meta = mArr[area];
				mArr[area] = 0;
				mArea &= ~off;
				if (meta == mWireMeta && !containeWire()) {
					mWireMeta = 0;
				}
				LogHelper.info( "tryRemove: %b, %s, %s", worldObj != null && worldObj.isRemote, Cube.toString( area), toString());
				return meta;
			}
		}
		catch (IndexOutOfBoundsException ex) {
		}
		return -1;
	}

	@SuppressWarnings( "unused")
	private void updateAll() {
		int blockID = BlockType.STAGE.getId();
//		worldObj.notifyBlocksOfNeighborChange( xCoord, yCoord, zCoord, blockID);
//		worldObj.notifyBlocksOfNeighborChange( xCoord, yCoord + 1, zCoord, blockID, IDirection.DIR_DOWN);
//		worldObj.notifyBlocksOfNeighborChange( xCoord, yCoord - 1, zCoord, blockID, IDirection.DIR_UP);
//		worldObj.notifyBlocksOfNeighborChange( xCoord, yCoord, zCoord + 1, blockID, IDirection.DIR_NORTH);
//		worldObj.notifyBlocksOfNeighborChange( xCoord, yCoord, zCoord - 1, blockID, IDirection.DIR_SOUTH);
//		worldObj.notifyBlocksOfNeighborChange( xCoord + 1, yCoord, zCoord, blockID, IDirection.DIR_WEST);
//		worldObj.notifyBlocksOfNeighborChange( xCoord - 1, yCoord, zCoord, blockID, IDirection.DIR_EAST);
		Cube.updateAll( worldObj, xCoord, yCoord, zCoord, blockID);
	}

	@Override
	public void updateEntity() {
		refresh();
		if (mNeedUpdate && worldObj.isRemote) {
			worldObj.markBlockForRenderUpdate( xCoord, yCoord, zCoord);
			mNeedUpdate = false;
		}
	}

	@Override
	public void writeToNBT( NBTTagCompound nbt) {
		super.writeToNBT( nbt);
		int n = 0;
		for (int a = mArea; a != 0; a >>>= 1) {
			if ((a & 1) != 0) {
				n += 2;
			}
		}
		nbt.setInteger( NBT_AREAS, mArea);
		byte[] arr = new byte[n];
		n = 0;
		for (int a = mArea, i = 0; a != 0; a >>>= 1, ++i) {
			if ((a & 1) != 0) {
				int value = mArr[i];
				arr[n++] = (byte) (value & 0xFF);
				arr[n++] = (byte) (value >> 8 & 0xFF);
			}
		}
		nbt.setByteArray( NBT_SECTIONS, arr);
		nbt.setInteger( NBT_POWER, mPower);
		LogHelper.info( "writeToNBT: %s", nbt);
	}

	private static final class AreaIterator implements Iterator<Integer> {
		private int mArea;
		private int mIndex;

		private AreaIterator( int area) {
			mArea = area;
			findNext();
		}

		private void findNext() {
			while (mArea != 0 && (mArea & 1) == 0) {
				mArea >>>= 1;
				++mIndex;
			}
		}

		@Override
		public boolean hasNext() {
			return mArea != 0;
		}

		@Override
		public Integer next() {
			if (mArea == 0) {
				throw new NoSuchElementException( "No more elements");
			}
			Integer n = mIndex;
			mArea >>>= 1;
			++mIndex;
			findNext();
			return n;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException( "Removal not supported");
		}
	}
}
