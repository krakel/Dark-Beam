/**
 * Dark Beam
 * TestBlockSimple.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.krakel.darkbeam.DarkBeam;
import de.krakel.darkbeam.lib.BlockType;

public class TestBlockSimple extends Block {
	public TestBlockSimple( BlockType type) {
		super( type.getId(), Material.rock);
		setHardness( 3.0F);
		setResistance( 5.0F);
		setStepSound( soundStoneFootstep);
		setUnlocalizedName( type.mName);
		setCreativeTab( DarkBeam.sMainTab);
		disableStats();
	}

	@Override
	@SideOnly( Side.CLIENT)
	public void registerIcons( IconRegister reg) {
		blockIcon = reg.registerIcon( Block.stone.getUnlocalizedName2());
	}
}
