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
import de.krakel.darkbeam.tile.IConnetable;
import de.krakel.darkbeam.tile.TileStage;

public class SectionStrip extends ASectionStructure {
	private static final int VALID_DN = AreaType.toMask( AreaType.DOWN_NORTH, AreaType.DOWN_NORTH_WEST, AreaType.DOWN_NORTH_EAST);
	private static final int VALID_DS = AreaType.toMask( AreaType.DOWN_SOUTH, AreaType.DOWN_SOUTH_WEST, AreaType.DOWN_SOUTH_EAST);
	private static final int VALID_DW = AreaType.toMask( AreaType.DOWN_WEST, AreaType.DOWN_NORTH_WEST, AreaType.DOWN_SOUTH_WEST);
	private static final int VALID_DE = AreaType.toMask( AreaType.DOWN_EAST, AreaType.DOWN_NORTH_EAST, AreaType.DOWN_SOUTH_EAST);
	private static final int VALID_UN = AreaType.toMask( AreaType.UP_NORTH, AreaType.UP_NORTH_WEST, AreaType.UP_NORTH_EAST);
	private static final int VALID_US = AreaType.toMask( AreaType.UP_SOUTH, AreaType.UP_SOUTH_WEST, AreaType.UP_SOUTH_EAST);
	private static final int VALID_UW = AreaType.toMask( AreaType.UP_WEST, AreaType.UP_NORTH_WEST, AreaType.UP_SOUTH_WEST);
	private static final int VALID_UE = AreaType.toMask( AreaType.UP_EAST, AreaType.UP_NORTH_EAST, AreaType.UP_SOUTH_EAST);
	private static final int VALID_NW = AreaType.toMask( AreaType.NORTH_WEST, AreaType.DOWN_NORTH_WEST, AreaType.UP_NORTH_WEST);
	private static final int VALID_NE = AreaType.toMask( AreaType.NORTH_EAST, AreaType.DOWN_NORTH_EAST, AreaType.UP_NORTH_EAST);
	private static final int VALID_SW = AreaType.toMask( AreaType.SOUTH_WEST, AreaType.DOWN_SOUTH_WEST, AreaType.UP_SOUTH_WEST);
	private static final int VALID_SE = AreaType.toMask( AreaType.SOUTH_EAST, AreaType.DOWN_SOUTH_EAST, AreaType.UP_SOUTH_EAST);
	private static final int VALID_DU = AreaType.toMask( AreaType.DOWN_UP, AreaType.NORTH_SOUTH, AreaType.WEST_EAST, AreaType.CENTER);
	private static final int VALID_NS = AreaType.toMask( AreaType.DOWN_UP, AreaType.NORTH_SOUTH, AreaType.WEST_EAST, AreaType.CENTER);
	private static final int VALID_WE = AreaType.toMask( AreaType.DOWN_UP, AreaType.NORTH_SOUTH, AreaType.WEST_EAST, AreaType.CENTER);

	SectionStrip( int nr) {
		super( "strip." + nr, new SectionStripRenderer( nr));
	}

