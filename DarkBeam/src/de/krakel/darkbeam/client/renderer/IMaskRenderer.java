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

import de.krakel.darkbeam.tile.TileMasking;

public interface IMaskRenderer {
	static final double BOX_BORDER_MIN = 1D / 4D;
	static final double BOX_BORDER_MAX = 1D - BOX_BORDER_MIN;

	int getSubHit( int side, double dx, double dy, double dz);

	boolean isValid( TileMasking tile, int area);

	void renderItem( RenderBlocks rndrBlk, Block blk, int meta);

	void renderSide( RenderBlocks rndrBlk, int area, Block blk, int meta, int x, int y, int z);

	void setMaskBounds( Block blk, int area);
}
