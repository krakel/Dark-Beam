/**
 * Dark Beam
 * ItemPanelRenderer.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.client.renderer;

import net.minecraft.client.renderer.RenderBlocks;

public class ItemPanelRenderer extends AItemMaskRenderer {
	private static final double THICKNESS = 1D / 8D;

	public ItemPanelRenderer() {
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
