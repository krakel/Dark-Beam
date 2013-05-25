/**
 * Dark Beam
 * LogHelper.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core.helper;

import java.util.logging.Level;
import java.util.logging.Logger;

import cpw.mods.fml.common.FMLLog;

import de.krakel.darkbeam.lib.FReferences;

public class LogHelper {
	private static Logger sLogger = Logger.getLogger( FReferences.MOD_ID);

	public static void debug( String msg, Object... data) {
		StringBuffer sb = new StringBuffer( msg);
		for (Object d : data) {
			sb.append( ", ");
			sb.append( d);
		}
		sLogger.log( Level.INFO, sb.toString());
	}

	public static void fine( String msg, Object... data) {
		sLogger.log( Level.FINE, String.format( msg, data));
	}

	public static void finer( String msg, Object... data) {
		sLogger.log( Level.FINER, String.format( msg, data));
	}

	public static void finest( String msg, Object... data) {
		sLogger.log( Level.FINEST, String.format( msg, data));
	}

	public static void info( String msg, Object... data) {
		sLogger.log( Level.INFO, String.format( msg, data));
	}

	public static void preInit() {
		sLogger.setParent( FMLLog.getLogger());
	}

	public static void severe( String msg, Object... data) {
		sLogger.log( Level.SEVERE, String.format( msg, data));
	}

	public static void severe( Throwable ex, String msg, Object... data) {
		sLogger.log( Level.SEVERE, String.format( msg, data), ex);
	}

	public static void warning( String msg, Object... data) {
		sLogger.log( Level.WARNING, String.format( msg, data));
	}
}
