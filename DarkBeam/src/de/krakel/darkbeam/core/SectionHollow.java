/**
 * Dark Beam
 * SectionHollow.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import net.minecraft.block.Block;

import de.krakel.darkbeam.client.renderer.ASectionRenderer;
import de.krakel.darkbeam.client.renderer.SectionHollowRenderer;

class SectionHollow extends ASectionCover {
	private SectionHollowRenderer mRenderer;

	public SectionHollow( int nr) {
		super( "hollow." + nr);
		mRenderer = new SectionHollowRenderer( nr);
	}

	@Override
	public ASectionRenderer getRenderer() {
		return mRenderer;
	}

	@Override
	public void setSectionBounds( int area, Block blk) {
		mRenderer.setSectionBounds( area, blk);
	}
}
