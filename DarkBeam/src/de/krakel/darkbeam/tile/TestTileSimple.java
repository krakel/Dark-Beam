/**
 * Dark Beam
 * TestTileSimple.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TestTileSimple extends TileEntity {
	private static final String NBT_OWNER = "owner";
	protected String mOwner = "";

	public TestTileSimple() {
	}

	boolean isOwner( EntityPlayer player) {
		return mOwner.equals( player.getEntityName());
	}

	@Override
	public void readFromNBT( NBTTagCompound nbt) {
		super.readFromNBT( nbt);
		mOwner = nbt.getString( NBT_OWNER);
	}

	void setOwner( EntityPlayer player) {
		mOwner = player.getEntityName();
	}

	@Override
	public void updateEntity() {
	}

	@Override
	public void writeToNBT( NBTTagCompound nbt) {
		super.writeToNBT( nbt);
		nbt.setString( NBT_OWNER, mOwner);
	}
}
