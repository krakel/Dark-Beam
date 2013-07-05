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

import de.krakel.darkbeam.core.AreaType;
import de.krakel.darkbeam.core.DarkLib;
import de.krakel.darkbeam.core.ISection;
import de.krakel.darkbeam.core.SectionLib;
import de.krakel.darkbeam.core.helper.LogHelper;
import de.krakel.darkbeam.lib.BlockType;

public class TileStage extends TileEntity implements Iterable<AreaType> {
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
	private IConnetable mConnet = IConnetable.NO_CONNECT;
	private boolean mNeedUpdate = true;

	public TileStage() {
	}

	private boolean canConnect( TileStage other) {
		if (mConnet == other.mConnet) {
			return true;
		}
		int diff = mConnet.getLevel() - other.mConnet.getLevel();
		return diff == 1 || diff == -1;
	}

	private int getAngled() {
		return mInnerBlock & mNeighborBlock & mAngledBlock & mAngledConn;
	}

	private int getConnections() {
		return mInnerBlock & (mInnerConn | mNeighborBlock & (mNeighborConn | mAngledBlock & mAngledConn));
	}

	public int getCount( int meta) {
		ISection sec = SectionLib.getForDmg( meta);
		if (mConnet.isAllowed( sec) && mConnet.isInvalid()) {
			mConnet = IConnetable.NO_CONNECT;
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

	public int getMeta( AreaType area) {
		try {
			return mArr[area.ordinal()];
		}
		catch (IndexOutOfBoundsException ex) {
			return 0;
		}
	}

	public ISection getSection( AreaType area) {
		int dmg = getMeta( area);
		return SectionLib.getForDmg( dmg);
	}

	public boolean isConnected( AreaType edge) {
		return (getConnections() & edge.mMask) != 0;
	}

	public boolean isConnection( AreaType area) {
		int offs = AreaType.offEdges( area);
		return (getConnections() & offs) != 0;
	}

	public boolean isEdged( AreaType edge) {
		return (getAngled() & edge.mMask) != 0;
	}

	public boolean isEmpty() {
		return mArea == 0;
	}

	public boolean isInner( AreaType side) {
		return (mInnerConn & side.mMask) != 0;
	}

	public boolean isInUse( AreaType area) {
		return (mArea & area.mMask) != 0;
	}

	public int isProvidingStrongPower( AreaType side) {
		if (mConnet.isPowerd() && isWired( side)) {
			return 15;
		}
		return 0;
	}

	public int isProvidingWeakPower( AreaType side) {
		if (mConnet.isPowerd() && isWired( side)) {
			return 15;
		}
		return 0;
	}

	public boolean isUsed( AreaType area) {
		return (mArea & area.mMask) != 0;
	}

	public boolean isValid( int value) {
		return (mArea & value) == 0;
	}

	public boolean isWired() {
		return mConnet != IConnetable.NO_CONNECT;
	}

	public boolean isWired( AreaType area) {
		return mConnet.isWired( area);
	}

	@Override
	public Iterator<AreaType> iterator() {
		return new AreaIterator( mArea);
	}

	@Override
	public void onDataPacket( INetworkManager net, Packet132TileEntityData paket) {
		readFromNBT( paket.customParam1);
	}

	private void powerAngled() {
		for (AreaType edge : AreaType.edges()) {
			int x = xCoord + edge.mDx;
			int y = yCoord + edge.mDy;
			int z = zCoord + edge.mDz;
			TileStage tile = DarkLib.getTileEntity( worldObj, x, y, z, TileStage.class);
			if (tile != null) {
			}
			else {
			}
		}
	}

	private void powerNeighbor() {
		for (AreaType side : AreaType.sides()) {
			int x = xCoord + side.mDx;
			int y = yCoord + side.mDy;
			int z = zCoord + side.mDz;
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
		mConnet.readFromNBT( nbt);
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
		for (AreaType edge : AreaType.edges()) {
			int x = xCoord + edge.mDx;
			int y = yCoord + edge.mDy;
			int z = zCoord + edge.mDz;
			TileStage tile = DarkLib.getTileEntity( worldObj, x, y, z, TileStage.class);
			if (tile != null) {
				refreshAngled( tile, edge);
			}
			else {
				int id = worldObj.getBlockId( x, y, z);
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
			if (tile.getSection( sideA).isWire()) {
				mAngledConn |= edge.mMask;
			}
			else {
				mAngledBlock &= ~edge.mMask;
			}
		}
		AreaType sideB = AreaType.anti( AreaType.sideB( edge));
		if (tile.isUsed( sideB)) {
			if (tile.getSection( sideB).isWire()) {
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

	private void refreshInner() {
		int temp = 0;
		for (AreaType side : AreaType.sides()) {
			if (isUsed( side)) {
				if (getSection( side).isWire()) {
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
			if (isUsed( edge)) {
				mInnerBlock &= ~edge.mMask;
			}
		}
	}

	private void refreshNeighbor() {
		for (AreaType side : AreaType.sides()) {
			int x = xCoord + side.mDx;
			int y = yCoord + side.mDy;
			int z = zCoord + side.mDz;
			TileStage tile = DarkLib.getTileEntity( worldObj, x, y, z, TileStage.class);
			if (tile != null) {
				refreshNeighbor( tile, side);
			}
			else if (!isUsed( side)) {
				int id = worldObj.getBlockId( x, y, z);
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
			if (tile.isUsed( sideB) && canConnect( tile)) {
				if (!tile.getSection( sideB).isWire()) {
					mNeighborBlock &= ~AreaType.edge( side, sideB).mMask;
				}
				else if (isUsed( sideB) && getSection( sideB).isWire()) {
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

	private void refreshWire() {
		for (AreaType side : AreaType.sides()) {
			int dmg = getMeta( side);
			ISection sec = SectionLib.getForDmg( dmg);
			if (mConnet.isAllowed( sec)) {
				mConnet.set( side);
			}
		}
		if (mConnet.isEmpty()) {
			mConnet = IConnetable.NO_CONNECT;
		}
	}

	public void setSectionBounds( AreaType area, Block blk) {
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

	public boolean tryAdd( AreaType area, int meta) {
		try {
			if (isValid( area.mMask)) {
				ISection sec = SectionLib.getForDmg( meta);
				if (mConnet == IConnetable.NO_CONNECT) {
					mConnet = sec.createConnect();
				}
				if (mConnet.isAllowed( sec)) {
					mConnet.set( area);
				}
				else {
					return false;
				}
				mArr[area.ordinal()] = meta;
				mArea |= area.mMask;
				LogHelper.info( "tryAdd: %b, %s, %s", worldObj != null && worldObj.isRemote, area.name(), toString());
				return true;
			}
		}
		catch (IndexOutOfBoundsException ex) {
		}
		return false;
	}

	public int tryRemove( AreaType area) {
		try {
			if (isUsed( area)) {
				int meta = mArr[area.ordinal()];
				mArr[area.ordinal()] = 0;
				mArea &= ~area.mMask;
				mConnet.delete( area);
				if (mConnet.isEmpty()) {
					mConnet = IConnetable.NO_CONNECT;
				}
				LogHelper.info( "tryRemove: %b, %s, %s", worldObj != null && worldObj.isRemote, area.name(), toString());
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
		AreaType.updateNeighbor( worldObj, xCoord, yCoord, zCoord, blockID);
		AreaType.updateEdges( worldObj, xCoord, yCoord, zCoord, blockID);
		AreaType.updateNeighbor2( worldObj, xCoord, yCoord, zCoord, blockID);
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
		mConnet.writeToNBT( nbt);
		LogHelper.info( "writeToNBT: %s", nbt);
	}

	private static final class AreaIterator implements Iterator<AreaType> {
		private int mArea;
		private int mIndex;
		private AreaType[] mValues = AreaType.values();

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
		public AreaType next() {
			if (mArea == 0) {
				throw new NoSuchElementException( "No more elements");
			}
			Integer n = mIndex;
			mArea >>>= 1;
			++mIndex;
			findNext();
			return mValues[n];
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException( "Removal not supported");
		}
	}
}
