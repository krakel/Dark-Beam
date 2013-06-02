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

import de.krakel.darkbeam.DarkBeam;
import de.krakel.darkbeam.IDirection;
import de.krakel.darkbeam.lib.BlockType;

public class TestBlockPanel extends Block implements IDirection {
	private static final float THICKNESS = 0.5F;
	public static final String[] PANEL_NAMES = new String[] {
		"stone", "sand", "wood", "cobble", "brick", "smoothStoneBrick", "netherBrick", "quartz"
	};

//	private Icon mSideTexture;
	public TestBlockPanel( BlockType type) {
		super( type.getId(), Material.rock);
		setHardness( 2.0F);
		setResistance( 10.0F);
		setStepSound( soundStoneFootstep);
		setUnlocalizedName( type.mName);
		setCreativeTab( DarkBeam.sMainTab);
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
		return new ItemStack( blockID, 2, meta & 7);
	}

	@Override
	public int damageDropped( int dmg) {
		return dmg & 7;
	}

	@Override
	public int getDamageValue( World world, int x, int y, int z) {
		int dmg = world.getBlockMetadata( x, y, z);
		return damageDropped( dmg);
	}

	@Override
	@SideOnly( Side.CLIENT)
	public Icon getIcon( int side, int meta) {
		switch (meta & 7) {
			case 0:
				return Block.stone.getIcon( side, 0);
//				return side == DIR_UP || side == DIR_DOWN ? blockIcon : mSideTexture;
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

	@Override
	public int onBlockPlaced( World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
		if (side == DIR_UP || hitY <= THICKNESS) {
			return meta;
		}
		return meta | 8;
	}

	@Override
	@SideOnly( Side.CLIENT)
	public void registerIcons( IconRegister reg) {
//		blockIcon = reg.registerIcon( "stoneslab_top");
//		mSideTexture = reg.registerIcon( "stoneslab_side");
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void setBlockBoundsBasedOnState( IBlockAccess world, int x, int y, int z) {
		int meta = world.getBlockMetadata( x, y, z);
		boolean isTop = (meta & 8) != 0;
		if (isTop) {
			setBlockBounds( 0.0F, THICKNESS, 0.0F, 1.0F, 1.0F, 1.0F);
		}
		else {
			setBlockBounds( 0.0F, 0.0F, 0.0F, 1.0F, THICKNESS, 1.0F);
		}
	}

	@Override
	public void setBlockBoundsForItemRender() {
		setBlockBounds( 0.0F, 0.0F, 0.0F, 1.0F, THICKNESS, 1.0F);
	}

	@Override
	@SideOnly( Side.CLIENT)
	public boolean shouldSideBeRendered( IBlockAccess world, int x, int y, int z, int side) {
		return super.shouldSideBeRendered( world, x, y, z, side);
//		if (side != 1 && side != 0 && !super.shouldSideBeRendered( world, x, y, z, side)) {
//			return false;
//		}
//		else {
//			int i1 = x + Facing.offsetsXForSide[Facing.oppositeSide[side]];
//			int j1 = y + Facing.offsetsYForSide[Facing.oppositeSide[side]];
//			int k1 = z + Facing.offsetsZForSide[Facing.oppositeSide[side]];
//			boolean flag = (world.getBlockMetadata( i1, j1, k1) & 8) != 0;
//			return flag ? side == 0 ? true : side == 1 && super.shouldSideBeRendered( world, x, y, z, side) ? true : (world.getBlockMetadata( x, y, z) & 8) == 0 : side == 1 ? true : side == 0
//				&& super.shouldSideBeRendered( world, x, y, z, side) ? true : (world.getBlockMetadata( x, y, z) & 8) != 0;
//		}
		//
//		boolean sr = super.shouldSideBeRendered( world, x, y, z, side);
//		if (sr && (side == DIR_DOWN || side == DIR_UP)) {
//			return true;
//		}
//		int sx = x + Facing.offsetsXForSide[Facing.oppositeSide[side]];
//		int sy = y + Facing.offsetsYForSide[Facing.oppositeSide[side]];
//		int sz = z + Facing.offsetsZForSide[Facing.oppositeSide[side]];
//		boolean isSideTop = (world.getBlockMetadata( sx, sy, sz) & 8) != 0;
//		boolean isThisTop = (world.getBlockMetadata( x, y, z) & 8) != 0;
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
