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
import de.krakel.darkbeam.item.TestItemBlockItem;
import de.krakel.darkbeam.lib.BlockType;

public class ModBlocks {
	public static Block sOreBeaming = new BlockOre( BlockType.OreBeaming);
	public static Block sOreDarkening = new BlockOre( BlockType.OreDarkening);
	public static Block sBlockRedWire = new BlockRedWire( BlockType.BlockRedWire);
	public static Block sBlockUnits = new BlockUnits( BlockType.BlockUnits);
	public static Block sTestBlockSimple = new TestBlockSimple( BlockType.TestBlockSimple);
	public static Block sTestBlockItem = new TestBlockItem( BlockType.TestBlockItem);

	public static void preInit() {
		GameRegistry.registerBlock( sOreBeaming, sOreBeaming.getUnlocalizedName2());
		GameRegistry.registerBlock( sOreDarkening, sOreDarkening.getUnlocalizedName2());
		GameRegistry.registerBlock( sBlockRedWire, ItemBlockRedWire.class, sBlockRedWire.getUnlocalizedName2());
		GameRegistry.registerBlock( sBlockUnits, ItemUnit.class, sBlockUnits.getUnlocalizedName2());
		//
		GameRegistry.registerBlock( sTestBlockSimple, sTestBlockSimple.getUnlocalizedName2());
		GameRegistry.registerBlock( sTestBlockItem, TestItemBlockItem.class, sTestBlockItem.getUnlocalizedName2());
	}
}
