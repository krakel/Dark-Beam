/**
 * Dark Beam
 * LocalizationHandler.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core.handler;

import cpw.mods.fml.common.registry.LanguageRegistry;

import de.krakel.darkbeam.core.ISection;
import de.krakel.darkbeam.core.Insulate;
import de.krakel.darkbeam.core.Material;
import de.krakel.darkbeam.core.SectionLib;

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

	public static void addInsulate( Insulate insu) {
		for (String loc : LOCALES) {
			insu.addStringLocalization( SectionLib.sInsuwire, loc);
		}
	}

	public static void addMaterial( Material mat) {
		for (String loc : LOCALES) {
			for (ISection sec : SectionLib.values()) {
				if (sec.isStructure()) {
					mat.addStringLocalization( sec, loc);
				}
			}
		}
	}

	public static String getLocalization( String key, String loc) {
		LanguageRegistry reg = LanguageRegistry.instance();
		String result = reg.getStringLocalization( key, loc);
		if ("".equals( result)) {
			result = reg.getStringLocalization( key);
			if ("".equals( result)) {
				result = key;
			}
		}
		return result;
	}

	public static void preInit() {
		LanguageRegistry registry = LanguageRegistry.instance();
		for (String loc : LOCALES) {
			registry.loadLocalization( LANG_LOCATION + loc + ".properties", loc, false);
		}
	}
}
