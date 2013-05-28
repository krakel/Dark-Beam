/**
 * Dark Beam
 * BlockUnit.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import de.krakel.darkbeam.DarkBeam;
import de.krakel.darkbeam.tile.TileUnit;

public class BlockUnit extends Block {
	public BlockUnit( int id, String name) {
		super( id, DarkBeam.MAT_DARK);
		setUnlocalizedName( name);
		setHardness( 0.1F);
		setCreativeTab( DarkBeam.sMainTab);
		disableStats();
	}

	public TileEntity createNewTileEntity( World world) {
		return null;
	}

	@Override
	public TileEntity createTileEntity( World world, int meta) {
		// TODO tile depend on meta
		return new TileUnit();
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
	public boolean renderAsNormalBlock() {
		return false;
	}
}
