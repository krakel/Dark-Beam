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

import de.krakel.darkbeam.core.helper.LogHelper;
import de.krakel.darkbeam.tile.TileMasking;

public class MaskStripRenderer extends AStructureRenderer {
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
	private static final int VALID_DU = DU | NS | WE;
	private static final int VALID_NS = DU | NS | WE;
	private static final int VALID_WE = DU | NS | WE;

	public MaskStripRenderer( int base) {
		super( base);
	}

	@Override
	public int getArea( int side, double dx, double dy, double dz) {
		switch (side) {
			case DIR_DOWN:
				if (dx < BOX_BORDER_MIN) {
					if (dz < BOX_BORDER_MIN) {
						return EDGE_NORTH_WEST;
					}
					if (dz >= BOX_BORDER_MAX) {
						return EDGE_SOUTH_WEST;
					}
					return EDGE_DOWN_WEST;
				}
				if (dx >= BOX_BORDER_MAX) {
					if (dz < BOX_BORDER_MIN) {
						return EDGE_NORTH_EAST;
					}
					if (dz >= BOX_BORDER_MAX) {
						return EDGE_SOUTH_EAST;
					}
					return EDGE_DOWN_EAST;
				}
				if (dz < BOX_BORDER_MIN) {
					return EDGE_DOWN_NORTH;
				}
				if (dz >= BOX_BORDER_MAX) {
					return EDGE_DOWN_SOUTH;
				}
				return AXIS_DOWN_UP;
			case DIR_UP:
				if (dx < BOX_BORDER_MIN) {
					if (dz < BOX_BORDER_MIN) {
						return EDGE_NORTH_WEST;
					}
					if (dz >= BOX_BORDER_MAX) {
						return EDGE_SOUTH_WEST;
					}
					return EDGE_UP_WEST;
				}
				if (dx >= BOX_BORDER_MAX) {
					if (dz < BOX_BORDER_MIN) {
						return EDGE_NORTH_EAST;
					}
					if (dz >= BOX_BORDER_MAX) {
						return EDGE_SOUTH_EAST;
					}
					return EDGE_UP_EAST;
				}
				if (dz < BOX_BORDER_MIN) {
					return EDGE_UP_NORTH;
				}
				if (dz >= BOX_BORDER_MAX) {
					return EDGE_UP_SOUTH;
				}
				return AXIS_DOWN_UP;
			case DIR_NORTH:
				if (dx < BOX_BORDER_MIN) {
					if (dy < BOX_BORDER_MIN) {
						return EDGE_DOWN_WEST;
					}
					if (dy >= BOX_BORDER_MAX) {
						return EDGE_UP_WEST;
					}
					return EDGE_NORTH_WEST;
				}
				if (dx >= BOX_BORDER_MAX) {
					if (dy < BOX_BORDER_MIN) {
						return EDGE_DOWN_EAST;
					}
					if (dy >= BOX_BORDER_MAX) {
						return EDGE_UP_EAST;
					}
					return EDGE_NORTH_EAST;
				}
				if (dy < BOX_BORDER_MIN) {
					return EDGE_DOWN_NORTH;
				}
				if (dy >= BOX_BORDER_MAX) {
					return EDGE_UP_NORTH;
				}
				return AXIS_NORTH_SOUTH;
			case DIR_SOUTH:
				if (dx < BOX_BORDER_MIN) {
					if (dy < BOX_BORDER_MIN) {
						return EDGE_DOWN_WEST;
					}
					if (dy >= BOX_BORDER_MAX) {
						return EDGE_UP_WEST;
					}
					return EDGE_SOUTH_WEST;
				}
				if (dx >= BOX_BORDER_MAX) {
					if (dy < BOX_BORDER_MIN) {
						return EDGE_DOWN_EAST;
					}
					if (dy >= BOX_BORDER_MAX) {
						return EDGE_UP_EAST;
					}
					return EDGE_SOUTH_EAST;
				}
				if (dy < BOX_BORDER_MIN) {
					return EDGE_DOWN_SOUTH;
				}
				if (dy >= BOX_BORDER_MAX) {
					return EDGE_UP_SOUTH;
				}
				return AXIS_NORTH_SOUTH;
			case DIR_WEST:
				if (dy < BOX_BORDER_MIN) {
					if (dz < BOX_BORDER_MIN) {
						return EDGE_DOWN_NORTH;
					}
					if (dz >= BOX_BORDER_MAX) {
						return EDGE_DOWN_SOUTH;
					}
					return EDGE_DOWN_WEST;
				}
				if (dy >= BOX_BORDER_MAX) {
					if (dz < BOX_BORDER_MIN) {
						return EDGE_UP_NORTH;
					}
					if (dz >= BOX_BORDER_MAX) {
						return EDGE_UP_SOUTH;
					}
					return EDGE_UP_WEST;
				}
				if (dz < BOX_BORDER_MIN) {
					return EDGE_NORTH_WEST;
				}
				if (dz >= BOX_BORDER_MAX) {
					return EDGE_SOUTH_WEST;
				}
				return AXIS_WEST_EAST;
			case DIR_EAST:
				if (dy < BOX_BORDER_MIN) {
					if (dz < BOX_BORDER_MIN) {
						return EDGE_DOWN_NORTH;
					}
					if (dz >= BOX_BORDER_MAX) {
						return EDGE_DOWN_SOUTH;
					}
					return EDGE_DOWN_EAST;
				}
				if (dy >= BOX_BORDER_MAX) {
					if (dz < BOX_BORDER_MIN) {
						return EDGE_UP_NORTH;
					}
					if (dz >= BOX_BORDER_MAX) {
						return EDGE_UP_SOUTH;
					}
					return EDGE_UP_EAST;
				}
				if (dz < BOX_BORDER_MIN) {
					return EDGE_NORTH_EAST;
				}
				if (dz >= BOX_BORDER_MAX) {
					return EDGE_SOUTH_EAST;
				}
				return AXIS_WEST_EAST;
			default:
				return -1;
		}
	}

