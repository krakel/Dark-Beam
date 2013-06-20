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
	boolean isValid( TileMasking tile, int area);

	void renderItem( RenderBlocks rndrBlk, Block blk, int meta);

	void renderSide( RenderBlocks rndrBlk, int area, Block blk, int meta, int x, int y, int z);

	void setMaskBounds( Block blk, int area);
}
