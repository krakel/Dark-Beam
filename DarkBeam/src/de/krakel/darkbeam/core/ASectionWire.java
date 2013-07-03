/**
 * Dark Beam
 * ASectionWire.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import net.minecraft.util.MovingObjectPosition;

import de.krakel.darkbeam.client.renderer.ASectionRenderer;
import de.krakel.darkbeam.tile.TileStage;

abstract class ASectionWire extends ASection {
	private static final int VALID_D = AreaType.toMask( AreaType.SIDE_DOWN, AreaType.AXIS_DOWN_UP);
	private static final int VALID_U = AreaType.toMask( AreaType.SIDE_UP, AreaType.AXIS_DOWN_UP);
	private static final int VALID_N = AreaType.toMask( AreaType.SIDE_NORTH, AreaType.AXIS_NORTH_SOUTH);
	private static final int VALID_S = AreaType.toMask( AreaType.SIDE_SOUTH, AreaType.AXIS_NORTH_SOUTH);
	private static final int VALID_W = AreaType.toMask( AreaType.SIDE_WEST, AreaType.AXIS_WEST_EAST);
	private static final int VALID_E = AreaType.toMask( AreaType.SIDE_EAST, AreaType.AXIS_WEST_EAST);

	protected ASectionWire( String name, ASectionRenderer renderer) {
		super( name, renderer);
	}

	private static AreaType getArea( int side, double dx, double dy, double dz) {
		switch (side) {
			case IDirection.DIR_DOWN:
				return AreaType.SIDE_DOWN;
			case IDirection.DIR_UP:
				return AreaType.SIDE_UP;
			case IDirection.DIR_NORTH:
				return AreaType.SIDE_NORTH;
			case IDirection.DIR_SOUTH:
				return AreaType.SIDE_SOUTH;
			case IDirection.DIR_WEST:
				return AreaType.SIDE_WEST;
			case IDirection.DIR_EAST:
				return AreaType.SIDE_EAST;
			default:
				return AreaType.SIDE_DOWN;
		}
	}

	@Override
	public boolean isJoinable() {
		return true;
	}

	@Override
	public boolean isStructure() {
		return false;
	}

	@Override
	public boolean isValid( TileStage tile, AreaType area) {
		switch (area) {
			case SIDE_DOWN:
				return tile.isValid( VALID_D)
					&& (!tile.isWired() || tile.isWire( AreaType.SIDE_NORTH) || tile.isWire( AreaType.SIDE_SOUTH)
						|| tile.isWire( AreaType.SIDE_WEST) || tile.isWire( AreaType.SIDE_EAST));
			case SIDE_UP:
				return tile.isValid( VALID_U)
					&& (!tile.isWired() || tile.isWire( AreaType.SIDE_NORTH) || tile.isWire( AreaType.SIDE_SOUTH)
						|| tile.isWire( AreaType.SIDE_WEST) || tile.isWire( AreaType.SIDE_EAST));
			case SIDE_NORTH:
				return tile.isValid( VALID_N)
					&& (!tile.isWired() || tile.isWire( AreaType.SIDE_DOWN) || tile.isWire( AreaType.SIDE_UP)
						|| tile.isWire( AreaType.SIDE_WEST) || tile.isWire( AreaType.SIDE_EAST));
			case SIDE_SOUTH:
				return tile.isValid( VALID_S)
					&& (!tile.isWired() || tile.isWire( AreaType.SIDE_DOWN) || tile.isWire( AreaType.SIDE_UP)
						|| tile.isWire( AreaType.SIDE_WEST) || tile.isWire( AreaType.SIDE_EAST));
			case SIDE_WEST:
				return tile.isValid( VALID_W)
					&& (!tile.isWired() || tile.isWire( AreaType.SIDE_DOWN) || tile.isWire( AreaType.SIDE_UP)
						|| tile.isWire( AreaType.SIDE_NORTH) || tile.isWire( AreaType.SIDE_SOUTH));
			case SIDE_EAST:
				return tile.isValid( VALID_E)
					&& (!tile.isWired() || tile.isWire( AreaType.SIDE_DOWN) || tile.isWire( AreaType.SIDE_UP)
						|| tile.isWire( AreaType.SIDE_NORTH) || tile.isWire( AreaType.SIDE_SOUTH));
			default:
				return false;
		}
	}

	@Override
	public boolean isWire() {
		return true;
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
