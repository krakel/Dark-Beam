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

public interface IMaskRenderer {
	void renderItem( RenderBlocks rndrBlk, Block blk, int meta);

	void renderSide( RenderBlocks rndrBlk, int side, Block blk, int meta, int x, int y, int z);

	void setBounds( RenderBlocks rndrBlk, int side);

	void setInventoryBounds( RenderBlocks rndrBlk);

	void setMaskBounds( Block blk, int side);
}
