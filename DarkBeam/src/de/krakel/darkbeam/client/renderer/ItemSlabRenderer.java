/**
 * Dark Beam
 * ItemSlabRenderer.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.client.renderer;

import net.minecraft.client.renderer.RenderBlocks;

public class ItemSlabRenderer extends AItemMaskRenderer {
	private static final double THICKNESS = 1D / 4D;

	public ItemSlabRenderer() {
	}

	@Override
	public void setBounds( RenderBlocks rndr, int side) {
		setBounds( rndr, side, THICKNESS);
	}

	@Override
	public void setInventoryBounds( RenderBlocks rndr) {
		setInventoryBounds( rndr, THICKNESS);
	}
}
