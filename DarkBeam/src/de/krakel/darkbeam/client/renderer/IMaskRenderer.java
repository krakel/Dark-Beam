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

import de.krakel.darkbeam.core.AreaType;

public interface IMaskRenderer {
	void renderItem( RenderBlocks rndrBlk, Block blk, int meta);

	void renderSide( RenderBlocks rndrBlk, int side, Block blk, int meta, int x, int y, int z);

	void setMaskBounds( Block blk, int side);

	boolean validate( int[] arr, AreaType area);
}
