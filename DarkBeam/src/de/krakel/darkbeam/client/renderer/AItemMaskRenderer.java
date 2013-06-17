/**
 * Dark Beam
 * AItemMaskRenderer.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;

import org.lwjgl.opengl.GL11;

import de.krakel.darkbeam.core.IDirection;

abstract class AItemMaskRenderer implements IMaskRenderer, IDirection {
	public AItemMaskRenderer() {
	}

	@Override
	public void renderItem( RenderBlocks rndrBlk, Block blk, int meta) {
//		GL11.glRotatef( 90F, 0F, 1F, 0F);
		GL11.glTranslatef( -0.5F, -0.5F, -0.5F);
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.setNormal( 0F, -1F, 0F);
		rndrBlk.renderFaceYNeg( blk, 0D, 0D, 0D, rndrBlk.getBlockIconFromSideAndMetadata( blk, DIR_UP, meta));
		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal( 0F, 1F, 0F);
		rndrBlk.renderFaceYPos( blk, 0D, 0D, 0D, rndrBlk.getBlockIconFromSideAndMetadata( blk, DIR_DOWN, meta));
		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal( 0F, 0F, -1F);
		rndrBlk.renderFaceZNeg( blk, 0D, 0D, 0D, rndrBlk.getBlockIconFromSideAndMetadata( blk, DIR_NORTH, meta));
		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal( 0F, 0F, 1F);
		rndrBlk.renderFaceZPos( blk, 0D, 0D, 0D, rndrBlk.getBlockIconFromSideAndMetadata( blk, DIR_SOUTH, meta));
		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal( -1F, 0F, 0F);
		rndrBlk.renderFaceXNeg( blk, 0D, 0D, 0D, rndrBlk.getBlockIconFromSideAndMetadata( blk, DIR_WEST, meta));
		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal( 1F, 0F, 0F);
		rndrBlk.renderFaceXPos( blk, 0D, 0D, 0D, rndrBlk.getBlockIconFromSideAndMetadata( blk, DIR_EAST, meta));
		tess.draw();
		GL11.glTranslatef( 0.5F, 0.5F, 0.5F);
	}

	@Override
	public void renderSide( RenderBlocks rndrBlk, int side, Block blk, int meta, int x, int y, int z) {
		Icon icon = rndrBlk.getBlockIconFromSideAndMetadata( blk, side, meta);
		rndrBlk.setOverrideBlockTexture( icon);
		rndrBlk.renderStandardBlock( blk, x, y, z);
		rndrBlk.clearOverrideBlockTexture();
	}

	protected void setBounds( Block blk, int side, float thickness) {
		switch (side) {
			case DIR_DOWN:
				blk.setBlockBounds( 0F, 0F, 0F, 1F, thickness, 1F);
				break;
			case DIR_UP:
				blk.setBlockBounds( 0F, 1F - thickness, 0F, 1F, 1F, 1F);
				break;
			case DIR_NORTH:
				blk.setBlockBounds( 0F, 0F, 0F, 1F, 1F, thickness);
				break;
			case DIR_SOUTH:
				blk.setBlockBounds( 0F, 0F, 1F - thickness, 1F, 1F, 1F);
				break;
			case DIR_WEST:
				blk.setBlockBounds( 0F, 0F, 0F, thickness, 1F, 1F);
				break;
			case DIR_EAST:
				blk.setBlockBounds( 1F - thickness, 0F, 0F, 1F, 1F, 1F);
				break;
			default:
				blk.setBlockBounds( 0F, 0F, 0F, 1F, 1F, 1F);
				break;
		}
	}

	protected void setBounds( RenderBlocks rndrBlk, int side, double thickness) {
		switch (side) {
			case DIR_DOWN:
				rndrBlk.setRenderBounds( 0D, 0D, 0D, 1D, thickness, 1D);
				break;
			case DIR_UP:
				rndrBlk.setRenderBounds( 0D, 1D - thickness, 0D, 1D, 1D, 1D);
				break;
			case DIR_NORTH:
				rndrBlk.setRenderBounds( 0D, 0D, 0D, 1D, 1D, thickness);
				break;
			case DIR_SOUTH:
				rndrBlk.setRenderBounds( 0D, 0D, 1D - thickness, 1D, 1D, 1D);
				break;
			case DIR_WEST:
				rndrBlk.setRenderBounds( 0D, 0D, 0D, thickness, 1D, 1D);
				break;
			case DIR_EAST:
				rndrBlk.setRenderBounds( 1D - thickness, 0D, 0D, 1D, 1D, 1D);
				break;
			default:
				rndrBlk.setRenderBounds( 0D, 0D, 0D, 1D, 1D, 1D);
				break;
		}
	}

	protected void setInventoryBounds( RenderBlocks rndr, double thickness) {
		rndr.setRenderBounds( 0D, 0D, 0.5D - thickness, 1D, 1D, 0.5D + thickness);
	}
}
