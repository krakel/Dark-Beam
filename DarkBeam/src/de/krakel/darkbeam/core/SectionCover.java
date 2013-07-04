/**
 * Dark Beam
 * SectionCover.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import de.krakel.darkbeam.client.renderer.SectionCoverRenderer;

public class SectionCover extends ASectionCover {
	SectionCover( int nr) {
		super( "cover." + nr, new SectionCoverRenderer( nr));
	}
}
