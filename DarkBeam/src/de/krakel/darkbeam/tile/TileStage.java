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
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

import de.krakel.darkbeam.core.AreaType;
import de.krakel.darkbeam.core.DarkLib;
import de.krakel.darkbeam.core.IMaterial;
import de.krakel.darkbeam.core.ISection;
import de.krakel.darkbeam.core.MaterialLib;
import de.krakel.darkbeam.core.SectionLib;
import de.krakel.darkbeam.lib.BlockType;

public class TileStage extends TileEntity implements Iterable<AreaType> {
	private static final String NBT_AREAS = "as";
	private static final String NBT_SECTIONS = "ss";
	private int mArea;
	private ISection[] mSec = new ISection[32];
	private IMaterial[] mMat = new IMaterial[32];
	private IConnectable mConnect = IConnectable.NO_CONNECT;

	public TileStage() {
	}

	@Override
	public boolean canUpdate() {
		return false;
	}

	private void dropItem( int meta) {
		ISection sec = SectionLib.getForDmg( meta);
		ItemStack stk = new ItemStack( BlockType.STAGE.getBlock(), getDropCount( sec, sec.getForDmg( meta)), meta);
		DarkLib.dropItem( worldObj, xCoord, yCoord, zCoord, stk);
	}

	public IConnectable getConnect() {
		return mConnect;
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT( nbt);
		return new Packet132TileEntityData( xCoord, yCoord, zCoord, 0, nbt);
	}

	private int getDropCount( ISection sec, IMaterial mat) {
		if (mConnect.isAllowed( sec, mat) && mConnect.isIllegal()) {
			mConnect = IConnectable.NO_CONNECT;
			return 3;
		}
		return 1;
	}

	public IMaterial getMaterial( AreaType area) {
		return getMaterial( area.ordinal());
	}

	private IMaterial getMaterial( int idx) {
		IMaterial mat = mMat[idx];
		return mat != null ? mat : MaterialLib.UNKNOWN;
	}

	public int getMeta( AreaType area) {
		try {
			int idx = area.ordinal();
			return DarkLib.toDmg( getSection( idx), getMaterial( idx));
		}
		catch (IndexOutOfBoundsException ex) {
		}
		return 0;
	}

	public ISection getSection( AreaType area) {
		return getSection( area.ordinal());
	}

	private ISection getSection( int idx) {
		ISection sec = mSec[idx];
		return sec != null ? sec : SectionLib.UNKNOWN;
	}

	boolean isAllowed( AreaType area) {
		return mConnect.isAllowed( getSection( area), getMaterial( area));
	}

	public boolean isEmpty() {
		return mArea == 0;
	}

	public boolean isUsed( AreaType area) {
		return (mArea & area.mMask) != 0;
	}

	public boolean isValid( int value) {
		return (mArea & value) == 0;
	}

	@Override
	public Iterator<AreaType> iterator() {
		return new AreaIterator( mArea);
	}

	private void markForUpdate() {
//		LogHelper.info( "markForUpdate: %s", LogHelper.toString( this));
		worldObj.markBlockForUpdate( xCoord, yCoord, zCoord);
	}

	private void notifyAllNeighbor() {
//		LogHelper.info( "notifyAllChange: %s", LogHelper.toString( this));
		int blockID = BlockType.STAGE.getId();
		DarkLib.notifyNeighborChange( worldObj, xCoord, yCoord, zCoord, blockID);
		DarkLib.notifyEdgesChange( worldObj, xCoord, yCoord, zCoord, blockID);
		DarkLib.notifyNeighborChange2( worldObj, xCoord, yCoord, zCoord, blockID);
	}

	@Override
	public void onDataPacket( INetworkManager net, Packet132TileEntityData paket) {
//		LogHelper.info( "onDataPacket");
		readFromNBT( paket.customParam1);
		markForUpdate();
	}

	public boolean onItemUse( AreaType area, ItemStack stk) {
		if (tryAdd( area, stk.getItemDamage())) {
//			LogHelper.info( "onItemUse: %s", LogHelper.toString( this));
			DarkLib.placeNoise( worldObj, xCoord, yCoord, zCoord, BlockType.STAGE.getId());
			--stk.stackSize;
			refresh();
			notifyAllNeighbor();
			return true;
		}
		return false;
	}

	public void onNeighborBlockChange( int blkID) {
		refresh();
	}

	public void onRemove( AreaType area) {
		int meta = tryRemove( area);
		if (meta >= 0) {
			dropItem( meta);
			refresh();
			notifyAllNeighbor();
		}
	}

