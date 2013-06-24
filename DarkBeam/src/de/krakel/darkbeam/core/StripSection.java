/**
 * Dark Beam
 * StripSection.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import de.krakel.darkbeam.client.renderer.MaskStripRenderer;

public class StripSection extends ASection {
	public StripSection( int nr) {
		super( "strip." + nr, new MaskStripRenderer( nr));
	}
}
