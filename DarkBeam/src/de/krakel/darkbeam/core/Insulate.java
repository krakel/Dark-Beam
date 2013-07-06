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

public class Insulate implements IMaterial {
	private int mInsuID;
	private String mName;

	Insulate( int insuID, String name) {
		mInsuID = insuID;
		mName = name;
	}

	@Override
	public void addStringLocalization( ISection sec, String lang) {
		String key = getName( sec) + ".name";
		String pattern = LocalizationHandler.getLocalization( "db.section." + sec.getName(), lang);
		String matter = LocalizationHandler.getLocalization( "db.insulate." + mName, lang);
		String translation = DarkLib.format( pattern, matter);
		LanguageRegistry.instance().addStringLocalization( key, lang, translation);
	}

	@Override
	public Icon getIcon( int side) {
		return Block.cloth.getIcon( 0, mInsuID);
	}

	@Override
	public int getID() {
		return mInsuID;
	}

	@Override
	public String getName( ISection sec) {
		return "tile." + sec.getName() + "." + mName;
	}

	@Override
	public boolean isIsolation() {
		return true;
	}

	@Override
	public int toDmg() {
		return mInsuID;
	}
}
