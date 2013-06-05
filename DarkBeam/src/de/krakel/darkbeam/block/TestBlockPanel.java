/**
 * Dark Beam
 * TestBlockPanel.java
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
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.krakel.darkbeam.core.DarkLib;
import de.krakel.darkbeam.core.IDirection;

public class TestBlockPanel extends Block implements IDirection {
	private static final float THICKNESS = 1F / 8F;

	public TestBlockPanel( int id) {
		super( id, Material.rock);
		setHardness( 2.0F);
		setResistance( 10.0F);
		setStepSound( soundStoneFootstep);
		setBlockBounds( 0.0F, 0.0F, 0.0F, 1.0F, THICKNESS, 1.0F);
		setLightOpacity( 255);
	}

	@Override
	@SuppressWarnings( "rawtypes")
	public void addCollisionBoxesToList( World world, int x, int y, int z, AxisAlignedBB mask, List lst, Entity entity) {
		setBlockBoundsBasedOnState( world, x, y, z);
		super.addCollisionBoxesToList( world, x, y, z, mask, lst, entity);
	}

	@Override
	protected ItemStack createStackedBlock( int meta) {
		return new ItemStack( blockID, 1, DarkLib.panelSubID( meta));
	}

	@Override
	public int damageDropped( int meta) {
		return DarkLib.panelSubID( meta);
	}

	@Override
	@SideOnly( Side.CLIENT)
	public Icon getIcon( int side, int meta) {
		switch (DarkLib.panelSubID( meta)) {
			case 0:
				return Block.stone.getIcon( side, 0);
			case 1:
				return Block.sandStone.getIcon( side, 0);
			case 2:
				return Block.planks.getIcon( side, 0);
			case 3:
				return Block.cobblestone.getIcon( side, 0);
			case 4:
				return Block.brick.getIcon( side, 0);
			case 5:
				return Block.stoneBrick.getIcon( side, 0);
			case 6:
				return Block.netherBrick.getIcon( side, 0);
			case 7:
				return Block.blockNetherQuartz.getIcon( side, 0);
			default:
				return null;
		}
	}

	@Override
	@SideOnly( Side.CLIENT)
	@SuppressWarnings( {
		"rawtypes", "unchecked"
	})
	public void getSubBlocks( int blkID, CreativeTabs tab, List lst) {
		for (int i = 0; i < 7; ++i) {
			lst.add( new ItemStack( blkID, 1, i));
		}
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

//	@Override
//	public int onBlockPlaced( World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
//		if (side == DIR_UP || hitY <= THICKNESS) {
//			return meta;
//		}
//		return meta | 8;
//	}
//
	public void placeBlock( ItemStack stk, World world, int x, int y, int z, int posSubID) {
		if (world.checkNoEntityCollision( getCollisionBoundingBoxFromPool( world, x, y, z))) {
			if (world.setBlock( x, y, z, blockID, posSubID, 3)) {
				world.playSoundEffect( x + 0.5F, y + 0.5F, z + 0.5F, stepSound.getPlaceSound(), (stepSound.getVolume() + 1.0F) / 2.0F, stepSound.getPitch() * 0.8F);
				--stk.stackSize;
			}
		}
	}

	@Override
	@SideOnly( Side.CLIENT)
	public void registerIcons( IconRegister reg) {
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void setBlockBoundsBasedOnState( IBlockAccess world, int x, int y, int z) {
//		int meta = world.getBlockMetadata( x, y, z);
//		boolean isTop = (meta & 8) != 0;
//		if (isTop) {
//			setBlockBounds( 0.0F, THICKNESS, 0.0F, 1.0F, 1.0F, 1.0F);
//		}
//		else {
		setBlockBounds( 0.0F, 0.0F, 0.0F, 1.0F, THICKNESS, 1.0F);
//		}
	}

	@Override
	public void setBlockBoundsForItemRender() {
		setBlockBounds( 0.0F, 0.0F, 0.0F, 1.0F, THICKNESS, 1.0F);
	}

	@Override
	@SideOnly( Side.CLIENT)
	public boolean shouldSideBeRendered( IBlockAccess world, int x, int y, int z, int side) {
		return super.shouldSideBeRendered( world, x, y, z, side);
//		boolean sr = super.shouldSideBeRendered( world, x, y, z, side);
//		if (sr && (side == DIR_DOWN || side == DIR_UP)) {
//			return true;
//		}
//		boolean isThisTop = (world.getBlockMetadata( x, y, z) & 8) != 0;
//		x += Position.antiX( side);
//		y += Position.antiY( side);
//		z += Position.antiZ( side);
//		boolean isSideTop = (world.getBlockMetadata( x, y, z) & 8) != 0;
//		if (sr) {
//			return isSideTop ^ isThisTop;
//		}
//		switch (side) {
//			case DIR_DOWN:
//				return isSideTop || isThisTop;
//			case DIR_UP:
//				return !isSideTop || !isThisTop;
//			default:
//				return false;
//		}
	}
}
