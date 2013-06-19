/**
 * Dark Beam
 * FTextures.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.testing.lib;

import net.minecraft.item.Item;

public class Textures {
	/* Base file pathes */
	public static final String PATH_DEFAULT = "darktesting:";
	public static final String PATH_BLOCKS = "darktesting:blocks/";
	public static final String PATH_EFFECTS = "darktesting:effects/";
	public static final String PATH_GUIS = "darktesting:guis/";
	public static final String PATH_ITEMS = "darktesting:items/";
	public static final String PATH_MODELS = "darktesting:models/";

	private Textures() {
	}

	public static String getItemName( Item item) {
		String name = item.getUnlocalizedName();
		return name.substring( name.indexOf( '.') + 1);
	}
}
