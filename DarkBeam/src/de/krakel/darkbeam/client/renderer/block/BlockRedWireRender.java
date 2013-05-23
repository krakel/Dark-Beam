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
//		int meta = world.getBlockMetadata( x, y, z);
		boolean toWest = BlockRedWire.isPowerProviderOrWire( world, x - 1, y, z, 0);
		boolean toEast = BlockRedWire.isPowerProviderOrWire( world, x + 1, y, z, 0);
		boolean toNorth = BlockRedWire.isPowerProviderOrWire( world, x, y, z - 1, 0);
		boolean toSouth = BlockRedWire.isPowerProviderOrWire( world, x, y, z + 1, 0);
		boolean isConnected = toWest || toEast || toNorth || toSouth;
		float d = BlockRedWire.THICK / 2;
		float minS = 0.5f - d;
		float maxS = 0.5f + d;
		float min = isConnected ? minS : 0.3F;
		float max = isConnected ? maxS : 0.7F;
		float minX = toWest ? 0F : min;
		float maxX = toEast ? 1F : max;
		renderer.setRenderBounds( minX, 0F, minS, maxX, BlockRedWire.THICK, maxS);
		renderer.renderStandardBlock( block, x, y, z);
		float minZ = toNorth ? 0F : min;
		float maxZ = toSouth ? 1F : max;
		renderer.setRenderBounds( minS, 0F, minZ, maxS, BlockRedWire.THICK, maxZ);
		renderer.renderStandardBlock( block, x, y, z);
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return false;
	}
}
