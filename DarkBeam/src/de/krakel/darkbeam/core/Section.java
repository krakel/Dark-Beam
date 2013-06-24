/**
 * Dark Beam
 * Section.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import de.krakel.darkbeam.client.renderer.IMaskRenderer;

public class Section extends ASection {
	Section( int secID, String name, IMaskRenderer renderer) {
		super( secID, name, renderer);
	}

	Section( String name, IMaskRenderer renderer) {
		super( SectionLib.nextID(), name, renderer);
	}
}
