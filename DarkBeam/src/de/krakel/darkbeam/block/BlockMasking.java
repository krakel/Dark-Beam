/**
 * Dark Beam
 * BlockMasking.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.krakel.darkbeam.DarkBeam;
import de.krakel.darkbeam.client.renderer.BlockMaskingRender;
import de.krakel.darkbeam.core.Material;
import de.krakel.darkbeam.core.MaterialLib;
import de.krakel.darkbeam.tile.TileMasking;

public class BlockMasking extends Block {
	public BlockMasking( int id) {
		super( id, DarkBeam.MAT_DARK);
		setHardness( 0.1F);
		disableStats();
	}

	@Override
	public TileEntity createTileEntity( World world, int meta) {
		// TODO tile depend on meta
		return new TileMasking();
	}

	@Override
	@SideOnly( Side.CLIENT)
	public Icon getIcon( int side, int meta) {
		Material mat = MaterialLib.getForDmg( meta);
		return mat.mBlock.getIcon( side, 0);
	}

	@Override
	public int getRenderType() {
		return BlockMaskingRender.ID;
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
