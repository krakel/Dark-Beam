/**
 * Dark Beam
 * ACoverRenderer.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;

import de.krakel.darkbeam.core.AreaType;

abstract class ACoverRenderer extends AMaskRenderer {
	protected float mThickness;

	protected ACoverRenderer( float thickness) {
		mThickness = thickness;
	}

	@Override
	public void renderItem( RenderBlocks rndrBlk, Block blk, int meta) {
		setInventoryBounds( rndrBlk, mThickness);
		renderInventoryItem( rndrBlk, blk, meta);
	}

	@Override
	public void renderSide( RenderBlocks rndrBlk, int side, Block blk, int meta, int x, int y, int z) {
		setMaskBounds( blk, side);
		renderStandard( rndrBlk, blk, side, meta, x, y, z);
	}

	@Override
	public void setMaskBounds( Block blk, int side) {
		setBounds( blk, side, mThickness + mThickness);
	}

	@Override
	public boolean validate( int[] arr, AreaType area) {
		return true;
	}
}
