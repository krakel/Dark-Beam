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
	private static final int VALID_DNW = AreaType.toMask( AreaType.DOWN, AreaType.NORTH, AreaType.WEST, AreaType.DOWN_NORTH, AreaType.DOWN_WEST, AreaType.NORTH_WEST, AreaType.DOWN_NORTH_WEST);
	private static final int VALID_UNW = AreaType.toMask( AreaType.UP, AreaType.NORTH, AreaType.WEST, AreaType.UP_NORTH, AreaType.UP_WEST, AreaType.NORTH_WEST, AreaType.UP_NORTH_WEST);
	private static final int VALID_DSW = AreaType.toMask( AreaType.DOWN, AreaType.SOUTH, AreaType.WEST, AreaType.DOWN_SOUTH, AreaType.DOWN_WEST, AreaType.SOUTH_WEST, AreaType.DOWN_SOUTH_WEST);
	private static final int VALID_USW = AreaType.toMask( AreaType.UP, AreaType.SOUTH, AreaType.WEST, AreaType.UP_SOUTH, AreaType.UP_WEST, AreaType.DOWN_WEST, AreaType.UP_SOUTH_WEST);
	private static final int VALID_DNE = AreaType.toMask( AreaType.DOWN, AreaType.NORTH, AreaType.EAST, AreaType.DOWN_NORTH, AreaType.DOWN_EAST, AreaType.NORTH_EAST, AreaType.DOWN_NORTH_EAST);
	private static final int VALID_UNE = AreaType.toMask( AreaType.UP, AreaType.NORTH, AreaType.EAST, AreaType.UP_NORTH, AreaType.UP_EAST, AreaType.NORTH_EAST, AreaType.UP_NORTH_EAST);
	private static final int VALID_DSE = AreaType.toMask( AreaType.DOWN, AreaType.SOUTH, AreaType.EAST, AreaType.DOWN_SOUTH, AreaType.DOWN_EAST, AreaType.SOUTH_EAST, AreaType.DOWN_SOUTH_EAST);
	private static final int VALID_USE = AreaType.toMask( AreaType.UP, AreaType.SOUTH, AreaType.EAST, AreaType.UP_SOUTH, AreaType.UP_EAST, AreaType.SOUTH_EAST, AreaType.UP_SOUTH_EAST);

	public SectionCorner( int nr) {
		super( "corner." + nr, new SectionCornerRenderer( nr));
	}

	private static AreaType getArea( int side, double dx, double dy, double dz) {
		switch (side) {
			case IDirection.DIR_DOWN:
				if (dx < 0.5D) {
					return dz < 0.5D ? AreaType.DOWN_NORTH_WEST : AreaType.DOWN_SOUTH_WEST;
				}
				return dz < 0.5D ? AreaType.DOWN_NORTH_EAST : AreaType.DOWN_SOUTH_EAST;
			case IDirection.DIR_UP:
				if (dx < 0.5D) {
					return dz < 0.5D ? AreaType.UP_NORTH_WEST : AreaType.UP_SOUTH_WEST;
				}
				return dz < 0.5D ? AreaType.UP_NORTH_EAST : AreaType.UP_SOUTH_EAST;
			case IDirection.DIR_NORTH:
				if (dx < 0.5D) {
					return dy < 0.5D ? AreaType.DOWN_NORTH_WEST : AreaType.UP_NORTH_WEST;
				}
				return dy < 0.5D ? AreaType.DOWN_NORTH_EAST : AreaType.UP_NORTH_EAST;
			case IDirection.DIR_SOUTH:
				if (dx < 0.5D) {
					return dy < 0.5D ? AreaType.DOWN_SOUTH_WEST : AreaType.UP_SOUTH_WEST;
				}
				return dy < 0.5D ? AreaType.DOWN_SOUTH_EAST : AreaType.UP_SOUTH_EAST;
			case IDirection.DIR_WEST:
				if (dy < 0.5D) {
					return dz < 0.5D ? AreaType.DOWN_NORTH_WEST : AreaType.DOWN_SOUTH_WEST;
				}
				return dz < 0.5D ? AreaType.UP_NORTH_WEST : AreaType.UP_SOUTH_WEST;
			case IDirection.DIR_EAST:
				if (dy < 0.5D) {
					return dz < 0.5D ? AreaType.DOWN_NORTH_EAST : AreaType.DOWN_SOUTH_EAST;
				}
				return dz < 0.5D ? AreaType.UP_NORTH_EAST : AreaType.UP_SOUTH_EAST;
			default:
				return AreaType.UNKNOWN;
		}
	}

	private static AreaType getOpposite( int side, AreaType area) {
		switch (side) {
			case IDirection.DIR_DOWN:
				switch (area) {
					case DOWN_NORTH_WEST:
						return AreaType.UP_NORTH_WEST;
					case DOWN_SOUTH_WEST:
						return AreaType.UP_SOUTH_WEST;
					case DOWN_NORTH_EAST:
						return AreaType.UP_NORTH_EAST;
					case DOWN_SOUTH_EAST:
						return AreaType.UP_SOUTH_EAST;
					default:
						return area;
				}
			case IDirection.DIR_UP:
				switch (area) {
					case UP_NORTH_WEST:
						return AreaType.DOWN_NORTH_WEST;
					case UP_SOUTH_WEST:
						return AreaType.DOWN_SOUTH_WEST;
					case UP_NORTH_EAST:
						return AreaType.DOWN_NORTH_EAST;
					case UP_SOUTH_EAST:
						return AreaType.DOWN_SOUTH_EAST;
					default:
						return area;
				}
			case IDirection.DIR_NORTH:
				switch (area) {
					case DOWN_NORTH_WEST:
						return AreaType.DOWN_SOUTH_WEST;
					case DOWN_NORTH_EAST:
						return AreaType.DOWN_SOUTH_EAST;
					case UP_NORTH_WEST:
						return AreaType.UP_SOUTH_WEST;
					case UP_NORTH_EAST:
						return AreaType.UP_SOUTH_EAST;
					default:
						return area;
				}
			case IDirection.DIR_SOUTH:
				switch (area) {
					case DOWN_SOUTH_WEST:
						return AreaType.DOWN_NORTH_WEST;
					case DOWN_SOUTH_EAST:
						return AreaType.DOWN_NORTH_EAST;
					case UP_SOUTH_WEST:
						return AreaType.UP_NORTH_WEST;
					case UP_SOUTH_EAST:
						return AreaType.UP_NORTH_EAST;
					default:
						return area;
				}
			case IDirection.DIR_WEST:
				switch (area) {
					case DOWN_NORTH_WEST:
						return AreaType.DOWN_NORTH_EAST;
					case DOWN_SOUTH_WEST:
						return AreaType.DOWN_SOUTH_EAST;
					case UP_NORTH_WEST:
						return AreaType.UP_NORTH_EAST;
					case UP_SOUTH_WEST:
						return AreaType.UP_SOUTH_EAST;
					default:
						return area;
				}
			case IDirection.DIR_EAST:
				switch (area) {
					case DOWN_NORTH_EAST:
						return AreaType.DOWN_NORTH_WEST;
					case DOWN_SOUTH_EAST:
						return AreaType.DOWN_SOUTH_WEST;
					case UP_NORTH_EAST:
						return AreaType.UP_NORTH_WEST;
					case UP_SOUTH_EAST:
						return AreaType.UP_SOUTH_WEST;
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
			case DOWN_NORTH_WEST:
				return tile.isValid( VALID_DNW);
			case UP_NORTH_WEST:
				return tile.isValid( VALID_UNW);
			case DOWN_SOUTH_WEST:
				return tile.isValid( VALID_DSW);
			case UP_SOUTH_WEST:
				return tile.isValid( VALID_USW);
			case DOWN_NORTH_EAST:
				return tile.isValid( VALID_DNE);
			case UP_NORTH_EAST:
				return tile.isValid( VALID_UNE);
			case DOWN_SOUTH_EAST:
				return tile.isValid( VALID_DSE);
			case UP_SOUTH_EAST:
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
