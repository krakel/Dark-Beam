/**
 * Dark Beam
 * VersionHelper.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core.helper;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.LanguageRegistry;

import de.krakel.darkbeam.core.handler.ConfigurationHandler;
import de.krakel.darkbeam.lib.Colors;
import de.krakel.darkbeam.lib.Configs;
import de.krakel.darkbeam.lib.References;
import de.krakel.darkbeam.lib.Strings;

public class VersionHelper implements Runnable {
	private static final String REMOTE_VERSION = "https://raw.github.com/krakel/Dark-Beam/master/DarkBeam/version.xml";
	private static final byte ID_UNINITIALIZED = 0;
	private static final byte ID_CURRENT = 1;
	private static final byte ID_OUTDATED = 2;
	private static final byte ID_ERROR = 3;
	private static final byte ID_FINAL = 4;
	private static final byte ID_MC_NOT_FOUND = 5;
	private static byte sResult = ID_UNINITIALIZED;
	private static String sLocation;
	private static String sVersion;

	private VersionHelper() {
	}

	private static void checkVersion() {
		sResult = ID_UNINITIALIZED;
		try {
			Properties props = new Properties();
			InputStream is = null;
			try {
				URL url = new URL( REMOTE_VERSION);
				is = url.openStream();
				props.loadFromXML( is);
			}
			catch (Exception ex) {
				// ignore
			}
			finally {
				try {
					if (is != null) {
						is.close();
					}
				}
				catch (Exception ex) {
					// ignore
				}
			}
			String version = props.getProperty( Loader.instance().getMCVersionString());
			if (version == null) {
				sResult = ID_MC_NOT_FOUND;
				return;
			}
			String[] tokens = version.split( "\\|");
			if (tokens.length < 2) {
				sResult = ID_ERROR;
				return;
			}
			sVersion = tokens[0];
			sLocation = tokens[1];
			if (sVersion != null) {
				if (!Configs.sLastDiscoveredVersion.equalsIgnoreCase( sVersion)) {
					ConfigurationHandler.set( Configuration.CATEGORY_GENERAL, Configs.LAST_DISCOVERED_VERSION_NAME, sVersion);
				}
				sResult = compareVersion( sVersion, References.VERSION) < 0 ? ID_OUTDATED : ID_CURRENT;
			}
		}
		catch (Exception ex) {
			// ignore
		}
		finally {
			if (sResult == ID_UNINITIALIZED) {
				sResult = ID_ERROR;
			}
		}
	}

	private static int compareVersion( String s1, String s2) {
		String[] a1 = s1.split( "\\.");
		String[] a2 = s2.split( "\\.");
		int min = Math.min( a1.length, a2.length);
		int i = 0;
		while (i < min && a1[i].equals( a2[i])) {
			++i;
		}
		if (i < min) {
			return Integer.valueOf( a1[i]).compareTo( Integer.valueOf( a2[i]));
		}
		if (i < a1.length) {
			boolean zeros = true;
			do {
				zeros &= Integer.parseInt( a1[i++]) == 0;
			}
			while (zeros & i < a1.length);
			return zeros ? 0 : -1;
		}
		if (i < a2.length) {
			boolean zeros = true;
			do {
				zeros &= Integer.parseInt( a2[i++]) == 0;
			}
			while (zeros & i < a2.length);
			return zeros ? 0 : 1;
		}
		return 0;
	}

	public static void execute() {
		VersionHelper helper = new VersionHelper();
		new Thread( helper).start();
	}

	public static String getMessageForClient() {
		String msg = LanguageRegistry.instance().getStringLocalization( Strings.VERSION_OUTDATED);
		String modName = Colors.get( References.MOD_NAME);
		String mcVersion = Colors.get( Loader.instance().getMCVersionString());
		return LogHelper.format( msg, modName, mcVersion, Colors.get( sVersion), Colors.get( sLocation));
	}

	public static boolean isInitialized() {
		return sResult != ID_UNINITIALIZED && sResult != ID_FINAL;
	}

	public static boolean isOutdated() {
		return sResult == ID_OUTDATED;
	}

	private static void logResult() {
		String mcVersion = Loader.instance().getMCVersionString();
		LanguageRegistry reg = LanguageRegistry.instance();
		switch (sResult) {
			case ID_CURRENT:
				LogHelper.info( reg.getStringLocalization( Strings.VERSION_CURRENT), References.MOD_NAME, mcVersion, sVersion);
				break;
			case ID_OUTDATED:
				LogHelper.info( reg.getStringLocalization( Strings.VERSION_OUTDATED), References.MOD_NAME, mcVersion, sVersion, sLocation);
				break;
			case ID_MC_NOT_FOUND:
				LogHelper.warning( reg.getStringLocalization( Strings.VERSION_MC), References.MOD_NAME, mcVersion);
				break;
			case ID_UNINITIALIZED:
				LogHelper.warning( reg.getStringLocalization( Strings.VERSION_UNINITIALIZED));
				break;
			default:
				sResult = ID_ERROR;
				LogHelper.warning( reg.getStringLocalization( Strings.VERSION_ERROR));
		}
	}

	@Override
	public void run() {
		LanguageRegistry reg = LanguageRegistry.instance();
		LogHelper.info( reg.getStringLocalization( Strings.VERSION_INIT), REMOTE_VERSION);
		try {
			for (int i = 0; i < References.VERSION_CHECK_ATTEMPTS; ++i) {
				checkVersion();
				logResult();
				if (sResult != ID_UNINITIALIZED && sResult != ID_ERROR) {
					break;
				}
				Thread.sleep( 10000);
			}
			if (sResult == ID_ERROR) {
				sResult = ID_FINAL;
				LogHelper.warning( reg.getStringLocalization( Strings.VERSION_FINAL));
			}
		}
		catch (InterruptedException ex) {
			LogHelper.severe( ex);
		}
	}
}
