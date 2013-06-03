/**
 * Dark Beam
 * ModBlocks.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.block;

import cpw.mods.fml.common.registry.GameRegistry;

import de.krakel.darkbeam.item.ItemBlockRedWire;
import de.krakel.darkbeam.item.ItemUnit;
import de.krakel.darkbeam.item.TestItemBlockItem;
import de.krakel.darkbeam.item.TestItemBlockMulti;
import de.krakel.darkbeam.item.TestItemBlockPanel;
import de.krakel.darkbeam.lib.BlockType;

public class ModBlocks {
	public static BlockOre sOreBeaming = new BlockOre( BlockType.OreBeaming);
	public static BlockOre sOreDarkening = new BlockOre( BlockType.OreDarkening);
	public static BlockRedWire sBlockRedWire = new BlockRedWire( BlockType.BlockRedWire);
	public static BlockUnits sBlockUnits = new BlockUnits( BlockType.BlockUnits);
	public static TestBlockSimple sTestBlockSimple = new TestBlockSimple( BlockType.TestBlockSimple);
	public static TestBlockItem sTestBlockItem = new TestBlockItem( BlockType.TestBlockItem);
	public static TestBlockMulti sTestBlockMulti = new TestBlockMulti( BlockType.TestBlockMulti);
	public static TestBlockPanel sTestBlockPanel = new TestBlockPanel( BlockType.TestBlockPanel);

	public static void preInit() {
		GameRegistry.registerBlock( sOreBeaming, BlockType.OreBeaming.mName);
		GameRegistry.registerBlock( sOreDarkening, BlockType.OreDarkening.mName);
		GameRegistry.registerBlock( sBlockRedWire, ItemBlockRedWire.class, BlockType.BlockRedWire.mName);
		GameRegistry.registerBlock( sBlockUnits, ItemUnit.class, BlockType.BlockUnits.mName);
		//
		GameRegistry.registerBlock( sTestBlockSimple, BlockType.TestBlockSimple.mName);
		GameRegistry.registerBlock( sTestBlockItem, TestItemBlockItem.class, BlockType.TestBlockItem.mName);
		GameRegistry.registerBlock( sTestBlockMulti, TestItemBlockMulti.class, BlockType.TestBlockMulti.mName);
		GameRegistry.registerBlock( sTestBlockPanel, TestItemBlockPanel.class, BlockType.TestBlockPanel.mName);
	}
}
