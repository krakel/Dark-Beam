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

import org.lwjgl.opengl.GL11;

import de.krakel.darkbeam.core.IDirection;

abstract class AItemMaskRenderer implements IItemRenderer, IDirection {
	public AItemMaskRenderer() {
	}

	@Override
	public void render( Block blk, int meta, RenderBlocks rndr) {
//		GL11.glRotatef( 90F, 0F, 1F, 0F);
		GL11.glTranslatef( -0.5F, -0.5F, -0.5F);
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.setNormal( 0F, -1F, 0F);
		rndr.renderFaceYNeg( blk, 0D, 0D, 0D, rndr.getBlockIconFromSideAndMetadata( blk, DIR_UP, meta));
		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal( 0F, 1F, 0F);
		rndr.renderFaceYPos( blk, 0D, 0D, 0D, rndr.getBlockIconFromSideAndMetadata( blk, DIR_DOWN, meta));
		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal( 0F, 0F, -1F);
		rndr.renderFaceZNeg( blk, 0D, 0D, 0D, rndr.getBlockIconFromSideAndMetadata( blk, DIR_NORTH, meta));
		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal( 0F, 0F, 1F);
		rndr.renderFaceZPos( blk, 0D, 0D, 0D, rndr.getBlockIconFromSideAndMetadata( blk, DIR_SOUTH, meta));
		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal( -1F, 0F, 0F);
		rndr.renderFaceXNeg( blk, 0D, 0D, 0D, rndr.getBlockIconFromSideAndMetadata( blk, DIR_WEST, meta));
		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal( 1F, 0F, 0F);
		rndr.renderFaceXPos( blk, 0D, 0D, 0D, rndr.getBlockIconFromSideAndMetadata( blk, DIR_EAST, meta));
		tess.draw();
		GL11.glTranslatef( 0.5F, 0.5F, 0.5F);
	}

	protected void setBounds( RenderBlocks rndr, int side, double thickness) {
		switch (side) {
			case DIR_DOWN:
				rndr.setRenderBounds( 0D, 0D, 0D, 1D, thickness + thickness, 1D);
				break;
			case DIR_UP:
				rndr.setRenderBounds( 0D, 0D, 0D, 1D, 1D - thickness - thickness, 1D);
				break;
			case DIR_NORTH:
				rndr.setRenderBounds( 0D, 0D, 0D, 1D, 1D, thickness + thickness);
				break;
			case DIR_SOUTH:
				rndr.setRenderBounds( 0D, 0D, 0D, 1D, 1D, 1D - thickness - thickness);
				break;
			case DIR_WEST:
				rndr.setRenderBounds( 0D, 0D, 0D, thickness + thickness, 1D, 1D);
				break;
			case DIR_EAST:
				rndr.setRenderBounds( 0D, 0D, 0D, 1D - thickness - thickness, 1D, 1D);
				break;
			default:
				rndr.setRenderBounds( 0D, 0D, 0D, 1D, 1D, 1D);
				break;
		}
	}

	protected void setInventoryBounds( RenderBlocks rndr, double thickness) {
		rndr.setRenderBounds( 0D, 0D, 0.5D - thickness, 1D, 1D, 0.5D + thickness);
	}
}
