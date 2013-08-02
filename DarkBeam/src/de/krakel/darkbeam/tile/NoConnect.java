/**
 * Dark Beam
 * NoConnect.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

import de.krakel.darkbeam.core.AreaType;
import de.krakel.darkbeam.core.IMaterial;
import de.krakel.darkbeam.core.ISection;
import de.krakel.darkbeam.core.SectionLib;

class NoConnect implements IConnectable {
	NoConnect() {
	}

	@Override
	public int colorMultiplier() {
		return 0;
	}

	@Override
	public void delete( AreaType area) {
	}

	@Override
	public int getPower(IMaterial insu) {
		return 0;
	}

	@Override
	public int getProvidingStrongPower( AreaType side) {
		return 0;
	}

	@Override
	public int getProvidingWeakPower( AreaType side) {
		return 0;
	}

	@Override
	public ISection getWire() {
		return SectionLib.UNKNOWN;
	}

	@Override
	public int indirectPower( World world, ChunkPosition pos) {
		return 0;
	}

	@Override
	public boolean isAllowed( ISection sec, IMaterial mat) {
		return false;
	}

	@Override
	public boolean isConnected( AreaType side) {
		return false;
	}

	@Override
	public boolean isEmpty() {
		return true;
	}

	@Override
	public boolean isIllegal() {
		return false;
	}

	@Override
	public boolean isValid( int value) {
		return false;
	}

	@Override
	public boolean isValidEdge( AreaType edge) {
		return false;
	}

	@Override
	public boolean isValidEdgeCon( AreaType edge) {
		return false;
	}

	@Override
	public boolean isValidSideCon( AreaType side) {
		return false;
	}

	@Override
	public boolean isWired( AreaType area) {
		return false;
	}

	@Override
	public void power( TileStage tile) {
	}

	@Override
	public void readFromNBT( NBTTagCompound nbt) {
	}

	@Override
	public void refresh( TileStage tile) {
	}

	@Override
	public void set( AreaType area) {
	}

	@Override
	public String toString() {
		return "NoConnect=[]";
	}

	@Override
	public void updatePower( World world, ChunkPosition pos) {
	}

	@Override
	public void writeToNBT( NBTTagCompound nbt) {
	}
}
