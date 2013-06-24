/**
 * Dark Beam
 * CoverSection.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import de.krakel.darkbeam.client.renderer.AMaskRenderer;
import de.krakel.darkbeam.client.renderer.MaskCoverRenderer;

public class CoverSection extends ACoverSection {
	private MaskCoverRenderer mRenderer;

	public CoverSection( int nr) {
		super( SectionLib.nextID(), "cover." + nr);
		mRenderer = new MaskCoverRenderer( nr);
	}

	@Override
	public AMaskRenderer getRenderer() {
		return mRenderer;
	}
}
