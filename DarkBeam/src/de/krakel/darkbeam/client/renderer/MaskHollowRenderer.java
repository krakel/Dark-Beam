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

import de.krakel.darkbeam.core.helper.LogHelper;
import de.krakel.darkbeam.tile.TileMasking;

public class MaskHollowRenderer extends AMaskRenderer {
	private static final int VALID_D = D | DN | DS | DW | DE | DNW | DNE | DSW | DSE;
	private static final int VALID_U = U | UN | US | UW | UE | UNW | UNE | USW | USE;
	private static final int VALID_N = N | DN | UN | NW | NE | DNW | DNE | UNW | UNE;
	private static final int VALID_S = S | DS | US | SW | SE | USW | USE | USW | USE;
	private static final int VALID_W = W | DW | UW | NW | SW | DNW | DSW | UNW | USW;
	private static final int VALID_E = E | DE | UE | NE | SE | DNE | DSE | UNE | USE;
	private float mThickness;
	private float mSize;

	public MaskHollowRenderer( int base) {
		mThickness = base / 16F;
		mSize = mThickness + mThickness;
	}

	@Override
	public int getSubHit( int side, double dx, double dy, double dz) {
		switch (side) {
			case DIR_DOWN:
				if (BOX_BORDER_MIN < dx && dx < BOX_BORDER_MAX && BOX_BORDER_MIN < dz && dz < BOX_BORDER_MAX) {
					return SIDE_DOWN;
				}
				if (dz > dx) {
					return dz + dx > 1D ? SIDE_SOUTH : SIDE_WEST;
				}
				return dz + dx > 1D ? SIDE_EAST : SIDE_NORTH;
			case DIR_UP:
				if (BOX_BORDER_MIN < dx && dx < BOX_BORDER_MAX && BOX_BORDER_MIN < dz && dz < BOX_BORDER_MAX) {
					return SIDE_UP;
				}
				if (dz > dx) {
					return dz + dx > 1D ? SIDE_SOUTH : SIDE_WEST;
				}
				return dz + dx > 1D ? SIDE_EAST : SIDE_NORTH;
			case DIR_NORTH:
				if (BOX_BORDER_MIN < dx && dx < BOX_BORDER_MAX && BOX_BORDER_MIN < dy && dy < BOX_BORDER_MAX) {
					return SIDE_NORTH;
				}
				if (dy > dx) {
					return dy + dx > 1D ? SIDE_UP : SIDE_WEST;
				}
				return dy + dx > 1D ? SIDE_EAST : SIDE_DOWN;
			case DIR_SOUTH:
				if (BOX_BORDER_MIN < dx && dx < BOX_BORDER_MAX && BOX_BORDER_MIN < dy && dy < BOX_BORDER_MAX) {
					return SIDE_SOUTH;
				}
				if (dy > dx) {
					return dy + dx > 1D ? SIDE_UP : SIDE_WEST;
				}
				return dy + dx > 1D ? SIDE_EAST : SIDE_DOWN;
			case DIR_WEST:
				if (BOX_BORDER_MIN < dy && dy < BOX_BORDER_MAX && BOX_BORDER_MIN < dz && dz < BOX_BORDER_MAX) {
					return SIDE_WEST;
				}
				if (dy > dz) {
					return dy + dz > 1D ? SIDE_UP : SIDE_NORTH;
				}
				return dy + dz > 1D ? SIDE_SOUTH : SIDE_DOWN;
			case DIR_EAST:
				if (BOX_BORDER_MIN < dy && dy < BOX_BORDER_MAX && BOX_BORDER_MIN < dz && dz < BOX_BORDER_MAX) {
					return SIDE_EAST;
				}
				if (dy > dz) {
					return dy + dz > 1D ? SIDE_UP : SIDE_NORTH;
				}
				return dy + dz > 1D ? SIDE_SOUTH : SIDE_DOWN;
			default:
				return -1;
		}
	}

