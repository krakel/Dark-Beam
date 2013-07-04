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
import de.krakel.darkbeam.core.ISection;

class NoConnect implements IConnetable {
	NoConnect() {
	}

	@Override
	public void add( AreaType area) {
	}

	@Override
	public boolean isAllowed( ISection sec) {
		return true;
	}

	@Override
	public boolean isEmpty() {
		return true;
	}

	@Override
	public boolean isPowerd() {
		return false;
	}

	@Override
	public void readFromNBT( NBTTagCompound nbt) {
	}

	@Override
	public void remove( AreaType area) {
	}

	@Override
	public void writeToNBT( NBTTagCompound nbt) {
	}
}
