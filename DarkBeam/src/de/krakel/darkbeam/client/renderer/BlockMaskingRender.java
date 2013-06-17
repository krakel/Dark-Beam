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

import de.krakel.darkbeam.core.DarkLib;
import de.krakel.darkbeam.core.MaskLib;
import de.krakel.darkbeam.core.Position;
import de.krakel.darkbeam.core.helper.LogHelper;
import de.krakel.darkbeam.tile.TileMasking;

public class BlockMaskingRender implements ISimpleBlockRenderingHandler {
	public static final int ID = RenderingRegistry.getNextAvailableRenderId();

	public BlockMaskingRender() {
	}

	@Override
	public int getRenderId() {
		return ID;
	}

	@Override
	public void renderInventoryBlock( Block blk, int meta, int modelID, RenderBlocks rndrBlk) {
		IMaskRenderer rndr = MaskLib.getRendererForDmg( meta);
		rndr.setInventoryBounds( rndrBlk);
		rndr.renderItem( rndrBlk, blk, meta);
	}

	@Override
	public boolean renderWorldBlock( IBlockAccess world, int x, int y, int z, Block blk, int modelID, RenderBlocks rndrBlk) {
		TileMasking tile = DarkLib.getTileEntity( world, x, y, z, TileMasking.class);
		if (tile != null) {
			LogHelper.info( "renderWorldBlock: %s, %s", LogHelper.toString( x, y, z), tile);
			for (int side = 0; side < TileMasking.MAX_SIDE; ++side) {
				if (tile.isInUse( side)) {
					LogHelper.info( "renderWorldBlock: side=%s", Position.toString( side));
					int meta = tile.getMeta( side);
					IMaskRenderer rndr = MaskLib.getRendererForDmg( meta);
					rndr.setBounds( rndrBlk, side);
					rndr.renderSide( rndrBlk, side, blk, meta, x, y, z);
				}
			}
		}
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}
}
