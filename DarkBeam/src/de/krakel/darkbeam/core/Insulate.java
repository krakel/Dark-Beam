/**
 * Dark Beam
 * Insulate.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import net.minecraft.block.Block;
import net.minecraft.util.Icon;
import cpw.mods.fml.common.registry.LanguageRegistry;

import de.krakel.darkbeam.core.handler.LocalizationHandler;

public class Insulate {
	public final int mInsuID;
	final String mName;

	Insulate( int insuID, String name) {
		mInsuID = insuID;
		mName = name;
	}

	public void addStringLocalization( ISection sec, String lang) {
		String key = getName( sec) + ".name";
		String pattern = LocalizationHandler.getLocalization( "db.section." + sec.getName(), lang);
		String matter = LocalizationHandler.getLocalization( "db.insulated." + mName, lang);
		String translation = DarkLib.format( pattern, matter);
		LanguageRegistry.instance().addStringLocalization( key, lang, translation);
	}

	public Icon getIcon( int side) {
		return Block.cloth.getIcon( 0, mInsuID);
	}

	public String getName( ISection sec) {
		return "tile." + sec.getName() + "." + mName;
	}

	public int toDmg( ISection sec) {
		return sec.toDmg() | mInsuID;
	}
}
