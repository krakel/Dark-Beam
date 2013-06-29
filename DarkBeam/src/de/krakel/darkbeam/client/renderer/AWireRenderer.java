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

import de.krakel.darkbeam.core.IArea;
import de.krakel.darkbeam.core.helper.LogHelper;
import de.krakel.darkbeam.tile.TileStage;

abstract class AWireRenderer extends ASectionRenderer implements IArea {
	protected float mCrossness;

	protected AWireRenderer( int base, float crossness) {
		super( base);
		mCrossness = crossness;
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

	@Override
	public void renderSide( RenderBlocks rndrBlk, int side, Block blk, int meta, int x, int y, int z, TileStage tile) {
		boolean isConnected = tile.isConnected( side);
		float minS = 0.5F - mThickness;
		float maxS = 0.5F + mThickness;
		float min = isConnected ? minS : mCrossness;
		float max = isConnected ? maxS : 1F - mCrossness;
		float minX, maxX;
		float minY, maxY;
		float minZ, maxZ;
		switch (side) {
			case SIDE_DOWN:
				minX = tile.isConnected( side, SIDE_WEST) ? 0F : min;
				maxX = tile.isConnected( side, SIDE_EAST) ? 1F : max;
				minZ = tile.isConnected( side, SIDE_NORTH) ? 0F : min;
				maxZ = tile.isConnected( side, SIDE_SOUTH) ? 1F : max;
				blk.setBlockBounds( minX, 0F, minS, maxX, mSize, maxS);
				renderStandard( rndrBlk, blk, DIR_DOWN, meta, x, y, z);
				blk.setBlockBounds( minS, 0F, minZ, maxS, mSize, maxZ);
				renderStandard( rndrBlk, blk, DIR_DOWN, meta, x, y, z);
				break;
			case SIDE_UP:
				minX = tile.isConnected( side, SIDE_WEST) ? 0F : min;
				maxX = tile.isConnected( side, SIDE_EAST) ? 1F : max;
				minZ = tile.isConnected( side, SIDE_NORTH) ? 0F : min;
				maxZ = tile.isConnected( side, SIDE_SOUTH) ? 1F : max;
				blk.setBlockBounds( minX, 1F - mSize, minS, maxX, 1F, maxS);
				renderStandard( rndrBlk, blk, DIR_UP, meta, x, y, z);
				blk.setBlockBounds( minS, 1F - mSize, minZ, maxS, 1F, maxZ);
				renderStandard( rndrBlk, blk, DIR_UP, meta, x, y, z);
				break;
			case SIDE_NORTH:
				minX = tile.isConnected( side, SIDE_WEST) ? 0F : min;
				maxX = tile.isConnected( side, SIDE_EAST) ? 1F : max;
				minY = tile.isConnected( side, SIDE_DOWN) ? 0F : min;
				maxY = tile.isConnected( side, SIDE_UP) ? 1F : max;
				if (tile.isAngled( side, SIDE_DOWN)) {
					minY -= mSize;
				}
				if (tile.isAngled( side, SIDE_UP)) {
					maxY += mSize;
				}
				if (tile.isAngled( side, SIDE_EAST)) {
					maxX += mSize;
				}
				blk.setBlockBounds( minX, minS, 0F, maxX, maxS, mSize);
				renderStandard( rndrBlk, blk, DIR_NORTH, meta, x, y, z);
				blk.setBlockBounds( minS, minY, 0F, maxS, maxY, mSize);
				renderStandard( rndrBlk, blk, DIR_NORTH, meta, x, y, z);
				break;
			case SIDE_SOUTH:
				minX = tile.isConnected( side, SIDE_WEST) ? 0F : min;
				maxX = tile.isConnected( side, SIDE_EAST) ? 1F : max;
				minY = tile.isConnected( side, SIDE_DOWN) ? 0F : min;
				maxY = tile.isConnected( side, SIDE_UP) ? 1F : max;
				if (tile.isAngled( side, SIDE_DOWN)) {
					minY -= mSize;
				}
				if (tile.isAngled( side, SIDE_UP)) {
					maxY += mSize;
				}
				if (tile.isAngled( side, SIDE_WEST)) {
					minX -= mSize;
				}
				blk.setBlockBounds( minX, minS, 1F - mSize, maxX, maxS, 1F);
				renderStandard( rndrBlk, blk, DIR_SOUTH, meta, x, y, z);
				blk.setBlockBounds( minS, minY, 1F - mSize, maxS, maxY, 1F);
				renderStandard( rndrBlk, blk, DIR_SOUTH, meta, x, y, z);
				break;
			case SIDE_WEST:
				minY = tile.isConnected( side, SIDE_DOWN) ? 0F : min;
				maxY = tile.isConnected( side, SIDE_UP) ? 1F : max;
				minZ = tile.isConnected( side, SIDE_NORTH) ? 0F : min;
				maxZ = tile.isConnected( side, SIDE_SOUTH) ? 1F : max;
				if (tile.isAngled( side, SIDE_DOWN)) {
					minY -= mSize;
				}
				if (tile.isAngled( side, SIDE_UP)) {
					maxY += mSize;
				}
				if (tile.isAngled( side, SIDE_NORTH)) {
					minZ -= mSize;
				}
				blk.setBlockBounds( 0F, minY, minS, mSize, maxY, maxS);
				renderStandard( rndrBlk, blk, DIR_WEST, meta, x, y, z);
				blk.setBlockBounds( 0F, minS, minZ, mSize, maxS, maxZ);
				renderStandard( rndrBlk, blk, DIR_WEST, meta, x, y, z);
				break;
			case SIDE_EAST:
				minY = tile.isConnected( side, SIDE_DOWN) ? 0F : min;
				maxY = tile.isConnected( side, SIDE_UP) ? 1F : max;
				minZ = tile.isConnected( side, SIDE_NORTH) ? 0F : min;
				maxZ = tile.isConnected( side, SIDE_SOUTH) ? 1F : max;
				if (tile.isAngled( side, SIDE_DOWN)) {
					minY -= mSize;
				}
				if (tile.isAngled( side, SIDE_UP)) {
					maxY += mSize;
				}
				if (tile.isAngled( side, SIDE_SOUTH)) {
					maxZ += mSize;
				}
				blk.setBlockBounds( 1F - mSize, minY, minS, 1F, maxY, maxS);
				renderStandard( rndrBlk, blk, DIR_EAST, meta, x, y, z);
				blk.setBlockBounds( 1F - mSize, minS, minZ, 1F, maxS, maxZ);
				renderStandard( rndrBlk, blk, DIR_EAST, meta, x, y, z);
				break;
			default:
				blk.setBlockBounds( 0F, 0F, 0F, 1F, 1F, 1F);
				renderStandard( rndrBlk, blk, DIR_EAST, meta, x, y, z);
				break;
		}
	}

	@Override
	public void setSectionBounds( int area, Block blk) {
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
			default:
				LogHelper.warning( "unknown area %d", area);
				blk.setBlockBounds( 0F, 0F, 0F, 1F, 1F, 1F);
				break;
		}
	}
}
