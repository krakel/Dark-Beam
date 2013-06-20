/**
 * Dark Beam
 * MaskCover1Renderer.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;

import de.krakel.darkbeam.core.IArea;
import de.krakel.darkbeam.tile.TileMasking;

public class MaskCoverRenderer extends AMaskRenderer implements IArea {
	private static final int VALID_D = D | DN | DS | DW | DE | DNW | DNE | DSW | DSE | DU;
	private static final int VALID_U = U | UN | US | UW | UE | UNW | UNE | USW | USE | DU;
	private static final int VALID_N = N | DN | UN | NW | NE | DNW | DNE | UNW | UNE | NS;
	private static final int VALID_S = S | DS | US | SW | SE | UNW | UNE | USW | USE | NS;
	private static final int VALID_W = W | DW | UW | NW | SW | DNW | DSW | UNW | USW | WE;
	private static final int VALID_E = E | DE | UE | NE | SE | DNE | DSE | UNE | USE | WE;
	private float mThickness;

	public MaskCoverRenderer( float base) {
		mThickness = base / 16F;
	}

	@Override
	public boolean isValid( TileMasking tile, int area) {
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
	public void renderItem( RenderBlocks rndrBlk, Block blk, int meta) {
		setInventoryBounds( rndrBlk, mThickness);
		renderInventoryItem( rndrBlk, blk, meta);
	}

	@Override
	public void renderSide( RenderBlocks rndrBlk, int side, Block blk, int meta, int x, int y, int z) {
		setMaskBounds( blk, side);
		renderStandard( rndrBlk, blk, side, meta, x, y, z);
	}

	@Override
	public void setMaskBounds( Block blk, int side) {
		setBounds( blk, side, mThickness + mThickness);
	}
}
