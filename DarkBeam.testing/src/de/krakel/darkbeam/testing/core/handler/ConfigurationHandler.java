/**
 * Dark Beam
 * ConfigurationHandler.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.testing.core.handler;

import java.io.File;

import net.minecraftforge.common.ConfigCategory;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import de.krakel.darkbeam.testing.core.helper.LogHelper;
import de.krakel.darkbeam.testing.lib.BlockType;
import de.krakel.darkbeam.testing.lib.ItemType;
import de.krakel.darkbeam.testing.lib.References;

public class ConfigurationHandler {
	public static final String CAT_AUDIO = "audio";
	public static final String CAT_GRAPHICS = "graphics";
	public static final String CAT_KEYBIND = "keybindings";
	public static final String CAT_BLOCK_PROPERTIES = Configuration.CATEGORY_BLOCK + Configuration.CATEGORY_SPLITTER
		+ "properties";
	public static final String CAT_DURABILITY = Configuration.CATEGORY_ITEM + Configuration.CATEGORY_SPLITTER
		+ "durability";
//	public static final String CAT_RED_WATER_PROPERTIES = CAT_BLOCK_PROPERTIES + Configuration.CATEGORY_SPLITTER + "red_water";
//	public static final String CAT_TRANSMUTATION = "transmutation";
	public static Configuration sConfig;

	@SuppressWarnings( "unused")
	private static boolean getConfigBoolean( String category, String name, boolean def) {
		return sConfig.get( category, name, def).getBoolean( def);
	}

	@SuppressWarnings( "unused")
	private static String getConfigString( String category, String name, String def) {
		return sConfig.get( category, name, def).getString();
	}

	public static void load( File config) {
		sConfig = new Configuration( config);
		try {
			sConfig.load();
			/* Graphic configs */
//			ConfigurationSettings.ENABLE_PARTICLE_FX = sConfig.get( CAT_GRAPHICS, ConfigurationSettings.ENABLE_PARTICLE_FX_CONFIGNAME, ConfigurationSettings.ENABLE_PARTICLE_FX_DEFAULT).getBoolean( ConfigurationSettings.ENABLE_PARTICLE_FX_DEFAULT);
//			ConfigurationSettings.ENABLE_OVERLAY_WORLD_TRANSMUTATION = sConfig.get( CAT_GRAPHICS, ConfigurationSettings.ENABLE_OVERLAY_WORLD_TRANSMUTATION_CONFIGNAME, ConfigurationSettings.ENABLE_OVERLAY_WORLD_TRANSMUTATION_DEFAULT).getBoolean( ConfigurationSettings.ENABLE_OVERLAY_WORLD_TRANSMUTATION_DEFAULT);
//			ConfigurationSettings.TARGET_BLOCK_OVERLAY_POSITION = sConfig.get( CAT_GRAPHICS, ConfigurationSettings.TARGET_BLOCK_OVERLAY_POSITION_CONFIGNAME, ConfigurationSettings.TARGET_BLOCK_OVERLAY_POSITION_DEFAULT).getInt( ConfigurationSettings.TARGET_BLOCK_OVERLAY_POSITION_DEFAULT);
//			try {
//				ConfigurationSettings.TARGET_BLOCK_OVERLAY_SCALE = Float.parseFloat( sConfig.get( CAT_GRAPHICS, ConfigurationSettings.TARGET_BLOCK_OVERLAY_SCALE_CONFIGNAME, ConfigurationSettings.TARGET_BLOCK_OVERLAY_SCALE_DEFAULT).getString());
//				if (ConfigurationSettings.TARGET_BLOCK_OVERLAY_SCALE <= 0F) {
//					ConfigurationSettings.TARGET_BLOCK_OVERLAY_SCALE = ConfigurationSettings.TARGET_BLOCK_OVERLAY_SCALE_DEFAULT;
//				}
//			}
//			catch (Exception e) {
//				ConfigurationSettings.TARGET_BLOCK_OVERLAY_SCALE = ConfigurationSettings.TARGET_BLOCK_OVERLAY_SCALE_DEFAULT;
//			}
//			try {
//				ConfigurationSettings.TARGET_BLOCK_OVERLAY_OPACITY = Float.parseFloat( sConfig.get( CAT_GRAPHICS, ConfigurationSettings.TARGET_BLOCK_OVERLAY_OPACITY_CONFIGNAME, ConfigurationSettings.TARGET_BLOCK_OVERLAY_OPACITY_DEFAULT).getString());
//				if (ConfigurationSettings.TARGET_BLOCK_OVERLAY_OPACITY < 0F || ConfigurationSettings.TARGET_BLOCK_OVERLAY_OPACITY > 1F) {
//					ConfigurationSettings.TARGET_BLOCK_OVERLAY_OPACITY = ConfigurationSettings.TARGET_BLOCK_OVERLAY_OPACITY_DEFAULT;
//				}
//			}
//			catch (Exception e) {
//				ConfigurationSettings.TARGET_BLOCK_OVERLAY_OPACITY = ConfigurationSettings.TARGET_BLOCK_OVERLAY_OPACITY_DEFAULT;
//			}
			/* Audio configs */
