package de.krakel.darkbeam.core.helper;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;

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
	private static final byte ID_MC_VERSION = 5;
	private static byte sResult = ID_UNINITIALIZED;
	private static String sLocation;
	private static String sVersion;

	public static void execute() {
		VersionHelper helper = new VersionHelper();
		new Thread( helper).start();
	}

	public static String getMessage() {
		if (sResult == ID_UNINITIALIZED) {
			return LanguageRegistry.instance().getStringLocalization( FStrings.VERSION_UNINITIALIZED);
		}
		if (sResult == ID_CURRENT) {
			String res = LanguageRegistry.instance().getStringLocalization( FStrings.VERSION_CURRENT);
			res = res.replace( "@MOD_NAME@", FReferences.MOD_NAME);
			res = res.replace( "@REMOTE_MOD_VERSION@", sVersion);
			res = res.replace( "@MINECRAFT_VERSION@", Loader.instance().getMCVersionString());
			return res;
		}
		if (sResult == ID_OUTDATED && sVersion != null && sLocation != null) {
			String res = LanguageRegistry.instance().getStringLocalization( FStrings.VERSION_OUTDATED);
			res = res.replace( "@MOD_NAME@", FReferences.MOD_NAME);
			res = res.replace( "@REMOTE_MOD_VERSION@", sVersion);
			res = res.replace( "@MINECRAFT_VERSION@", Loader.instance().getMCVersionString());
			res = res.replace( "@MOD_UPDATE_LOCATION@", sLocation);
			return res;
		}
		if (sResult == ID_OUTDATED && sVersion != null && sLocation != null) {
			String res = LanguageRegistry.instance().getStringLocalization( FStrings.VERSION_OUTDATED);
			res = res.replace( "@MOD_NAME@", FReferences.MOD_NAME);
			res = res.replace( "@REMOTE_MOD_VERSION@", sVersion);
			res = res.replace( "@MINECRAFT_VERSION@", Loader.instance().getMCVersionString());
			res = res.replace( "@MOD_UPDATE_LOCATION@", sLocation);
			return res;
		}
		if (sResult == ID_ERROR) {
			return LanguageRegistry.instance().getStringLocalization( FStrings.VERSION_GENERAL_ERROR);
		}
		if (sResult == ID_FINAL) {
			return LanguageRegistry.instance().getStringLocalization( FStrings.VERSION_FINAL_ERROR);
		}
		if (sResult == ID_MC_VERSION) {
			String res = LanguageRegistry.instance().getStringLocalization( FStrings.VERSION_MC_NOT_FOUND);
			res = res.replace( "@MOD_NAME@", FReferences.MOD_NAME);
			res = res.replace( "@MINECRAFT_VERSION@", Loader.instance().getMCVersionString());
			return res;
		}
		sResult = ID_ERROR;
		return LanguageRegistry.instance().getStringLocalization( FStrings.VERSION_GENERAL_ERROR);
	}

	public static String getMessageForClient() {
		String res = LanguageRegistry.instance().getStringLocalization( FStrings.VERSION_OUTDATED);
		res = res.replace( "@MOD_NAME@", FColors.PREFIX_YELLOW + FReferences.MOD_NAME + FColors.PREFIX_WHITE);
		res = res.replace( "@REMOTE_MOD_VERSION@", FColors.PREFIX_YELLOW + sVersion + FColors.PREFIX_WHITE);
		res = res.replace( "@MINECRAFT_VERSION@", FColors.PREFIX_YELLOW + Loader.instance().getMCVersionString() + FColors.PREFIX_WHITE);
		res = res.replace( "@MOD_UPDATE_LOCATION@", FColors.PREFIX_YELLOW + sLocation + FColors.PREFIX_WHITE);
		return res;
	}

	public static boolean isInitialized() {
		return sResult != ID_UNINITIALIZED && sResult != ID_FINAL;
	}

	public static boolean isOutdated() {
		return sResult == ID_OUTDATED;
	}

	private static String getVersionForCheck() {
		String[] tokens = FReferences.VERSION.split( " ");
		if (tokens.length < 1) {
			return FReferences.VERSION;
		}
		return tokens[0];
	}

	@Override
	public void run() {
		LogHelper.log( Level.INFO, LanguageRegistry.instance().getStringLocalization( FStrings.VERSION_CHECK_INIT) + " " + REMOTE_VERSION);
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

	private void checkVersion() {
		sResult = ID_UNINITIALIZED;
		InputStream is = null;
		try {
			URL url = new URL( REMOTE_VERSION);
			is = url.openStream();
			Properties props = new Properties();
			props.loadFromXML( is);
			String version = props.getProperty( Loader.instance().getMCVersionString());
			if (version != null) {
				String[] tokens = version.split( "\\|");
				if (tokens.length >= 2) {
					sVersion = tokens[0];
					sLocation = tokens[1];
				}
				else {
					sResult = ID_ERROR;
				}
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
			else {
				sResult = ID_MC_VERSION;
			}
		}
		catch (Exception ex) {
		}
		finally {
			if (sResult == ID_UNINITIALIZED) {
				sResult = ID_ERROR;
			}
			try {
				if (is != null) {
					is.close();
				}
			}
			catch (Exception ex) {
			}
		}
	}

	private void logResult() {
		if (sResult == ID_CURRENT || sResult == ID_OUTDATED) {
			LogHelper.log( Level.INFO, getMessage());
		}
		else {
			LogHelper.log( Level.WARNING, getMessage());
		}
	}
}
