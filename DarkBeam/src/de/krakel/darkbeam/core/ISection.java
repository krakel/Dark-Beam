/**
 * Dark Beam
 * ISection.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;

import de.krakel.darkbeam.tile.IConnectable;
import de.krakel.darkbeam.tile.TileStage;

public interface ISection {
	IConnectable createConnect( IMaterial mat);

	IMaterial getForDmg( int dmg);

	Icon getIcon( int side, int dmg);

	int getID();

	String getName();

	String getSectionName( int dmg);

	boolean isJoinable();

	boolean isStructure();

	boolean isValid( TileStage tile, AreaType area);

	boolean isWire();

	void oppositeArea( MovingObjectPosition pos);

	void renderItem( RenderBlocks rndrBlk, Block blk, int dmg);

	void renderSide( RenderBlocks rndrBlk, AreaType area, Block blk, int x, int y, int z, TileStage tile);

	void setSectionBounds( AreaType area, Block blk, TileStage tile);

	int toDmg();

	void updateArea( MovingObjectPosition pos);
}
