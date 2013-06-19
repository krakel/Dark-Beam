/**
 * Dark Beam
 * LocalizationHandler.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.testing.core.handler;

import cpw.mods.fml.common.registry.LanguageRegistry;

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

	public static String getLocalization( LanguageRegistry reg, String mskKey, String loc) {
		String mskName = reg.getStringLocalization( mskKey, loc);
		if ("".equals( mskName)) {
			mskName = reg.getStringLocalization( mskKey);
			if ("".equals( mskName)) {
				mskName = mskKey;
			}
		}
		return mskName;
	}

	public static void preInit() {
		LanguageRegistry registry = LanguageRegistry.instance();
		for (String loc : LOCALES) {
			registry.loadLocalization( LANG_LOCATION + loc + ".properties", loc, false);
		}
	}
}
