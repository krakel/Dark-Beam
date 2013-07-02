/**
 * Dark Beam
 * SectionCorner.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import net.minecraft.util.MovingObjectPosition;

import de.krakel.darkbeam.client.renderer.SectionCornerRenderer;
import de.krakel.darkbeam.tile.TileStage;

class SectionCorner extends ASectionStructure {
	private static final int VALID_DNW = AreaType.toMask( AreaType.SIDE_DOWN, AreaType.SIDE_NORTH, AreaType.SIDE_WEST, AreaType.EDGE_DOWN_NORTH, AreaType.EDGE_DOWN_WEST, AreaType.EDGE_NORTH_WEST, AreaType.CORNER_DOWN_NORTH_WEST);
	private static final int VALID_UNW = AreaType.toMask( AreaType.SIDE_UP, AreaType.SIDE_NORTH, AreaType.SIDE_WEST, AreaType.EDGE_UP_NORTH, AreaType.EDGE_UP_WEST, AreaType.EDGE_NORTH_WEST, AreaType.CORNER_UP_NORTH_WEST);
	private static final int VALID_DSW = AreaType.toMask( AreaType.SIDE_DOWN, AreaType.SIDE_SOUTH, AreaType.SIDE_WEST, AreaType.EDGE_DOWN_SOUTH, AreaType.EDGE_DOWN_WEST, AreaType.EDGE_SOUTH_WEST, AreaType.CORNER_DOWN_SOUTH_WEST);
	private static final int VALID_USW = AreaType.toMask( AreaType.SIDE_UP, AreaType.SIDE_SOUTH, AreaType.SIDE_WEST, AreaType.EDGE_UP_SOUTH, AreaType.EDGE_UP_WEST, AreaType.EDGE_DOWN_WEST, AreaType.CORNER_UP_SOUTH_WEST);
	private static final int VALID_DNE = AreaType.toMask( AreaType.SIDE_DOWN, AreaType.SIDE_NORTH, AreaType.SIDE_EAST, AreaType.EDGE_DOWN_NORTH, AreaType.EDGE_DOWN_EAST, AreaType.EDGE_NORTH_EAST, AreaType.CORNER_DOWN_NORTH_EAST);
	private static final int VALID_UNE = AreaType.toMask( AreaType.SIDE_UP, AreaType.SIDE_NORTH, AreaType.SIDE_EAST, AreaType.EDGE_UP_NORTH, AreaType.EDGE_UP_EAST, AreaType.EDGE_NORTH_EAST, AreaType.CORNER_UP_NORTH_EAST);
	private static final int VALID_DSE = AreaType.toMask( AreaType.SIDE_DOWN, AreaType.SIDE_SOUTH, AreaType.SIDE_EAST, AreaType.EDGE_DOWN_SOUTH, AreaType.EDGE_DOWN_EAST, AreaType.EDGE_SOUTH_EAST, AreaType.CORNER_DOWN_SOUTH_EAST);
	private static final int VALID_USE = AreaType.toMask( AreaType.SIDE_UP, AreaType.SIDE_SOUTH, AreaType.SIDE_EAST, AreaType.EDGE_UP_SOUTH, AreaType.EDGE_UP_EAST, AreaType.EDGE_SOUTH_EAST, AreaType.CORNER_UP_SOUTH_EAST);

	public SectionCorner( int nr) {
		super( "corner." + nr, new SectionCornerRenderer( nr));
	}

	private static AreaType getArea( int side, double dx, double dy, double dz) {
		switch (side) {
			case DIR_DOWN:
				if (dx < 0.5D) {
					return dz < 0.5D ? AreaType.CORNER_DOWN_NORTH_WEST : AreaType.CORNER_DOWN_SOUTH_WEST;
				}
				return dz < 0.5D ? AreaType.CORNER_DOWN_NORTH_EAST : AreaType.CORNER_DOWN_SOUTH_EAST;
			case DIR_UP:
				if (dx < 0.5D) {
					return dz < 0.5D ? AreaType.CORNER_UP_NORTH_WEST : AreaType.CORNER_UP_SOUTH_WEST;
				}
				return dz < 0.5D ? AreaType.CORNER_UP_NORTH_EAST : AreaType.CORNER_UP_SOUTH_EAST;
			case DIR_NORTH:
				if (dx < 0.5D) {
					return dy < 0.5D ? AreaType.CORNER_DOWN_NORTH_WEST : AreaType.CORNER_UP_NORTH_WEST;
				}
				return dy < 0.5D ? AreaType.CORNER_DOWN_NORTH_EAST : AreaType.CORNER_UP_NORTH_EAST;
			case DIR_SOUTH:
				if (dx < 0.5D) {
					return dy < 0.5D ? AreaType.CORNER_DOWN_SOUTH_WEST : AreaType.CORNER_UP_SOUTH_WEST;
				}
				return dy < 0.5D ? AreaType.CORNER_DOWN_SOUTH_EAST : AreaType.CORNER_UP_SOUTH_EAST;
			case DIR_WEST:
				if (dy < 0.5D) {
					return dz < 0.5D ? AreaType.CORNER_DOWN_NORTH_WEST : AreaType.CORNER_DOWN_SOUTH_WEST;
				}
				return dz < 0.5D ? AreaType.CORNER_UP_NORTH_WEST : AreaType.CORNER_UP_SOUTH_WEST;
			case DIR_EAST:
				if (dy < 0.5D) {
					return dz < 0.5D ? AreaType.CORNER_DOWN_NORTH_EAST : AreaType.CORNER_DOWN_SOUTH_EAST;
				}
				return dz < 0.5D ? AreaType.CORNER_UP_NORTH_EAST : AreaType.CORNER_UP_SOUTH_EAST;
			default:
				return AreaType.SIDE_DOWN;
		}
	}

	private static AreaType getOpposite( int side, AreaType area) {
		switch (side) {
			case DIR_DOWN:
				switch (area) {
					case CORNER_DOWN_NORTH_WEST:
						return AreaType.CORNER_UP_NORTH_WEST;
					case CORNER_DOWN_SOUTH_WEST:
						return AreaType.CORNER_UP_SOUTH_WEST;
					case CORNER_DOWN_NORTH_EAST:
						return AreaType.CORNER_UP_NORTH_EAST;
					case CORNER_DOWN_SOUTH_EAST:
						return AreaType.CORNER_UP_SOUTH_EAST;
					default:
						return area;
				}
			case DIR_UP:
				switch (area) {
					case CORNER_UP_NORTH_WEST:
						return AreaType.CORNER_DOWN_NORTH_WEST;
					case CORNER_UP_SOUTH_WEST:
						return AreaType.CORNER_DOWN_SOUTH_WEST;
					case CORNER_UP_NORTH_EAST:
						return AreaType.CORNER_DOWN_NORTH_EAST;
					case CORNER_UP_SOUTH_EAST:
						return AreaType.CORNER_DOWN_SOUTH_EAST;
					default:
						return area;
				}
			case DIR_NORTH:
				switch (area) {
					case CORNER_DOWN_NORTH_WEST:
						return AreaType.CORNER_DOWN_SOUTH_WEST;
					case CORNER_DOWN_NORTH_EAST:
						return AreaType.CORNER_DOWN_SOUTH_EAST;
					case CORNER_UP_NORTH_WEST:
						return AreaType.CORNER_UP_SOUTH_WEST;
					case CORNER_UP_NORTH_EAST:
						return AreaType.CORNER_UP_SOUTH_EAST;
					default:
						return area;
				}
			case DIR_SOUTH:
				switch (area) {
					case CORNER_DOWN_SOUTH_WEST:
						return AreaType.CORNER_DOWN_NORTH_WEST;
					case CORNER_DOWN_SOUTH_EAST:
						return AreaType.CORNER_DOWN_NORTH_EAST;
					case CORNER_UP_SOUTH_WEST:
						return AreaType.CORNER_UP_NORTH_WEST;
					case CORNER_UP_SOUTH_EAST:
						return AreaType.CORNER_UP_NORTH_EAST;
					default:
						return area;
				}
			case DIR_WEST:
				switch (area) {
					case CORNER_DOWN_NORTH_WEST:
						return AreaType.CORNER_DOWN_NORTH_EAST;
					case CORNER_DOWN_SOUTH_WEST:
						return AreaType.CORNER_DOWN_SOUTH_EAST;
					case CORNER_UP_NORTH_WEST:
						return AreaType.CORNER_UP_NORTH_EAST;
					case CORNER_UP_SOUTH_WEST:
						return AreaType.CORNER_UP_SOUTH_EAST;
					default:
						return area;
				}
			case DIR_EAST:
				switch (area) {
					case CORNER_DOWN_NORTH_EAST:
						return AreaType.CORNER_DOWN_NORTH_WEST;
					case CORNER_DOWN_SOUTH_EAST:
						return AreaType.CORNER_DOWN_SOUTH_WEST;
					case CORNER_UP_NORTH_EAST:
						return AreaType.CORNER_UP_NORTH_WEST;
					case CORNER_UP_SOUTH_EAST:
						return AreaType.CORNER_UP_SOUTH_WEST;
					default:
						return area;
				}
			default:
				return area;
		}
	}

	@Override
	public boolean isJoinable() {
		return false;
	}

	@Override
	public boolean isValid( TileStage tile, AreaType area) {
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
