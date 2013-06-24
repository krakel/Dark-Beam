/**
 * Dark Beam
 * ModBlocks.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.block;

import de.krakel.darkbeam.lib.BlockType;

public class ModBlocks {
	public static BlockStage sStage = BlockType.STAGE.create( BlockStage.class);

	private ModBlocks() {
	}

	public static void preInit() {
		for (BlockType type : BlockType.values()) {
			type.register();
		}
	}
}
