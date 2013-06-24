/**
 * Dark Beam
 * ACoverRenderer.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.client.renderer;

import net.minecraft.block.Block;

import de.krakel.darkbeam.core.helper.LogHelper;
import de.krakel.darkbeam.tile.TileStage;

abstract class ACoverRenderer extends AStructureRenderer {
	protected static final int VALID_D = D | DN | DS | DW | DE | DNW | DNE | DSW | DSE;
	protected static final int VALID_U = U | UN | US | UW | UE | UNW | UNE | USW | USE;
	protected static final int VALID_N = N | DN | UN | NW | NE | DNW | DNE | UNW | UNE;
	protected static final int VALID_S = S | DS | US | SW | SE | USW | USE | USW | USE;
	protected static final int VALID_W = W | DW | UW | NW | SW | DNW | DSW | UNW | USW;
	protected static final int VALID_E = E | DE | UE | NE | SE | DNE | DSE | UNE | USE;

	protected ACoverRenderer( int base) {
		super( base);
	}

	@Override
	public int getArea( int side, double dx, double dy, double dz) {
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
	public int getOpposite( int side, int area) {
		if (area == side) {
			return area ^= 1;
		}
		return area;
	}

	@Override
	public boolean isValid( int area, TileStage tile) {
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
	public void setSectionBounds( int area, Block blk) {
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
