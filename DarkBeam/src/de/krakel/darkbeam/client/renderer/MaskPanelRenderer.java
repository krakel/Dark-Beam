/**
 * Dark Beam
 * ItemPanelRenderer.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;

public class MaskPanelRenderer extends AMaskRenderer {
	private static final float THICKNESS = 1F / 8F;

	public MaskPanelRenderer() {
	}

	@Override
	public void renderItem( RenderBlocks rndrBlk, Block blk, int meta) {
		setInventoryBounds( rndrBlk, THICKNESS);
		renderInventoryItem( rndrBlk, blk, meta);
	}

	@Override
	public void renderSide( RenderBlocks rndrBlk, int side, Block blk, int meta, int x, int y, int z) {
		setMaskBounds( blk, side);
		renderStandard( rndrBlk, blk, side, meta, x, y, z);
	}

	@Override
	public void setMaskBounds( Block blk, int side) {
		setBounds( blk, side, THICKNESS + THICKNESS);
	}
}
