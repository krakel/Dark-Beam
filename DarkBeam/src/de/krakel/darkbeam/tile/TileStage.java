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

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

import de.krakel.darkbeam.client.renderer.ASectionRenderer;
import de.krakel.darkbeam.core.DarkLib;
import de.krakel.darkbeam.core.IArea;
import de.krakel.darkbeam.core.ISection;
import de.krakel.darkbeam.core.Position;
import de.krakel.darkbeam.core.SectionLib;
import de.krakel.darkbeam.core.helper.LogHelper;
import de.krakel.darkbeam.lib.BlockType;

public class TileStage extends TileEntity implements Iterable<Integer> {
	private static final String NBT_AREAS = "areas";
	private static final String NBT_SECTIONS = "secs";
	private int mArea;
	private int mInner;
	private int[] mArr = new int[32];

	public TileStage() {
	}

	public int getArea() {
		return mArea;
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

	public boolean isConnect( int area, int meta, int side, int x, int y, int z) {
		if (worldObj == null) {
			LogHelper.info( "missing world");
			return false;
		}
		x += Position.relX( side);
		y += Position.relY( side);
		z += Position.relZ( side);
		TileStage tile1 = DarkLib.getTileEntity( worldObj, x, y, z, TileStage.class);
		if (tile1 != null && tile1.isMeta( area, meta)) {
			return true;
		}
		return false;
	}

	public boolean isEmpty() {
		return mArea == 0;
	}

	public boolean isInner( int side) {
		int off = 1 << side;
		return (mInner & off) != 0;
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

	public boolean isSided( int area, int meta, int side, int x, int y, int z) {
		if (worldObj == null) {
			LogHelper.info( "missing world");
			return false;
		}
		int anti = Position.toAnti( side);
		if (area == anti) {
			return false;
		}
		x += Position.relX( side);
		y += Position.relY( side);
		z += Position.relZ( side);
		int id = worldObj.getBlockId( x, y, z);
		if (id != 0 && id != BlockType.STAGE.getId()) {
			return false;
		}
		x += Position.relX( area);
		y += Position.relY( area);
		z += Position.relZ( area);
		TileStage tile = DarkLib.getTileEntity( worldObj, x, y, z, TileStage.class);
		if (tile == null) {
			return false;
		}
		return tile.isMeta( anti, meta);
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
		mInner = 0;
		for (int area = 0; area < IArea.MAX_DIR; ++area) {
			int off = 1 << area;
			if (!isValid( off)) {
				ISection sec = getSection( area);
				if (sec.isWire()) {
					mInner |= off;
				}
			}
		}
	}

	public void reset() {
		mArea = 0;
		mInner = 0;
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
			if (!isValid( off)) {
				int value = mArr[area];
				mArr[area] = 0;
				mArea &= ~off;
				LogHelper.info( "tryRemove: %b, %s, %s", worldObj != null && worldObj.isRemote, Position.toString( area), toString());
				return value;
			}
		}
		catch (IndexOutOfBoundsException ex) {
		}
		return -1;
	}

	@Override
	public void updateEntity() {
//		if (worldObj != null) {
//			worldObj.markBlockForUpdate( xCoord, yCoord, zCoord);
//		}
	}

	public boolean validate( int area, ASectionRenderer rndr) {
		return true;
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
