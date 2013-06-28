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

import com.sun.istack.internal.NotNull;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

import de.krakel.darkbeam.core.DarkLib;
import de.krakel.darkbeam.core.ISection;
import de.krakel.darkbeam.core.SectionLib;
import de.krakel.darkbeam.tile.TileStage;

public class BlockStageRender implements ISimpleBlockRenderingHandler {
	public static final int ID = RenderingRegistry.getNextAvailableRenderId();

	public BlockStageRender() {
	}

	@Override
	public int getRenderId() {
		return ID;
	}

	@Override
	public void renderInventoryBlock( Block blk, int dmg, int modelID, RenderBlocks rndrBlk) {
		ISection sec = SectionLib.getForDmg( dmg);
		sec.renderItem( rndrBlk, blk, dmg);
	}

	@Override
	public boolean renderWorldBlock( @NotNull IBlockAccess world, int x, int y, int z, Block blk, int modelID, RenderBlocks rndrBlk) {
//		LogHelper.info( "renderWorldBlockA: %s", LogHelper.toString( x, y, z));
		TileStage tile = DarkLib.getTileEntity( world, x, y, z, TileStage.class);
		if (tile != null) {
//			LogHelper.info( "renderWorldBlockB: %s", tile);
			tile.refresh();
			for (int area : tile) {
//				LogHelper.info( "renderWorldBlock: side=%s", Position.toString( side));
				int dmg = tile.getMeta( area);
				ISection sec = SectionLib.getForDmg( dmg);
				sec.renderSide( rndrBlk, area, blk, dmg, x, y, z, tile);
			}
		}
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}
}
