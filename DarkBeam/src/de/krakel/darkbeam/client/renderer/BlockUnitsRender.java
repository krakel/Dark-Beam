/**
 * Dark Beam
 * BlockUnitsRender.java
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
import de.krakel.darkbeam.core.Position;

public class BlockUnitsRender implements ISimpleBlockRenderingHandler {
	public static final int ID = RenderingRegistry.getNextAvailableRenderId();

	public BlockUnitsRender() {
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
		GL11.glRotatef( 90F, 0F, 1F, 0F);
		GL11.glTranslatef( -0.5F, -0.5F, -0.5F);
		for (int side = 0; side < IDirection.DIR_MAX; ++side) {
			tess.startDrawingQuads();
			tess.setNormal( Position.normX( side), Position.normY( side), Position.normZ( side));
			renderer.renderFaceYNeg( blk, 0D, 0D, 0D, renderer.getBlockIconFromSideAndMetadata( blk, side, meta));
			tess.draw();
		}
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
