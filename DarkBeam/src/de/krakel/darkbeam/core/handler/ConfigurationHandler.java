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

import de.krakel.darkbeam.core.helper.LogHelper;
import de.krakel.darkbeam.lib.FBlockIds;
import de.krakel.darkbeam.lib.FConfiguration;
import de.krakel.darkbeam.lib.FItemIds;
import de.krakel.darkbeam.lib.FReferences;
import de.krakel.darkbeam.lib.FStrings;

public class ConfigurationHandler {
	public static final String CAT_AUDIO = "audio";
	public static final String CAT_GRAPHICS = "graphics";
	public static final String CAT_KEYBIND = "keybindings";
	public static final String CAT_BLOCK_PROPERTIES = Configuration.CATEGORY_BLOCK + Configuration.CATEGORY_SPLITTER + "properties";
	public static final String CAT_DURABILITY = Configuration.CATEGORY_ITEM + Configuration.CATEGORY_SPLITTER + "durability";
//	public static final String CAT_RED_WATER_PROPERTIES = CAT_BLOCK_PROPERTIES + Configuration.CATEGORY_SPLITTER + "red_water";
//	public static final String CAT_TRANSMUTATION = "transmutation";
	public static Configuration sConfig;

	public static void init( File config) {
		sConfig = new Configuration( config);
		try {
			sConfig.load();
			/* General configs */
			FConfiguration.sDisplayVersionResult = getConfigBoolean( Configuration.CATEGORY_GENERAL, FConfiguration.DISPLAY_VERSION_RESULT_NAME, FConfiguration.DISPLAY_VERSION_RESULT_DEFAULT);
			FConfiguration.sLastDiscoveredVersion = getConfigString( Configuration.CATEGORY_GENERAL, FConfiguration.LAST_DISCOVERED_VERSION_NAME, FConfiguration.LAST_DISCOVERED_VERSION_DEFAULT);
			FConfiguration.sLastDiscoveredVersionType = getConfigString( Configuration.CATEGORY_GENERAL, FConfiguration.LAST_DISCOVERED_VERSION_TYPE_NAME, FConfiguration.LAST_DISCOVERED_VERSION_TYPE_DEFAULT);
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
			FBlockIds.sOreDarkeningID = getConfigBlock( FStrings.ORE_DARKENING_NAME, FBlockIds.ORE_DARKENING_DEFAULT_ID);
			FBlockIds.sOreBeamingID = getConfigBlock( FStrings.ORE_BEAMING_NAME, FBlockIds.ORE_BEAMING_DEFAULT_ID);
			FBlockIds.sBlockRedWireID = getConfigBlock( FStrings.BLOCK_RED_WIRE_NAME, FBlockIds.BLOCK_RED_WIRE_DEFAULT_ID);
//			BlockIds.CALCINATOR = sConfig.getBlock( Strings.CALCINATOR_NAME, BlockIds.CALCINATOR_DEFAULT).getInt( BlockIds.CALCINATOR_DEFAULT);
//			BlockIds.ALUDEL_BASE = sConfig.getBlock( Strings.ALUDEL_NAME, BlockIds.ALUDEL_BASE_DEFAULT).getInt( BlockIds.ALUDEL_BASE_DEFAULT);
//			BlockIds.ALCHEMICAL_CHEST = sConfig.getBlock( Strings.ALCHEMICAL_CHEST_NAME, BlockIds.ALCHEMICAL_CHEST_DEFAULT).getInt( BlockIds.ALCHEMICAL_CHEST_DEFAULT);
//			BlockIds.GLASS_BELL = sConfig.getBlock( Strings.GLASS_BELL_NAME, BlockIds.GLASS_BELL_DEFAULT).getInt( BlockIds.GLASS_BELL_DEFAULT);
//			BlockIds.RED_WATER_STILL = sConfig.getBlock( Strings.RED_WATER_STILL_NAME, BlockIds.RED_WATER_STILL_DEFAULT).getInt( BlockIds.RED_WATER_STILL_DEFAULT);
			/* Block property configs */
//			sConfig.addCustomCategoryComment( CAT_BLOCK_PROPERTIES, "Custom block properties");
			/* Red Water configs */
//			sConfig.addCustomCategoryComment( CAT_RED_WATER_PROPERTIES, "Configuration settings for various properties of Red Water");
//			ConfigurationSettings.RED_WATER_DURATION_BASE = sConfig.get( CAT_RED_WATER_PROPERTIES, ConfigurationSettings.RED_WATER_DURATION_BASE_CONFIGNAME, ConfigurationSettings.RED_WATER_DURATION_BASE_DEFAULT).getInt( ConfigurationSettings.RED_WATER_DURATION_BASE_DEFAULT);
//			ConfigurationSettings.RED_WATER_DURATION_MODIFIER = sConfig.get( CAT_RED_WATER_PROPERTIES, ConfigurationSettings.RED_WATER_DURATION_MODIFIER_CONFIGNAME, ConfigurationSettings.RED_WATER_DURATION_MODIFIER_DEFAULT).getInt( ConfigurationSettings.RED_WATER_DURATION_MODIFIER_DEFAULT);
//			ConfigurationSettings.RED_WATER_RANGE_BASE = sConfig.get( CAT_RED_WATER_PROPERTIES, ConfigurationSettings.RED_WATER_RANGE_BASE_CONFIGNAME, ConfigurationSettings.RED_WATER_RANGE_BASE_DEFAULT).getInt( ConfigurationSettings.RED_WATER_RANGE_BASE_DEFAULT);
//			ConfigurationSettings.RED_WATER_RANGE_MODIFIER = sConfig.get( CAT_RED_WATER_PROPERTIES, ConfigurationSettings.RED_WATER_RANGE_MODIFIER_CONFIGNAME, ConfigurationSettings.RED_WATER_RANGE_MODIFIER_DEFAULT).getInt( ConfigurationSettings.RED_WATER_RANGE_MODIFIER_DEFAULT);
			/* Item configs */
			FItemIds.sItemDarkeningID = getConfigItem( FStrings.ITEM_DARKENING_NAME, FItemIds.ITEM_DARKENING_ID);
//			ItemIds.MINIUM_SHARD = sConfig.getItem( Strings.MINIUM_SHARD_NAME, ItemIds.MINIUM_SHARD_DEFAULT).getInt( ItemIds.MINIUM_SHARD_DEFAULT);
//			ItemIds.INERT_STONE = sConfig.getItem( Strings.INERT_STONE_NAME, ItemIds.INERT_STONE_DEFAULT).getInt( ItemIds.INERT_STONE_DEFAULT);
//			ItemIds.MINIUM_STONE = sConfig.getItem( Strings.MINIUM_STONE_NAME, ItemIds.MINIUM_STONE_DEFAULT).getInt( ItemIds.MINIUM_STONE_DEFAULT);
//			ItemIds.PHILOSOPHERS_STONE = sConfig.getItem( Strings.PHILOSOPHERS_STONE_NAME, ItemIds.PHILOSOPHERS_STONE_DEFAULT).getInt( ItemIds.PHILOSOPHERS_STONE_DEFAULT);
//			ItemIds.ALCHEMICAL_DUST = sConfig.getItem( Strings.ALCHEMICAL_DUST_NAME, ItemIds.ALCHEMICAL_DUST_DEFAULT).getInt( ItemIds.ALCHEMICAL_DUST_DEFAULT);
//			ItemIds.ALCHEMICAL_BAG = sConfig.getItem( Strings.ALCHEMICAL_BAG_NAME, ItemIds.ALCHEMICAL_BAG_DEFAULT).getInt( ItemIds.ALCHEMICAL_BAG_DEFAULT);
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
			LogHelper.severe( ex, FReferences.MOD_NAME + " has had a problem loading its configuration");
		}
		finally {
			sConfig.save();
		}
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

	private static int getConfigBlock( String name, int id) {
		return sConfig.getBlock( name, id).getInt( id);
	}

	private static boolean getConfigBoolean( String category, String name, boolean def) {
		return sConfig.get( category, name, def).getBoolean( def);
	}

	private static int getConfigItem( String name, int id) {
		return sConfig.getItem( name, id).getInt( id);
	}

	private static String getConfigString( String category, String name, String def) {
		return sConfig.get( category, name, def).getString();
	}
}
