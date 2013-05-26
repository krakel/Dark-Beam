/**
 * Dark Beam
 * TileRedWire.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

public class TileRedWire extends TileEntity {
	private static final String NBT_CONNECTIONS = "cons";
	private static final String NBT_SURFACES = "faces";
	private boolean mPowered;
	private byte mConnections;
	private byte mSurfaces;

	public TileRedWire() {
	}

	public boolean isConnected( ForgeDirection dir) {
		return isConnected( dir.ordinal());
	}

	public boolean isConnected( int side) {
		int mask = 1 << side;
		return (mConnections & mask) != 0;
	}

	public boolean isNormal( ForgeDirection dir) {
		return true;
	}

	public boolean isPowered() {
		return mPowered;
	}

	@Override
	public void readFromNBT( NBTTagCompound nbt) {
		super.readFromNBT( nbt);
		mConnections = nbt.getByte( NBT_CONNECTIONS);
	}

	public void resConnection( int side) {
		int mask = 1 << side;
		mConnections &= ~mask;
	}

	public void setConnection( int side) {
		int mask = 1 << side;
		mConnections |= mask;
	}

	public void updateOnNeighbor() {
	}

	public void updateOnPlace() {
	}

	@Override
	public void writeToNBT( NBTTagCompound nbt) {
		super.writeToNBT( nbt);
		nbt.setByte( NBT_CONNECTIONS, mConnections);
	}
}
