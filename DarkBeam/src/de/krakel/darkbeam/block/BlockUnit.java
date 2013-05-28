/**
 * Dark Beam
 * BlockUnit.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.block;

import net.minecraft.block.Block;

import de.krakel.darkbeam.DarkBeam;

public class BlockUnit extends Block {
	public BlockUnit( int id, String name) {
		super( id, DarkBeam.MAT_DARK);
		setUnlocalizedName( name);
		setHardness( 0.1F);
		setCreativeTab( DarkBeam.sMainTab);
	}
}
