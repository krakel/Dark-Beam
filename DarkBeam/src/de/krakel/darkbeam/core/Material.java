/**
 * Dark Beam
 * Material.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import net.minecraft.block.Block;
import net.minecraft.util.Icon;
import cpw.mods.fml.common.registry.LanguageRegistry;

import de.krakel.darkbeam.core.handler.LocalizationHandler;

public class Material implements IMaterial {
	private int mMatID;
	private String mName;
	private Block mBlock;
	private int mSubID;

	Material( int matID, String name, Block blk, int subID) {
		mMatID = matID;
		mName = name;
		mBlock = blk;
		mSubID = subID;
	}

	@Override
	public void addStringLocalization( ISection sec, String lang) {
		String key = getName( sec) + ".name";
		String pattern = LocalizationHandler.getLocalization( "db.section." + sec.getName(), lang);
		String matter = LocalizationHandler.getLocalization( "db.material." + mName, lang);
		String translation = DarkLib.format( pattern, matter);
		LanguageRegistry.instance().addStringLocalization( key, lang, translation);
	}

	@Override
	public Icon getIcon( int side) {
		return mBlock.getIcon( side, mSubID);
	}

	@Override
	public int getID() {
		return mMatID;
	}

	@Override
	public String getName( ISection sec) {
		return "tile." + sec.getName() + "." + mName;
	}

	@Override
	public boolean isIsolation() {
		return false;
	}

	@Override
	public int toDmg() {
		return mMatID;
	}
}
