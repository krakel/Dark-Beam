/**
 * Dark Beam
 * BlockUnit.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.krakel.darkbeam.DarkBeam;
import de.krakel.darkbeam.core.Material;
import de.krakel.darkbeam.core.MaterialLib;
import de.krakel.darkbeam.creativetab.ModTabs;
import de.krakel.darkbeam.tile.TileUnits;

public class BlockUnits extends Block {
	public BlockUnits( int id) {
		super( id, DarkBeam.MAT_DARK);
		setHardness( 0.1F);
		disableStats();
	}

	@Override
	public TileEntity createTileEntity( World world, int meta) {
		// TODO tile depend on meta
		return new TileUnits();
	}

	@Override
	@SideOnly( Side.CLIENT)
	public Icon getIcon( int side, int meta) {
		int matID = MaterialLib.matID( meta);
		Material mat = MaterialLib.get( matID);
		return mat.mBlock.getIcon( side, 0);
	}

	@Override
	@SideOnly( Side.CLIENT)
	@SuppressWarnings( {
		"rawtypes", "unchecked"
	})
	public void getSubBlocks( int blkID, CreativeTabs tab, List lst) {
		if (tab == ModTabs.sSubTabUnit) {
			for (Material mat : MaterialLib.values()) {
				lst.add( new ItemStack( ModBlocks.sUnits, 1, mat.mMatID));
			}
		}
	}

	@Override
	public void harvestBlock( World world, EntityPlayer player, int x, int y, int z, int meta) {
	}

	@Override
	public int idDropped( int meta, Random rand, int fortune) {
		return 0;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	@SideOnly( Side.CLIENT)
	public void registerIcons( IconRegister reg) {
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
}
