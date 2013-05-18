/**
 * Dark Beam
 * FTextures.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.lib;

import net.minecraft.item.Item;

public final class FTextures {
	/* Base file pathes */
	public static final String PATH_DEFAULT = "darkbeam:";
	public static final String PATH_BLOCKS = "darkbeam:blocks/";
	public static final String PATH_EFFECTS = "darkbeam:effects/";
	public static final String PATH_GUIS = "darkbeam:guis/";
	public static final String PATH_ITEMS = "darkbeam:items/";
	public static final String PATH_MODELS = "darkbeam:models/";

	private FTextures() {
	}

	public static String getItemName( Item item) {
		String name = item.getUnlocalizedName();
		return name.substring( name.indexOf( '.') + 1);
	}
}
