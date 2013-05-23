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
import de.krakel.darkbeam.lib.FBlockIds;
import de.krakel.darkbeam.lib.FTextures;

public class BlockRedWire extends Block {
	public static final float MIN = 0.0625F;
	public static final float THICK = 2F / 16F;
	private boolean mPower;

	public BlockRedWire( int id, String name) {
		super( id, Material.circuits);
		setUnlocalizedName( name);
		setCreativeTab( DarkBeam.sMainTab);
		setBlockBounds( 0.0F, 0.0F, 0.0F, 1.0F, MIN, 1.0F);
		disableStats();
	}

	public static boolean isPoweredOrRepeater( IBlockAccess world, int x, int y, int z, int side) {
		if (isPowerProviderOrWire( world, x, y, z, side)) {
			return true;
		}
		int id = world.getBlockId( x, y, z);
		if (id == Block.redstoneRepeaterActive.blockID) {
			int meta = world.getBlockMetadata( x, y, z);
			return side == (meta & 3);
		}
		return false;
	}

	public static boolean isPowerProviderOrWire( IBlockAccess world, int x, int y, int z, int side) {
		int id = world.getBlockId( x, y, z);
		if (id == FBlockIds.sBlockRedWireID) {
			return true;
		}
		if (id == 0 || Block.redstoneRepeaterIdle.func_94487_f( id)) {
			return false;
		}
		Block blk = Block.blocksList[id];
		return blk != null && blk.canProvidePower();
	}

	@Override
	public boolean canPlaceBlockAt( World world, int x, int y, int z) {
		return world.doesBlockHaveSolidTopSurface( x, y - 1, z) || world.getBlockId( x, y - 1, z) == Block.glowStone.blockID;
	}

	@Override
	public boolean canProvidePower() {
		return mPower;
	}

	@Override
	@SideOnly( Side.CLIENT)
	public int colorMultiplier( IBlockAccess world, int x, int y, int z) {
		return mPower ? 0xFFFFFF : 0x5F5F5F;
	}

	@Override
	public Icon getBlockTexture( IBlockAccess world, int x, int y, int z, int side) {
//		TileEntityRedWire redWire = (TileEntityRedWire) world.getBlockTileEntity( x, y, z);
//		return redWire.isPowered ? Block.blockRedstone.getIcon( 0, 0) : Block.stone.getIcon( 0, 0);
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
	public int isProvidingStrongPower( IBlockAccess world, int x, int y, int z, int side) {
		return mPower ? isProvidingWeakPower( world, x, y, z, side) : 0;
	}

	@Override
	public int isProvidingWeakPower( IBlockAccess world, int x, int y, int z, int side) {
		if (!mPower) {
			return 0;
		}
		int meta = world.getBlockMetadata( x, y, z);
		if (meta == 0) {
			return 0;
		}
		if (side == 1) {
			return meta;
		}
		boolean toWest = isPoweredOrRepeater( world, x - 1, y, z, 1) || !world.isBlockNormalCube( x - 1, y, z) && isPoweredOrRepeater( world, x - 1, y - 1, z, -1);
		boolean toEast = isPoweredOrRepeater( world, x + 1, y, z, 3) || !world.isBlockNormalCube( x + 1, y, z) && isPoweredOrRepeater( world, x + 1, y - 1, z, -1);
		boolean toNorth = isPoweredOrRepeater( world, x, y, z - 1, 2) || !world.isBlockNormalCube( x, y, z - 1) && isPoweredOrRepeater( world, x, y - 1, z - 1, -1);
		boolean toSouth = isPoweredOrRepeater( world, x, y, z + 1, 0) || !world.isBlockNormalCube( x, y, z + 1) && isPoweredOrRepeater( world, x, y - 1, z + 1, -1);
		if (!world.isBlockNormalCube( x, y + 1, z)) {
			if (world.isBlockNormalCube( x - 1, y, z) && isPoweredOrRepeater( world, x - 1, y + 1, z, -1)) {
				toWest = true;
			}
			if (world.isBlockNormalCube( x + 1, y, z) && isPoweredOrRepeater( world, x + 1, y + 1, z, -1)) {
				toEast = true;
			}
			if (world.isBlockNormalCube( x, y, z - 1) && isPoweredOrRepeater( world, x, y + 1, z - 1, -1)) {
				toNorth = true;
			}
			if (world.isBlockNormalCube( x, y, z + 1) && isPoweredOrRepeater( world, x, y + 1, z + 1, -1)) {
				toSouth = true;
			}
		}
		if (side < 2 || side > 5) {
			return 0;
		}
		if (!toEast && !toWest) {
			if (side == 2 && (toNorth || !toSouth)) {
				return meta;
			}
			if (side == 3 && (toSouth || !toNorth)) {
				return meta;
			}
		}
		if (!toNorth && !toSouth) {
			if (side == 4 && (toWest || !toEast)) {
				return meta;
			}
			if (side == 5 && (toEast || !toWest)) {
				return meta;
			}
		}
		return 0;
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
	public void onNeighborBlockChange( World world, int x, int y, int z, int id) {
		if (!world.isRemote) {
			boolean power = world.isBlockIndirectlyGettingPowered( x, y, z);
			if (mPower != power) {
				mPower = power;
				world.markBlockForUpdate( x, y, z);
			}
		}
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
