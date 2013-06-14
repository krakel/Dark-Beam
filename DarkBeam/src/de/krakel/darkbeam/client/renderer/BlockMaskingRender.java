/**
 * Dark Beam
 * BlockMaskingRender.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

import de.krakel.darkbeam.core.IDirection;

public class BlockMaskingRender implements ISimpleBlockRenderingHandler {
	public static final int ID = RenderingRegistry.getNextAvailableRenderId();

	public BlockMaskingRender() {
	}

	@Override
	public int getRenderId() {
		return ID;
	}

	@Override
	public void renderInventoryBlock( Block blk, int meta, int modelID, RenderBlocks renderer) {
		Tessellator tess = Tessellator.instance;
		blk.setBlockBoundsForItemRender();
		renderer.setRenderBoundsFromBlock( blk);
//		GL11.glRotatef( 90F, 0F, 1F, 0F);
		GL11.glTranslatef( -0.5F, -0.5F, -0.5F);
		tess.startDrawingQuads();
		tess.setNormal( 0F, -1F, 0F);
		renderer.renderFaceYNeg( blk, 0D, 0D, 0D, renderer.getBlockIconFromSideAndMetadata( blk, IDirection.DIR_UP, meta));
		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal( 0F, 1F, 0F);
		renderer.renderFaceYPos( blk, 0D, 0D, 0D, renderer.getBlockIconFromSideAndMetadata( blk, IDirection.DIR_DOWN, meta));
		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal( 0F, 0F, -1F);
		renderer.renderFaceZNeg( blk, 0D, 0D, 0D, renderer.getBlockIconFromSideAndMetadata( blk, IDirection.DIR_NORTH, meta));
		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal( 0F, 0F, 1F);
		renderer.renderFaceZPos( blk, 0D, 0D, 0D, renderer.getBlockIconFromSideAndMetadata( blk, IDirection.DIR_SOUTH, meta));
		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal( -1F, 0F, 0F);
		renderer.renderFaceXNeg( blk, 0D, 0D, 0D, renderer.getBlockIconFromSideAndMetadata( blk, IDirection.DIR_WEST, meta));
		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal( 1F, 0F, 0F);
		renderer.renderFaceXPos( blk, 0D, 0D, 0D, renderer.getBlockIconFromSideAndMetadata( blk, IDirection.DIR_EAST, meta));
		tess.draw();
		GL11.glTranslatef( 0.5F, 0.5F, 0.5F);
	}

	@Override
	public boolean renderWorldBlock( IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}
}
