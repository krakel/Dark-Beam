/**
 * Dark Beam
 * SectionStrip.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import net.minecraft.util.MovingObjectPosition;

import de.krakel.darkbeam.client.renderer.SectionStripRenderer;
import de.krakel.darkbeam.tile.TileStage;

class SectionStrip extends ASectionStructure {
	private static final int VALID_DN = AreaType.toMask( AreaType.EDGE_DOWN_NORTH, AreaType.CORNER_DOWN_NORTH_WEST, AreaType.CORNER_DOWN_NORTH_EAST);
	private static final int VALID_DS = AreaType.toMask( AreaType.EDGE_DOWN_SOUTH, AreaType.CORNER_DOWN_SOUTH_WEST, AreaType.CORNER_DOWN_SOUTH_EAST);
	private static final int VALID_DW = AreaType.toMask( AreaType.EDGE_DOWN_WEST, AreaType.CORNER_DOWN_NORTH_WEST, AreaType.CORNER_DOWN_SOUTH_WEST);
	private static final int VALID_DE = AreaType.toMask( AreaType.EDGE_DOWN_EAST, AreaType.CORNER_DOWN_NORTH_EAST, AreaType.CORNER_DOWN_SOUTH_EAST);
	private static final int VALID_UN = AreaType.toMask( AreaType.EDGE_UP_NORTH, AreaType.CORNER_UP_NORTH_WEST, AreaType.CORNER_UP_NORTH_EAST);
	private static final int VALID_US = AreaType.toMask( AreaType.EDGE_UP_SOUTH, AreaType.CORNER_UP_SOUTH_WEST, AreaType.CORNER_UP_SOUTH_EAST);
	private static final int VALID_UW = AreaType.toMask( AreaType.EDGE_UP_WEST, AreaType.CORNER_UP_NORTH_WEST, AreaType.CORNER_UP_SOUTH_WEST);
	private static final int VALID_UE = AreaType.toMask( AreaType.EDGE_UP_EAST, AreaType.CORNER_UP_NORTH_EAST, AreaType.CORNER_UP_SOUTH_EAST);
	private static final int VALID_NW = AreaType.toMask( AreaType.EDGE_NORTH_WEST, AreaType.CORNER_DOWN_NORTH_WEST, AreaType.CORNER_UP_NORTH_WEST);
	private static final int VALID_NE = AreaType.toMask( AreaType.EDGE_NORTH_EAST, AreaType.CORNER_DOWN_NORTH_EAST, AreaType.CORNER_UP_NORTH_EAST);
	private static final int VALID_SW = AreaType.toMask( AreaType.EDGE_SOUTH_WEST, AreaType.CORNER_DOWN_SOUTH_WEST, AreaType.CORNER_UP_SOUTH_WEST);
	private static final int VALID_SE = AreaType.toMask( AreaType.EDGE_SOUTH_EAST, AreaType.CORNER_DOWN_SOUTH_EAST, AreaType.CORNER_UP_SOUTH_EAST);
	private static final int VALID_DU = AreaType.toMask( AreaType.AXIS_DOWN_UP, AreaType.AXIS_NORTH_SOUTH, AreaType.AXIS_WEST_EAST);
	private static final int VALID_NS = AreaType.toMask( AreaType.AXIS_DOWN_UP, AreaType.AXIS_NORTH_SOUTH, AreaType.AXIS_WEST_EAST);
	private static final int VALID_WE = AreaType.toMask( AreaType.AXIS_DOWN_UP, AreaType.AXIS_NORTH_SOUTH, AreaType.AXIS_WEST_EAST);

	public SectionStrip( int nr) {
		super( "strip." + nr, new SectionStripRenderer( nr));
	}

	public static AreaType getArea( int side, double dx, double dy, double dz) {
		switch (side) {
			case IDirection.DIR_DOWN:
				if (dx < BOX_BORDER_MIN) {
					if (dz < BOX_BORDER_MIN) {
						return AreaType.EDGE_NORTH_WEST;
					}
					if (dz >= BOX_BORDER_MAX) {
						return AreaType.EDGE_SOUTH_WEST;
					}
					return AreaType.EDGE_DOWN_WEST;
				}
				if (dx >= BOX_BORDER_MAX) {
					if (dz < BOX_BORDER_MIN) {
						return AreaType.EDGE_NORTH_EAST;
					}
					if (dz >= BOX_BORDER_MAX) {
						return AreaType.EDGE_SOUTH_EAST;
					}
					return AreaType.EDGE_DOWN_EAST;
				}
				if (dz < BOX_BORDER_MIN) {
					return AreaType.EDGE_DOWN_NORTH;
				}
				if (dz >= BOX_BORDER_MAX) {
					return AreaType.EDGE_DOWN_SOUTH;
				}
				return AreaType.AXIS_DOWN_UP;
			case IDirection.DIR_UP:
				if (dx < BOX_BORDER_MIN) {
					if (dz < BOX_BORDER_MIN) {
						return AreaType.EDGE_NORTH_WEST;
					}
					if (dz >= BOX_BORDER_MAX) {
						return AreaType.EDGE_SOUTH_WEST;
					}
					return AreaType.EDGE_UP_WEST;
				}
				if (dx >= BOX_BORDER_MAX) {
					if (dz < BOX_BORDER_MIN) {
						return AreaType.EDGE_NORTH_EAST;
					}
					if (dz >= BOX_BORDER_MAX) {
						return AreaType.EDGE_SOUTH_EAST;
					}
					return AreaType.EDGE_UP_EAST;
				}
				if (dz < BOX_BORDER_MIN) {
					return AreaType.EDGE_UP_NORTH;
				}
				if (dz >= BOX_BORDER_MAX) {
					return AreaType.EDGE_UP_SOUTH;
				}
				return AreaType.AXIS_DOWN_UP;
			case IDirection.DIR_NORTH:
				if (dx < BOX_BORDER_MIN) {
					if (dy < BOX_BORDER_MIN) {
						return AreaType.EDGE_DOWN_WEST;
					}
					if (dy >= BOX_BORDER_MAX) {
						return AreaType.EDGE_UP_WEST;
					}
					return AreaType.EDGE_NORTH_WEST;
				}
				if (dx >= BOX_BORDER_MAX) {
					if (dy < BOX_BORDER_MIN) {
						return AreaType.EDGE_DOWN_EAST;
					}
					if (dy >= BOX_BORDER_MAX) {
						return AreaType.EDGE_UP_EAST;
					}
					return AreaType.EDGE_NORTH_EAST;
				}
				if (dy < BOX_BORDER_MIN) {
					return AreaType.EDGE_DOWN_NORTH;
				}
				if (dy >= BOX_BORDER_MAX) {
					return AreaType.EDGE_UP_NORTH;
				}
				return AreaType.AXIS_NORTH_SOUTH;
			case IDirection.DIR_SOUTH:
				if (dx < BOX_BORDER_MIN) {
					if (dy < BOX_BORDER_MIN) {
						return AreaType.EDGE_DOWN_WEST;
					}
					if (dy >= BOX_BORDER_MAX) {
						return AreaType.EDGE_UP_WEST;
					}
					return AreaType.EDGE_SOUTH_WEST;
				}
				if (dx >= BOX_BORDER_MAX) {
					if (dy < BOX_BORDER_MIN) {
						return AreaType.EDGE_DOWN_EAST;
					}
					if (dy >= BOX_BORDER_MAX) {
						return AreaType.EDGE_UP_EAST;
					}
					return AreaType.EDGE_SOUTH_EAST;
				}
				if (dy < BOX_BORDER_MIN) {
					return AreaType.EDGE_DOWN_SOUTH;
				}
				if (dy >= BOX_BORDER_MAX) {
					return AreaType.EDGE_UP_SOUTH;
				}
				return AreaType.AXIS_NORTH_SOUTH;
			case IDirection.DIR_WEST:
				if (dy < BOX_BORDER_MIN) {
					if (dz < BOX_BORDER_MIN) {
						return AreaType.EDGE_DOWN_NORTH;
					}
					if (dz >= BOX_BORDER_MAX) {
						return AreaType.EDGE_DOWN_SOUTH;
					}
					return AreaType.EDGE_DOWN_WEST;
				}
				if (dy >= BOX_BORDER_MAX) {
					if (dz < BOX_BORDER_MIN) {
						return AreaType.EDGE_UP_NORTH;
					}
					if (dz >= BOX_BORDER_MAX) {
						return AreaType.EDGE_UP_SOUTH;
					}
					return AreaType.EDGE_UP_WEST;
				}
				if (dz < BOX_BORDER_MIN) {
					return AreaType.EDGE_NORTH_WEST;
				}
				if (dz >= BOX_BORDER_MAX) {
					return AreaType.EDGE_SOUTH_WEST;
				}
				return AreaType.AXIS_WEST_EAST;
			case IDirection.DIR_EAST:
				if (dy < BOX_BORDER_MIN) {
					if (dz < BOX_BORDER_MIN) {
						return AreaType.EDGE_DOWN_NORTH;
					}
					if (dz >= BOX_BORDER_MAX) {
						return AreaType.EDGE_DOWN_SOUTH;
					}
					return AreaType.EDGE_DOWN_EAST;
				}
				if (dy >= BOX_BORDER_MAX) {
					if (dz < BOX_BORDER_MIN) {
						return AreaType.EDGE_UP_NORTH;
					}
					if (dz >= BOX_BORDER_MAX) {
						return AreaType.EDGE_UP_SOUTH;
					}
					return AreaType.EDGE_UP_EAST;
				}
				if (dz < BOX_BORDER_MIN) {
					return AreaType.EDGE_NORTH_EAST;
				}
				if (dz >= BOX_BORDER_MAX) {
					return AreaType.EDGE_SOUTH_EAST;
				}
				return AreaType.AXIS_WEST_EAST;
			default:
				return AreaType.UNKNOWN;
		}
	}

	private static AreaType getOpposite( int side, AreaType area) {
		switch (side) {
			case IDirection.DIR_DOWN:
				switch (area) {
					case EDGE_DOWN_NORTH:
						return AreaType.EDGE_UP_NORTH;
					case EDGE_DOWN_SOUTH:
						return AreaType.EDGE_UP_SOUTH;
					case EDGE_DOWN_WEST:
						return AreaType.EDGE_UP_WEST;
					case EDGE_DOWN_EAST:
						return AreaType.EDGE_UP_EAST;
					default:
						return area;
				}
			case IDirection.DIR_UP:
				switch (area) {
					case EDGE_UP_NORTH:
						return AreaType.EDGE_DOWN_NORTH;
					case EDGE_UP_SOUTH:
						return AreaType.EDGE_DOWN_SOUTH;
					case EDGE_UP_WEST:
						return AreaType.EDGE_DOWN_WEST;
					case EDGE_UP_EAST:
						return AreaType.EDGE_DOWN_EAST;
					default:
						return area;
				}
			case IDirection.DIR_NORTH:
				switch (area) {
					case EDGE_DOWN_NORTH:
						return AreaType.EDGE_DOWN_SOUTH;
					case EDGE_UP_NORTH:
						return AreaType.EDGE_UP_SOUTH;
					case EDGE_NORTH_WEST:
						return AreaType.EDGE_SOUTH_WEST;
					case EDGE_NORTH_EAST:
						return AreaType.EDGE_SOUTH_EAST;
					default:
						return area;
				}
			case IDirection.DIR_SOUTH:
				switch (area) {
					case EDGE_DOWN_SOUTH:
						return AreaType.EDGE_DOWN_NORTH;
					case EDGE_UP_SOUTH:
						return AreaType.EDGE_UP_NORTH;
					case EDGE_SOUTH_WEST:
						return AreaType.EDGE_NORTH_WEST;
					case EDGE_SOUTH_EAST:
						return AreaType.EDGE_NORTH_EAST;
					default:
						return area;
				}
			case IDirection.DIR_WEST:
				switch (area) {
					case EDGE_DOWN_WEST:
						return AreaType.EDGE_DOWN_EAST;
					case EDGE_UP_WEST:
						return AreaType.EDGE_UP_EAST;
					case EDGE_NORTH_WEST:
						return AreaType.EDGE_NORTH_EAST;
					case EDGE_SOUTH_WEST:
						return AreaType.EDGE_SOUTH_EAST;
					default:
						return area;
				}
			case IDirection.DIR_EAST:
				switch (area) {
					case EDGE_DOWN_EAST:
						return AreaType.EDGE_DOWN_WEST;
					case EDGE_UP_EAST:
						return AreaType.EDGE_UP_WEST;
					case EDGE_NORTH_EAST:
						return AreaType.EDGE_NORTH_WEST;
					case EDGE_SOUTH_EAST:
						return AreaType.EDGE_SOUTH_WEST;
					default:
						return area;
				}
			default:
				return area;
		}
	}

	private static boolean isValidForAxis( TileStage tile, AreaType sideA, AreaType sideB) {
		return (tile.isValid( sideA.mMask) || !tile.isWire( sideA))
			&& (tile.isValid( sideB.mMask) || !tile.isWire( sideB));
	}

	private static boolean isValidForStrip( TileStage tile, AreaType sideA, AreaType sideB) {
		return (tile.isValid( sideA.mMask) || tile.isWire( sideA))
			&& (tile.isValid( sideB.mMask) || tile.isWire( sideB));
	}

	@Override
	public boolean isJoinable() {
		return true;
	}

	@Override
	public boolean isValid( TileStage tile, AreaType area) {
		switch (area) {
			case EDGE_DOWN_NORTH:
				return tile.isValid( VALID_DN) && isValidForStrip( tile, AreaType.SIDE_DOWN, AreaType.SIDE_NORTH);
			case EDGE_DOWN_SOUTH:
				return tile.isValid( VALID_DS) && isValidForStrip( tile, AreaType.SIDE_DOWN, AreaType.SIDE_SOUTH);
			case EDGE_DOWN_WEST:
				return tile.isValid( VALID_DW) && isValidForStrip( tile, AreaType.SIDE_DOWN, AreaType.SIDE_WEST);
			case EDGE_DOWN_EAST:
				return tile.isValid( VALID_DE) && isValidForStrip( tile, AreaType.SIDE_DOWN, AreaType.SIDE_EAST);
			case EDGE_UP_NORTH:
				return tile.isValid( VALID_UN) && isValidForStrip( tile, AreaType.SIDE_UP, AreaType.SIDE_NORTH);
			case EDGE_UP_SOUTH:
				return tile.isValid( VALID_US) && isValidForStrip( tile, AreaType.SIDE_UP, AreaType.SIDE_SOUTH);
			case EDGE_UP_WEST:
				return tile.isValid( VALID_UW) && isValidForStrip( tile, AreaType.SIDE_UP, AreaType.SIDE_WEST);
			case EDGE_UP_EAST:
				return tile.isValid( VALID_UE) && isValidForStrip( tile, AreaType.SIDE_UP, AreaType.SIDE_EAST);
			case EDGE_NORTH_WEST:
				return tile.isValid( VALID_NW) && isValidForStrip( tile, AreaType.SIDE_NORTH, AreaType.SIDE_WEST);
			case EDGE_NORTH_EAST:
				return tile.isValid( VALID_NE) && isValidForStrip( tile, AreaType.SIDE_NORTH, AreaType.SIDE_EAST);
			case EDGE_SOUTH_WEST:
				return tile.isValid( VALID_SW) && isValidForStrip( tile, AreaType.SIDE_SOUTH, AreaType.SIDE_WEST);
			case EDGE_SOUTH_EAST:
				return tile.isValid( VALID_SE) && isValidForStrip( tile, AreaType.SIDE_SOUTH, AreaType.SIDE_EAST);
			case AXIS_DOWN_UP:
				return tile.isValid( VALID_DU) && isValidForAxis( tile, AreaType.SIDE_DOWN, AreaType.SIDE_UP);
			case AXIS_NORTH_SOUTH:
				return tile.isValid( VALID_NS) && isValidForAxis( tile, AreaType.SIDE_NORTH, AreaType.SIDE_SOUTH);
			case AXIS_WEST_EAST:
				return tile.isValid( VALID_WE) && isValidForAxis( tile, AreaType.SIDE_WEST, AreaType.SIDE_EAST);
			default:
				return false;
		}
	}

	@Override
	public void oppositeArea( MovingObjectPosition pos) {
		pos.subHit = getOpposite( pos.sideHit, AreaType.values()[pos.subHit]).ordinal();
	}

	@Override
	public void updateArea( MovingObjectPosition pos) {
		double dx = pos.hitVec.xCoord - pos.blockX;
		double dy = pos.hitVec.yCoord - pos.blockY;
		double dz = pos.hitVec.zCoord - pos.blockZ;
		pos.subHit = getArea( pos.sideHit, dx, dy, dz).ordinal();
	}
}
