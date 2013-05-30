/**
 * Dark Beam
 * LogHelper.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core.helper;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import cpw.mods.fml.common.FMLLog;

import de.krakel.darkbeam.lib.FReferences;

public class LogHelper {
	private static final String SELF = LogHelper.class.getName();
	private static Logger sLogger = Logger.getLogger( FReferences.MOD_ID);
	private static boolean sTraceStack;

	private static void doLog( Level lvl, String msg, Object[] arr) {
		StackTraceElement frame = inferCaller( SELF);
		if (frame != null) {
			sLogger.logp( lvl, frame.getClassName(), frame.getMethodName(), msg, arr);
		}
		else {
			sLogger.log( lvl, msg, arr);
		}
	}

	private static void doLogThrows( Level lvl, String msg, Throwable ex) {
		StackTraceElement frame = inferCaller( SELF);
		if (frame != null) {
			sLogger.logp( lvl, frame.getClassName(), frame.getMethodName(), msg, ex);
		}
		else {
			sLogger.log( lvl, msg, ex);
		}
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

	public static String format( String msg, Object... data) {
		if (data == null || data.length == 0) {
			return msg;
		}
		try {
			return MessageFormat.format( msg, data);
		}
		catch (IllegalArgumentException ex) {
			return msg;
		}
	}

	private static StackTraceElement inferCaller( String self) {
		StackTraceElement[] stack = new Throwable().getStackTrace();
		int ix = 0;
		for (; ix < stack.length; ++ix) {
			if (self.equals( stack[ix].getClassName())) {
				break;
			}
		}
		for (; ix < stack.length; ++ix) {
			StackTraceElement frame = stack[ix];
			if (!self.equals( frame.getClassName())) {
				return frame;
			}
		}
		return null;
	}

	public static void info( String msg, Object... data) {
		log( Level.INFO, msg, data);
	}

	public static void log( Level lvl, String msg, Object... os) {
		if (sLogger.isLoggable( lvl)) {
			Object[] arr = new Object[os.length];
			for (int i = 0; i < os.length; ++i) {
				arr[i] = String.valueOf( os[i]);
			}
			doLog( lvl, msg, arr);
		}
	}

	public static void logArr( Level lvl, String msg, Object[] arr) {
		if (sLogger.isLoggable( lvl)) {
			doLog( lvl, msg, new Object[] {
				toString( arr)
			});
		}
	}

	public static void logProperty( Level lvl, String property) {
		if (sLogger.isLoggable( lvl)) {
			doLog( lvl, "{0} = {1}", new Object[] {
				property, System.getProperty( property, null)
			});
		}
	}

	public static void logThrows( Level lvl, String msg, Throwable ex) {
		if (sLogger.isLoggable( lvl)) {
			if (sTraceStack) {
				doLogThrows( lvl, msg, ex);
//				ex.printStackTrace();
			}
			else {
				doLog( lvl, msg, new Object[] {
					ex
				});
			}
		}
	}

	public static void preInit() {
		sLogger.setParent( FMLLog.getLogger());
	}

	public static void setStackTrace( boolean traceStack) {
		sTraceStack = traceStack;
	}

	public static void severe( String msg, Object... data) {
		log( Level.SEVERE, msg, data);
	}

	public static void severe( Throwable ex, String msg, Object... data) {
		logThrows( Level.SEVERE, format( msg, data), ex);
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
