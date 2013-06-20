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

import de.krakel.darkbeam.tile.TileMasking;

public class MaskCornerRenderer extends AMaskRenderer {
	private static final int VALID_DNW = D | N | W | DN | DW | NW | DNW;
	private static final int VALID_DNE = D | N | E | DN | DE | NE | DNE;
	private static final int VALID_DSW = D | S | W | DS | DW | SW | DSW;
	private static final int VALID_DSE = D | S | E | DS | DE | SE | DSE;
	private static final int VALID_UNW = U | N | W | UN | UW | NW | UNW;
	private static final int VALID_UNE = U | N | E | UN | UE | NE | UNE;
	private static final int VALID_USW = U | S | W | US | UW | DW | USW;
	private static final int VALID_USE = U | S | E | US | UE | SE | USE;
	private float mThickness;
	private float mSize;

	public MaskCornerRenderer( float base) {
		mThickness = base / 16F;
		mSize = mThickness + mThickness;
	}

	@Override
	public boolean isValid( TileMasking tile, int area) {
		switch (area) {
			case CORNER_DOWN_NORTH_WEST:
				return tile.isValid( VALID_DNW);
			case CORNER_DOWN_NORTH_EAST:
				return tile.isValid( VALID_DNE);
			case CORNER_DOWN_SOUTH_WEST:
				return tile.isValid( VALID_DSW);
			case CORNER_DOWN_SOUTH_EAST:
				return tile.isValid( VALID_DSE);
			case CORNER_UP_NORTH_WEST:
				return tile.isValid( VALID_UNW);
			case CORNER_UP_NORTH_EAST:
				return tile.isValid( VALID_UNE);
			case CORNER_UP_SOUTH_WEST:
				return tile.isValid( VALID_USW);
			case CORNER_UP_SOUTH_EAST:
				return tile.isValid( VALID_USE);
			default:
				return false;
		}
	}

	@Override
	public void renderItem( RenderBlocks rndrBlk, Block blk, int meta) {
		rndrBlk.setRenderBounds( 0D, 0D, 0D, 0.5D + mSize, 0.5D + mSize, 0.5D + mSize);
		renderStandardInventory( rndrBlk, blk, meta);
	}

	@Override
	public void renderSide( RenderBlocks rndrBlk, int area, Block blk, int meta, int x, int y, int z) {
		setMaskBounds( blk, area);
		renderStandard( rndrBlk, blk, DIR_NORTH, meta, x, y, z);
	}

	@Override
	public void setMaskBounds( Block blk, int area) {
		switch (area) {
			case CORNER_DOWN_NORTH_WEST:
				blk.setBlockBounds( 0F, 0F, 0F, mSize, mSize, mSize);
				break;
			case CORNER_DOWN_NORTH_EAST:
				blk.setBlockBounds( 1F - mSize, 0F, 0F, 1F, mSize, mSize);
				break;
			case CORNER_DOWN_SOUTH_WEST:
				blk.setBlockBounds( 0F, 0F, 1F - mSize, mSize, mSize, 1F);
				break;
			case CORNER_DOWN_SOUTH_EAST:
				blk.setBlockBounds( 1F - mSize, 0F, 1F - mSize, 1F, mSize, 1F);
				break;
			case CORNER_UP_NORTH_WEST:
				blk.setBlockBounds( 0F, 1F - mSize, 0F, mSize, 1F, mSize);
				break;
			case CORNER_UP_NORTH_EAST:
				blk.setBlockBounds( 1F - mSize, 1F - mSize, 0F, 1F, 1F, mSize);
				break;
			case CORNER_UP_SOUTH_WEST:
				blk.setBlockBounds( 0F, 1F - mSize, 1F - mSize, mSize, 1F, 1F);
				break;
			case CORNER_UP_SOUTH_EAST:
				blk.setBlockBounds( 1F - mSize, 1F - mSize, 1F - mSize, 1F, 1F, 1F);
				break;
			default:
				blk.setBlockBounds( 0F, 0F, 0F, 1F, 1F, 1F);
				break;
		}
	}
}
