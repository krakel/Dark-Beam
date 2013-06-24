/**
 * Dark Beam
 * CoverSection.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;

import de.krakel.darkbeam.client.renderer.AMaskRenderer;
import de.krakel.darkbeam.client.renderer.MaskCoverRenderer;
import de.krakel.darkbeam.tile.TileStage;

public class CoverSection extends ASection {
	protected static final int VALID_D = D | DN | DS | DW | DE | DNW | DNE | DSW | DSE;
	protected static final int VALID_U = U | UN | US | UW | UE | UNW | UNE | USW | USE;
	protected static final int VALID_N = N | DN | UN | NW | NE | DNW | DNE | UNW | UNE;
	protected static final int VALID_S = S | DS | US | SW | SE | USW | USE | USW | USE;
	protected static final int VALID_W = W | DW | UW | NW | SW | DNW | DSW | UNW | USW;
	protected static final int VALID_E = E | DE | UE | NE | SE | DNE | DSE | UNE | USE;
	private MaskCoverRenderer mRenderer;

	public CoverSection( int nr) {
		super( "cover." + nr);
		mRenderer = new MaskCoverRenderer( nr);
	}

	@Override
	public int getBlockID( int dmg) {
		Material mat = MaterialLib.getForDmg( dmg);
		return mat.mBlock.blockID;
	}

	@Override
	public Icon getIcon( int side, int dmg) {
		Material mat = MaterialLib.getForDmg( dmg);
		return mat.getIcon( side);
	}

	@Override
	public AMaskRenderer getRenderer() {
		return mRenderer;
	}

	@Override
	public String getSectionName( int dmg) {
		Material mat = MaterialLib.getForDmg( dmg);
		return mat.getMatName( this);
	}

	@Override
	public boolean hasMaterials() {
		return true;
	}

	@Override
	public boolean isValid( TileStage tile, int area) {
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
		pos.subHit = CoverSection.getArea( pos.sideHit, dx, dy, dz);
	}

	public static int getArea( int side, double dx, double dy, double dz) {
		switch (side) {
			case DIR_DOWN:
				if (BOX_BORDER_MIN < dx && dx < BOX_BORDER_MAX && BOX_BORDER_MIN < dz && dz < BOX_BORDER_MAX) {
					return SIDE_DOWN;
				}
				if (dz > dx) {
					return dz + dx > 1D ? SIDE_SOUTH : SIDE_WEST;
				}
				return dz + dx > 1D ? SIDE_EAST : SIDE_NORTH;
			case DIR_UP:
				if (BOX_BORDER_MIN < dx && dx < BOX_BORDER_MAX && BOX_BORDER_MIN < dz && dz < BOX_BORDER_MAX) {
					return SIDE_UP;
				}
				if (dz > dx) {
					return dz + dx > 1D ? SIDE_SOUTH : SIDE_WEST;
				}
				return dz + dx > 1D ? SIDE_EAST : SIDE_NORTH;
			case DIR_NORTH:
				if (BOX_BORDER_MIN < dx && dx < BOX_BORDER_MAX && BOX_BORDER_MIN < dy && dy < BOX_BORDER_MAX) {
					return SIDE_NORTH;
				}
				if (dy > dx) {
					return dy + dx > 1D ? SIDE_UP : SIDE_WEST;
				}
				return dy + dx > 1D ? SIDE_EAST : SIDE_DOWN;
			case DIR_SOUTH:
				if (BOX_BORDER_MIN < dx && dx < BOX_BORDER_MAX && BOX_BORDER_MIN < dy && dy < BOX_BORDER_MAX) {
					return SIDE_SOUTH;
				}
				if (dy > dx) {
					return dy + dx > 1D ? SIDE_UP : SIDE_WEST;
				}
				return dy + dx > 1D ? SIDE_EAST : SIDE_DOWN;
			case DIR_WEST:
				if (BOX_BORDER_MIN < dy && dy < BOX_BORDER_MAX && BOX_BORDER_MIN < dz && dz < BOX_BORDER_MAX) {
					return SIDE_WEST;
				}
				if (dy > dz) {
					return dy + dz > 1D ? SIDE_UP : SIDE_NORTH;
				}
				return dy + dz > 1D ? SIDE_SOUTH : SIDE_DOWN;
			case DIR_EAST:
				if (BOX_BORDER_MIN < dy && dy < BOX_BORDER_MAX && BOX_BORDER_MIN < dz && dz < BOX_BORDER_MAX) {
					return SIDE_EAST;
				}
				if (dy > dz) {
					return dy + dz > 1D ? SIDE_UP : SIDE_NORTH;
				}
				return dy + dz > 1D ? SIDE_SOUTH : SIDE_DOWN;
			default:
				return -1;
		}
	}
}
