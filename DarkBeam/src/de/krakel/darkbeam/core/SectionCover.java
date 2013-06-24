/**
 * Dark Beam
 * SectionCover.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import net.minecraft.block.Block;

import de.krakel.darkbeam.client.renderer.ASectionRenderer;
import de.krakel.darkbeam.client.renderer.SectionCoverRenderer;

class SectionCover extends ASectionCover {
	private SectionCoverRenderer mRenderer;

	public SectionCover( int nr) {
		super( "cover." + nr);
		mRenderer = new SectionCoverRenderer( nr);
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
