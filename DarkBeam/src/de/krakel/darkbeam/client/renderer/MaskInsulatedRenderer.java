/**
 * Dark Beam
 * MaskInsulatedRenderer.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.Item;
import net.minecraft.util.Icon;

import de.krakel.darkbeam.core.ISection;
import de.krakel.darkbeam.core.Insulate;
import de.krakel.darkbeam.core.InsulateLib;
import de.krakel.darkbeam.core.helper.LogHelper;
import de.krakel.darkbeam.tile.TileStage;

public class MaskInsulatedRenderer extends AWireRenderer {
	public MaskInsulatedRenderer() {
		super( 2);
	}

	@Override
	public int getBlockID( int dmg) {
		return Item.dyePowder.itemID;
	}

	@Override
	public Icon getIcon( int side, int dmg) {
		Insulate insu = InsulateLib.getForDmg( dmg);
		return insu.getIcon( side);
	}

	@Override
	public String getNameForSection( ISection sec, int dmg) {
		Insulate insu = InsulateLib.getForDmg( dmg);
		return insu.getInsuName( sec);
	}

	@Override
	public void renderSide( RenderBlocks rndrBlk, int area, Block blk, int meta, int x, int y, int z, TileStage tile) {
		boolean sideDown = tile.isSided( area, meta, SIDE_DOWN, x, y, z);
		boolean sideUp = tile.isSided( area, meta, SIDE_UP, x, y, z);
		boolean sideNorth = tile.isSided( area, meta, SIDE_NORTH, x, y, z);
		boolean sideSouth = tile.isSided( area, meta, SIDE_SOUTH, x, y, z);
		boolean sideWest = tile.isSided( area, meta, SIDE_WEST, x, y, z);
		boolean sideEast = tile.isSided( area, meta, SIDE_EAST, x, y, z);
		boolean toDown = tile.isConnect( area, meta, SIDE_DOWN, x, y, z);
		boolean toUp = tile.isConnect( area, meta, SIDE_UP, x, y, z);
		boolean toNorth = tile.isConnect( area, meta, SIDE_NORTH, x, y, z);
		boolean toSouth = tile.isConnect( area, meta, SIDE_SOUTH, x, y, z);
		boolean toWest = tile.isConnect( area, meta, SIDE_WEST, x, y, z);
		boolean toEast = tile.isConnect( area, meta, SIDE_EAST, x, y, z);
		boolean isSided = sideDown || sideUp || sideNorth || sideSouth || sideWest || sideEast;
		boolean isConnected = toDown || toUp || toNorth || toSouth || toWest || toEast || isSided;
		float minS = 0.5F - mThickness;
		float maxS = 0.5F + mThickness;
		float min = isConnected ? minS : 0.2F;
		float max = isConnected ? maxS : 0.8F;
		float minX = toWest || sideWest ? 0F : min;
		float maxX = toEast || sideEast ? 1F : max;
		float minY = toDown || sideDown ? 0F : min;
		float maxY = toUp || sideUp ? 1F : max;
		float minZ = toNorth || sideNorth ? 0F : min;
		float maxZ = toSouth || sideSouth ? 1F : max;
		switch (area) {
			case SIDE_DOWN:
				blk.setBlockBounds( minX, 0F, minS, maxX, mSize, maxS);
				renderStandard( rndrBlk, blk, DIR_SOUTH, meta, x, y, z);
				blk.setBlockBounds( minS, 0F, minZ, maxS, mSize, maxZ);
				renderStandard( rndrBlk, blk, DIR_SOUTH, meta, x, y, z);
				break;
			case SIDE_UP:
				blk.setBlockBounds( minX, 1F - mSize, minS, maxX, 1F, maxS);
				renderStandard( rndrBlk, blk, DIR_SOUTH, meta, x, y, z);
				blk.setBlockBounds( minS, 1F - mSize, minZ, maxS, 1F, maxZ);
				renderStandard( rndrBlk, blk, DIR_SOUTH, meta, x, y, z);
				break;
			case SIDE_NORTH:
				if (sideDown) {
					minY -= mSize;
				}
				if (sideUp) {
					maxY += mSize;
				}
				if (sideEast) {
					maxX += mSize;
				}
				blk.setBlockBounds( minX, minS, 0F, maxX, maxS, mSize);
				renderStandard( rndrBlk, blk, DIR_SOUTH, meta, x, y, z);
				blk.setBlockBounds( minS, minY, 0F, maxS, maxY, mSize);
				renderStandard( rndrBlk, blk, DIR_SOUTH, meta, x, y, z);
				break;
			case SIDE_SOUTH:
				if (sideDown) {
					minY -= mSize;
				}
				if (sideUp) {
					maxY += mSize;
				}
				if (sideWest) {
					minX -= mSize;
				}
				blk.setBlockBounds( minX, minS, 1F - mSize, maxX, maxS, 1F);
				renderStandard( rndrBlk, blk, DIR_SOUTH, meta, x, y, z);
				blk.setBlockBounds( minS, minY, 1F - mSize, maxS, maxY, 1F);
				renderStandard( rndrBlk, blk, DIR_SOUTH, meta, x, y, z);
				break;
			case SIDE_WEST:
				if (sideDown) {
					minY -= mSize;
				}
				if (sideUp) {
					maxY += mSize;
				}
				if (sideNorth) {
					minZ -= mSize;
				}
				blk.setBlockBounds( 0F, minY, minS, mSize, maxY, maxS);
				renderStandard( rndrBlk, blk, DIR_SOUTH, meta, x, y, z);
				blk.setBlockBounds( 0F, minS, minZ, mSize, maxS, maxZ);
				renderStandard( rndrBlk, blk, DIR_SOUTH, meta, x, y, z);
				break;
			case SIDE_EAST:
				if (sideDown) {
					minY -= mSize;
				}
				if (sideUp) {
					maxY += mSize;
				}
				if (sideSouth) {
					maxZ += mSize;
				}
				blk.setBlockBounds( 1F - mSize, minY, minS, 1F, maxY, maxS);
				renderStandard( rndrBlk, blk, DIR_SOUTH, meta, x, y, z);
				blk.setBlockBounds( 1F - mSize, minS, minZ, 1F, maxS, maxZ);
				renderStandard( rndrBlk, blk, DIR_SOUTH, meta, x, y, z);
				break;
			default:
				blk.setBlockBounds( 0F, 0F, 0F, 1F, 1F, 1F);
				renderStandard( rndrBlk, blk, DIR_SOUTH, meta, x, y, z);
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