//			ConfigurationSettings.ENABLE_SOUNDS = sConfig.get( CAT_AUDIO, ConfigurationSettings.ENABLE_SOUNDS_CONFIGNAME, ConfigurationSettings.ENABLE_SOUNDS_DEFAULT).getString();
			/* Block configs */
			for (BlockType block : BlockType.values()) {
				block.updateID( sConfig);
			}
			/* Block property configs */
//			sConfig.addCustomCategoryComment( CAT_BLOCK_PROPERTIES, "Custom block properties");
			/* Red Water configs */
//			sConfig.addCustomCategoryComment( CAT_RED_WATER_PROPERTIES, "Configuration settings for various properties of Red Water");
//			ConfigurationSettings.RED_WATER_DURATION_BASE = sConfig.get( CAT_RED_WATER_PROPERTIES, ConfigurationSettings.RED_WATER_DURATION_BASE_CONFIGNAME, ConfigurationSettings.RED_WATER_DURATION_BASE_DEFAULT).getInt( ConfigurationSettings.RED_WATER_DURATION_BASE_DEFAULT);
//			ConfigurationSettings.RED_WATER_DURATION_MODIFIER = sConfig.get( CAT_RED_WATER_PROPERTIES, ConfigurationSettings.RED_WATER_DURATION_MODIFIER_CONFIGNAME, ConfigurationSettings.RED_WATER_DURATION_MODIFIER_DEFAULT).getInt( ConfigurationSettings.RED_WATER_DURATION_MODIFIER_DEFAULT);
//			ConfigurationSettings.RED_WATER_RANGE_BASE = sConfig.get( CAT_RED_WATER_PROPERTIES, ConfigurationSettings.RED_WATER_RANGE_BASE_CONFIGNAME, ConfigurationSettings.RED_WATER_RANGE_BASE_DEFAULT).getInt( ConfigurationSettings.RED_WATER_RANGE_BASE_DEFAULT);
//			ConfigurationSettings.RED_WATER_RANGE_MODIFIER = sConfig.get( CAT_RED_WATER_PROPERTIES, ConfigurationSettings.RED_WATER_RANGE_MODIFIER_CONFIGNAME, ConfigurationSettings.RED_WATER_RANGE_MODIFIER_DEFAULT).getInt( ConfigurationSettings.RED_WATER_RANGE_MODIFIER_DEFAULT);
			/* Item configs */
			for (ItemType item : ItemType.values()) {
				item.updateID( sConfig);
			}
			/* Item durability configs */
