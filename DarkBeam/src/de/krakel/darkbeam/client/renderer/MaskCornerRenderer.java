/**
 * Dark Beam
 * MaskCornerRenderer.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;

import de.krakel.darkbeam.core.IArea;
import de.krakel.darkbeam.tile.TileMasking;

public class MaskCornerRenderer extends AMaskRenderer implements IArea {
	private float mThickness;
	private float mSize;

	public MaskCornerRenderer( float base) {
		mThickness = base / 16F;
		mSize = mThickness + mThickness;
	}

	@Override
	public boolean isValid( TileMasking tile, int area) {
		return false;
	}

	@Override
	public void renderItem( RenderBlocks rndrBlk, Block blk, int meta) {
		rndrBlk.setRenderBounds( 0D, 0D, 0D, 0.5D + mSize, 0.5D + mSize, 0.5D + mSize);
		renderInventoryItem( rndrBlk, blk, meta);
	}

	@Override
	public void renderSide( RenderBlocks rndrBlk, int side, Block blk, int meta, int x, int y, int z) {
		setMaskBounds( blk, side);
		renderStandard( rndrBlk, blk, side, meta, x, y, z);
	}

	@Override
	public void setMaskBounds( Block blk, int side) {
		setBounds( blk, side, mSize);
	}
}
