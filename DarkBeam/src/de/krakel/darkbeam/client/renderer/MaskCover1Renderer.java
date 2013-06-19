/**
 * Dark Beam
 * MaskCover1Renderer.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.client.renderer;

import de.krakel.darkbeam.core.IArea;

public class MaskCover1Renderer extends ACoverRenderer implements IArea {
	private static final float THICKNESS = 1F / 16F;

	public MaskCover1Renderer() {
		super( THICKNESS);
	}
}
