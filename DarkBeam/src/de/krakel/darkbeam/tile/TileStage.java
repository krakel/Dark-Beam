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
import de.krakel.darkbeam.core.ISection;
import de.krakel.darkbeam.core.SectionLib;
import de.krakel.darkbeam.lib.BlockType;

public class TileStage extends TileEntity implements Iterable<AreaType> {
	private static final String NBT_AREAS = "as";
	private static final String NBT_SECTIONS = "ss";
	private int mArea;
	private int[] mArr = new int[32];
	private IConnetable mConnet = IConnetable.NO_CONNECT;
	private boolean mNeedUpdate = true;

	public TileStage() {
	}

	public void dropItem( int meta) {
		ISection sec = SectionLib.getForDmg( meta);
		ItemStack stk = new ItemStack( BlockType.STAGE.getBlock(), getDropCount( sec), meta);
		DarkLib.dropItem( worldObj, xCoord, yCoord, zCoord, stk);
	}

	public IConnetable getConnet() {
		return mConnet;
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT( nbt);
		return new Packet132TileEntityData( xCoord, yCoord, zCoord, 0, nbt);
	}

	private int getDropCount( ISection sec) {
		if (mConnet.isAllowed( sec) && mConnet.isInvalid()) {
			mConnet = IConnetable.NO_CONNECT;
			return 3;
		}
		return 1;
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

	@Override
	public void onDataPacket( INetworkManager net, Packet132TileEntityData paket) {
		readFromNBT( paket.customParam1);
	}

	@Override
	public void readFromNBT( NBTTagCompound nbt) {
//		LogHelper.info( "readFromNBT: %s", nbt);
		super.readFromNBT( nbt);
		int areas = nbt.getInteger( NBT_AREAS);
		byte[] arr = nbt.getByteArray( NBT_SECTIONS);
		int n = 0;
		for (int a = areas, i = 0; a != 0; a >>>= 1, ++i) {
			if ((a & 1) != 0) {
				int mat = arr[n++] & 0xFF;
				int sec = arr[n++] & 0xFF;
				tryAdd( AreaType.toArea( i), sec << 8 | mat);
			}
		}
		mConnet.readFromNBT( nbt);
	}

	public void refresh() {
		mConnet.refresh( this);
		if (mConnet.isEmpty()) {
			mConnet = IConnetable.NO_CONNECT;
		}
		if (isEmpty()) {
			invalidate();
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
//		sb.append( mConnet.toString());
		sb.append( "]");
		return sb.toString();
	}

	public boolean tryAdd( AreaType area, int meta) {
		try {
			if (!isUsed( area)) {
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
//				LogHelper.info( "tryAdd: %b, %s, %s", worldObj != null && worldObj.isRemote, area.name(), toString());
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
//				LogHelper.info( "tryRemove: %b, %s, %s", worldObj != null && worldObj.isRemote, area.name(), toString());
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
