/**
 * Dark Beam
 * LocalizationHandler.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core.handler;

import net.minecraft.block.Block;
import cpw.mods.fml.common.registry.LanguageRegistry;

import de.krakel.darkbeam.core.DarkLib;
import de.krakel.darkbeam.core.Mask;
import de.krakel.darkbeam.core.MaskLib;

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

	public static void addMask( Block blk) {
		LanguageRegistry registry = LanguageRegistry.instance();
		for (String loc : LOCALES) {
			String matName = blk.getLocalizedName();
			for (Mask msk : MaskLib.values()) {
				String mskKey = msk.mName + ".name";
				String mskName = registry.getStringLocalization( mskKey, loc);
				if ("".equals( mskName)) {
					mskName = registry.getStringLocalization( mskKey);
				}
				String translation = DarkLib.format( mskName, matName);
				registry.addStringLocalization( msk.mName + "." + blk.getUnlocalizedName2() + ".name", loc, translation);
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
