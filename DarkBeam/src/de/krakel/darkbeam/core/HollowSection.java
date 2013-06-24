/**
 * Dark Beam
 * HollowSection.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import de.krakel.darkbeam.client.renderer.MaskHollowRenderer;

public class HollowSection extends ASection {
	public HollowSection( int nr) {
		super( "hollow." + nr, new MaskHollowRenderer( nr));
	}
}
