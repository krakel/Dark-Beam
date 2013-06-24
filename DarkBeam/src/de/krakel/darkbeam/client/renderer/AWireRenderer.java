/**
 * Dark Beam
 * AWireRenderer.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;

import de.krakel.darkbeam.tile.TileStage;

abstract class AWireRenderer extends AMaskRenderer {
	private static final int VALID_D = D | DN | DS | DW | DE | DNW | DNE | DSW | DSE;
	private static final int VALID_U = U | UN | US | UW | UE | UNW | UNE | USW | USE;
	private static final int VALID_N = N | DN | UN | NW | NE | DNW | DNE | UNW | UNE;
	private static final int VALID_S = S | DS | US | SW | SE | USW | USE | USW | USE;
	private static final int VALID_W = W | DW | UW | NW | SW | DNW | DSW | UNW | USW;
	private static final int VALID_E = E | DE | UE | NE | SE | DNE | DSE | UNE | USE;

	protected AWireRenderer( int base) {
		super( base);
	}

	@Override
	public int getArea( int side, double dx, double dy, double dz) {
		switch (side) {
			case DIR_DOWN:
				return SIDE_DOWN;
			case DIR_UP:
				return SIDE_UP;
			case DIR_NORTH:
				return SIDE_NORTH;
			case DIR_SOUTH:
				return SIDE_SOUTH;
			case DIR_WEST:
				return SIDE_WEST;
			case DIR_EAST:
				return SIDE_EAST;
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
	public boolean hasMaterials() {
		return false;
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
	public void renderItem( RenderBlocks rndrBlk, Block blk, int meta) {
		double minS = 0.5D - mThickness;
		double maxS = 0.5D + mThickness;
		rndrBlk.setRenderBounds( 0D, minS, minS, 1D, maxS, maxS);
		renderStandardInventory( rndrBlk, blk, meta);
		rndrBlk.setRenderBounds( minS, minS, 0D, maxS, maxS, 1D);
		renderStandardInventory( rndrBlk, blk, meta);
	}
}
