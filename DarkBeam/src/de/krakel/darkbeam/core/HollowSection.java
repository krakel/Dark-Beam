/**
 * Dark Beam
 * HollowSection.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import net.minecraft.block.Block;

import de.krakel.darkbeam.client.renderer.AMaskRenderer;
import de.krakel.darkbeam.client.renderer.MaskHollowRenderer;

class HollowSection extends ACoverSection {
	private MaskHollowRenderer mRenderer;

	public HollowSection( int nr) {
		super( "hollow." + nr);
		mRenderer = new MaskHollowRenderer( nr);
	}

	@Override
	public AMaskRenderer getRenderer() {
		return mRenderer;
	}

	@Override
	public void setSectionBounds( int area, Block blk) {
		mRenderer.setSectionBounds( area, blk);
	}
}
