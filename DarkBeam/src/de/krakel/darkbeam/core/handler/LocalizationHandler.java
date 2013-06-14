/**
 * Dark Beam
 * LocalizationHandler.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core.handler;

import cpw.mods.fml.common.registry.LanguageRegistry;

import de.krakel.darkbeam.core.DarkLib;
import de.krakel.darkbeam.lib.MaskType;

public class LocalizationHandler {
	private static final String LANG_LOCATION = "/mods/darkbeam/lang/";
	private static final String[] LOCALES = {
//@formatter:off
		"en_US"
//		"de_DE", 
//		"cs_CZ", 
//		"cy_GB", 
//		"da_DK", 
//		"es_ES", 
//		"fi_FI", 
//		"fr_FR", 
//		"it_IT", 
//		"ja_JP", 
//		"la_IT", 
//		"nl_NL", 
//		"nb_NO", 
//		"pl_PL", 
//		"pt_BR", 
//		"pt_PT",
//		"ru_RU",
//		"sk_SK", 
//		"sr_RS", 
//		"sv_SE", 
//		"tr_TR", 
//		"zh_CN", 
//		"zh_TW", 
//		"el_GR"
//@formatter:on
	};

	public static void addMask( String blkName) {
		LanguageRegistry registry = LanguageRegistry.instance();
		for (String loc : LOCALES) {
			String matName = registry.getStringLocalization( blkName, loc);
			for (MaskType type : MaskType.values()) {
				String maskName = registry.getStringLocalization( type.mName, loc);
				String translation = DarkLib.format( maskName, matName);
				registry.addStringLocalization( type.mName + "." + blkName, loc, translation);
			}
		}
	}

	public static String get( String key) {
		return LanguageRegistry.instance().getStringLocalization( key);
	}

	public static void preInit() {
		LanguageRegistry registry = LanguageRegistry.instance();
		for (String loc : LOCALES) {
			registry.loadLocalization( LANG_LOCATION + loc + ".properties", loc, false);
		}
	}
}
