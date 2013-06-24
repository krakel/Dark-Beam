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
import net.minecraft.util.Icon;

import de.krakel.darkbeam.core.Section;
import de.krakel.darkbeam.tile.TileSection;

public interface IMaskRenderer {
	static final double BOX_BORDER_MIN = 1D / 4D;
	static final double BOX_BORDER_MAX = 1D - BOX_BORDER_MIN;

	int getArea( int side, double dx, double dy, double dz);

	int getBlockID( int dmg);

	Icon getIcon( int side, int dmg);

	String getNameForSection( Section sec, int dmg);

	int getOpposite( int side, int area);

	boolean hasMaterials();

	boolean isValid( int area, TileSection tile);

	void renderItem( RenderBlocks rndrBlk, Block blk, int meta);

	void renderSide( RenderBlocks rndrBlk, int area, Block blk, int meta, int x, int y, int z, TileSection tile);

	void setSectionBounds( int area, Block blk);
}
