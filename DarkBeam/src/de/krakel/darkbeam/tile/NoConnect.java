/**
 * Dark Beam
 * NoConnect.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.tile;

import net.minecraft.nbt.NBTTagCompound;

import de.krakel.darkbeam.core.AreaType;
import de.krakel.darkbeam.core.IMaterial;
import de.krakel.darkbeam.core.ISection;

class NoConnect implements IConnectable {
	NoConnect() {
	}

	@Override
	public void delete( AreaType area) {
	}

	@Override
	public int getLevel() {
		return 0;
	}

	@Override
	public int getProvidingPower( AreaType side) {
		return 0;
	}

	@Override
	public boolean isAllowed( ISection sec, IMaterial mat) {
		return false;
	}

	@Override
	public boolean isAngled( AreaType edge) {
		return false;
	}

	@Override
	public boolean isConnected( AreaType edge) {
		return false;
	}

	@Override
	public boolean isConnection( AreaType area) {
		return false;
	}

	@Override
	public boolean isEmpty() {
		return true;
	}

	@Override
	public boolean isInvalid() {
		return false;
	}

	@Override
	public boolean isPowerd() {
		return false;
	}

	@Override
	public boolean isValid( int value) {
		return false;
	}

	@Override
	public boolean isWired( AreaType area) {
		return false;
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
	public void writeToNBT( NBTTagCompound nbt) {
	}
}
