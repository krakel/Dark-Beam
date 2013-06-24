/**
 * Dark Beam
 * IItemRenderer.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;

import de.krakel.darkbeam.core.ISection;
import de.krakel.darkbeam.tile.TileStage;

public interface IMaskRenderer {
	int getArea( int side, double dx, double dy, double dz);

	int getBlockID( int dmg);

	String getNameForSection( ISection sec, int dmg);

	int getOpposite( int side, int area);

	boolean hasMaterials();

	boolean isValid( int area, TileStage tile);

	void renderItem( RenderBlocks rndrBlk, Block blk, int meta);

	void renderSide( RenderBlocks rndrBlk, int area, Block blk, int meta, int x, int y, int z, TileStage tile);

	void setSectionBounds( int area, Block blk);
}