//			ConfigurationSettings.MINIUM_STONE_MAX_DURABILITY = sConfig.get( CAT_DURABILITY, ConfigurationSettings.MINIUM_STONE_MAX_DURABILITY_CONFIGNAME, ConfigurationSettings.MINIUM_STONE_MAX_DURABILITY_DEFAULT).getInt( ConfigurationSettings.MINIUM_STONE_MAX_DURABILITY_DEFAULT);
//			ConfigurationSettings.PHILOSOPHERS_STONE_MAX_DURABILITY = sConfig.get( CAT_DURABILITY, ConfigurationSettings.PHILOSOPHERS_STONE_MAX_DURABILITY_CONFIGNAME, ConfigurationSettings.PHILOSOPHERS_STONE_MAX_DURABILITY_DEFAULT).getInt( ConfigurationSettings.PHILOSOPHERS_STONE_MAX_DURABILITY_DEFAULT);
			/* KeyBinding configs */
//			sConfig.addCustomCategoryComment( CAT_KEYBIND, "Keybindings for Equivalent Exchange 3. See http://www.minecraftwiki.net/wiki/Key_codes for mapping of key codes to keyboard keys");
//			EquivalentExchange3.proxy.setKeyBinding( ConfigurationSettings.KEYBINDING_EXTRA, sConfig.get( CAT_KEYBIND, ConfigurationSettings.KEYBINDING_EXTRA, ConfigurationSettings.KEYBINDING_EXTRA_DEFAULT).getInt( ConfigurationSettings.KEYBINDING_EXTRA_DEFAULT));
//			EquivalentExchange3.proxy.setKeyBinding( ConfigurationSettings.KEYBINDING_CHARGE, sConfig.get( CAT_KEYBIND, ConfigurationSettings.KEYBINDING_CHARGE, ConfigurationSettings.KEYBINDING_CHARGE_DEFAULT).getInt( ConfigurationSettings.KEYBINDING_CHARGE_DEFAULT));
//			EquivalentExchange3.proxy.setKeyBinding( ConfigurationSettings.KEYBINDING_TOGGLE, sConfig.get( CAT_KEYBIND, ConfigurationSettings.KEYBINDING_TOGGLE, ConfigurationSettings.KEYBINDING_TOGGLE_DEFAULT).getInt( ConfigurationSettings.KEYBINDING_TOGGLE_DEFAULT));
//			EquivalentExchange3.proxy.setKeyBinding( ConfigurationSettings.KEYBINDING_RELEASE, sConfig.get( CAT_KEYBIND, ConfigurationSettings.KEYBINDING_RELEASE, ConfigurationSettings.KEYBINDING_RELEASE_DEFAULT).getInt( ConfigurationSettings.KEYBINDING_RELEASE_DEFAULT));
			/* Transmutation configs */
//			ConfigurationSettings.TRANSMUTE_COST_ITEM = sConfig.get( CAT_TRANSMUTATION, ConfigurationSettings.TRANSMUTE_COST_ITEM_CONFIGNAME, ConfigurationSettings.TRANSMUTE_COST_ITEM_DEFAULT).getInt( ConfigurationSettings.TRANSMUTE_COST_ITEM_DEFAULT);
//			ConfigurationSettings.TRANSMUTE_COST_BLOCK = sConfig.get( CAT_TRANSMUTATION, ConfigurationSettings.TRANSMUTE_COST_BLOCK_CONFIGNAME, ConfigurationSettings.TRANSMUTE_COST_BLOCK_DEFAULT).getInt( ConfigurationSettings.TRANSMUTE_COST_BLOCK_DEFAULT);
//			ConfigurationSettings.TRANSMUTE_COST_MOB = sConfig.get( CAT_TRANSMUTATION, ConfigurationSettings.TRANSMUTE_COST_MOB_CONFIGNAME, ConfigurationSettings.TRANSMUTE_COST_MOB_DEFAULT).getInt( ConfigurationSettings.TRANSMUTE_COST_MOB_DEFAULT);
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
