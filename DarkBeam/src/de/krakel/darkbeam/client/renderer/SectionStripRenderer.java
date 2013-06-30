/**
 * Dark Beam
 * SectionStripRenderer.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;

import de.krakel.darkbeam.core.IArea;
import de.krakel.darkbeam.core.helper.LogHelper;
import de.krakel.darkbeam.tile.TileStage;

public class SectionStripRenderer extends ASectionRenderer implements IArea {
	public SectionStripRenderer( int base) {
		super( base);
	}

	@Override
	public void renderItem( RenderBlocks rndrBlk, Block blk, int meta) {
		rndrBlk.setRenderBounds( 0.5D - mThickness, 0D, 0.5D - mThickness, 0.5D + mThickness, 1D, 0.5D + mThickness);
		renderStandardInventory( rndrBlk, blk, meta);
	}

	@Override
	public void renderSide( RenderBlocks rndrBlk, int area, Block blk, int meta, int x, int y, int z, TileStage tile) {
		setSectionBounds( area, blk, tile);
		renderStandard( rndrBlk, blk, DIR_SOUTH, meta, x, y, z);
	}

	@Override
	public void setSectionBounds( int area, Block blk, TileStage tile) {
		switch (area) {
			case EDGE_DOWN_NORTH:
				blk.setBlockBounds( 0F, 0F, 0F, 1F, mSize, mSize);
				break;
			case EDGE_DOWN_SOUTH:
				blk.setBlockBounds( 0F, 0F, 1F - mSize, 1F, mSize, 1F);
				break;
			case EDGE_DOWN_WEST:
				blk.setBlockBounds( 0F, 0F, 0F, mSize, mSize, 1F);
				break;
			case EDGE_DOWN_EAST:
				blk.setBlockBounds( 1F - mSize, 0F, 0F, 1F, mSize, 1F);
				break;
			case EDGE_UP_NORTH:
				blk.setBlockBounds( 0F, 1F - mSize, 0F, 1F, 1F, mSize);
				break;
			case EDGE_UP_SOUTH:
				blk.setBlockBounds( 0F, 1F - mSize, 1F - mSize, 1F, 1F, 1F);
				break;
			case EDGE_UP_WEST:
				blk.setBlockBounds( 0F, 1F - mSize, 0F, mSize, 1F, 1F);
				break;
			case EDGE_UP_EAST:
				blk.setBlockBounds( 1F - mSize, 1F - mSize, 0F, 1F, 1F, 1F);
				break;
			case EDGE_NORTH_WEST:
				blk.setBlockBounds( 0F, 0F, 0F, mSize, 1F, mSize);
				break;
			case EDGE_NORTH_EAST:
				blk.setBlockBounds( 1F - mSize, 0F, 0F, 1F, 1F, mSize);
				break;
			case EDGE_SOUTH_WEST:
				blk.setBlockBounds( 0F, 0F, 1F - mSize, mSize, 1F, 1F);
				break;
			case EDGE_SOUTH_EAST:
				blk.setBlockBounds( 1F - mSize, 0F, 1F - mSize, 1F, 1F, 1F);
				break;
			case AXIS_DOWN_UP:
				blk.setBlockBounds( 0.5F - mThickness, 0F, 0.5F - mThickness, 0.5F + mThickness, 1F, 0.5F + mThickness);
				break;
			case AXIS_NORTH_SOUTH:
				blk.setBlockBounds( 0.5F - mThickness, 0.5F - mThickness, 0F, 0.5F + mThickness, 0.5F + mThickness, 1F);
				break;
			case AXIS_WEST_EAST:
				blk.setBlockBounds( 0F, 0.5F - mThickness, 0.5F - mThickness, 1F, 0.5F + mThickness, 0.5F + mThickness);
				break;
			default:
				LogHelper.warning( "unknown area %d", area);
				blk.setBlockBounds( 0F, 0F, 0F, 1F, 1F, 1F);
				break;
		}
	}
}
