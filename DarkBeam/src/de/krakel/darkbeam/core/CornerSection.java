/**
 * Dark Beam
 * CornerSection.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;

import de.krakel.darkbeam.client.renderer.AMaskRenderer;
import de.krakel.darkbeam.client.renderer.MaskCornerRenderer;
import de.krakel.darkbeam.tile.TileStage;

public class CornerSection extends ASection {
	private static final int VALID_DNW = D | N | W | DN | DW | NW | DNW;
	private static final int VALID_UNW = U | N | W | UN | UW | NW | UNW;
	private static final int VALID_DSW = D | S | W | DS | DW | SW | DSW;
	private static final int VALID_USW = U | S | W | US | UW | DW | USW;
	private static final int VALID_DNE = D | N | E | DN | DE | NE | DNE;
	private static final int VALID_UNE = U | N | E | UN | UE | NE | UNE;
	private static final int VALID_DSE = D | S | E | DS | DE | SE | DSE;
	private static final int VALID_USE = U | S | E | US | UE | SE | USE;
	private MaskCornerRenderer mRenderer;

	public CornerSection( int nr) {
		super( "corner." + nr);
		mRenderer = new MaskCornerRenderer( nr);
	}

	private static int getOpposite( int side, int area) {
		switch (side) {
			case DIR_DOWN:
				switch (area) {
					case CORNER_DOWN_NORTH_WEST:
					case CORNER_DOWN_SOUTH_WEST:
					case CORNER_DOWN_NORTH_EAST:
					case CORNER_DOWN_SOUTH_EAST:
						return area + 1;
					default:
						return area;
				}
			case DIR_UP:
				switch (area) {
					case CORNER_UP_NORTH_WEST:
					case CORNER_UP_SOUTH_WEST:
					case CORNER_UP_NORTH_EAST:
					case CORNER_UP_SOUTH_EAST:
						return area - 1;
					default:
						return area;
				}
			case DIR_NORTH:
				switch (area) {
					case CORNER_DOWN_NORTH_WEST:
					case CORNER_UP_NORTH_WEST:
					case CORNER_DOWN_NORTH_EAST:
					case CORNER_UP_NORTH_EAST:
						return area + 2;
					default:
						return area;
				}
			case DIR_SOUTH:
				switch (area) {
					case CORNER_DOWN_SOUTH_WEST:
					case CORNER_UP_SOUTH_WEST:
					case CORNER_DOWN_SOUTH_EAST:
					case CORNER_UP_SOUTH_EAST:
						return area - 2;
					default:
						return area;
				}
			case DIR_WEST:
				switch (area) {
					case CORNER_DOWN_NORTH_WEST:
					case CORNER_DOWN_SOUTH_WEST:
					case CORNER_UP_NORTH_WEST:
					case CORNER_UP_SOUTH_WEST:
						return area + 4;
					default:
						return area;
				}
			case DIR_EAST:
				switch (area) {
					case CORNER_DOWN_NORTH_EAST:
					case CORNER_DOWN_SOUTH_EAST:
					case CORNER_UP_NORTH_EAST:
					case CORNER_UP_SOUTH_EAST:
						return area - 4;
					default:
						return area;
				}
			default:
				return area;
		}
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
		pos.subHit = getOpposite( pos.sideHit, pos.subHit);
	}

	@Override
	public void updateArea( MovingObjectPosition pos) {
		double dx = pos.hitVec.xCoord - pos.blockX;
		double dy = pos.hitVec.yCoord - pos.blockY;
		double dz = pos.hitVec.zCoord - pos.blockZ;
		pos.subHit = mRenderer.getArea( pos.sideHit, dx, dy, dz);
	}
}
