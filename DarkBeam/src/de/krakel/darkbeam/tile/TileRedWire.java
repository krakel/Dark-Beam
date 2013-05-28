/**
 * Dark Beam
 * TileRedWire.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.tile;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class TileRedWire extends TileEntity {
	private static final String NBT_CONNECTIONS = "cons";
	private static final String NBT_SURFACES = "faces";
	private boolean mPowered;
	public int mConnections;
	public int mSurfaces;
	public short[] mCovers = new short[29];

	public TileRedWire() {
	}

	public static ItemStack convertCoverPlate( int subHit, int coverID) {
//		if (blockCoverPlate != null) {
//			return new ItemStack( blockCoverPlate, 1, coverValueToDamage( subHit, coverID));
//		}
		return null;
	}

	public static void dropItem( World world, int x, int y, int z, ItemStack stack) {
		if (!world.isRemote) {
			double zoom = 0.7D;
			double off = (1.0D - zoom) * 0.5D;
			double xOff = world.rand.nextFloat() * zoom + off;
			double yOff = world.rand.nextFloat() * zoom + off;
			double zOff = world.rand.nextFloat() * zoom + off;
			EntityItem item = new EntityItem( world, xOff + x, yOff + y, zOff + z, stack);
			item.delayBeforeCanPickup = 10;
			world.spawnEntityInWorld( item);
		}
	}

	public static boolean isSet( int b, ForgeDirection dir) {
		return (b & dir.flag) != 0;
	}

	public static int res( int b, ForgeDirection dir) {
		return b & ~dir.flag;
	}

	public static int set( int b, ForgeDirection dir) {
		return b | dir.flag;
	}

	public void dropCover( int subHit, int coverID) {
		ItemStack stack = convertCoverPlate( subHit, coverID);
		if (stack != null) {
			dropItem( worldObj, xCoord, yCoord, zCoord, stack);
		}
	}

	public boolean isNormal( ForgeDirection dir) {
		return true;
	}

	public boolean isPowered() {
		return mPowered;
	}

	public void onHarvestPart( EntityPlayer player, int subHit) {
		int coverID = tryRemoveCover( subHit);
		if (coverID >= 0) {
			dropCover( subHit, coverID);
			if (mSurfaces == 0) {
				worldObj.setBlockToAir( xCoord, yCoord, zCoord);
			}
		}
	}

	@Override
	public void readFromNBT( NBTTagCompound nbt) {
		super.readFromNBT( nbt);
		mConnections = nbt.getByte( NBT_CONNECTIONS) & 0xFF;
		mSurfaces = nbt.getByte( NBT_SURFACES) & 0xFF;
	}

	public int tryRemoveCover( int subHit) {
		int mask = 1 << subHit;
		if ((mSurfaces & mask) == 0) {
			return -1;
		}
		mSurfaces &= ~mask;
		short coverID = mCovers[subHit];
		mCovers[subHit] = 0;
		updateBlockChange();
		return coverID;
	}

	public void updateBlockChange() {
//		RedPowerLib.updateIndirectNeighbors( worldObj, xCoord, yCoord, zCoord, this.getBlockID());
		worldObj.markBlockForUpdate( xCoord, yCoord, zCoord);
//		CoreLib.markBlockDirty( worldObj, xCoord, yCoord, zCoord);
	}

	public void updateOnNeighbor() {
	}

	public void updateOnPlace() {
	}

	@Override
	public void writeToNBT( NBTTagCompound nbt) {
		super.writeToNBT( nbt);
		nbt.setByte( NBT_CONNECTIONS, (byte) mConnections);
		nbt.setByte( NBT_SURFACES, (byte) mSurfaces);
	}
}
