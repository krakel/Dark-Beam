/**
 * Dark Beam
 * MaskStripRenderer.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;

import de.krakel.darkbeam.tile.TileMasking;

public class MaskStripRenderer extends AMaskRenderer {
	private static final int VALID_DN = D | N | DN | DNW | DNE;
	private static final int VALID_DS = D | S | DS | DSW | DSE;
	private static final int VALID_DW = D | W | DW | DNW | DSW;
	private static final int VALID_DE = D | E | DE | DNE | DSE;
	private static final int VALID_UN = U | N | UN | UNW | UNE;
	private static final int VALID_US = U | S | US | USW | USE;
	private static final int VALID_UW = U | W | UW | UNW | USW;
	private static final int VALID_UE = U | E | UE | UNE | USE;
	private static final int VALID_NW = N | W | NW | DNW | UNW;
	private static final int VALID_NE = N | E | NE | DNE | UNE;
	private static final int VALID_SW = S | W | SW | DSW | USW;
	private static final int VALID_SE = S | E | SE | DSE | USE;
	private float mThickness;
	private float mSize;

	public MaskStripRenderer( float base) {
		mThickness = base / 16F;
		mSize = mThickness + mThickness;
	}

	@Override
	public boolean isValid( TileMasking tile, int area) {
		switch (area) {
			case EDGE_DOWN_NORTH:
				return tile.isValid( VALID_DN);
			case EDGE_DOWN_SOUTH:
				return tile.isValid( VALID_DS);
			case EDGE_DOWN_WEST:
				return tile.isValid( VALID_DW);
			case EDGE_DOWN_EAST:
				return tile.isValid( VALID_DE);
			case EDGE_UP_NORTH:
				return tile.isValid( VALID_UN);
			case EDGE_UP_SOUTH:
				return tile.isValid( VALID_US);
			case EDGE_UP_WEST:
				return tile.isValid( VALID_UW);
			case EDGE_UP_EAST:
				return tile.isValid( VALID_UE);
			case EDGE_NORTH_WEST:
				return tile.isValid( VALID_NW);
			case EDGE_NORTH_EAST:
				return tile.isValid( VALID_NE);
			case EDGE_SOUTH_WEST:
				return tile.isValid( VALID_SW);
			case EDGE_SOUTH_EAST:
				return tile.isValid( VALID_SE);
			default:
				return false;
		}
	}

	@Override
	public void renderItem( RenderBlocks rndrBlk, Block blk, int meta) {
		rndrBlk.setRenderBounds( 0.5D - mThickness, 0D, 0.5D - mThickness, 0.5D + mThickness, 1D, 0.5D + mThickness);
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
			default:
				blk.setBlockBounds( 0F, 0F, 0F, 1F, 1F, 1F);
				break;
		}
	}
}
