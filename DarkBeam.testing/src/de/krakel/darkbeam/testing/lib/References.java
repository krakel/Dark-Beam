/**
 * Dark Beam
 * FReferences.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.testing.lib;

public class References {
	public static final String MOD_ID = "DarkBeam.testing";
	public static final String MOD_NAME = "Dark Beam Testing";
	public static final String MOD_CHANNEL = "DarkBeamTesting";
	public static final String VERSION = "@VERSION@.@BUILD_NUMBER@";
	public static final String DEPENDENCIES = "required-after:Forge@[7.8.0.684,)";
	public static final String FINGERPRINT = "@FINGERPRINT@";
	//
	public static final String CLASS_CLIENT_PROXY = "de.krakel.darkbeam.testing.core.proxy.ClientProxy";
	public static final String CLASS_SERVER_PROXY = "de.krakel.darkbeam.testing.core.proxy.CommonProxy";
	//
	public static final int SHIFTED_ID_RANGE = 256;
	public static final int TICKS_PER_SECOND = 20;
	public static final int VERSION_CHECK_ATTEMPTS = 3;

	private References() {
	}
}
