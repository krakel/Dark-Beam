/**
 * Dark Beam
 * LocalizationHandler
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core.handler;

import cpw.mods.fml.common.registry.LanguageRegistry;
import de.krakel.darkbeam.lib.FLocalization;

public class LocalizationHandler {
	public static String get( String key) {
		return LanguageRegistry.instance().getStringLocalization( key);
	}

	public static void load() {
		LanguageRegistry registry = LanguageRegistry.instance();
		for (String name : FLocalization.LOCALES) {
			registry.loadLocalization( FLocalization.LANG_LOCATION + name, getLocale( name), isXML( name));
		}
	}

	private static String getLocale( String fileName) {
		return fileName.substring( 1, fileName.indexOf( '.'));
	}

	private static boolean isXML( String fileName) {
		return fileName.endsWith( ".xml");
	}
}
