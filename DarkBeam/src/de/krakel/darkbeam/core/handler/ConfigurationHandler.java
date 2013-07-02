/**
 * Dark Beam
 * ConfigurationHandler.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core.handler;

import java.io.File;

import net.minecraftforge.common.ConfigCategory;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import de.krakel.darkbeam.core.helper.LogHelper;
import de.krakel.darkbeam.lib.BlockType;
import de.krakel.darkbeam.lib.Configs;
import de.krakel.darkbeam.lib.ItemType;
import de.krakel.darkbeam.lib.References;

public class ConfigurationHandler {
	public static final String CAT_AUDIO = "audio";
	public static final String CAT_GRAPHICS = "graphics";
	public static final String CAT_KEYBIND = "keybindings";
	public static final String CAT_BLOCK_PROPERTIES = Configuration.CATEGORY_BLOCK + Configuration.CATEGORY_SPLITTER
		+ "properties";
	public static final String CAT_DURABILITY = Configuration.CATEGORY_ITEM + Configuration.CATEGORY_SPLITTER
		+ "durability";
	public static Configuration sConfig;

	private static boolean getConfigBoolean( String category, String name, boolean def) {
		return sConfig.get( category, name, def).getBoolean( def);
	}

	private static String getConfigString( String category, String name, String def) {
		return sConfig.get( category, name, def).getString();
	}

	public static void load( File config) {
		sConfig = new Configuration( config);
		try {
			sConfig.load();
			/* General configs */
			Configs.sDisplayVersionResult = getConfigBoolean( Configuration.CATEGORY_GENERAL, Configs.DISPLAY_VERSION_RESULT_NAME, Configs.DISPLAY_VERSION_RESULT_DEFAULT);
			Configs.sLastDiscoveredVersion = getConfigString( Configuration.CATEGORY_GENERAL, Configs.LAST_DISCOVERED_VERSION_NAME, Configs.LAST_DISCOVERED_VERSION_DEFAULT);
			Configs.sLastDiscoveredVersionType = getConfigString( Configuration.CATEGORY_GENERAL, Configs.LAST_DISCOVERED_VERSION_TYPE_NAME, Configs.LAST_DISCOVERED_VERSION_TYPE_DEFAULT);
			/* Graphic configs */
			/* Audio configs */
			/* Block configs */
			for (BlockType block : BlockType.values()) {
				block.updateID( sConfig);
			}
			/* Block property configs */
			/* Item configs */
			for (ItemType item : ItemType.values()) {
				item.updateID( sConfig);
			}
			/* Item durability configs */
			/* KeyBinding configs */
		}
		catch (Exception ex) {
			LogHelper.severe( ex, "%s has had a problem loading its configuration", References.MOD_NAME);
		}
		finally {
			sConfig.save();
		}
	}

	public static void preInit( FMLPreInitializationEvent event) {
		File confDir = new File( event.getModConfigurationDirectory(), References.MOD_CHANNEL);
		File confFile = new File( confDir, References.MOD_ID + ".cfg");
		load( confFile);
	}

	public static void set( String category, String property, String value) {
		sConfig.load();
		if (sConfig.getCategoryNames().contains( category)) {
			ConfigCategory cat = sConfig.getCategory( category);
			if (cat.containsKey( property)) {
				cat.get( property).set( value);
			}
		}
		sConfig.save();
	}
}
