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

import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import cpw.mods.fml.common.FMLLog;

import de.krakel.darkbeam.core.DarkLib;
import de.krakel.darkbeam.core.Position;
import de.krakel.darkbeam.lib.References;

public class LogHelper {
	private static Logger sLogger = Logger.getLogger( References.MOD_ID);

	private LogHelper() {
	}

	public static void fine( String msg, Object... data) {
		log( Level.FINE, msg, data);
	}

	public static void finer( String msg, Object... data) {
		log( Level.FINER, msg, data);
	}

	public static void finest( String msg, Object... data) {
		log( Level.FINEST, msg, data);
	}

	public static void info( String msg, Object... data) {
		log( Level.INFO, msg, data);
	}

	public static void log( Level lvl, String msg, Object... data) {
		if (sLogger.isLoggable( lvl)) {
			sLogger.log( lvl, DarkLib.format( msg, data));
		}
	}

	public static void logArr( Level lvl, String msg, Object[] arr) {
		if (sLogger.isLoggable( lvl)) {
			sLogger.log( lvl, DarkLib.format( msg, toString( arr)));
		}
	}

	public static void logProperty( Level lvl, String property) {
		if (sLogger.isLoggable( lvl)) {
			sLogger.log( lvl, DarkLib.format( "%s = %s", property, System.getProperty( property, null)));
		}
	}

	public static void logThrows( Level lvl, String msg, Throwable ex) {
		if (sLogger.isLoggable( lvl)) {
			sLogger.log( lvl, msg, ex);
		}
	}

	public static void preInit() {
		sLogger.setParent( FMLLog.getLogger());
	}

	public static void severe( String msg, Object... data) {
		log( Level.SEVERE, msg, data);
	}

	public static void severe( Throwable ex) {
		if (sLogger.isLoggable( Level.SEVERE)) {
			sLogger.log( Level.SEVERE, "unexpected error", ex);
		}
	}

	public static void severe( Throwable ex, String msg) {
		if (sLogger.isLoggable( Level.SEVERE)) {
			sLogger.log( Level.SEVERE, msg, ex);
		}
	}

	public static void severe( Throwable ex, String msg, Object... data) {
		if (sLogger.isLoggable( Level.SEVERE)) {
			sLogger.log( Level.SEVERE, DarkLib.format( msg, data), ex);
		}
	}

	public static String toString( double hitX, double hitY, double hitZ) {
		return DarkLib.format( "hit=(%f|%f|%f)", hitX, hitY, hitZ);
	}

	public static String toString( int x, int y, int z) {
		return DarkLib.format( "pos=(%d|%d|%d)", x, y, z);
	}

	public static String toString( int x, int y, int z, int dir, double hitX, double hitY, double hitZ, EnumMovingObjectType type) {
		return DarkLib.format( "obj=[dir=%s, %s, %s, type=%s]", Position.toString( dir), toString( x, y, z), toString( hitX, hitY, hitZ), type);
	}

	public static String toString( MovingObjectPosition pos) {
		double hitX = pos.hitVec.xCoord - pos.blockX;
		double hitY = pos.hitVec.yCoord - pos.blockY;
		double hitZ = pos.hitVec.zCoord - pos.blockZ;
		return toString( pos.blockX, pos.blockY, pos.blockZ, pos.sideHit, hitX, hitY, hitZ, pos.typeOfHit);
	}

	private static String toString( Object[] arr) {
		StringBuffer sb = new StringBuffer();
		if (arr != null) {
			sb.append( '[');
			for (int i = 0; i < arr.length; ++i) {
				if (i > 0) {
					sb.append( ", ");
				}
				sb.append( '"');
				sb.append( arr[i].toString());
				sb.append( '"');
			}
			sb.append( ']');
		}
		else {
			sb.append( "null");
		}
		return sb.toString();
	}

	public static void warning( String msg, Object... data) {
		log( Level.WARNING, msg, data);
	}
}