	@Override
	public int getOpposite( int side, int area) {
		switch (side) {
			case DIR_DOWN:
				switch (area) {
					case EDGE_DOWN_NORTH:
						return EDGE_UP_NORTH;
					case EDGE_DOWN_SOUTH:
						return EDGE_UP_SOUTH;
					case EDGE_DOWN_WEST:
						return EDGE_UP_WEST;
					case EDGE_DOWN_EAST:
						return EDGE_UP_EAST;
					default:
						return area;
				}
			case DIR_UP:
				switch (area) {
					case EDGE_UP_NORTH:
						return EDGE_DOWN_NORTH;
					case EDGE_UP_SOUTH:
						return EDGE_DOWN_SOUTH;
					case EDGE_UP_WEST:
						return EDGE_DOWN_WEST;
					case EDGE_UP_EAST:
						return EDGE_DOWN_EAST;
					default:
						return area;
				}
			case DIR_NORTH:
				switch (area) {
					case EDGE_DOWN_NORTH:
						return EDGE_DOWN_SOUTH;
					case EDGE_UP_NORTH:
						return EDGE_UP_SOUTH;
					case EDGE_NORTH_WEST:
						return EDGE_SOUTH_WEST;
					case EDGE_NORTH_EAST:
						return EDGE_SOUTH_EAST;
					default:
						return area;
				}
			case DIR_SOUTH:
				switch (area) {
					case EDGE_DOWN_SOUTH:
						return EDGE_DOWN_NORTH;
					case EDGE_UP_SOUTH:
						return EDGE_UP_NORTH;
					case EDGE_SOUTH_WEST:
						return EDGE_NORTH_WEST;
					case EDGE_SOUTH_EAST:
						return EDGE_NORTH_EAST;
					default:
						return area;
				}
			case DIR_WEST:
				switch (area) {
					case EDGE_DOWN_WEST:
						return EDGE_DOWN_EAST;
					case EDGE_UP_WEST:
						return EDGE_UP_EAST;
					case EDGE_NORTH_WEST:
						return EDGE_NORTH_EAST;
					case EDGE_SOUTH_WEST:
						return EDGE_SOUTH_EAST;
					default:
						return area;
				}
			case DIR_EAST:
				switch (area) {
					case EDGE_DOWN_EAST:
						return EDGE_DOWN_WEST;
					case EDGE_UP_EAST:
						return EDGE_UP_WEST;
					case EDGE_NORTH_EAST:
						return EDGE_NORTH_WEST;
					case EDGE_SOUTH_EAST:
						return EDGE_SOUTH_WEST;
					default:
						return area;
				}
			default:
				return area;
		}
	}

	@Override
	public boolean isValid( int area, TileMasking tile) {
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
			case AXIS_DOWN_UP:
				return tile.isValid( VALID_DU);
			case AXIS_NORTH_SOUTH:
				return tile.isValid( VALID_NS);
			case AXIS_WEST_EAST:
				return tile.isValid( VALID_WE);
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
	public void renderSide( RenderBlocks rndrBlk, int area, Block blk, int meta, int x, int y, int z, TileMasking tile) {
		setMaskBounds( area, blk);
		renderStandard( rndrBlk, blk, DIR_SOUTH, meta, x, y, z);
	}

	@Override
	public void setMaskBounds( int area, Block blk) {
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
