/**
 * Dark Beam
 * TileMasking.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import de.krakel.darkbeam.core.IDirection;
import de.krakel.darkbeam.lib.BlockType;

public class TileMasking extends TileEntity {
	private static final String NBT_SIDES = "sides";
	private static final String NBT_METAS = "metas";
	private static final int IN_USE = 0xFFFF0000;
	public static final int MAX_SIDE = IDirection.DIR_MAX;
	private int[] mArr = new int[MAX_SIDE];

	public TileMasking() {
	}

	public int getMeta( int side) {
		try {
			return mArr[side] & ~IN_USE;
		}
		catch (IndexOutOfBoundsException ex) {
			return 0;
		}
	}

	public boolean isInUse( int side) {
		try {
			return mArr[side] < 0;
		}
		catch (IndexOutOfBoundsException ex) {
			return true;
		}
	}

	public void onChanged() {
		if (worldObj != null) {
			worldObj.notifyBlocksOfNeighborChange( xCoord, yCoord, zCoord, BlockType.Masking.getId());
			worldObj.markBlockForUpdate( xCoord, yCoord, zCoord);
			worldObj.updateTileEntityChunkAndDoNothing( xCoord, yCoord, zCoord, this);
		}
	}

	@Override
	public void readFromNBT( NBTTagCompound nbt) {
		super.readFromNBT( nbt);
		int sides = nbt.getInteger( NBT_SIDES);
		byte[] arr = nbt.getByteArray( NBT_METAS);
		int side = 0;
		int count = 0;
		while (sides != 0) {
			if ((sides & 1) != 0) {
				int mat = arr[count++] & 0xFF;
				int msk = arr[count++] & 0xFF;
				mArr[side] = mat | msk << 8 | IN_USE;
			}
			++side;
			sides >>= 1;
		}
	}

	public void reset() {
		for (int i = 0; i < MAX_SIDE; ++i) {
			mArr[i] = 0;
		}
	}

	public boolean tryAdd( int side, int meta) {
		try {
			if (mArr[side] >= 0) {
				mArr[side] = meta | IN_USE;
				onChanged();
				return true;
			}
		}
		catch (IndexOutOfBoundsException ex) {
		}
		return false;
	}

	public int tryRemove( int side) {
		try {
			int value = mArr[side];
			if (value < 0) {
				mArr[side] = 0;
				onChanged();
				return value & ~IN_USE;
			}
		}
		catch (IndexOutOfBoundsException ex) {
		}
		return -1;
	}

	@Override
	public void writeToNBT( NBTTagCompound nbt) {
		super.writeToNBT( nbt);
		int sides = 0;
		int count = 0;
		for (int i = MAX_SIDE - 1; i >= 0; --i) {
			sides <<= 1;
			if (mArr[i] < 0) {
				sides |= 1;
				count += 2;
			}
		}
		nbt.setInteger( NBT_SIDES, sides);
		byte[] arr = new byte[count];
		for (int i = MAX_SIDE - 1; i >= 0; --i) {
			int value = mArr[i];
			if (value < 0) {
				arr[--count] = (byte) (value >> 8 & 0xFF);
				arr[--count] = (byte) (value & 0xFF);
			}
		}
		nbt.setByteArray( NBT_METAS, arr);
	}
}
