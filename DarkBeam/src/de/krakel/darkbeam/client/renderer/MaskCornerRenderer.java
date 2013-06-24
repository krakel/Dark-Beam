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

import de.krakel.darkbeam.core.helper.LogHelper;
import de.krakel.darkbeam.tile.TileStage;

public class MaskCornerRenderer extends AStructureRenderer {
	public MaskCornerRenderer( int base) {
		super( base);
	}

	@Override
	public int getArea( int side, double dx, double dy, double dz) {
		switch (side) {
			case DIR_DOWN:
				if (dx < 0.5D) {
					return dz < 0.5D ? CORNER_DOWN_NORTH_WEST : CORNER_DOWN_SOUTH_WEST;
				}
				return dz < 0.5D ? CORNER_DOWN_NORTH_EAST : CORNER_DOWN_SOUTH_EAST;
			case DIR_UP:
				if (dx < 0.5D) {
					return dz < 0.5D ? CORNER_UP_NORTH_WEST : CORNER_UP_SOUTH_WEST;
				}
				return dz < 0.5D ? CORNER_UP_NORTH_EAST : CORNER_UP_SOUTH_EAST;
			case DIR_NORTH:
				if (dx < 0.5D) {
					return dy < 0.5D ? CORNER_DOWN_NORTH_WEST : CORNER_UP_NORTH_WEST;
				}
				return dy < 0.5D ? CORNER_DOWN_NORTH_EAST : CORNER_UP_NORTH_EAST;
			case DIR_SOUTH:
				if (dx < 0.5D) {
					return dy < 0.5D ? CORNER_DOWN_SOUTH_WEST : CORNER_UP_SOUTH_WEST;
				}
				return dy < 0.5D ? CORNER_DOWN_SOUTH_EAST : CORNER_UP_SOUTH_EAST;
			case DIR_WEST:
				if (dy < 0.5D) {
					return dz < 0.5D ? CORNER_DOWN_NORTH_WEST : CORNER_DOWN_SOUTH_WEST;
				}
				return dz < 0.5D ? CORNER_UP_NORTH_WEST : CORNER_UP_SOUTH_WEST;
			case DIR_EAST:
				if (dy < 0.5D) {
					return dz < 0.5D ? CORNER_DOWN_NORTH_EAST : CORNER_DOWN_SOUTH_EAST;
				}
				return dz < 0.5D ? CORNER_UP_NORTH_EAST : CORNER_UP_SOUTH_EAST;
			default:
				return -1;
		}
	}

	@Override
	public void renderItem( RenderBlocks rndrBlk, Block blk, int meta) {
		rndrBlk.setRenderBounds( 0D, 0D, 0D, mSize, mSize, mSize);
		renderStandardInventory( rndrBlk, blk, meta);
	}

	@Override
	public void renderSide( RenderBlocks rndrBlk, int area, Block blk, int meta, int x, int y, int z, TileStage tile) {
		setSectionBounds( area, blk);
		renderStandard( rndrBlk, blk, DIR_SOUTH, meta, x, y, z);
	}

	@Override
	public void setSectionBounds( int area, Block blk) {
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
