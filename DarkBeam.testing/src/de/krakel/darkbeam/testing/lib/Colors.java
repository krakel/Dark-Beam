/**
 * Dark Beam
 * FColors.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.testing.lib;

public class Colors {
	public static final String PREFIX_GRAY = "\u00a77";
	public static final String PREFIX_WHITE = "\u00a7f";
	public static final String PREFIX_YELLOW = "\u00a7e";
	public static final String PURE_WHITE = "ffffff";

	private Colors() {
	}

	public static String get( String name) {
		return PREFIX_YELLOW + name + PREFIX_WHITE;
	}

	public static String get( String name, boolean withColor) {
		if (withColor) {
			return PREFIX_YELLOW + name + PREFIX_WHITE;
		}
		return String.valueOf( name);
	}
}
