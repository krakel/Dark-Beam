/**
 * Dark Beam
 * ItemCoverRenderer.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.client.renderer;

import net.minecraft.client.renderer.RenderBlocks;

public class ItemCoverRenderer extends AItemMaskRenderer {
	private static final double THICKNESS = 1D / 16D;

	public ItemCoverRenderer() {
	}

	@Override
	public void setBounds( RenderBlocks rndr, int side) {
		setBounds( rndr, side, THICKNESS + THICKNESS);
	}

	@Override
	public void setInventoryBounds( RenderBlocks rndr) {
		setInventoryBounds( rndr, THICKNESS);
	}
}
