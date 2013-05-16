/**
 * Dark Beam
 * ModBlocks
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core.block;

import net.minecraft.block.Block;
import cpw.mods.fml.common.registry.GameRegistry;
import de.krakel.darkbeam.lib.FBlockIds;
import de.krakel.darkbeam.lib.FStrings;

public class ModBlocks {
	public static Block sOreBeaming = new BlockOre( FBlockIds.sOreBeamingID, FStrings.ORE_BEAMING_NAME);
	public static Block sOreDarkening = new BlockOre( FBlockIds.sOreDarkeningID, FStrings.ORE_DARKENING_NAME);

	public static void init() {
		GameRegistry.registerBlock( sOreBeaming, FStrings.ORE_BEAMING_NAME);
		GameRegistry.registerBlock( sOreDarkening, FStrings.ORE_DARKENING_NAME);
	}
}
