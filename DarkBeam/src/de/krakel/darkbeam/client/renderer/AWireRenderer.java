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

import de.krakel.darkbeam.core.AreaType;
import de.krakel.darkbeam.core.IDirection;
import de.krakel.darkbeam.core.helper.LogHelper;
import de.krakel.darkbeam.tile.TileStage;

abstract class AWireRenderer extends ASectionRenderer {
	protected float mCrossness;
	private float mHigh;

	protected AWireRenderer( int base, float crossness) {
		super( base);
		mCrossness = crossness;
		mHigh = (base + 1) / 16F;
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
	public void renderSide( RenderBlocks rndrBlk, AreaType area, Block blk, int meta, int x, int y, int z, TileStage tile) {
		boolean isConnected = tile.isConnected( area);
		float minS = 0.5F - mThickness;
		float maxS = 0.5F + mThickness;
		float min = isConnected ? minS : mCrossness;
		float max = isConnected ? maxS : 1F - mCrossness;
		float minX, maxX;
		float minY, maxY;
		float minZ, maxZ;
		switch (area) {
			case DOWN:
				minX = tile.isConnected( area, AreaType.WEST) ? 0F : min;
				maxX = tile.isConnected( area, AreaType.EAST) ? 1F : max;
				minZ = tile.isConnected( area, AreaType.NORTH) ? 0F : min;
				maxZ = tile.isConnected( area, AreaType.SOUTH) ? 1F : max;
				blk.setBlockBounds( minX, 0F, minS, maxX, mHigh, maxS);
				renderStandard( rndrBlk, blk, IDirection.DIR_DOWN, meta, x, y, z);
				blk.setBlockBounds( minS, 0F, minZ, maxS, mHigh, maxZ);
				renderStandard( rndrBlk, blk, IDirection.DIR_DOWN, meta, x, y, z);
				break;
			case UP:
				minX = tile.isConnected( area, AreaType.WEST) ? 0F : min;
				maxX = tile.isConnected( area, AreaType.EAST) ? 1F : max;
				minZ = tile.isConnected( area, AreaType.NORTH) ? 0F : min;
				maxZ = tile.isConnected( area, AreaType.SOUTH) ? 1F : max;
				blk.setBlockBounds( minX, 1F - mHigh, minS, maxX, 1F, maxS);
				renderStandard( rndrBlk, blk, IDirection.DIR_UP, meta, x, y, z);
				blk.setBlockBounds( minS, 1F - mHigh, minZ, maxS, 1F, maxZ);
				renderStandard( rndrBlk, blk, IDirection.DIR_UP, meta, x, y, z);
				break;
			case NORTH:
				minX = tile.isConnected( area, AreaType.WEST) ? 0F : min;
				maxX = tile.isConnected( area, AreaType.EAST) ? 1F : max;
				minY = tile.isConnected( area, AreaType.DOWN) ? 0F : min;
				maxY = tile.isConnected( area, AreaType.UP) ? 1F : max;
				if (tile.isAngled( area, AreaType.DOWN)) {
					minY -= mHigh;
				}
				if (tile.isAngled( area, AreaType.UP)) {
					maxY += mHigh;
				}
				if (tile.isAngled( area, AreaType.EAST)) {
					maxX += mHigh;
				}
				blk.setBlockBounds( minX, minS, 0F, maxX, maxS, mHigh);
				renderStandard( rndrBlk, blk, IDirection.DIR_NORTH, meta, x, y, z);
				blk.setBlockBounds( minS, minY, 0F, maxS, maxY, mHigh);
				renderStandard( rndrBlk, blk, IDirection.DIR_NORTH, meta, x, y, z);
				break;
			case SOUTH:
				minX = tile.isConnected( area, AreaType.WEST) ? 0F : min;
				maxX = tile.isConnected( area, AreaType.EAST) ? 1F : max;
				minY = tile.isConnected( area, AreaType.DOWN) ? 0F : min;
				maxY = tile.isConnected( area, AreaType.UP) ? 1F : max;
				if (tile.isAngled( area, AreaType.DOWN)) {
					minY -= mHigh;
				}
				if (tile.isAngled( area, AreaType.UP)) {
					maxY += mHigh;
				}
				if (tile.isAngled( area, AreaType.WEST)) {
					minX -= mHigh;
				}
				blk.setBlockBounds( minX, minS, 1F - mHigh, maxX, maxS, 1F);
				renderStandard( rndrBlk, blk, IDirection.DIR_SOUTH, meta, x, y, z);
				blk.setBlockBounds( minS, minY, 1F - mHigh, maxS, maxY, 1F);
				renderStandard( rndrBlk, blk, IDirection.DIR_SOUTH, meta, x, y, z);
				break;
			case WEST:
				minY = tile.isConnected( area, AreaType.DOWN) ? 0F : min;
				maxY = tile.isConnected( area, AreaType.UP) ? 1F : max;
				minZ = tile.isConnected( area, AreaType.NORTH) ? 0F : min;
				maxZ = tile.isConnected( area, AreaType.SOUTH) ? 1F : max;
				if (tile.isAngled( area, AreaType.DOWN)) {
					minY -= mHigh;
				}
				if (tile.isAngled( area, AreaType.UP)) {
					maxY += mHigh;
				}
				if (tile.isAngled( area, AreaType.NORTH)) {
					minZ -= mHigh;
				}
				blk.setBlockBounds( 0F, minY, minS, mHigh, maxY, maxS);
				renderStandard( rndrBlk, blk, IDirection.DIR_WEST, meta, x, y, z);
				blk.setBlockBounds( 0F, minS, minZ, mHigh, maxS, maxZ);
				renderStandard( rndrBlk, blk, IDirection.DIR_WEST, meta, x, y, z);
				break;
			case EAST:
				minY = tile.isConnected( area, AreaType.DOWN) ? 0F : min;
				maxY = tile.isConnected( area, AreaType.UP) ? 1F : max;
				minZ = tile.isConnected( area, AreaType.NORTH) ? 0F : min;
				maxZ = tile.isConnected( area, AreaType.SOUTH) ? 1F : max;
				if (tile.isAngled( area, AreaType.DOWN)) {
					minY -= mHigh;
				}
				if (tile.isAngled( area, AreaType.UP)) {
					maxY += mHigh;
				}
				if (tile.isAngled( area, AreaType.SOUTH)) {
					maxZ += mHigh;
				}
				blk.setBlockBounds( 1F - mHigh, minY, minS, 1F, maxY, maxS);
				renderStandard( rndrBlk, blk, IDirection.DIR_EAST, meta, x, y, z);
				blk.setBlockBounds( 1F - mHigh, minS, minZ, 1F, maxS, maxZ);
				renderStandard( rndrBlk, blk, IDirection.DIR_EAST, meta, x, y, z);
				break;
			default:
				blk.setBlockBounds( 0F, 0F, 0F, 1F, 1F, 1F);
				renderStandard( rndrBlk, blk, IDirection.DIR_EAST, meta, x, y, z);
				break;
		}
	}

	@Override
	public void setSectionBounds( AreaType area, Block blk, TileStage tile) {
		boolean isConnected = tile.isConnected( area);
		float minS = 0.5F - mThickness;
		float maxS = 0.5F + mThickness;
		float min = isConnected ? minS : mCrossness;
		float max = isConnected ? maxS : 1F - mCrossness;
		float minX, maxX;
		float minY, maxY;
		float minZ, maxZ;
		switch (area) {
			case DOWN:
				minX = tile.isConnected( area, AreaType.WEST) ? 0F : min;
				maxX = tile.isConnected( area, AreaType.EAST) ? 1F : max;
				minZ = tile.isConnected( area, AreaType.NORTH) ? 0F : min;
				maxZ = tile.isConnected( area, AreaType.SOUTH) ? 1F : max;
				blk.setBlockBounds( minX, 0F, minZ, maxX, mHigh, maxZ);
//				blk.setBlockBounds( 0F, 0F, 0F, 1F, mHigh, 1F);
				break;
			case UP:
				minX = tile.isConnected( area, AreaType.WEST) ? 0F : min;
				maxX = tile.isConnected( area, AreaType.EAST) ? 1F : max;
				minZ = tile.isConnected( area, AreaType.NORTH) ? 0F : min;
				maxZ = tile.isConnected( area, AreaType.SOUTH) ? 1F : max;
				blk.setBlockBounds( minX, 1F - mHigh, minZ, maxX, 1F, maxZ);
//				blk.setBlockBounds( 0F, 1F - mHigh, 0F, 1F, 1F, 1F);
				break;
			case NORTH:
				minX = tile.isConnected( area, AreaType.WEST) ? 0F : min;
				maxX = tile.isConnected( area, AreaType.EAST) ? 1F : max;
				minY = tile.isConnected( area, AreaType.DOWN) ? 0F : min;
				maxY = tile.isConnected( area, AreaType.UP) ? 1F : max;
				blk.setBlockBounds( minX, minY, 0F, maxX, maxY, mHigh);
//				blk.setBlockBounds( 0F, 0F, 0F, 1F, 1F, mHigh);
				break;
			case SOUTH:
				minX = tile.isConnected( area, AreaType.WEST) ? 0F : min;
				maxX = tile.isConnected( area, AreaType.EAST) ? 1F : max;
				minY = tile.isConnected( area, AreaType.DOWN) ? 0F : min;
				maxY = tile.isConnected( area, AreaType.UP) ? 1F : max;
				blk.setBlockBounds( minX, minY, 1F - mHigh, maxX, maxY, 1F);
//				blk.setBlockBounds( 0F, 0F, 1F - mHigh, 1F, 1F, 1F);
				break;
			case WEST:
				minY = tile.isConnected( area, AreaType.DOWN) ? 0F : min;
				maxY = tile.isConnected( area, AreaType.UP) ? 1F : max;
				minZ = tile.isConnected( area, AreaType.NORTH) ? 0F : min;
				maxZ = tile.isConnected( area, AreaType.SOUTH) ? 1F : max;
				blk.setBlockBounds( 0F, minY, minZ, mHigh, maxY, maxZ);
//				blk.setBlockBounds( 0F, 0F, 0F, mHigh, 1F, 1F);
				break;
			case EAST:
				minY = tile.isConnected( area, AreaType.DOWN) ? 0F : min;
				maxY = tile.isConnected( area, AreaType.UP) ? 1F : max;
				minZ = tile.isConnected( area, AreaType.NORTH) ? 0F : min;
				maxZ = tile.isConnected( area, AreaType.SOUTH) ? 1F : max;
				blk.setBlockBounds( 1F - mHigh, minY, minZ, 1F, maxY, maxZ);
//				blk.setBlockBounds( 1F - mHigh, 0F, 0F, 1F, 1F, 1F);
				break;
			default:
				LogHelper.warning( "unknown area %d", area);
				blk.setBlockBounds( 0F, 0F, 0F, 1F, 1F, 1F);
				break;
		}
	}
}
