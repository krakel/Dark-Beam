/**
 * Dark Beam
 * ASectionCover.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import net.minecraft.util.MovingObjectPosition;

import de.krakel.darkbeam.client.renderer.ASectionRenderer;
import de.krakel.darkbeam.tile.TileStage;

abstract class ASectionCover extends ASectionStructure {
	private static final int VALID_D = AreaType.toMask( AreaType.DOWN, AreaType.DOWN_NORTH, AreaType.DOWN_SOUTH, AreaType.DOWN_WEST, AreaType.DOWN_EAST, AreaType.DOWN_NORTH_WEST, AreaType.DOWN_NORTH_EAST, AreaType.DOWN_SOUTH_WEST, AreaType.DOWN_SOUTH_EAST);
	private static final int VALID_U = AreaType.toMask( AreaType.UP, AreaType.UP_NORTH, AreaType.UP_SOUTH, AreaType.UP_WEST, AreaType.UP_EAST, AreaType.UP_NORTH_WEST, AreaType.UP_NORTH_EAST, AreaType.UP_SOUTH_WEST, AreaType.UP_SOUTH_EAST);
	private static final int VALID_N = AreaType.toMask( AreaType.NORTH, AreaType.DOWN_NORTH, AreaType.UP_NORTH, AreaType.NORTH_WEST, AreaType.NORTH_EAST, AreaType.DOWN_NORTH_WEST, AreaType.DOWN_NORTH_EAST, AreaType.UP_NORTH_WEST, AreaType.UP_NORTH_EAST);
	private static final int VALID_S = AreaType.toMask( AreaType.SOUTH, AreaType.DOWN_SOUTH, AreaType.UP_SOUTH, AreaType.SOUTH_WEST, AreaType.SOUTH_EAST, AreaType.DOWN_SOUTH_WEST, AreaType.DOWN_SOUTH_EAST, AreaType.UP_SOUTH_WEST, AreaType.UP_SOUTH_EAST);
	private static final int VALID_W = AreaType.toMask( AreaType.WEST, AreaType.DOWN_WEST, AreaType.UP_WEST, AreaType.NORTH_WEST, AreaType.SOUTH_WEST, AreaType.DOWN_NORTH_WEST, AreaType.DOWN_SOUTH_WEST, AreaType.UP_NORTH_WEST, AreaType.UP_SOUTH_WEST);
	private static final int VALID_E = AreaType.toMask( AreaType.EAST, AreaType.DOWN_EAST, AreaType.UP_EAST, AreaType.NORTH_EAST, AreaType.SOUTH_EAST, AreaType.DOWN_NORTH_EAST, AreaType.DOWN_SOUTH_EAST, AreaType.UP_NORTH_EAST, AreaType.UP_SOUTH_EAST);

	ASectionCover( int secID, String name, ASectionRenderer renderer) {
		super( secID, name, renderer);
	}

	protected ASectionCover( String name, ASectionRenderer renderer) {
		super( name, renderer);
	}

	private static AreaType getArea( int side, double dx, double dy, double dz) {
		switch (side) {
			case IDirection.DIR_DOWN:
				if (BOX_BORDER_MIN < dx && dx < BOX_BORDER_MAX && BOX_BORDER_MIN < dz && dz < BOX_BORDER_MAX) {
					return AreaType.DOWN;
				}
				if (dz > dx) {
					return dz + dx > 1D ? AreaType.SOUTH : AreaType.WEST;
				}
				return dz + dx > 1D ? AreaType.EAST : AreaType.NORTH;
			case IDirection.DIR_UP:
				if (BOX_BORDER_MIN < dx && dx < BOX_BORDER_MAX && BOX_BORDER_MIN < dz && dz < BOX_BORDER_MAX) {
					return AreaType.UP;
				}
				if (dz > dx) {
					return dz + dx > 1D ? AreaType.SOUTH : AreaType.WEST;
				}
				return dz + dx > 1D ? AreaType.EAST : AreaType.NORTH;
			case IDirection.DIR_NORTH:
				if (BOX_BORDER_MIN < dx && dx < BOX_BORDER_MAX && BOX_BORDER_MIN < dy && dy < BOX_BORDER_MAX) {
					return AreaType.NORTH;
				}
				if (dy > dx) {
					return dy + dx > 1D ? AreaType.UP : AreaType.WEST;
				}
				return dy + dx > 1D ? AreaType.EAST : AreaType.DOWN;
			case IDirection.DIR_SOUTH:
				if (BOX_BORDER_MIN < dx && dx < BOX_BORDER_MAX && BOX_BORDER_MIN < dy && dy < BOX_BORDER_MAX) {
					return AreaType.SOUTH;
				}
				if (dy > dx) {
					return dy + dx > 1D ? AreaType.UP : AreaType.WEST;
				}
				return dy + dx > 1D ? AreaType.EAST : AreaType.DOWN;
			case IDirection.DIR_WEST:
				if (BOX_BORDER_MIN < dy && dy < BOX_BORDER_MAX && BOX_BORDER_MIN < dz && dz < BOX_BORDER_MAX) {
					return AreaType.WEST;
				}
				if (dy > dz) {
					return dy + dz > 1D ? AreaType.UP : AreaType.NORTH;
				}
				return dy + dz > 1D ? AreaType.SOUTH : AreaType.DOWN;
			case IDirection.DIR_EAST:
				if (BOX_BORDER_MIN < dy && dy < BOX_BORDER_MAX && BOX_BORDER_MIN < dz && dz < BOX_BORDER_MAX) {
					return AreaType.EAST;
				}
				if (dy > dz) {
					return dy + dz > 1D ? AreaType.UP : AreaType.NORTH;
				}
				return dy + dz > 1D ? AreaType.SOUTH : AreaType.DOWN;
			default:
				return AreaType.UNKNOWN;
		}
	}

	@Override
	public boolean isJoinable() {
		return false;
	}

	@Override
	public boolean isValid( TileStage tile, AreaType area) {
		switch (area) {
			case DOWN:
				return tile.isValid( VALID_D);
			case UP:
				return tile.isValid( VALID_U);
			case NORTH:
				return tile.isValid( VALID_N);
			case SOUTH:
				return tile.isValid( VALID_S);
			case WEST:
				return tile.isValid( VALID_W);
			case EAST:
				return tile.isValid( VALID_E);
			default:
				return false;
		}
	}

	@Override
	public void oppositeArea( MovingObjectPosition pos) {
		if (pos.subHit == pos.sideHit) {
			pos.subHit = pos.subHit ^= 1;
		}
		else {
			pos.subHit = pos.subHit;
		}
	}

	@Override
	public void updateArea( MovingObjectPosition pos) {
		double dx = pos.hitVec.xCoord - pos.blockX;
		double dy = pos.hitVec.yCoord - pos.blockY;
		double dz = pos.hitVec.zCoord - pos.blockZ;
		pos.subHit = getArea( pos.sideHit, dx, dy, dz).ordinal();
	}
}
