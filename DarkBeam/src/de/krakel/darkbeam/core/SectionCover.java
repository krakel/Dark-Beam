/**
 * Dark Beam
 * SectionCover.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import de.krakel.darkbeam.client.renderer.SectionCoverRenderer;

class SectionCover extends ASectionCover {
	public SectionCover( int nr) {
		super( "cover." + nr, new SectionCoverRenderer( nr));
	}
}
