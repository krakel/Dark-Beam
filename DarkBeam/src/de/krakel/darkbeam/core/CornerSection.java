/**
 * Dark Beam
 * CornerSection.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import de.krakel.darkbeam.client.renderer.MaskCornerRenderer;

public class CornerSection extends ASection {
	public CornerSection( int nr) {
		super( "corner." + nr, new MaskCornerRenderer( nr));
	}
}
