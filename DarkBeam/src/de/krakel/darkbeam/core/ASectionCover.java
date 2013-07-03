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
	private static final int VALID_D = AreaType.toMask( AreaType.SIDE_DOWN, AreaType.EDGE_DOWN_NORTH, AreaType.EDGE_DOWN_SOUTH, AreaType.EDGE_DOWN_WEST, AreaType.EDGE_DOWN_EAST, AreaType.CORNER_DOWN_NORTH_WEST, AreaType.CORNER_DOWN_NORTH_EAST, AreaType.CORNER_DOWN_SOUTH_WEST, AreaType.CORNER_DOWN_SOUTH_EAST);
	private static final int VALID_U = AreaType.toMask( AreaType.SIDE_UP, AreaType.EDGE_UP_NORTH, AreaType.EDGE_UP_SOUTH, AreaType.EDGE_UP_WEST, AreaType.EDGE_UP_EAST, AreaType.CORNER_UP_NORTH_WEST, AreaType.CORNER_UP_NORTH_EAST, AreaType.CORNER_UP_SOUTH_WEST, AreaType.CORNER_UP_SOUTH_EAST);
	private static final int VALID_N = AreaType.toMask( AreaType.SIDE_NORTH, AreaType.EDGE_DOWN_NORTH, AreaType.EDGE_UP_NORTH, AreaType.EDGE_NORTH_WEST, AreaType.EDGE_NORTH_EAST, AreaType.CORNER_DOWN_NORTH_WEST, AreaType.CORNER_DOWN_NORTH_EAST, AreaType.CORNER_UP_NORTH_WEST, AreaType.CORNER_UP_NORTH_EAST);
	private static final int VALID_S = AreaType.toMask( AreaType.SIDE_SOUTH, AreaType.EDGE_DOWN_SOUTH, AreaType.EDGE_UP_SOUTH, AreaType.EDGE_SOUTH_WEST, AreaType.EDGE_SOUTH_EAST, AreaType.CORNER_DOWN_SOUTH_WEST, AreaType.CORNER_DOWN_SOUTH_EAST, AreaType.CORNER_UP_SOUTH_WEST, AreaType.CORNER_UP_SOUTH_EAST);
	private static final int VALID_W = AreaType.toMask( AreaType.SIDE_WEST, AreaType.EDGE_DOWN_WEST, AreaType.EDGE_UP_WEST, AreaType.EDGE_NORTH_WEST, AreaType.EDGE_SOUTH_WEST, AreaType.CORNER_DOWN_NORTH_WEST, AreaType.CORNER_DOWN_SOUTH_WEST, AreaType.CORNER_UP_NORTH_WEST, AreaType.CORNER_UP_SOUTH_WEST);
	private static final int VALID_E = AreaType.toMask( AreaType.SIDE_EAST, AreaType.EDGE_DOWN_EAST, AreaType.EDGE_UP_EAST, AreaType.EDGE_NORTH_EAST, AreaType.EDGE_SOUTH_EAST, AreaType.CORNER_DOWN_NORTH_EAST, AreaType.CORNER_DOWN_SOUTH_EAST, AreaType.CORNER_UP_NORTH_EAST, AreaType.CORNER_UP_SOUTH_EAST);

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
					return AreaType.SIDE_DOWN;
				}
				if (dz > dx) {
					return dz + dx > 1D ? AreaType.SIDE_SOUTH : AreaType.SIDE_WEST;
				}
				return dz + dx > 1D ? AreaType.SIDE_EAST : AreaType.SIDE_NORTH;
			case IDirection.DIR_UP:
				if (BOX_BORDER_MIN < dx && dx < BOX_BORDER_MAX && BOX_BORDER_MIN < dz && dz < BOX_BORDER_MAX) {
					return AreaType.SIDE_UP;
				}
				if (dz > dx) {
					return dz + dx > 1D ? AreaType.SIDE_SOUTH : AreaType.SIDE_WEST;
				}
				return dz + dx > 1D ? AreaType.SIDE_EAST : AreaType.SIDE_NORTH;
			case IDirection.DIR_NORTH:
				if (BOX_BORDER_MIN < dx && dx < BOX_BORDER_MAX && BOX_BORDER_MIN < dy && dy < BOX_BORDER_MAX) {
					return AreaType.SIDE_NORTH;
				}
				if (dy > dx) {
					return dy + dx > 1D ? AreaType.SIDE_UP : AreaType.SIDE_WEST;
				}
				return dy + dx > 1D ? AreaType.SIDE_EAST : AreaType.SIDE_DOWN;
			case IDirection.DIR_SOUTH:
				if (BOX_BORDER_MIN < dx && dx < BOX_BORDER_MAX && BOX_BORDER_MIN < dy && dy < BOX_BORDER_MAX) {
					return AreaType.SIDE_SOUTH;
				}
				if (dy > dx) {
					return dy + dx > 1D ? AreaType.SIDE_UP : AreaType.SIDE_WEST;
				}
				return dy + dx > 1D ? AreaType.SIDE_EAST : AreaType.SIDE_DOWN;
			case IDirection.DIR_WEST:
				if (BOX_BORDER_MIN < dy && dy < BOX_BORDER_MAX && BOX_BORDER_MIN < dz && dz < BOX_BORDER_MAX) {
					return AreaType.SIDE_WEST;
				}
				if (dy > dz) {
					return dy + dz > 1D ? AreaType.SIDE_UP : AreaType.SIDE_NORTH;
				}
				return dy + dz > 1D ? AreaType.SIDE_SOUTH : AreaType.SIDE_DOWN;
			case IDirection.DIR_EAST:
				if (BOX_BORDER_MIN < dy && dy < BOX_BORDER_MAX && BOX_BORDER_MIN < dz && dz < BOX_BORDER_MAX) {
					return AreaType.SIDE_EAST;
				}
				if (dy > dz) {
					return dy + dz > 1D ? AreaType.SIDE_UP : AreaType.SIDE_NORTH;
				}
				return dy + dz > 1D ? AreaType.SIDE_SOUTH : AreaType.SIDE_DOWN;
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
