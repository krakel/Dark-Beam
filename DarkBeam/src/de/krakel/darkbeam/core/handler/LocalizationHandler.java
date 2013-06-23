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
import de.krakel.darkbeam.core.Mask;
import de.krakel.darkbeam.core.MaskLib;
import de.krakel.darkbeam.core.Material;

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

	public static void addMask( Material mat) {
		LanguageRegistry reg = LanguageRegistry.instance();
		for (String loc : LOCALES) {
			String matName = getLocalization( reg, mat.getUnlocalizedName(), loc);
			for (Mask msk : MaskLib.values()) {
				if (msk.hasMaterials()) {
					String mskKey = msk.getUnlocalizedName();
					String mskName = getLocalization( reg, mskKey, loc);
					String translation = DarkLib.format( mskName, matName);
					reg.addStringLocalization( mat.getUnlocalizedName( msk) + ".name", loc, translation);
				}
			}
		}
	}

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
