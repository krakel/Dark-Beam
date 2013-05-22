/**
 * Dark Beam
 * FReferences.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.lib;

public final class FReferences {
	public static final String MOD_ID = "DarkBeam";
	public static final String MOD_NAME = "Dark Beam";
	public static final String MOD_CHANNEL = "DarkBeam";
	public static final String VERSION = "@VERSION@ (build @BUILD_NUMBER@)";
	public static final String DEPENDENCIES = "required-after:Forge@[7.8.0.684,)";
	public static final String FINGERPRINT = "@FINGERPRINT@";
	//
	public static final String CLASS_CLIENT_PROXY = "de.krakel.darkbeam.core.proxy.ClientProxy";
	public static final String CLASS_SERVER_PROXY = "de.krakel.darkbeam.core.proxy.CommonProxy";
	//
	public static final int SHIFTED_ID_RANGE = 256;
	public static final int TICKS_PER_SECOND = 20;
	public static final int VERSION_CHECK_ATTEMPTS = 3;

	private FReferences() {
	}
}
