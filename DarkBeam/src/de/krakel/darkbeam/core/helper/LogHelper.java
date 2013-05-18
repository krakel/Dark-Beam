package de.krakel.darkbeam.core.helper;

import java.util.logging.Level;
import java.util.logging.Logger;

import cpw.mods.fml.common.FMLLog;
import de.krakel.darkbeam.lib.FReferences;

public class LogHelper {
	private static Logger sLogger = Logger.getLogger( FReferences.MOD_ID);

	public static void fine( String msg, Object... data) {
		log( Level.FINE, String.format( msg, data));
	}

	public static void finer( String msg, Object... data) {
		log( Level.FINER, String.format( msg, data));
	}

	public static void finest( String msg, Object... data) {
		log( Level.FINEST, String.format( msg, data));
	}

	public static void info( String msg, Object... data) {
		log( Level.INFO, String.format( msg, data));
	}

	public static void init() {
		sLogger.setParent( FMLLog.getLogger());
	}

	public static void severe( String msg, Object... data) {
		log( Level.SEVERE, String.format( msg, data));
	}

	public static void severe( Throwable ex, String msg, Object... data) {
		log( Level.SEVERE, ex, String.format( msg, data));
	}

	public static void warning( String msg, Object... data) {
		log( Level.WARNING, String.format( msg, data));
	}

	private static void log( Level logLevel, String msg) {
		sLogger.log( logLevel, msg);
	}

	//	public static void log( Level logLevel, String msg, Object... data) {
//		sLogger.log( logLevel, String.format( msg, data));
//	}
//
	private static void log( Level logLevel, Throwable ex, String msg) {
		sLogger.log( logLevel, msg, ex);
	}
}
