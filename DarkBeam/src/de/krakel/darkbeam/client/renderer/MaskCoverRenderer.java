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

import de.krakel.darkbeam.tile.TileMasking;

public class MaskCoverRenderer extends AMaskRenderer {
	private static final int VALID_D = D | DN | DS | DW | DE | DNW | DNE | DSW | DSE;
	private static final int VALID_U = U | UN | US | UW | UE | UNW | UNE | USW | USE;
	private static final int VALID_N = N | DN | UN | NW | NE | DNW | DNE | UNW | UNE;
	private static final int VALID_S = S | DS | US | SW | SE | UNW | UNE | USW | USE;
	private static final int VALID_W = W | DW | UW | NW | SW | DNW | DSW | UNW | USW;
	private static final int VALID_E = E | DE | UE | NE | SE | DNE | DSE | UNE | USE;
	private static final int VALID_DU = DU | NS | WE;
	private static final int VALID_NS = DU | NS | WE;
	private static final int VALID_WE = DU | NS | WE;
	private float mThickness;
	private float mSize;

	public MaskCoverRenderer( float base) {
		mThickness = base / 16F;
		mSize = mThickness + mThickness;
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
			case AXIS_DOWN_UP:
				return tile.isValid( VALID_DU);
			case AXIS_NORTH_SOUTH:
				return tile.isValid( VALID_NS);
			case AXIS_WEST_EAST:
				return tile.isValid( VALID_WE);
			default:
				return false;
		}
	}

	@Override
	public void renderItem( RenderBlocks rndrBlk, Block blk, int meta) {
		rndrBlk.setRenderBounds( 0D, 0D, 0.5D - mThickness, 1D, 1D, 0.5D + mThickness);
		renderStandardInventory( rndrBlk, blk, meta);
	}

	@Override
	public void renderSide( RenderBlocks rndrBlk, int area, Block blk, int meta, int x, int y, int z) {
		setMaskBounds( blk, area);
		renderStandard( rndrBlk, blk, DIR_NORTH, meta, x, y, z);
	}

	@Override
	public void setMaskBounds( Block blk, int area) {
		switch (area) {
			case SIDE_DOWN:
				blk.setBlockBounds( 0F, 0F, 0F, 1F, mSize, 1F);
				break;
			case SIDE_UP:
				blk.setBlockBounds( 0F, 1F - mSize, 0F, 1F, 1F, 1F);
				break;
			case SIDE_NORTH:
				blk.setBlockBounds( 0F, 0F, 0F, 1F, 1F, mSize);
				break;
			case SIDE_SOUTH:
				blk.setBlockBounds( 0F, 0F, 1F - mSize, 1F, 1F, 1F);
				break;
			case SIDE_WEST:
				blk.setBlockBounds( 0F, 0F, 0F, mSize, 1F, 1F);
				break;
			case SIDE_EAST:
				blk.setBlockBounds( 1F - mSize, 0F, 0F, 1F, 1F, 1F);
				break;
			case AXIS_DOWN_UP:
				blk.setBlockBounds( 0.5F - mThickness, 0F, 0.5F - mThickness, 0.5F + mThickness, 1F, 0.5F + mThickness);
				break;
			case AXIS_NORTH_SOUTH:
				blk.setBlockBounds( 0.5F - mThickness, 0.5F - mThickness, 0F, 0.5F + mThickness, 0.5F + mThickness, 1F);
				break;
			case AXIS_WEST_EAST:
				blk.setBlockBounds( 0F, 0.5F - mThickness, 0.5F - mThickness, 1F, 0.5F + mThickness, 0.5F + mThickness);
				break;
			default:
				blk.setBlockBounds( 0F, 0F, 0F, 1F, 1F, 1F);
				break;
		}
	}
}
