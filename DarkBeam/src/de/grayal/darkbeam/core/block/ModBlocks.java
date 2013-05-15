/**
 * Dark Beam
 * ModBlocks
 * 
 * @author Grayal
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.grayal.darkbeam.core.block;

import net.minecraft.block.Block;
import cpw.mods.fml.common.registry.GameRegistry;

import de.grayal.darkbeam.lib.FBlockIds;
import de.grayal.darkbeam.lib.FStrings;

public class ModBlocks {
	public static Block sBlockTest = new BlockTest( FBlockIds.sBlockTestID);
	public static Block sOreBeaming = new BlockOre( FBlockIds.sOreBeamingID, FStrings.ORE_BEAMING_NAME);
	public static Block sOreDarkening = new BlockOre( FBlockIds.sOreDarkeningID, FStrings.ORE_DARKENING_NAME);

	public static void init() {
		GameRegistry.registerBlock( sBlockTest, FStrings.BLOCK_TEST_NAME);
		GameRegistry.registerBlock( sOreBeaming, FStrings.ORE_BEAMING_NAME);
		GameRegistry.registerBlock( sOreDarkening, FStrings.ORE_DARKENING_NAME);
	}
}
