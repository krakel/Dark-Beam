/**
 * Dark Beam
 * BlockOre
 * 
 * @author Grayal
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.grayal.darkbeam.core.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import de.grayal.darkbeam.DarkBeam;

public class BlockOre extends Block {
	public BlockOre( int id, String name) {
		super( id, Material.rock);
		setHardness( 3.0F);
		setResistance( 5.0F);
		setStepSound( soundStoneFootstep);
		setUnlocalizedName( name);
		setCreativeTab( DarkBeam.sTabDB);
	}

//	@Override
//	public int damageDropped( int meta) {
//		return super.damageDropped( meta);
//	}
//
//	@Override
//	public void dropBlockAsItemWithChance( World world, int x, int y, int z, int meta, float chance, int dmg) {
//		super.dropBlockAsItemWithChance( world, x, y, z, meta, chance, dmg);
//	}
//
//	@Override
//	public int idDropped( int meta, Random rnd, int dmg) {
//		return super.idDropped( meta, rnd, dmg);
//	}
//
//	@Override
//	public int quantityDropped( Random rnd) {
//		return super.quantityDropped( rnd);
//	}
//
//	@Override
//	public int quantityDroppedWithBonus( int meta, Random rnd) {
//		return super.quantityDroppedWithBonus( meta, rnd);
//	}
}
