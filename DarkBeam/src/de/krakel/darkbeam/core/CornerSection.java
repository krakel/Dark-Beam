/**
 * Dark Beam
 * CornerSection.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import net.minecraft.util.MovingObjectPosition;

import de.krakel.darkbeam.client.renderer.AMaskRenderer;
import de.krakel.darkbeam.client.renderer.MaskCornerRenderer;
import de.krakel.darkbeam.tile.TileStage;

public class CornerSection extends AStructureSection {
	private static final int VALID_DNW = D | N | W | DN | DW | NW | DNW;
	private static final int VALID_UNW = U | N | W | UN | UW | NW | UNW;
	private static final int VALID_DSW = D | S | W | DS | DW | SW | DSW;
	private static final int VALID_USW = U | S | W | US | UW | DW | USW;
	private static final int VALID_DNE = D | N | E | DN | DE | NE | DNE;
	private static final int VALID_UNE = U | N | E | UN | UE | NE | UNE;
	private static final int VALID_DSE = D | S | E | DS | DE | SE | DSE;
	private static final int VALID_USE = U | S | E | US | UE | SE | USE;
	private MaskCornerRenderer mRenderer;

	public CornerSection( int nr) {
		super( SectionLib.nextID(), "corner." + nr);
		mRenderer = new MaskCornerRenderer( nr);
	}

	private static int getArea( int side, double dx, double dy, double dz) {
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

	private static int getOpposite( int side, int area) {
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
	public AMaskRenderer getRenderer() {
		return mRenderer;
	}

	@Override
	public boolean isValid( TileStage tile, int area) {
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
	public void oppositeArea( MovingObjectPosition pos) {
		pos.subHit = getOpposite( pos.sideHit, pos.subHit);
	}

	@Override
	public void updateArea( MovingObjectPosition pos) {
		double dx = pos.hitVec.xCoord - pos.blockX;
		double dy = pos.hitVec.yCoord - pos.blockY;
		double dz = pos.hitVec.zCoord - pos.blockZ;
		pos.subHit = getArea( pos.sideHit, dx, dy, dz);
	}
}
