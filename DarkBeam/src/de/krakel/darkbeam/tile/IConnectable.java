/**
 * Dark Beam
 * IConnectable.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.tile;

import net.minecraft.nbt.NBTTagCompound;

import de.krakel.darkbeam.core.AreaType;
import de.krakel.darkbeam.core.IMaterial;
import de.krakel.darkbeam.core.ISection;

public interface IConnectable {
	public static final IConnectable NO_CONNECT = new NoConnect();

	void delete( AreaType area);

	int getLevel();

	int getProvidingPower( AreaType side);

	boolean isAllowed( ISection sec, IMaterial mat);

	boolean isAngled( AreaType edge);

	boolean isConnected( AreaType edge);

	boolean isConnection( AreaType area);

	boolean isEmpty();

	boolean isInvalid();

	boolean isPowerd();

	boolean isValid( int value);

	boolean isWired( AreaType area);

	void readFromNBT( NBTTagCompound nbt);

	void refresh( TileStage tile);

	void set( AreaType area);

	void writeToNBT( NBTTagCompound nbt);
}
