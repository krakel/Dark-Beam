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
		float d = BlockRedWire.THICK / 2;
		renderer.setRenderBounds( 0.5F - d, 0.0F, 0.5F - d, 0.5F + d, BlockRedWire.THICK, 0.5F + d);
		renderer.renderStandardBlock( block, x, y, z);
		if (toWest) {
			renderer.setRenderBounds( 0.0F, 0.0F, 0.5F - d, 0.5F - d, BlockRedWire.THICK, 0.5F + d);
			renderer.renderStandardBlock( block, x, y, z);
		}
		if (toEast) {
			renderer.setRenderBounds( 0.5F + d, 0.0F, 0.5F - d, 1.0F, BlockRedWire.THICK, 0.5F + d);
			renderer.renderStandardBlock( block, x, y, z);
		}
		if (toNorth) {
			renderer.setRenderBounds( 0.5F - d, 0.0F, 0.0F, 0.5F + d, BlockRedWire.THICK, 0.5F - d);
			renderer.renderStandardBlock( block, x, y, z);
		}
		if (toSouth) {
			renderer.setRenderBounds( 0.5F - d, 0.0F, 0.5F + d, 0.5F + d, BlockRedWire.THICK, 1.0F);
			renderer.renderStandardBlock( block, x, y, z);
		}
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return false;
	}
}
