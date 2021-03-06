/**
 * Dark Beam
 * ModBlocks.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.testing.block;

import net.minecraft.block.BlockOre;

import de.krakel.darkbeam.testing.lib.BlockType;

public class ModBlocks {
	public static BlockOre sOreBeaming = BlockType.OreBeaming.create( BlockOre.class);
	public static BlockOre sOreDarkening = BlockType.OreDarkening.create( BlockOre.class);
	public static BlockRedWire sRedWire = BlockType.RedWire.create( BlockRedWire.class);
	public static TestBlockSimple sTestSimple = BlockType.TestSimple.create( TestBlockSimple.class);
	public static TestBlockItem sTestItem = BlockType.TestItem.create( TestBlockItem.class);
	public static TestBlockMulti sTestMulti = BlockType.TestMulti.create( TestBlockMulti.class);
	public static TestBlockPanel sTestPanel = BlockType.TestPanel.create( TestBlockPanel.class);
	public static TestContainerSimple sContainerSimple = BlockType.ContainerSimple.create( TestContainerSimple.class);

	private ModBlocks() {
	}

	public static void preInit() {
		for (BlockType type : BlockType.values()) {
			type.register();
		}
	}
}
