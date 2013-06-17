/**
 * Dark Beam
 * ItemCoverRenderer.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;

public class ItemCoverRenderer extends AItemMaskRenderer {
	private static final float THICKNESS = 1F / 16F;

	public ItemCoverRenderer() {
	}

	@Override
	public void setBounds( RenderBlocks rndrBlk, int side) {
		setBounds( rndrBlk, side, THICKNESS + THICKNESS);
	}

	@Override
	public void setInventoryBounds( RenderBlocks rndrBlk) {
		setInventoryBounds( rndrBlk, THICKNESS);
	}

	@Override
	public void setMaskBounds( Block blk, int side) {
		setBounds( blk, side, THICKNESS + THICKNESS);
	}
}
