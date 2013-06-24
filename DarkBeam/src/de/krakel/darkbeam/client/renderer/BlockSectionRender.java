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
import de.krakel.darkbeam.core.ISection;
import de.krakel.darkbeam.core.SectionLib;
import de.krakel.darkbeam.core.helper.LogHelper;
import de.krakel.darkbeam.tile.TileStage;

public class BlockSectionRender implements ISimpleBlockRenderingHandler {
	public static final int ID = RenderingRegistry.getNextAvailableRenderId();

	public BlockSectionRender() {
	}

	@Override
	public int getRenderId() {
		return ID;
	}

	@Override
	public void renderInventoryBlock( Block blk, int dmg, int modelID, RenderBlocks rndrBlk) {
		ISection sec = SectionLib.getForDmg( dmg);
		IMaskRenderer rndr = sec.getRenderer();
		rndr.renderItem( rndrBlk, blk, dmg);
	}

	@Override
	public boolean renderWorldBlock( IBlockAccess world, int x, int y, int z, Block blk, int modelID, RenderBlocks rndrBlk) {
		LogHelper.info( "renderWorldBlockA: %s", LogHelper.toString( x, y, z));
		TileStage tile = DarkLib.getTileEntity( world, x, y, z, TileStage.class);
		if (tile != null) {
			LogHelper.info( "renderWorldBlockB: %s", tile);
			for (int i : tile) {
//				LogHelper.info( "renderWorldBlock: side=%s", Position.toString( side));
				int dmg = tile.getMeta( i);
				ISection sec = SectionLib.getForDmg( dmg);
				IMaskRenderer rndr = sec.getRenderer();
				rndr.renderSide( rndrBlk, i, blk, dmg, x, y, z, tile);
			}
		}
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}
}
