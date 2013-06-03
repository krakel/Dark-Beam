/**
 * Dark Beam
 * TestBlockMulti.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.krakel.darkbeam.DarkBeam;
import de.krakel.darkbeam.core.DarkLib;
import de.krakel.darkbeam.lib.BlockType;

public class TestBlockMulti extends Block {
	@SideOnly( Side.CLIENT)
	private Icon[] mTextures = new Icon[16];

	public TestBlockMulti( BlockType type) {
		super( type.getId(), Material.cloth);
		setHardness( 0.8F);
		setStepSound( soundClothFootstep);
		setUnlocalizedName( type.mName);
		setCreativeTab( DarkBeam.sMainTab);
	}

	@Override
	protected ItemStack createStackedBlock( int meta) {
		return new ItemStack( blockID, 1, DarkLib.colorSubID( meta));
	}

	@Override
	public int damageDropped( int dmg) {
		return dmg;
	}

	@Override
	@SideOnly( Side.CLIENT)
	public Icon getIcon( int side, int meta) {
		return mTextures[DarkLib.colorSubID( meta)];
	}

	@Override
	@SideOnly( Side.CLIENT)
	@SuppressWarnings( {
		"rawtypes", "unchecked"
	})
	public void getSubBlocks( int blkID, CreativeTabs tab, List lst) {
		for (int i = 0; i < 16; ++i) {
			lst.add( new ItemStack( blkID, 1, i));
		}
	}

	@Override
	@SideOnly( Side.CLIENT)
	public void registerIcons( IconRegister reg) {
		for (int i = 0; i < 16; ++i) {
			mTextures[i] = reg.registerIcon( "cloth_" + i);
		}
	}
}
