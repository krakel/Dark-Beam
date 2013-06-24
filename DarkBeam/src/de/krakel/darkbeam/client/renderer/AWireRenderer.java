/**
 * Dark Beam
 * AWireRenderer.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;

abstract class AWireRenderer extends AMaskRenderer {
	protected AWireRenderer( int base) {
		super( base);
	}

	@Override
	public int getArea( int side, double dx, double dy, double dz) {
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
	public int getOpposite( int side, int area) {
		if (area == side) {
			return area ^= 1;
		}
		return area;
	}

	@Override
	public void renderItem( RenderBlocks rndrBlk, Block blk, int meta) {
		double minS = 0.5D - mThickness;
		double maxS = 0.5D + mThickness;
		rndrBlk.setRenderBounds( 0D, minS, minS, 1D, maxS, maxS);
		renderStandardInventory( rndrBlk, blk, meta);
		rndrBlk.setRenderBounds( minS, minS, 0D, maxS, maxS, 1D);
		renderStandardInventory( rndrBlk, blk, meta);
	}
}
