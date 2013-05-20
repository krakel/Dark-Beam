/**
 * Dark Beam
 * BlockRedWireRender.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.client.renderer.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

import de.krakel.darkbeam.core.block.BlockRedWire;

public class BlockRedWireRender implements ISimpleBlockRenderingHandler {
	public static final int ID = RenderingRegistry.getNextAvailableRenderId();

	@Override
	public int getRenderId() {
		return ID;
	}

	@Override
	public void renderInventoryBlock( Block block, int metadata, int modelID, RenderBlocks renderer) {
	}

	@Override
	public boolean renderWorldBlock( IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		block.setBlockBounds( BlockRedWire.MIN_SIZE, BlockRedWire.MIN_SIZE, BlockRedWire.MIN_SIZE, BlockRedWire.MAX_SIZE, BlockRedWire.MAX_SIZE, BlockRedWire.MAX_SIZE);
		renderer.setRenderBoundsFromBlock( block);
		renderer.renderStandardBlock( block, x, y, z);
		block.setBlockBounds( 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return false;
	}
}
