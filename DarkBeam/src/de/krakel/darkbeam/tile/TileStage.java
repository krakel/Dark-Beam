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
import de.krakel.darkbeam.core.Position;
import de.krakel.darkbeam.core.SectionLib;
import de.krakel.darkbeam.core.helper.LogHelper;

public class TileStage extends TileEntity implements Iterable<Integer> {
	private static final String NBT_AREAS = "as";
	private static final String NBT_SECTIONS = "ss";
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

	public TileStage() {
	}

	private boolean canConnect( TileStage other) {
		if (mWireMeta == other.mWireMeta) {
			return true;
		}
		int diff = SectionLib.getForDmg( mWireMeta).getLevel() - SectionLib.getForDmg( other.mWireMeta).getLevel();
		return diff == 1 || diff == -1;
	}

	private boolean canPowered( int id) {
		return id == Block.pistonBase.blockID || id == Block.pistonStickyBase.blockID || id == Block.dispenser.blockID
			|| id == Block.stoneButton.blockID || id == Block.woodenButton.blockID || id == Block.lever.blockID
			|| id == Block.torchRedstoneIdle.blockID || id == Block.torchRedstoneActive.blockID
			|| id == Block.redstoneRepeaterIdle.blockID || id == Block.redstoneRepeaterActive.blockID
			|| id == Block.redstoneLampIdle.blockID || id == Block.redstoneLampActive.blockID;
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

	public int getArea() {
		return mArea;
	}

	private int getConnections() {
		return mInnerBlock & (mInnerConn | mNeighborBlock & (mNeighborConn | mAngledBlock & mAngledConn));
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

	public boolean isMeta( int area, int meta) {
		try {
			return mArr[area] == meta;
		}
		catch (IndexOutOfBoundsException ex) {
			return false;
		}
	}

	public boolean isUsed( int value) {
		return (mArea & value) != 0;
	}

	public boolean isValid( int value) {
		return (mArea & value) == 0;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new AreaIterator( mArea);
	}

	@Override
	public void onDataPacket( INetworkManager net, Packet132TileEntityData paket) {
		readFromNBT( paket.customParam1);
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
	}

	public void refresh() {
		reset();
		setWire();
		refreshInner();
		refreshNeighbor();
		refreshAngled();
		if (isEmpty()) {
			invalidate();
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
				if (canPowered( id)) {
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
			int x = xCoord + Position.relX( side);
			int y = yCoord + Position.relY( side);
			int z = zCoord + Position.relZ( side);
			TileStage tile = DarkLib.getTileEntity( worldObj, x, y, z, TileStage.class);
			if (tile != null) {
				refreshNeighbor( tile, side);
			}
			else if (!isUsed( 1 << side)) {
				int id = worldObj.getBlockId( x, y, z);
				if (canPowered( id)) {
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

	public void reset() {
		mInnerConn = 0;
		mNeighborConn = 0;
		mAngledConn = 0;
		mInnerBlock = -1;
		mNeighborBlock = -1;
		mAngledBlock = -1;
	}

	private void setWire() {
		for (int side = IArea.MIN_SIDE; side < IArea.MAX_SIDE; ++side) {
			int dmg = getMeta( side);
			if (SectionLib.getForDmg( dmg).isWire()) {
				mWireMeta = dmg;
				return;
			}
		}
		mWireMeta = 0;
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
				LogHelper.info( "tryAdd: %b, %s, %s", worldObj != null && worldObj.isRemote, Position.toString( area), toString());
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
				LogHelper.info( "tryRemove: %b, %s, %s", worldObj != null && worldObj.isRemote, Position.toString( area), toString());
				return meta;
			}
		}
		catch (IndexOutOfBoundsException ex) {
		}
		return -1;
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
