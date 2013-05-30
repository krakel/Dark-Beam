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
import de.krakel.darkbeam.lib.FColors;
import de.krakel.darkbeam.lib.FConfiguration;
import de.krakel.darkbeam.lib.FReferences;
import de.krakel.darkbeam.lib.FStrings;

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
			if (tokens.length < 1) {
				sResult = ID_ERROR;
				return;
			}
			sVersion = tokens[0];
			sLocation = tokens[1];
			if (sVersion != null) {
				if (!FConfiguration.sLastDiscoveredVersion.equalsIgnoreCase( sVersion)) {
					ConfigurationHandler.set( Configuration.CATEGORY_GENERAL, FConfiguration.LAST_DISCOVERED_VERSION_NAME, sVersion);
				}
				if (sVersion.equalsIgnoreCase( getVersionForCheck())) {
					sResult = ID_CURRENT;
				}
				else {
					sResult = ID_OUTDATED;
				}
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

	public static void execute() {
		VersionHelper helper = new VersionHelper();
		new Thread( helper).start();
	}

	public static String getMessageForClient() {
		String msg = LanguageRegistry.instance().getStringLocalization( FStrings.VERSION_OUTDATED);
		String modName = FColors.get( FReferences.MOD_NAME);
		String mcVersion = FColors.get( Loader.instance().getMCVersionString());
		return LogHelper.format( msg, modName, mcVersion, FColors.get( sVersion), FColors.get( sLocation));
	}

	private static String getVersionForCheck() {
		String[] tokens = FReferences.VERSION.split( " ");
		if (tokens.length < 1) {
			return FReferences.VERSION;
		}
		return tokens[0];
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
			case ID_UNINITIALIZED:
				LogHelper.warning( reg.getStringLocalization( FStrings.VERSION_UNINITIALIZED));
			case ID_CURRENT:
				LogHelper.info( reg.getStringLocalization( FStrings.VERSION_CURRENT), FReferences.MOD_NAME, mcVersion, sVersion);
			case ID_FINAL:
				LogHelper.warning( reg.getStringLocalization( FStrings.VERSION_FINAL));
			case ID_MC_NOT_FOUND:
				LogHelper.warning( reg.getStringLocalization( FStrings.VERSION_MC_NOT_FOUND), FReferences.MOD_NAME, mcVersion);
			case ID_OUTDATED:
				if (sVersion != null && sLocation != null) {
					LogHelper.info( reg.getStringLocalization( FStrings.VERSION_OUTDATED), FReferences.MOD_NAME, mcVersion, sVersion, sLocation);
				}
			default:
				sResult = ID_ERROR;
				LogHelper.warning( reg.getStringLocalization( FStrings.VERSION_ERROR));
		}
	}

	@Override
	public void run() {
		LanguageRegistry rec = LanguageRegistry.instance();
		LogHelper.info( rec.getStringLocalization( FStrings.VERSION_CHECK_INIT), REMOTE_VERSION);
		try {
			for (int count = 0; count < FReferences.VERSION_CHECK_ATTEMPTS; ++count) {
				checkVersion();
				logResult();
				if (sResult != ID_UNINITIALIZED && sResult != ID_ERROR) {
					break;
				}
				Thread.sleep( 10000);
			}
			if (sResult == ID_ERROR) {
				sResult = ID_FINAL;
				logResult();
			}
		}
		catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}
