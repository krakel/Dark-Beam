/**
 * Dark Beam
 * ItemSlabRenderer.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;

public class ItemSlabRenderer extends AItemMaskRenderer {
	private static final double THICKNESS = 1D / 4D;

	public ItemSlabRenderer() {
	}

	@Override
	public void render( Block blk, int meta, RenderBlocks rndr) {
		rndr.setRenderBounds( 0D, 0D, 0.5D - THICKNESS, 1D, 1D, 0.5D + THICKNESS);
		renderMask( blk, meta, rndr);
	}
}
