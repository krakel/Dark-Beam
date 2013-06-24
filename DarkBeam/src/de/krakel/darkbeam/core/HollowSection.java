/**
 * Dark Beam
 * HollowSection.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

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
}