	public static AreaType getArea( int side, double dx, double dy, double dz) {
		switch (side) {
			case IDirection.DIR_DOWN:
				if (dx < BOX_BORDER_MIN) {
					if (dz < BOX_BORDER_MIN) {
						return AreaType.NORTH_WEST;
					}
					if (dz >= BOX_BORDER_MAX) {
						return AreaType.SOUTH_WEST;
					}
					return AreaType.DOWN_WEST;
				}
				if (dx >= BOX_BORDER_MAX) {
					if (dz < BOX_BORDER_MIN) {
						return AreaType.NORTH_EAST;
					}
					if (dz >= BOX_BORDER_MAX) {
						return AreaType.SOUTH_EAST;
					}
					return AreaType.DOWN_EAST;
				}
				if (dz < BOX_BORDER_MIN) {
					return AreaType.DOWN_NORTH;
				}
				if (dz >= BOX_BORDER_MAX) {
					return AreaType.DOWN_SOUTH;
				}
				return AreaType.DOWN_UP;
			case IDirection.DIR_UP:
				if (dx < BOX_BORDER_MIN) {
					if (dz < BOX_BORDER_MIN) {
						return AreaType.NORTH_WEST;
					}
					if (dz >= BOX_BORDER_MAX) {
						return AreaType.SOUTH_WEST;
					}
					return AreaType.UP_WEST;
				}
				if (dx >= BOX_BORDER_MAX) {
					if (dz < BOX_BORDER_MIN) {
						return AreaType.NORTH_EAST;
					}
					if (dz >= BOX_BORDER_MAX) {
						return AreaType.SOUTH_EAST;
					}
					return AreaType.UP_EAST;
				}
				if (dz < BOX_BORDER_MIN) {
					return AreaType.UP_NORTH;
				}
				if (dz >= BOX_BORDER_MAX) {
					return AreaType.UP_SOUTH;
				}
				return AreaType.DOWN_UP;
			case IDirection.DIR_NORTH:
				if (dx < BOX_BORDER_MIN) {
					if (dy < BOX_BORDER_MIN) {
						return AreaType.DOWN_WEST;
					}
					if (dy >= BOX_BORDER_MAX) {
						return AreaType.UP_WEST;
					}
					return AreaType.NORTH_WEST;
				}
				if (dx >= BOX_BORDER_MAX) {
					if (dy < BOX_BORDER_MIN) {
						return AreaType.DOWN_EAST;
					}
					if (dy >= BOX_BORDER_MAX) {
						return AreaType.UP_EAST;
					}
					return AreaType.NORTH_EAST;
				}
				if (dy < BOX_BORDER_MIN) {
					return AreaType.DOWN_NORTH;
				}
				if (dy >= BOX_BORDER_MAX) {
					return AreaType.UP_NORTH;
				}
				return AreaType.NORTH_SOUTH;
			case IDirection.DIR_SOUTH:
				if (dx < BOX_BORDER_MIN) {
					if (dy < BOX_BORDER_MIN) {
						return AreaType.DOWN_WEST;
					}
					if (dy >= BOX_BORDER_MAX) {
						return AreaType.UP_WEST;
					}
					return AreaType.SOUTH_WEST;
				}
				if (dx >= BOX_BORDER_MAX) {
					if (dy < BOX_BORDER_MIN) {
						return AreaType.DOWN_EAST;
					}
					if (dy >= BOX_BORDER_MAX) {
						return AreaType.UP_EAST;
					}
					return AreaType.SOUTH_EAST;
				}
				if (dy < BOX_BORDER_MIN) {
					return AreaType.DOWN_SOUTH;
				}
				if (dy >= BOX_BORDER_MAX) {
					return AreaType.UP_SOUTH;
				}
				return AreaType.NORTH_SOUTH;
			case IDirection.DIR_WEST:
				if (dy < BOX_BORDER_MIN) {
					if (dz < BOX_BORDER_MIN) {
						return AreaType.DOWN_NORTH;
					}
					if (dz >= BOX_BORDER_MAX) {
						return AreaType.DOWN_SOUTH;
					}
					return AreaType.DOWN_WEST;
				}
				if (dy >= BOX_BORDER_MAX) {
					if (dz < BOX_BORDER_MIN) {
						return AreaType.UP_NORTH;
					}
					if (dz >= BOX_BORDER_MAX) {
						return AreaType.UP_SOUTH;
					}
					return AreaType.UP_WEST;
				}
				if (dz < BOX_BORDER_MIN) {
					return AreaType.NORTH_WEST;
				}
				if (dz >= BOX_BORDER_MAX) {
					return AreaType.SOUTH_WEST;
				}
				return AreaType.WEST_EAST;
			case IDirection.DIR_EAST:
				if (dy < BOX_BORDER_MIN) {
					if (dz < BOX_BORDER_MIN) {
						return AreaType.DOWN_NORTH;
					}
					if (dz >= BOX_BORDER_MAX) {
						return AreaType.DOWN_SOUTH;
					}
					return AreaType.DOWN_EAST;
				}
				if (dy >= BOX_BORDER_MAX) {
					if (dz < BOX_BORDER_MIN) {
						return AreaType.UP_NORTH;
					}
					if (dz >= BOX_BORDER_MAX) {
						return AreaType.UP_SOUTH;
					}
					return AreaType.UP_EAST;
				}
				if (dz < BOX_BORDER_MIN) {
					return AreaType.NORTH_EAST;
				}
				if (dz >= BOX_BORDER_MAX) {
					return AreaType.SOUTH_EAST;
				}
				return AreaType.WEST_EAST;
			default:
				return AreaType.UNKNOWN;
		}
	}

