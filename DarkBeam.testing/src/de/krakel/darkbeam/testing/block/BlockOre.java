/**
 * Dark Beam
 * BlockOre.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.testing.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.krakel.darkbeam.testing.lib.Textures;

public class BlockOre extends Block {
	public BlockOre( int id) {
		super( id, Material.rock);
		setHardness( 3.0F);
		setResistance( 5.0F);
		setStepSound( soundStoneFootstep);
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
	@Override
	@SideOnly( Side.CLIENT)
	public void registerIcons( IconRegister reg) {
		blockIcon = reg.registerIcon( Textures.PATH_DEFAULT + getUnlocalizedName2());
	}
}
