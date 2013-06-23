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
import de.krakel.darkbeam.tile.TileMasking;

public class MaskCornerRenderer extends AStructureRenderer {
	private static final int VALID_DNW = D | N | W | DN | DW | NW | DNW;
	private static final int VALID_UNW = U | N | W | UN | UW | NW | UNW;
	private static final int VALID_DSW = D | S | W | DS | DW | SW | DSW;
	private static final int VALID_USW = U | S | W | US | UW | DW | USW;
	private static final int VALID_DNE = D | N | E | DN | DE | NE | DNE;
	private static final int VALID_UNE = U | N | E | UN | UE | NE | UNE;
	private static final int VALID_DSE = D | S | E | DS | DE | SE | DSE;
	private static final int VALID_USE = U | S | E | US | UE | SE | USE;

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
	public int getOpposite( int side, int area) {
		switch (side) {
			case DIR_DOWN:
				switch (area) {
					case CORNER_DOWN_NORTH_WEST:
					case CORNER_DOWN_SOUTH_WEST:
					case CORNER_DOWN_NORTH_EAST:
					case CORNER_DOWN_SOUTH_EAST:
						return area + 1;
					default:
						return area;
				}
			case DIR_UP:
				switch (area) {
					case CORNER_UP_NORTH_WEST:
					case CORNER_UP_SOUTH_WEST:
					case CORNER_UP_NORTH_EAST:
					case CORNER_UP_SOUTH_EAST:
						return area - 1;
					default:
						return area;
				}
			case DIR_NORTH:
				switch (area) {
					case CORNER_DOWN_NORTH_WEST:
					case CORNER_UP_NORTH_WEST:
					case CORNER_DOWN_NORTH_EAST:
					case CORNER_UP_NORTH_EAST:
						return area + 2;
					default:
						return area;
				}
			case DIR_SOUTH:
				switch (area) {
					case CORNER_DOWN_SOUTH_WEST:
					case CORNER_UP_SOUTH_WEST:
					case CORNER_DOWN_SOUTH_EAST:
					case CORNER_UP_SOUTH_EAST:
						return area - 2;
					default:
						return area;
				}
			case DIR_WEST:
				switch (area) {
					case CORNER_DOWN_NORTH_WEST:
					case CORNER_DOWN_SOUTH_WEST:
					case CORNER_UP_NORTH_WEST:
					case CORNER_UP_SOUTH_WEST:
						return area + 4;
					default:
						return area;
				}
			case DIR_EAST:
				switch (area) {
					case CORNER_DOWN_NORTH_EAST:
					case CORNER_DOWN_SOUTH_EAST:
					case CORNER_UP_NORTH_EAST:
					case CORNER_UP_SOUTH_EAST:
						return area - 4;
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
			case CORNER_DOWN_NORTH_WEST:
				return tile.isValid( VALID_DNW);
			case CORNER_UP_NORTH_WEST:
				return tile.isValid( VALID_UNW);
			case CORNER_DOWN_SOUTH_WEST:
				return tile.isValid( VALID_DSW);
			case CORNER_UP_SOUTH_WEST:
				return tile.isValid( VALID_USW);
			case CORNER_DOWN_NORTH_EAST:
				return tile.isValid( VALID_DNE);
			case CORNER_UP_NORTH_EAST:
				return tile.isValid( VALID_UNE);
			case CORNER_DOWN_SOUTH_EAST:
				return tile.isValid( VALID_DSE);
			case CORNER_UP_SOUTH_EAST:
				return tile.isValid( VALID_USE);
			default:
				return false;
		}
	}

	@Override
	public void renderItem( RenderBlocks rndrBlk, Block blk, int meta) {
		rndrBlk.setRenderBounds( 0D, 0D, 0D, mSize, mSize, mSize);
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
