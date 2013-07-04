/**
 * Dark Beam
 * IConnetable.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.tile;

import net.minecraft.nbt.NBTTagCompound;

import de.krakel.darkbeam.core.AreaType;
import de.krakel.darkbeam.core.ISection;

public interface IConnetable {
	public static final IConnetable NO_CONNECT = new NoConnect();

	void add( AreaType area);

	boolean isAllowed( ISection sec);

	boolean isEmpty();

	boolean isPowerd();

	void readFromNBT( NBTTagCompound nbt);

	void remove( AreaType area);

	void writeToNBT( NBTTagCompound nbt);
}
