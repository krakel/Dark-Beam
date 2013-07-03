/**
 * Dark Beam
 * SectionCornerRenderer.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;

import de.krakel.darkbeam.core.AreaType;
import de.krakel.darkbeam.core.IDirection;
import de.krakel.darkbeam.core.helper.LogHelper;
import de.krakel.darkbeam.tile.TileStage;

public class SectionCornerRenderer extends ASectionRenderer {
	public SectionCornerRenderer( int base) {
		super( base);
	}

	@Override
	public void renderItem( RenderBlocks rndrBlk, Block blk, int meta) {
		rndrBlk.setRenderBounds( 0D, 0D, 0D, mSize, mSize, mSize);
		renderStandardInventory( rndrBlk, blk, meta);
	}

	@Override
	public void renderSide( RenderBlocks rndrBlk, AreaType area, Block blk, int meta, int x, int y, int z, TileStage tile) {
		setSectionBounds( area, blk, tile);
		renderStandard( rndrBlk, blk, IDirection.DIR_SOUTH, meta, x, y, z);
	}

	@Override
	public void setSectionBounds( AreaType area, Block blk, TileStage tile) {
		switch (area) {
			case CORNER_DOWN_NORTH_WEST:
				blk.setBlockBounds( 0F, 0F, 0F, mSize, mSize, mSize);
				break;
			case CORNER_UP_NORTH_WEST:
				blk.setBlockBounds( 0F, 1F - mSize, 0F, mSize, 1F, mSize);
				break;
			case CORNER_DOWN_SOUTH_WEST:
				blk.setBlockBounds( 0F, 0F, 1F - mSize, mSize, mSize, 1F);
				break;
			case CORNER_UP_SOUTH_WEST:
				blk.setBlockBounds( 0F, 1F - mSize, 1F - mSize, mSize, 1F, 1F);
				break;
			case CORNER_DOWN_NORTH_EAST:
				blk.setBlockBounds( 1F - mSize, 0F, 0F, 1F, mSize, mSize);
				break;
			case CORNER_UP_NORTH_EAST:
				blk.setBlockBounds( 1F - mSize, 1F - mSize, 0F, 1F, 1F, mSize);
				break;
			case CORNER_DOWN_SOUTH_EAST:
				blk.setBlockBounds( 1F - mSize, 0F, 1F - mSize, 1F, mSize, 1F);
				break;
			case CORNER_UP_SOUTH_EAST:
				blk.setBlockBounds( 1F - mSize, 1F - mSize, 1F - mSize, 1F, 1F, 1F);
				break;
			default:
				LogHelper.warning( "unknown area %d", area);
				blk.setBlockBounds( 0F, 0F, 0F, 1F, 1F, 1F);
				break;
		}
	}
}
