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

public interface IItemRenderer {
	void renderItem( RenderBlocks rndr, Block blk, int meta);

	void renderSide( RenderBlocks rndr, int side, Block blk, int meta, int x, int y, int z);

	void setBounds( RenderBlocks rndr, int side);

	void setInventoryBounds( RenderBlocks rndr);
}
