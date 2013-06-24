/**
 * Dark Beam
 * CoverSection.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import de.krakel.darkbeam.client.renderer.MaskCoverRenderer;

public class CoverSection extends ASection {
	public CoverSection( int nr) {
		super( "cover." + nr, new MaskCoverRenderer( nr));
	}
}
