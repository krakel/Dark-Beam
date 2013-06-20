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

import de.krakel.darkbeam.core.IArea;
import de.krakel.darkbeam.core.IDirection;

abstract class AMaskRenderer implements IMaskRenderer, IDirection, IArea {
	protected AMaskRenderer() {
	}

	protected void renderStandard( RenderBlocks rndrBlk, Block blk, int side, int meta, int x, int y, int z) {
		rndrBlk.setRenderBoundsFromBlock( blk);
		Icon icon = rndrBlk.getBlockIconFromSideAndMetadata( blk, side, meta);
		rndrBlk.setOverrideBlockTexture( icon);
		rndrBlk.renderStandardBlock( blk, x, y, z);
		rndrBlk.clearOverrideBlockTexture();
	}

	protected void renderStandardInventory( RenderBlocks rndrBlk, Block blk, int meta) {
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
}