	private static AreaType getOpposite( int side, AreaType area) {
		switch (side) {
			case IDirection.DIR_DOWN:
				switch (area) {
					case DOWN_NORTH:
						return AreaType.UP_NORTH;
					case DOWN_SOUTH:
						return AreaType.UP_SOUTH;
					case DOWN_WEST:
						return AreaType.UP_WEST;
					case DOWN_EAST:
						return AreaType.UP_EAST;
					default:
						return area;
				}
			case IDirection.DIR_UP:
				switch (area) {
					case UP_NORTH:
						return AreaType.DOWN_NORTH;
					case UP_SOUTH:
						return AreaType.DOWN_SOUTH;
					case UP_WEST:
						return AreaType.DOWN_WEST;
					case UP_EAST:
						return AreaType.DOWN_EAST;
					default:
						return area;
				}
			case IDirection.DIR_NORTH:
				switch (area) {
					case DOWN_NORTH:
						return AreaType.DOWN_SOUTH;
					case UP_NORTH:
						return AreaType.UP_SOUTH;
					case NORTH_WEST:
						return AreaType.SOUTH_WEST;
					case NORTH_EAST:
						return AreaType.SOUTH_EAST;
					default:
						return area;
				}
			case IDirection.DIR_SOUTH:
				switch (area) {
					case DOWN_SOUTH:
						return AreaType.DOWN_NORTH;
					case UP_SOUTH:
						return AreaType.UP_NORTH;
					case SOUTH_WEST:
						return AreaType.NORTH_WEST;
					case SOUTH_EAST:
						return AreaType.NORTH_EAST;
					default:
						return area;
				}
			case IDirection.DIR_WEST:
				switch (area) {
					case DOWN_WEST:
						return AreaType.DOWN_EAST;
					case UP_WEST:
						return AreaType.UP_EAST;
					case NORTH_WEST:
						return AreaType.NORTH_EAST;
					case SOUTH_WEST:
						return AreaType.SOUTH_EAST;
					default:
						return area;
				}
			case IDirection.DIR_EAST:
				switch (area) {
					case DOWN_EAST:
						return AreaType.DOWN_WEST;
					case UP_EAST:
						return AreaType.UP_WEST;
					case NORTH_EAST:
						return AreaType.NORTH_WEST;
					case SOUTH_EAST:
						return AreaType.SOUTH_WEST;
					default:
						return area;
				}
			default:
				return area;
		}
	}

	private static boolean isValidForAxis( TileStage tile, AreaType sideA, AreaType sideB) {
		IConnetable connet = tile.getConnet();
		return !connet.isWired( sideA) && !connet.isWired( sideB);
	}

	private static boolean isValidForStrip( TileStage tile, AreaType sideA, AreaType sideB) {
		IConnetable connet = tile.getConnet();
		return (!tile.isUsed( sideA) || connet.isWired( sideA)) && (!tile.isUsed( sideB) || connet.isWired( sideB));
	}

	@Override
	public boolean isJoinable() {
		return true;
	}

	@Override
	public boolean isValid( TileStage tile, AreaType area) {
		switch (area) {
			case DOWN_NORTH:
				return tile.isValid( VALID_DN) && isValidForStrip( tile, AreaType.DOWN, AreaType.NORTH);
			case DOWN_SOUTH:
				return tile.isValid( VALID_DS) && isValidForStrip( tile, AreaType.DOWN, AreaType.SOUTH);
			case DOWN_WEST:
				return tile.isValid( VALID_DW) && isValidForStrip( tile, AreaType.DOWN, AreaType.WEST);
			case DOWN_EAST:
				return tile.isValid( VALID_DE) && isValidForStrip( tile, AreaType.DOWN, AreaType.EAST);
			case UP_NORTH:
				return tile.isValid( VALID_UN) && isValidForStrip( tile, AreaType.UP, AreaType.NORTH);
			case UP_SOUTH:
				return tile.isValid( VALID_US) && isValidForStrip( tile, AreaType.UP, AreaType.SOUTH);
			case UP_WEST:
				return tile.isValid( VALID_UW) && isValidForStrip( tile, AreaType.UP, AreaType.WEST);
			case UP_EAST:
				return tile.isValid( VALID_UE) && isValidForStrip( tile, AreaType.UP, AreaType.EAST);
			case NORTH_WEST:
				return tile.isValid( VALID_NW) && isValidForStrip( tile, AreaType.NORTH, AreaType.WEST);
			case NORTH_EAST:
				return tile.isValid( VALID_NE) && isValidForStrip( tile, AreaType.NORTH, AreaType.EAST);
			case SOUTH_WEST:
				return tile.isValid( VALID_SW) && isValidForStrip( tile, AreaType.SOUTH, AreaType.WEST);
			case SOUTH_EAST:
				return tile.isValid( VALID_SE) && isValidForStrip( tile, AreaType.SOUTH, AreaType.EAST);
			case DOWN_UP:
				return tile.isValid( VALID_DU) && isValidForAxis( tile, AreaType.DOWN, AreaType.UP);
			case NORTH_SOUTH:
				return tile.isValid( VALID_NS) && isValidForAxis( tile, AreaType.NORTH, AreaType.SOUTH);
			case WEST_EAST:
				return tile.isValid( VALID_WE) && isValidForAxis( tile, AreaType.WEST, AreaType.EAST);
			default:
				return false;
		}
	}

	@Override
	public void oppositeArea( MovingObjectPosition pos) {
		pos.subHit = getOpposite( pos.sideHit, AreaType.toArea( pos.subHit)).ordinal();
	}

	@Override
	public void updateArea( MovingObjectPosition pos) {
		double dx = pos.hitVec.xCoord - pos.blockX;
		double dy = pos.hitVec.yCoord - pos.blockY;
		double dz = pos.hitVec.zCoord - pos.blockZ;
		pos.subHit = getArea( pos.sideHit, dx, dy, dz).ordinal();
	}
}
