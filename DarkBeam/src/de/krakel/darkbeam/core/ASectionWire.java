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
import de.krakel.darkbeam.tile.IConnectable;
import de.krakel.darkbeam.tile.TileStage;

public abstract class ASectionWire extends ASection {
	private static final int VALID_D = AreaType.toMask( AreaType.DOWN, AreaType.DOWN_UP);
	private static final int VALID_U = AreaType.toMask( AreaType.UP, AreaType.DOWN_UP);
	private static final int VALID_N = AreaType.toMask( AreaType.NORTH, AreaType.NORTH_SOUTH);
	private static final int VALID_S = AreaType.toMask( AreaType.SOUTH, AreaType.NORTH_SOUTH);
	private static final int VALID_W = AreaType.toMask( AreaType.WEST, AreaType.WEST_EAST);
	private static final int VALID_E = AreaType.toMask( AreaType.EAST, AreaType.WEST_EAST);
	private static final int VALID_DU = AreaType.toMask( AreaType.SIDES_DOWN_UP);
	private static final int VALID_NS = AreaType.toMask( AreaType.SIDES_NORTH_SOUTH);
	private static final int VALID_WE = AreaType.toMask( AreaType.SIDES_WEST_EAST);

	protected ASectionWire( String name, ASectionRenderer renderer) {
		super( name, renderer);
	}

	private static AreaType getArea( int side, double dx, double dy, double dz) {
		switch (side) {
			case IDirection.DIR_DOWN:
				return AreaType.DOWN;
			case IDirection.DIR_UP:
				return AreaType.UP;
			case IDirection.DIR_NORTH:
				return AreaType.NORTH;
			case IDirection.DIR_SOUTH:
				return AreaType.SOUTH;
			case IDirection.DIR_WEST:
				return AreaType.WEST;
			case IDirection.DIR_EAST:
				return AreaType.EAST;
			default:
				return AreaType.DOWN;
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
		IConnectable connet = tile.getConnect();
		switch (area) {
			case DOWN:
				return tile.isValid( VALID_D) && connet.isValid( VALID_DU);
			case UP:
				return tile.isValid( VALID_U) && connet.isValid( VALID_DU);
			case NORTH:
				return tile.isValid( VALID_N) && connet.isValid( VALID_NS);
			case SOUTH:
				return tile.isValid( VALID_S) && connet.isValid( VALID_NS);
			case WEST:
				return tile.isValid( VALID_W) && connet.isValid( VALID_WE);
			case EAST:
				return tile.isValid( VALID_E) && connet.isValid( VALID_WE);
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
