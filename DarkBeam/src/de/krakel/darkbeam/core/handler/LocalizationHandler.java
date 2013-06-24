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
		LanguageRegistry reg = LanguageRegistry.instance();
		for (String loc : LOCALES) {
			String insuName = getLocalization( reg, insu.getInsuKey(), loc);
			String secKey = SectionLib.sInsuwire.getSectionKey();
			String secName = getLocalization( reg, secKey, loc);
			String translation = DarkLib.format( secName, insuName);
			reg.addStringLocalization( insu.getInsuName( SectionLib.sInsuwire) + ".name", loc, translation);
		}
	}

	public static void addMaterial( Material mat) {
		LanguageRegistry reg = LanguageRegistry.instance();
		for (String loc : LOCALES) {
			String matName = getLocalization( reg, mat.getMatKey(), loc);
			for (ISection sec : SectionLib.values()) {
				if (sec.hasMaterials()) {
					String secKey = sec.getSectionKey();
					String secName = getLocalization( reg, secKey, loc);
					String translation = DarkLib.format( secName, matName);
					reg.addStringLocalization( mat.getMatName( sec) + ".name", loc, translation);
				}
			}
		}
	}

	public static String getLocalization( LanguageRegistry reg, String secKey, String loc) {
		String secName = reg.getStringLocalization( secKey, loc);
		if ("".equals( secName)) {
			secName = reg.getStringLocalization( secKey);
			if ("".equals( secName)) {
				secName = secKey;
			}
		}
		return secName;
	}

	public static void preInit() {
		LanguageRegistry registry = LanguageRegistry.instance();
		for (String loc : LOCALES) {
			registry.loadLocalization( LANG_LOCATION + loc + ".properties", loc, false);
		}
	}
}
