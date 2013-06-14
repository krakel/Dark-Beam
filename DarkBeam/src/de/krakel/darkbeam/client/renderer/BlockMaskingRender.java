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
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

import de.krakel.darkbeam.core.Mask;
import de.krakel.darkbeam.core.MaskLib;

public class BlockMaskingRender implements ISimpleBlockRenderingHandler {
	public static final int ID = RenderingRegistry.getNextAvailableRenderId();

	public BlockMaskingRender() {
	}

	@Override
	public int getRenderId() {
		return ID;
	}

	@Override
	public void renderInventoryBlock( Block blk, int meta, int modelID, RenderBlocks rndr) {
		Mask mask = MaskLib.getForDmg( meta);
		mask.renderItem( blk, meta, rndr);
	}

	@Override
	public boolean renderWorldBlock( IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks rndr) {
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}
}
