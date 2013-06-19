/**
 * Dark Beam
 * FConfiguration.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.lib;

public class Configs {
	public static final String DISPLAY_VERSION_RESULT_NAME = "version_check.display_results";
	public static final boolean DISPLAY_VERSION_RESULT_DEFAULT = true;
	public static final String LAST_DISCOVERED_VERSION_NAME = "version_check.last_discovered_version";
	public static final String LAST_DISCOVERED_VERSION_DEFAULT = "";
	public static final String LAST_DISCOVERED_VERSION_TYPE_NAME = "version_check.last_discovered_version_type";
	public static final String LAST_DISCOVERED_VERSION_TYPE_DEFAULT = "";
	public static boolean sDisplayVersionResult;
	public static String sLastDiscoveredVersion;
	public static String sLastDiscoveredVersionType;

	private Configs() {
	}
}
