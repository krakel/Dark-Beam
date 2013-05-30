/**
 * Dark Beam
 * ModBlocks.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.block;

import net.minecraft.block.Block;
import cpw.mods.fml.common.registry.GameRegistry;

import de.krakel.darkbeam.item.ItemBlockRedWire;
import de.krakel.darkbeam.item.ItemUnit;
import de.krakel.darkbeam.lib.BlockIds;
import de.krakel.darkbeam.lib.Strings;

public class ModBlocks {
	public static Block sOreBeaming = new BlockOre( BlockIds.sOreBeamingID, Strings.ORE_BEAMING_NAME);
	public static Block sOreDarkening = new BlockOre( BlockIds.sOreDarkeningID, Strings.ORE_DARKENING_NAME);
	public static Block sBlockRedWire = new BlockRedWire( BlockIds.sBlockRedWireID, Strings.BLOCK_RED_WIRE_NAME);
	public static Block sBlockUnits = new BlockUnits( BlockIds.sBlockUnitsID, Strings.BLOCK_UNITS_NAME);

	public static void preInit() {
		GameRegistry.registerBlock( sOreBeaming, Strings.ORE_BEAMING_NAME);
		GameRegistry.registerBlock( sOreDarkening, Strings.ORE_DARKENING_NAME);
		GameRegistry.registerBlock( sBlockRedWire, ItemBlockRedWire.class, Strings.BLOCK_RED_WIRE_NAME);
		GameRegistry.registerBlock( sBlockUnits, ItemUnit.class, Strings.BLOCK_UNITS_NAME);
	}
}
