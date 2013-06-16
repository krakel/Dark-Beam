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
	void render( Block blk, int meta, RenderBlocks rndr);

	void setBounds( RenderBlocks rndr, int side);

	void setInventoryBounds( RenderBlocks rndr);
}
