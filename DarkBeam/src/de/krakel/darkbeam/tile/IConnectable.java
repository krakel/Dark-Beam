/**
 * Dark Beam
 * IConnectable.java
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

public interface IConnectable {
	public static final IConnectable NO_CONNECT = new NoConnect();

	void delete( AreaType area);

	int getPower();

	int getProvidingStrongPower( AreaType side);

	int getProvidingWeakPower( AreaType side);

	ISection getWire();

	int indirectPower( World world, ChunkPosition pos);

	boolean isAllowed( ISection sec, IMaterial mat);

	boolean isConnected( AreaType side);

	boolean isEmpty();

	boolean isIllegal();

	boolean isPowerd();

	boolean isValid( int value);

	boolean isValidEdge( AreaType edge);

	boolean isValidEdgeCon( AreaType edge);

	boolean isValidSideCon( AreaType side);

	boolean isWired( AreaType area);

	void power( TileStage tile);

	void readFromNBT( NBTTagCompound nbt);

	void refresh( TileStage tile);

	void set( AreaType area);

	void updatePower( World world, ChunkPosition pos);

	void writeToNBT( NBTTagCompound nbt);
}
