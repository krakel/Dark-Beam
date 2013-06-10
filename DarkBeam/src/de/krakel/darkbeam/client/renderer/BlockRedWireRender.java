/**
 * Dark Beam
 * BlockRedWireRender.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

import de.krakel.darkbeam.block.BlockRedWire;
import de.krakel.darkbeam.lib.BlockType;

public class BlockRedWireRender implements ISimpleBlockRenderingHandler {
	public static final int ID = RenderingRegistry.getNextAvailableRenderId();

	//	public static boolean isPoweredOrRepeater( IBlockAccess world, int x, int y, int z, int side) {
//		if (isPowerProviderOrWire( world, x, y, z, side)) {
//			return true;
//		}
//		int id = world.getBlockId( x, y, z);
//		if (id == Block.redstoneRepeaterActive.blockID) {
//			int meta = world.getBlockMetadata( x, y, z);
//			return side == (meta & 3);
//		}
//		return false;
//	} 
//
	private static boolean isPowerProviderOrWire( IBlockAccess world, int x, int y, int z, int dir) {
		int id = world.getBlockId( x, y, z);
		if (BlockType.RedWire.getId() == id) {
			return true;
		}
		if (id == 0 || Block.redstoneRepeaterIdle.func_94487_f( id)) {
			return false;
		}
		Block blk = Block.blocksList[id];
		return blk != null && blk.canProvidePower();
	}

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
		boolean toWest = isPowerProviderOrWire( world, x - 1, y, z, 0);
		boolean toEast = isPowerProviderOrWire( world, x + 1, y, z, 0);
		boolean toNorth = isPowerProviderOrWire( world, x, y, z - 1, 0);
		boolean toSouth = isPowerProviderOrWire( world, x, y, z + 1, 0);
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
