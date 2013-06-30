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
	private static final int VALID_D = D | DU;
	private static final int VALID_U = U | DU;
	private static final int VALID_N = N | NS;
	private static final int VALID_S = S | NS;
	private static final int VALID_W = W | WE;
	private static final int VALID_E = E | WE;

	protected ASectionWire( String name, ASectionRenderer renderer) {
		super( name, renderer);
	}

	private static int getArea( int side, double dx, double dy, double dz) {
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
	public boolean isJoinable() {
		return true;
	}

	@Override
	public boolean isStructure() {
		return false;
	}

	@Override
	public boolean isValid( TileStage tile, int area) {
		switch (area) {
			case SIDE_DOWN:
				return tile.isValid( VALID_D)
					&& (!tile.isWired() || tile.isWire( SIDE_NORTH) || tile.isWire( SIDE_SOUTH)
						|| tile.isWire( SIDE_WEST) || tile.isWire( SIDE_EAST));
			case SIDE_UP:
				return tile.isValid( VALID_U)
					&& (!tile.isWired() || tile.isWire( SIDE_NORTH) || tile.isWire( SIDE_SOUTH)
						|| tile.isWire( SIDE_WEST) || tile.isWire( SIDE_EAST));
			case SIDE_NORTH:
				return tile.isValid( VALID_N)
					&& (!tile.isWired() || tile.isWire( SIDE_DOWN) || tile.isWire( SIDE_UP) || tile.isWire( SIDE_WEST) || tile.isWire( SIDE_EAST));
			case SIDE_SOUTH:
				return tile.isValid( VALID_S)
					&& (!tile.isWired() || tile.isWire( SIDE_DOWN) || tile.isWire( SIDE_UP) || tile.isWire( SIDE_WEST) || tile.isWire( SIDE_EAST));
			case SIDE_WEST:
				return tile.isValid( VALID_W)
					&& (!tile.isWired() || tile.isWire( SIDE_DOWN) || tile.isWire( SIDE_UP) || tile.isWire( SIDE_NORTH) || tile.isWire( SIDE_SOUTH));
			case SIDE_EAST:
				return tile.isValid( VALID_E)
					&& (!tile.isWired() || tile.isWire( SIDE_DOWN) || tile.isWire( SIDE_UP) || tile.isWire( SIDE_NORTH) || tile.isWire( SIDE_SOUTH));
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
		pos.subHit = getArea( pos.sideHit, dx, dy, dz);
	}
}
