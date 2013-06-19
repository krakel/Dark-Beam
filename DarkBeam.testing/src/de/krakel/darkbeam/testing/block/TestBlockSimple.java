/**
 * Dark Beam
 * TestBlockSimple.java
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

public class TestBlockSimple extends Block {
	public TestBlockSimple( int id) {
		super( id, Material.rock);
		setHardness( 3.0F);
		setResistance( 5.0F);
		setStepSound( soundStoneFootstep);
	}

	@Override
	@SideOnly( Side.CLIENT)
	public void registerIcons( IconRegister reg) {
		blockIcon = reg.registerIcon( Block.stone.getUnlocalizedName2());
	}
}
