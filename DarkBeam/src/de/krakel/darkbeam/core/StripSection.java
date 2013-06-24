/**
 * Dark Beam
 * StripSection.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;

import de.krakel.darkbeam.client.renderer.AMaskRenderer;
import de.krakel.darkbeam.client.renderer.MaskStripRenderer;
import de.krakel.darkbeam.tile.TileStage;

public class StripSection extends ASection {
	private static final int VALID_DN = D | N | DN | DNW | DNE;
	private static final int VALID_DS = D | S | DS | DSW | DSE;
	private static final int VALID_DW = D | W | DW | DNW | DSW;
	private static final int VALID_DE = D | E | DE | DNE | DSE;
	private static final int VALID_UN = U | N | UN | UNW | UNE;
	private static final int VALID_US = U | S | US | USW | USE;
	private static final int VALID_UW = U | W | UW | UNW | USW;
	private static final int VALID_UE = U | E | UE | UNE | USE;
	private static final int VALID_NW = N | W | NW | DNW | UNW;
	private static final int VALID_NE = N | E | NE | DNE | UNE;
	private static final int VALID_SW = S | W | SW | DSW | USW;
	private static final int VALID_SE = S | E | SE | DSE | USE;
	private static final int VALID_DU = DU | NS | WE;
	private static final int VALID_NS = DU | NS | WE;
	private static final int VALID_WE = DU | NS | WE;
	private MaskStripRenderer mRenderer;

	public StripSection( int nr) {
		super( "strip." + nr);
		mRenderer = new MaskStripRenderer( nr);
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
			case IArea.EDGE_DOWN_NORTH:
				return tile.isValid( VALID_DN);
			case IArea.EDGE_DOWN_SOUTH:
				return tile.isValid( VALID_DS);
			case IArea.EDGE_DOWN_WEST:
				return tile.isValid( VALID_DW);
			case IArea.EDGE_DOWN_EAST:
				return tile.isValid( VALID_DE);
			case IArea.EDGE_UP_NORTH:
				return tile.isValid( VALID_UN);
			case IArea.EDGE_UP_SOUTH:
				return tile.isValid( VALID_US);
			case IArea.EDGE_UP_WEST:
				return tile.isValid( VALID_UW);
			case IArea.EDGE_UP_EAST:
				return tile.isValid( VALID_UE);
			case IArea.EDGE_NORTH_WEST:
				return tile.isValid( VALID_NW);
			case IArea.EDGE_NORTH_EAST:
				return tile.isValid( VALID_NE);
			case IArea.EDGE_SOUTH_WEST:
				return tile.isValid( VALID_SW);
			case IArea.EDGE_SOUTH_EAST:
				return tile.isValid( VALID_SE);
			case IArea.AXIS_DOWN_UP:
				return tile.isValid( VALID_DU);
			case IArea.AXIS_NORTH_SOUTH:
				return tile.isValid( VALID_NS);
			case IArea.AXIS_WEST_EAST:
				return tile.isValid( VALID_WE);
			default:
				return false;
		}
	}

	@Override
	public void oppositeArea( MovingObjectPosition pos) {
		pos.subHit = mRenderer.getOpposite( pos.sideHit, pos.subHit);
	}

	@Override
	public void updateArea( MovingObjectPosition pos) {
		double dx = pos.hitVec.xCoord - pos.blockX;
		double dy = pos.hitVec.yCoord - pos.blockY;
		double dz = pos.hitVec.zCoord - pos.blockZ;
		pos.subHit = mRenderer.getArea( pos.sideHit, dx, dy, dz);
	}
}