	@Override
	public boolean isValid( TileMasking tile, int area) {
		switch (area) {
			case SIDE_DOWN:
				return tile.isValid( VALID_D);
			case SIDE_UP:
				return tile.isValid( VALID_U);
			case SIDE_NORTH:
				return tile.isValid( VALID_N);
			case SIDE_SOUTH:
				return tile.isValid( VALID_S);
			case SIDE_WEST:
				return tile.isValid( VALID_W);
			case SIDE_EAST:
				return tile.isValid( VALID_E);
			default:
				return false;
		}
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
				renderStandard( rndrBlk, blk, DIR_NORTH, meta, x, y, z);
				blk.setBlockBounds( 0F, 0F, max, 1F, mSize, 1F);
				renderStandard( rndrBlk, blk, DIR_NORTH, meta, x, y, z);
				blk.setBlockBounds( 0F, 0F, min, min, mSize, max);
				renderStandard( rndrBlk, blk, DIR_NORTH, meta, x, y, z);
				blk.setBlockBounds( max, 0F, min, 1F, mSize, max);
				renderStandard( rndrBlk, blk, DIR_NORTH, meta, x, y, z);
				break;
			case SIDE_UP:
				blk.setBlockBounds( 0F, 1F - mSize, 0F, 1F, 1F, min);
				renderStandard( rndrBlk, blk, DIR_NORTH, meta, x, y, z);
				blk.setBlockBounds( 0F, 1F - mSize, max, 1F, 1F, 1F);
				renderStandard( rndrBlk, blk, DIR_NORTH, meta, x, y, z);
				blk.setBlockBounds( 0F, 1F - mSize, min, min, 1F, max);
				renderStandard( rndrBlk, blk, DIR_NORTH, meta, x, y, z);
				blk.setBlockBounds( max, 1F - mSize, min, 1F, 1F, max);
				renderStandard( rndrBlk, blk, DIR_NORTH, meta, x, y, z);
				break;
			case SIDE_NORTH:
				blk.setBlockBounds( 0F, 0F, 0F, 1F, min, mSize);
				renderStandard( rndrBlk, blk, DIR_NORTH, meta, x, y, z);
				blk.setBlockBounds( 0F, max, 0F, 1F, 1F, mSize);
				renderStandard( rndrBlk, blk, DIR_NORTH, meta, x, y, z);
				blk.setBlockBounds( 0F, min, 0F, min, max, mSize);
				renderStandard( rndrBlk, blk, DIR_NORTH, meta, x, y, z);
				blk.setBlockBounds( max, min, 0F, 1F, max, mSize);
				renderStandard( rndrBlk, blk, DIR_NORTH, meta, x, y, z);
				break;
			case SIDE_SOUTH:
				blk.setBlockBounds( 0F, 0F, 1F - mSize, 1F, min, 1F);
				renderStandard( rndrBlk, blk, DIR_NORTH, meta, x, y, z);
				blk.setBlockBounds( 0F, max, 1F - mSize, 1F, 1F, 1F);
				renderStandard( rndrBlk, blk, DIR_NORTH, meta, x, y, z);
				blk.setBlockBounds( 0F, min, 1F - mSize, min, max, 1F);
				renderStandard( rndrBlk, blk, DIR_NORTH, meta, x, y, z);
				blk.setBlockBounds( max, min, 1F - mSize, 1F, max, 1F);
				renderStandard( rndrBlk, blk, DIR_NORTH, meta, x, y, z);
				break;
			case SIDE_WEST:
				blk.setBlockBounds( 0F, 0F, 0F, mSize, 1F, min);
				renderStandard( rndrBlk, blk, DIR_NORTH, meta, x, y, z);
				blk.setBlockBounds( 0F, 0F, max, mSize, 1F, 1F);
				renderStandard( rndrBlk, blk, DIR_NORTH, meta, x, y, z);
				blk.setBlockBounds( 0F, 0F, min, mSize, min, max);
				renderStandard( rndrBlk, blk, DIR_NORTH, meta, x, y, z);
				blk.setBlockBounds( 0F, max, min, mSize, 1F, max);
				renderStandard( rndrBlk, blk, DIR_NORTH, meta, x, y, z);
				break;
			case SIDE_EAST:
				blk.setBlockBounds( 1F - mSize, 0F, 0F, 1F, 1F, min);
				renderStandard( rndrBlk, blk, DIR_NORTH, meta, x, y, z);
				blk.setBlockBounds( 1F - mSize, 0F, max, 1F, 1F, 1F);
				renderStandard( rndrBlk, blk, DIR_NORTH, meta, x, y, z);
				blk.setBlockBounds( 1F - mSize, 0F, min, 1F, min, max);
				renderStandard( rndrBlk, blk, DIR_NORTH, meta, x, y, z);
				blk.setBlockBounds( 1F - mSize, max, min, 1F, 1F, max);
				renderStandard( rndrBlk, blk, DIR_NORTH, meta, x, y, z);
				break;
			default:
				blk.setBlockBounds( 0F, 0F, 0F, 1F, 1F, 1F);
				renderStandard( rndrBlk, blk, DIR_NORTH, meta, x, y, z);
				break;
		}
	}

	@Override
	public void setMaskBounds( Block blk, int area) {
		switch (area) {
			case SIDE_DOWN:
				blk.setBlockBounds( 0F, 0F, 0F, 1F, mSize, 1F);
				break;
			case SIDE_UP:
				blk.setBlockBounds( 0F, 1F - mSize, 0F, 1F, 1F, 1F);
				break;
			case SIDE_NORTH:
				blk.setBlockBounds( 0F, 0F, 0F, 1F, 1F, mSize);
				break;
			case SIDE_SOUTH:
				blk.setBlockBounds( 0F, 0F, 1F - mSize, 1F, 1F, 1F);
				break;
			case SIDE_WEST:
				blk.setBlockBounds( 0F, 0F, 0F, mSize, 1F, 1F);
				break;
			case SIDE_EAST:
				blk.setBlockBounds( 1F - mSize, 0F, 0F, 1F, 1F, 1F);
				break;
			default:
				LogHelper.warning( "unknown area %d", area);
				blk.setBlockBounds( 0F, 0F, 0F, 1F, 1F, 1F);
				break;
		}
	}
}
