/**
 * Dark Beam
 * BlockTest
 * 
 * @author Grayal
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.grayal.darkbeam.core.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import de.grayal.darkbeam.DarkBeam;
import de.grayal.darkbeam.lib.FStrings;

public class BlockTest extends Block {
	protected BlockTest( int id) {
		super( id, Material.rock);
		setHardness( 5F);
		setResistance( 10.0F);
		setUnlocalizedName( FStrings.BLOCK_TEST_NAME);
//		setBlockBounds( 0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
		setCreativeTab( DarkBeam.sTabDB);
	}
}
