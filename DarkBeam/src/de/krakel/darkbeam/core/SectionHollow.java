/**
 * Dark Beam
 * SectionHollow.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import de.krakel.darkbeam.client.renderer.SectionHollowRenderer;

class SectionHollow extends ASectionCover {
	public SectionHollow( int nr) {
		super( "hollow." + nr, new SectionHollowRenderer( nr));
	}
}
