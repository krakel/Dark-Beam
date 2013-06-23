/**
 * Dark Beam
 * MaskHollowRenderer.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;

public class MaskHollowRenderer extends ACoverRenderer {
	public MaskHollowRenderer( int base) {
		super( base);
	}

	@Override
	public void renderItem( RenderBlocks rndrBlk, Block blk, int meta) {
		double min = 0.25D;
		double max = 1D - min;
		double x1 = 0.5D - mThickness;
		double x2 = 0.5D + mThickness;
		rndrBlk.setRenderBounds( x1, 0D, 0D, x2, min, 1D);
		renderStandardInventory( rndrBlk, blk, meta);
		rndrBlk.setRenderBounds( x1, min, 0D, x2, max, min);
		renderStandardInventory( rndrBlk, blk, meta);
		rndrBlk.setRenderBounds( x1, min, max, x2, max, 1D);
		renderStandardInventory( rndrBlk, blk, meta);
		rndrBlk.setRenderBounds( x1, max, 0D, x2, 1D, 1D);
		renderStandardInventory( rndrBlk, blk, meta);
	}

	@Override
	public void renderSide( RenderBlocks rndrBlk, int area, Block blk, int meta, int x, int y, int z) {
		float min = 0.25F;
		float max = 1F - min;
		switch (area) {
			case SIDE_DOWN:
				blk.setBlockBounds( 0F, 0F, 0F, 1F, mSize, min);
				renderStandard( rndrBlk, blk, DIR_SOUTH, meta, x, y, z);
				blk.setBlockBounds( 0F, 0F, max, 1F, mSize, 1F);
				renderStandard( rndrBlk, blk, DIR_SOUTH, meta, x, y, z);
				blk.setBlockBounds( 0F, 0F, min, min, mSize, max);
				renderStandard( rndrBlk, blk, DIR_SOUTH, meta, x, y, z);
				blk.setBlockBounds( max, 0F, min, 1F, mSize, max);
				renderStandard( rndrBlk, blk, DIR_SOUTH, meta, x, y, z);
				break;
			case SIDE_UP:
				blk.setBlockBounds( 0F, 1F - mSize, 0F, 1F, 1F, min);
				renderStandard( rndrBlk, blk, DIR_SOUTH, meta, x, y, z);
				blk.setBlockBounds( 0F, 1F - mSize, max, 1F, 1F, 1F);
				renderStandard( rndrBlk, blk, DIR_SOUTH, meta, x, y, z);
				blk.setBlockBounds( 0F, 1F - mSize, min, min, 1F, max);
				renderStandard( rndrBlk, blk, DIR_SOUTH, meta, x, y, z);
				blk.setBlockBounds( max, 1F - mSize, min, 1F, 1F, max);
				renderStandard( rndrBlk, blk, DIR_SOUTH, meta, x, y, z);
				break;
			case SIDE_NORTH:
				blk.setBlockBounds( 0F, 0F, 0F, 1F, min, mSize);
				renderStandard( rndrBlk, blk, DIR_SOUTH, meta, x, y, z);
				blk.setBlockBounds( 0F, max, 0F, 1F, 1F, mSize);
				renderStandard( rndrBlk, blk, DIR_SOUTH, meta, x, y, z);
				blk.setBlockBounds( 0F, min, 0F, min, max, mSize);
				renderStandard( rndrBlk, blk, DIR_SOUTH, meta, x, y, z);
				blk.setBlockBounds( max, min, 0F, 1F, max, mSize);
				renderStandard( rndrBlk, blk, DIR_SOUTH, meta, x, y, z);
				break;
			case SIDE_SOUTH:
				blk.setBlockBounds( 0F, 0F, 1F - mSize, 1F, min, 1F);
				renderStandard( rndrBlk, blk, DIR_SOUTH, meta, x, y, z);
				blk.setBlockBounds( 0F, max, 1F - mSize, 1F, 1F, 1F);
				renderStandard( rndrBlk, blk, DIR_SOUTH, meta, x, y, z);
				blk.setBlockBounds( 0F, min, 1F - mSize, min, max, 1F);
				renderStandard( rndrBlk, blk, DIR_SOUTH, meta, x, y, z);
				blk.setBlockBounds( max, min, 1F - mSize, 1F, max, 1F);
				renderStandard( rndrBlk, blk, DIR_SOUTH, meta, x, y, z);
				break;
			case SIDE_WEST:
				blk.setBlockBounds( 0F, 0F, 0F, mSize, 1F, min);
				renderStandard( rndrBlk, blk, DIR_SOUTH, meta, x, y, z);
				blk.setBlockBounds( 0F, 0F, max, mSize, 1F, 1F);
				renderStandard( rndrBlk, blk, DIR_SOUTH, meta, x, y, z);
				blk.setBlockBounds( 0F, 0F, min, mSize, min, max);
				renderStandard( rndrBlk, blk, DIR_SOUTH, meta, x, y, z);
				blk.setBlockBounds( 0F, max, min, mSize, 1F, max);
				renderStandard( rndrBlk, blk, DIR_SOUTH, meta, x, y, z);
				break;
			case SIDE_EAST:
				blk.setBlockBounds( 1F - mSize, 0F, 0F, 1F, 1F, min);
				renderStandard( rndrBlk, blk, DIR_SOUTH, meta, x, y, z);
				blk.setBlockBounds( 1F - mSize, 0F, max, 1F, 1F, 1F);
				renderStandard( rndrBlk, blk, DIR_SOUTH, meta, x, y, z);
				blk.setBlockBounds( 1F - mSize, 0F, min, 1F, min, max);
				renderStandard( rndrBlk, blk, DIR_SOUTH, meta, x, y, z);
				blk.setBlockBounds( 1F - mSize, max, min, 1F, 1F, max);
				renderStandard( rndrBlk, blk, DIR_SOUTH, meta, x, y, z);
				break;
			default:
				blk.setBlockBounds( 0F, 0F, 0F, 1F, 1F, 1F);
				renderStandard( rndrBlk, blk, DIR_SOUTH, meta, x, y, z);
				break;
		}
	}
}
