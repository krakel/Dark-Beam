package de.grayal.darkbeam.core.helper;

import java.util.logging.Level;
import java.util.logging.Logger;

import cpw.mods.fml.common.FMLLog;

import de.grayal.darkbeam.lib.FReferences;

public class LogHelper {
	private static Logger sLogger = Logger.getLogger( FReferences.MOD_ID);

	public static void init() {
		sLogger.setParent( FMLLog.getLogger());
	}

	public static void log( Level logLevel, String message) {
		sLogger.log( logLevel, message);
	}
}
