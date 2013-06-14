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

abstract class AItemMaskRenderer implements IItemRenderer {
	public AItemMaskRenderer() {
	}

	protected void renderMask( Block blk, int meta, RenderBlocks rndr) {
//		GL11.glRotatef( 90F, 0F, 1F, 0F);
		GL11.glTranslatef( -0.5F, -0.5F, -0.5F);
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.setNormal( 0F, -1F, 0F);
		rndr.renderFaceYNeg( blk, 0D, 0D, 0D, rndr.getBlockIconFromSideAndMetadata( blk, IDirection.DIR_UP, meta));
		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal( 0F, 1F, 0F);
		rndr.renderFaceYPos( blk, 0D, 0D, 0D, rndr.getBlockIconFromSideAndMetadata( blk, IDirection.DIR_DOWN, meta));
		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal( 0F, 0F, -1F);
		rndr.renderFaceZNeg( blk, 0D, 0D, 0D, rndr.getBlockIconFromSideAndMetadata( blk, IDirection.DIR_NORTH, meta));
		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal( 0F, 0F, 1F);
		rndr.renderFaceZPos( blk, 0D, 0D, 0D, rndr.getBlockIconFromSideAndMetadata( blk, IDirection.DIR_SOUTH, meta));
		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal( -1F, 0F, 0F);
		rndr.renderFaceXNeg( blk, 0D, 0D, 0D, rndr.getBlockIconFromSideAndMetadata( blk, IDirection.DIR_WEST, meta));
		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal( 1F, 0F, 0F);
		rndr.renderFaceXPos( blk, 0D, 0D, 0D, rndr.getBlockIconFromSideAndMetadata( blk, IDirection.DIR_EAST, meta));
		tess.draw();
		GL11.glTranslatef( 0.5F, 0.5F, 0.5F);
	}
}
