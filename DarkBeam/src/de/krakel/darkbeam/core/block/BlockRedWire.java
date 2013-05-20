/**
 * Dark Beam
 * BlockWire.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.krakel.darkbeam.DarkBeam;
import de.krakel.darkbeam.client.renderer.block.BlockRedWireRender;
import de.krakel.darkbeam.lib.FTextures;

public class BlockRedWire extends Block {
	public static final float MIN_SIZE = 0.0F;
	public static final float MAX_SIZE = 0.2F;

	public BlockRedWire( int id, String name) {
		super( id, Material.circuits);
		setUnlocalizedName( name);
		setCreativeTab( DarkBeam.sMainTab);
	}

	@Override
	public Icon getBlockTexture( IBlockAccess world, int x, int y, int z, int side) {
//		TileEntityRedWire redWire = (TileEntityRedWire) world.getBlockTileEntity( x, y, z);
//		return redWire.isPowered ? Block.blockRedstone.getIcon( 0, 0) : Block.stone.getIcon( 0, 0);
//		return super.getBlockTexture( world, x, y, z, side);
		return Block.blockRedstone.getIcon( 0, 0);
	}

	@Override
	public int getRenderType() {
		return BlockRedWireRender.ID;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void onBlockPlacedBy( World world, int x, int y, int z, EntityLiving entity, ItemStack stack) {
		if (!world.isRemote) {
//			TileEntityRedWire redWire = (TileEntityRedWire) world.getBlockTileEntity( x, y, z);
			TileEntity wire = world.getBlockTileEntity( x, y, z);
			if (wire != null && !world.isRemote) {
//				wire.updateMasks();
//				wire.scanAndUpdate();
			}
		}
		world.markBlockForUpdate( x, y, z);
	}

	@Override
	public void onNeighborBlockChange( World world, int x, int y, int z, int blockID) {
//		TileEntityRedWire redWire = (TileEntityRedWire) world.getBlockTileEntity( x, y, z);
//		if (!world.isRemote) {
//			redWire.scanAndUpdate();
//		}
//		world.markBlockForUpdate( x, y, z);
	}

	@Override
	@SideOnly( Side.CLIENT)
	public void registerIcons( IconRegister reg) {
		blockIcon = reg.registerIcon( FTextures.PATH_DEFAULT + getUnlocalizedName2());
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
}
