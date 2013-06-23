/**
 * Dark Beam
 * MaskCover1Renderer.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.client.renderer;

import de.krakel.darkbeam.tile.TileMasking;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;

public class MaskCoverRenderer extends ACoverRenderer {
	public MaskCoverRenderer( int base) {
		super( base);
	}

	@Override
	public boolean hasMaterials() {
		return true;
	}

	@Override
	public void renderItem( RenderBlocks rndrBlk, Block blk, int meta) {
		rndrBlk.setRenderBounds( 0D, 0D, 0.5D - mThickness, 1D, 1D, 0.5D + mThickness);
		renderStandardInventory( rndrBlk, blk, meta);
	}

	@Override
	public void renderSide( RenderBlocks rndrBlk, int area, Block blk, int meta, int x, int y, int z, TileMasking tile) {
		setMaskBounds( area, blk);
		renderStandard( rndrBlk, blk, DIR_SOUTH, meta, x, y, z);
	}
}
