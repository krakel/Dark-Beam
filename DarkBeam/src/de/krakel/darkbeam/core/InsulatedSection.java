/**
 * Dark Beam
 * InsulatedSection.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import de.krakel.darkbeam.client.renderer.MaskInsulatedRenderer;

public class InsulatedSection extends ASection {
	public InsulatedSection() {
		super( "insuwire", new MaskInsulatedRenderer());
	}
}
