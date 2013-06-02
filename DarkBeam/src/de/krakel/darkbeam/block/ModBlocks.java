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
import de.krakel.darkbeam.item.TestItemBlockMulti;
import de.krakel.darkbeam.lib.BlockType;

public class ModBlocks {
	public static Block sOreBeaming = new BlockOre( BlockType.OreBeaming);
	public static Block sOreDarkening = new BlockOre( BlockType.OreDarkening);
	public static Block sBlockRedWire = new BlockRedWire( BlockType.BlockRedWire);
	public static Block sBlockUnits = new BlockUnits( BlockType.BlockUnits);
	public static Block sTestBlockSimple = new TestBlockSimple( BlockType.TestBlockSimple);
	public static Block sTestBlockItem = new TestBlockItem( BlockType.TestBlockItem);
	public static Block sTestBlockMulti = new TestBlockMulti( BlockType.TestBlockMulti);

	public static void preInit() {
		GameRegistry.registerBlock( sOreBeaming, BlockType.OreBeaming.mName);
		GameRegistry.registerBlock( sOreDarkening, BlockType.OreDarkening.mName);
		GameRegistry.registerBlock( sBlockRedWire, ItemBlockRedWire.class, BlockType.BlockRedWire.mName);
		GameRegistry.registerBlock( sBlockUnits, ItemUnit.class, BlockType.BlockUnits.mName);
		//
		GameRegistry.registerBlock( sTestBlockSimple, BlockType.TestBlockSimple.mName);
		GameRegistry.registerBlock( sTestBlockItem, TestItemBlockItem.class, BlockType.TestBlockItem.mName);
		GameRegistry.registerBlock( sTestBlockMulti, TestItemBlockMulti.class, BlockType.TestBlockMulti.mName);
	}
}
