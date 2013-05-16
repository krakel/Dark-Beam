package de.krakel.darkbeam.lib;

import net.minecraft.item.Item;

public final class FTextures {
	public static final String TEXTURE = "darkbeam:";
	/* Base file pathes */
	public static final String PATH_BLOCKS = "/mods/darkbeam/textures/blocks/";
	public static final String PATH_EFFECTS = "/mods/darkbeam/textures/effects/";
	public static final String PATH_GUIS = "/mods/darkbeam/textures/gui/";
	public static final String PATH_ITEMS = "/mods/darkbeam/textures/items/";
	public static final String PATH_MODELS = "/mods/darkbeam/textures/models/";

	private FTextures() {
	}

	public static String get( Item item) {
		return TEXTURE + getName( item);
	}

	public static String get( Item item, String folder) {
		return TEXTURE + folder + '/' + getName( item);
	}

	public static String get( Item item, String face, String folder) {
		return TEXTURE + folder + '/' + getName( item) + face;
	}

	public static String get( String name) {
		return TEXTURE + name;
	}

	public static String get( String name, String folder) {
		return TEXTURE + folder + '/' + name;
	}

	public static String get( String name, String face, String folder) {
		return TEXTURE + folder + '/' + name + face;
	}

	private static String getName( Item item) {
		String name = item.getUnlocalizedName();
		return name.substring( name.indexOf( '.') + 1);
	}
}
