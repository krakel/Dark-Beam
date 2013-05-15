package de.grayal.darkbeam.core.helper;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;

import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.LanguageRegistry;

import de.grayal.darkbeam.core.ConfigurationSettings;
import de.grayal.darkbeam.core.handler.ConfigurationHandler;
import de.grayal.darkbeam.lib.FColors;
import de.grayal.darkbeam.lib.FReferences;
import de.grayal.darkbeam.lib.FStrings;

public class VersionHelper implements Runnable {
	private static final byte ID_CURRENT = 1;
	private static final byte ID_ERROR = 3;
	private static final byte ID_FINAL = 4;
	private static final byte ID_MC_VERSION = 5;
	private static final byte ID_OUTDATED = 2;
	private static final byte ID_UNINITIALIZED = 0;
	private static final String REMOTE_VERSION = "https://raw.github.com/grayal/Dark-Beam/master/version.xml";
	private static String sFoundLocation = null;
	private static String sFoundVersion = null;
	private static byte sResult = ID_UNINITIALIZED;

	public static void execute() {
		VersionHelper helper = new VersionHelper();
		new Thread( helper).start();
	}

	public static byte getResult() {
		return sResult;
	}

	private static String getVersionForCheck() {
		String[] versionTokens = FReferences.VERSION.split( " ");
		if (versionTokens.length >= 1) {
			return versionTokens[0];
		}
		return FReferences.VERSION;
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
					sFoundVersion = tokens[0];
					sFoundLocation = tokens[1];
				}
				else {
					sResult = ID_ERROR;
				}
				if (sFoundVersion != null) {
					if (!ConfigurationSettings.sLastDiscoveredVersion.equalsIgnoreCase( sFoundVersion)) {
						ConfigurationHandler.set( Configuration.CATEGORY_GENERAL, ConfigurationSettings.LAST_DISCOVERED_VERSION_CONFIGNAME, sFoundVersion);
					}
					if (sFoundVersion.equalsIgnoreCase( getVersionForCheck())) {
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

	private String getResultMessage() {
		if (sResult == ID_UNINITIALIZED) {
			return LanguageRegistry.instance().getStringLocalization( FStrings.VERSION_UNINITIALIZED);
		}
		if (sResult == ID_CURRENT) {
			String res = LanguageRegistry.instance().getStringLocalization( FStrings.VERSION_CURRENT);
			res = res.replace( "@MOD_NAME@", FReferences.MOD_NAME);
			res = res.replace( "@REMOTE_MOD_VERSION@", sFoundVersion);
			res = res.replace( "@MINECRAFT_VERSION@", Loader.instance().getMCVersionString());
			return res;
		}
		if (sResult == ID_OUTDATED && sFoundVersion != null && sFoundLocation != null) {
			String res = LanguageRegistry.instance().getStringLocalization( FStrings.VERSION_OUTDATED);
			res = res.replace( "@MOD_NAME@", FReferences.MOD_NAME);
			res = res.replace( "@REMOTE_MOD_VERSION@", sFoundVersion);
			res = res.replace( "@MINECRAFT_VERSION@", Loader.instance().getMCVersionString());
			res = res.replace( "@MOD_UPDATE_LOCATION@", sFoundLocation);
			return res;
		}
		if (sResult == ID_OUTDATED && sFoundVersion != null && sFoundLocation != null) {
			String res = LanguageRegistry.instance().getStringLocalization( FStrings.VERSION_OUTDATED);
			res = res.replace( "@MOD_NAME@", FReferences.MOD_NAME);
			res = res.replace( "@REMOTE_MOD_VERSION@", sFoundVersion);
			res = res.replace( "@MINECRAFT_VERSION@", Loader.instance().getMCVersionString());
			res = res.replace( "@MOD_UPDATE_LOCATION@", sFoundLocation);
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

	@SuppressWarnings( "unused")
	private String getResultMessageForClient() {
		String res = LanguageRegistry.instance().getStringLocalization( FStrings.VERSION_OUTDATED);
		res = res.replace( "@MOD_NAME@", FColors.PREFIX_YELLOW + FReferences.MOD_NAME + FColors.PREFIX_WHITE);
		res = res.replace( "@REMOTE_MOD_VERSION@", FColors.PREFIX_YELLOW + VersionHelper.sFoundVersion + FColors.PREFIX_WHITE);
		res = res.replace( "@MINECRAFT_VERSION@", FColors.PREFIX_YELLOW + Loader.instance().getMCVersionString() + FColors.PREFIX_WHITE);
		res = res.replace( "@MOD_UPDATE_LOCATION@", FColors.PREFIX_YELLOW + VersionHelper.sFoundLocation + FColors.PREFIX_WHITE);
		return res;
	}

	private void logResult() {
		if (sResult == ID_CURRENT || sResult == ID_OUTDATED) {
			LogHelper.log( Level.INFO, getResultMessage());
		}
		else {
			LogHelper.log( Level.WARNING, getResultMessage());
		}
	}
}