	@Override
	public void readFromNBT( NBTTagCompound nbt) {
		super.readFromNBT( nbt);
		int areas = nbt.getInteger( NBT_AREAS);
		byte[] arr = nbt.getByteArray( NBT_SECTIONS);
		int n = 0;
		for (int a = areas, i = 0; a != 0; a >>>= 1, ++i) {
			if ((a & 1) != 0) {
				int matID = arr[n++] & 0xFF;
				int segID = arr[n++] & 0xFF;
				tryAdd( AreaType.toArea( i), segID, matID);
			}
		}
		mConnect.readFromNBT( nbt);
//		LogHelper.info( "readFromNBT: %s", LogHelper.toString( this));
	}

	private void refresh() {
		if (isEmpty()) {
			worldObj.setBlockToAir( xCoord, yCoord, zCoord);
		}
		else {
			if (mConnect.isEmpty()) {
				mConnect = IConnectable.NO_CONNECT;
			}
			else {
				mConnect.refresh( this);
			}
			mConnect.power( this);
//			PowerSearch.update( worldObj, xCoord, yCoord, zCoord);
//			LogHelper.info( "refresh: %s", LogHelper.toString( this));
			worldObj.updateTileEntityChunkAndDoNothing( xCoord, yCoord, zCoord, this);
			markForUpdate();
		}
	}

	private boolean set( AreaType area, ISection sec, IMaterial mat) {
		try {
			mSec[area.ordinal()] = sec;
			mMat[area.ordinal()] = mat;
			mArea |= area.mMask;
//			LogHelper.info( "tryAdd: %s, %s, %s", LogHelper.toString( worldObj), area.name(), toString());
			return true;
		}
		catch (IndexOutOfBoundsException ex) {
		}
		return false;
	}

	public void setSectionBounds( AreaType area, Block blk) {
		ISection sec = getSection( area);
		sec.setSectionBounds( area, blk, this);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer( "TileSection[");
		boolean found = false;
		for (int bit = 0x8000, i = 0; bit != 0; bit >>>= 1, ++i) {
			if (found) {
				sb.append( ',');
			}
			int b = mArea & bit;
			if (b != 0) {
				found = true;
				sb.append( getSection( i).getID() & 0xFF);
				sb.append( '|');
				sb.append( getMaterial( i).getID() & 0xFF);
			}
			else if (found) {
				sb.append( "-|-");
			}
		}
		sb.append( ",");
		sb.append( mConnect.toString());
		sb.append( "]");
		return sb.toString();
	}

	private boolean tryAdd( AreaType area, int meta) {
		ISection sec = SectionLib.getForDmg( meta);
		IMaterial mat = sec.getForDmg( meta);
		return tryAdd( area, sec, mat);
	}

	private boolean tryAdd( AreaType area, int secID, int matID) {
		ISection sec = SectionLib.get( secID);
		IMaterial mat = sec.getForDmg( matID);
		return tryAdd( area, sec, mat);
	}

	private boolean tryAdd( AreaType area, ISection sec, IMaterial mat) {
		if (isUsed( area)) {
			return false;
		}
		if (sec.isStructure()) {
			return set( area, sec, mat);
		}
		if (mConnect == IConnectable.NO_CONNECT) {
			mConnect = sec.createConnect( mat);
		}
		if (mConnect.isAllowed( sec, mat)) {
			mConnect.set( area);
			return set( area, sec, mat);
		}
		return false;
	}

	private int tryRemove( AreaType area) {
		try {
			if (isUsed( area)) {
				int meta = getMeta( area);
				mSec[area.ordinal()] = null;
				mMat[area.ordinal()] = null;
				mArea &= ~area.mMask;
				mConnect.delete( area);
				if (mConnect.isEmpty()) {
					mConnect = IConnectable.NO_CONNECT;
				}
//				LogHelper.info( "tryRemove: %s, %s, %s", LogHelper.toString( worldObj), area.name(), toString());
				return meta;
			}
		}
		catch (IndexOutOfBoundsException ex) {
		}
		return -1;
	}

	public void updateAll() {
		int id = BlockType.STAGE.getId();
		DarkLib.notifyNeighborChange( worldObj, xCoord, yCoord, zCoord, id);
		DarkLib.notifyEdgesChange( worldObj, xCoord, yCoord, zCoord, id);
		DarkLib.notifyNeighborChange2( worldObj, xCoord, yCoord, zCoord, id);
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
				arr[n++] = (byte) (getMaterial( i).getID() & 0xFF);
				arr[n++] = (byte) (getSection( i).getID() & 0xFF);
			}
		}
		nbt.setByteArray( NBT_SECTIONS, arr);
		mConnect.writeToNBT( nbt);
//		LogHelper.info( "writeToNBT: %s", nbt);
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
