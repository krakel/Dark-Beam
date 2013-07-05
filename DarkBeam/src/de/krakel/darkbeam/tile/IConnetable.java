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

	void delete( AreaType area);

	int getCount();

	int getLevel();

	boolean isAllowed( ISection sec);

	boolean isEmpty();

	boolean isInvalid();

	boolean isPowerd();

	boolean isWired( AreaType area);

	void readFromNBT( NBTTagCompound nbt);

	void set( AreaType area);

	void writeToNBT( NBTTagCompound nbt);
}
