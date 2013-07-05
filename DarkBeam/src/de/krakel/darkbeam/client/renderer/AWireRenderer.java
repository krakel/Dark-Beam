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
import de.krakel.darkbeam.tile.IConnetable;
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
		IConnetable connect = tile.getConnet();
		boolean isConn = connect.isConnection( area);
		float minS = 0.5F - mThickness;
		float maxS = 0.5F + mThickness;
		float min = isConn ? minS : mCrossness;
		float max = isConn ? maxS : 1F - mCrossness;
		float minX, maxX;
		float minY, maxY;
		float minZ, maxZ;
		switch (area) {
			case DOWN:
				minX = connect.isConnected( AreaType.DOWN_WEST) ? 0F : min;
				maxX = connect.isConnected( AreaType.DOWN_EAST) ? 1F : max;
				minZ = connect.isConnected( AreaType.DOWN_NORTH) ? 0F : min;
				maxZ = connect.isConnected( AreaType.DOWN_SOUTH) ? 1F : max;
				blk.setBlockBounds( minX, 0F, minS, maxX, mHigh, maxS);
				renderStandard( rndrBlk, blk, IDirection.DIR_DOWN, meta, x, y, z);
				blk.setBlockBounds( minS, 0F, minZ, maxS, mHigh, maxZ);
				renderStandard( rndrBlk, blk, IDirection.DIR_DOWN, meta, x, y, z);
				break;
			case UP:
				minX = connect.isConnected( AreaType.UP_WEST) ? 0F : min;
				maxX = connect.isConnected( AreaType.UP_EAST) ? 1F : max;
				minZ = connect.isConnected( AreaType.UP_NORTH) ? 0F : min;
				maxZ = connect.isConnected( AreaType.UP_SOUTH) ? 1F : max;
				blk.setBlockBounds( minX, 1F - mHigh, minS, maxX, 1F, maxS);
				renderStandard( rndrBlk, blk, IDirection.DIR_UP, meta, x, y, z);
				blk.setBlockBounds( minS, 1F - mHigh, minZ, maxS, 1F, maxZ);
				renderStandard( rndrBlk, blk, IDirection.DIR_UP, meta, x, y, z);
				break;
			case NORTH:
				minX = connect.isConnected( AreaType.NORTH_WEST) ? 0F : min;
				maxX = connect.isConnected( AreaType.NORTH_EAST) ? 1F : max;
				minY = connect.isConnected( AreaType.DOWN_NORTH) ? 0F : min;
				maxY = connect.isConnected( AreaType.UP_NORTH) ? 1F : max;
				if (connect.isEdged( AreaType.DOWN_NORTH)) {
					minY -= mHigh;
				}
				if (connect.isEdged( AreaType.UP_NORTH)) {
					maxY += mHigh;
				}
				if (connect.isEdged( AreaType.NORTH_EAST)) {
					maxX += mHigh;
				}
				blk.setBlockBounds( minX, minS, 0F, maxX, maxS, mHigh);
				renderStandard( rndrBlk, blk, IDirection.DIR_NORTH, meta, x, y, z);
				blk.setBlockBounds( minS, minY, 0F, maxS, maxY, mHigh);
				renderStandard( rndrBlk, blk, IDirection.DIR_NORTH, meta, x, y, z);
				break;
			case SOUTH:
				minX = connect.isConnected( AreaType.SOUTH_WEST) ? 0F : min;
				maxX = connect.isConnected( AreaType.SOUTH_EAST) ? 1F : max;
				minY = connect.isConnected( AreaType.DOWN_SOUTH) ? 0F : min;
				maxY = connect.isConnected( AreaType.UP_SOUTH) ? 1F : max;
				if (connect.isEdged( AreaType.DOWN_SOUTH)) {
					minY -= mHigh;
				}
				if (connect.isEdged( AreaType.UP_SOUTH)) {
					maxY += mHigh;
				}
				if (connect.isEdged( AreaType.SOUTH_WEST)) {
					minX -= mHigh;
				}
				blk.setBlockBounds( minX, minS, 1F - mHigh, maxX, maxS, 1F);
				renderStandard( rndrBlk, blk, IDirection.DIR_SOUTH, meta, x, y, z);
				blk.setBlockBounds( minS, minY, 1F - mHigh, maxS, maxY, 1F);
				renderStandard( rndrBlk, blk, IDirection.DIR_SOUTH, meta, x, y, z);
				break;
			case WEST:
				minY = connect.isConnected( AreaType.DOWN_WEST) ? 0F : min;
				maxY = connect.isConnected( AreaType.UP_WEST) ? 1F : max;
				minZ = connect.isConnected( AreaType.NORTH_WEST) ? 0F : min;
				maxZ = connect.isConnected( AreaType.SOUTH_WEST) ? 1F : max;
				if (connect.isEdged( AreaType.DOWN_WEST)) {
					minY -= mHigh;
				}
				if (connect.isEdged( AreaType.UP_WEST)) {
					maxY += mHigh;
				}
				if (connect.isEdged( AreaType.NORTH_WEST)) {
					minZ -= mHigh;
				}
				blk.setBlockBounds( 0F, minY, minS, mHigh, maxY, maxS);
				renderStandard( rndrBlk, blk, IDirection.DIR_WEST, meta, x, y, z);
				blk.setBlockBounds( 0F, minS, minZ, mHigh, maxS, maxZ);
				renderStandard( rndrBlk, blk, IDirection.DIR_WEST, meta, x, y, z);
				break;
			case EAST:
				minY = connect.isConnected( AreaType.DOWN_EAST) ? 0F : min;
				maxY = connect.isConnected( AreaType.UP_EAST) ? 1F : max;
				minZ = connect.isConnected( AreaType.NORTH_EAST) ? 0F : min;
				maxZ = connect.isConnected( AreaType.SOUTH_EAST) ? 1F : max;
				if (connect.isEdged( AreaType.DOWN_EAST)) {
					minY -= mHigh;
				}
				if (connect.isEdged( AreaType.UP_EAST)) {
					maxY += mHigh;
				}
				if (connect.isEdged( AreaType.SOUTH_EAST)) {
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
		IConnetable connect = tile.getConnet();
		boolean isConn = connect.isConnection( area);
		float minS = 0.5F - mThickness;
		float maxS = 0.5F + mThickness;
		float min = isConn ? minS : mCrossness;
		float max = isConn ? maxS : 1F - mCrossness;
		float minX, maxX;
		float minY, maxY;
		float minZ, maxZ;
		switch (area) {
			case DOWN:
				minX = connect.isConnected( AreaType.DOWN_WEST) ? 0F : min;
				maxX = connect.isConnected( AreaType.DOWN_EAST) ? 1F : max;
				minZ = connect.isConnected( AreaType.DOWN_NORTH) ? 0F : min;
				maxZ = connect.isConnected( AreaType.DOWN_SOUTH) ? 1F : max;
				blk.setBlockBounds( minX, 0F, minZ, maxX, mHigh, maxZ);
//				blk.setBlockBounds( 0F, 0F, 0F, 1F, mHigh, 1F);
				break;
			case UP:
				minX = connect.isConnected( AreaType.UP_WEST) ? 0F : min;
				maxX = connect.isConnected( AreaType.UP_EAST) ? 1F : max;
				minZ = connect.isConnected( AreaType.UP_NORTH) ? 0F : min;
				maxZ = connect.isConnected( AreaType.UP_SOUTH) ? 1F : max;
				blk.setBlockBounds( minX, 1F - mHigh, minZ, maxX, 1F, maxZ);
//				blk.setBlockBounds( 0F, 1F - mHigh, 0F, 1F, 1F, 1F);
				break;
			case NORTH:
				minX = connect.isConnected( AreaType.NORTH_WEST) ? 0F : min;
				maxX = connect.isConnected( AreaType.NORTH_EAST) ? 1F : max;
				minY = connect.isConnected( AreaType.DOWN_NORTH) ? 0F : min;
				maxY = connect.isConnected( AreaType.UP_NORTH) ? 1F : max;
				blk.setBlockBounds( minX, minY, 0F, maxX, maxY, mHigh);
//				blk.setBlockBounds( 0F, 0F, 0F, 1F, 1F, mHigh);
				break;
			case SOUTH:
				minX = connect.isConnected( AreaType.SOUTH_WEST) ? 0F : min;
				maxX = connect.isConnected( AreaType.SOUTH_EAST) ? 1F : max;
				minY = connect.isConnected( AreaType.DOWN_SOUTH) ? 0F : min;
				maxY = connect.isConnected( AreaType.UP_SOUTH) ? 1F : max;
				blk.setBlockBounds( minX, minY, 1F - mHigh, maxX, maxY, 1F);
//				blk.setBlockBounds( 0F, 0F, 1F - mHigh, 1F, 1F, 1F);
				break;
			case WEST:
				minY = connect.isConnected( AreaType.DOWN_WEST) ? 0F : min;
				maxY = connect.isConnected( AreaType.UP_WEST) ? 1F : max;
				minZ = connect.isConnected( AreaType.NORTH_WEST) ? 0F : min;
				maxZ = connect.isConnected( AreaType.SOUTH_WEST) ? 1F : max;
				blk.setBlockBounds( 0F, minY, minZ, mHigh, maxY, maxZ);
//				blk.setBlockBounds( 0F, 0F, 0F, mHigh, 1F, 1F);
				break;
			case EAST:
				minY = connect.isConnected( AreaType.DOWN_EAST) ? 0F : min;
				maxY = connect.isConnected( AreaType.UP_EAST) ? 1F : max;
				minZ = connect.isConnected( AreaType.NORTH_EAST) ? 0F : min;
				maxZ = connect.isConnected( AreaType.SOUTH_EAST) ? 1F : max;
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
