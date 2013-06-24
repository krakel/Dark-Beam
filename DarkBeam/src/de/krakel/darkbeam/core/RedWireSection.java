/**
 * Dark Beam
 * RedWireSection.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import de.krakel.darkbeam.client.renderer.MaskRedWireRender;

public class RedWireSection extends ASection {
	public RedWireSection() {
		super( "db.redwire", new MaskRedWireRender());
	}
}
