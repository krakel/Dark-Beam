/**
 * Dark Beam
 * BlockWire.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core.block;

import static net.minecraftforge.common.ForgeDirection.DOWN;
import static net.minecraftforge.common.ForgeDirection.EAST;
import static net.minecraftforge.common.ForgeDirection.NORTH;
import static net.minecraftforge.common.ForgeDirection.SOUTH;
import static net.minecraftforge.common.ForgeDirection.UP;
import static net.minecraftforge.common.ForgeDirection.WEST;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLever;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.krakel.darkbeam.DarkBeam;
import de.krakel.darkbeam.client.renderer.block.BlockRedWireRender;
import de.krakel.darkbeam.lib.FBlockIds;
import de.krakel.darkbeam.lib.FTextures;

public class BlockRedWire extends Block {
	public static final float MIN = 0.0625F;
	public static final float THICK = 2F / 16F;
	private boolean mProvidePower;

	public BlockRedWire( int id, String name) {
		super( id, Material.circuits);
		setUnlocalizedName( name);
		setCreativeTab( DarkBeam.sMainTab);
		setBlockBounds( 0.0F, 0.0F, 0.0F, 1.0F, MIN, 1.0F);
		disableStats();
	}

//	public static boolean isPoweredOrRepeater( IBlockAccess world, int x, int y, int z, int side) {
//		if (isPowerProviderOrWire( world, x, y, z, side)) {
//			return true;
//		}
//		int id = world.getBlockId( x, y, z);
//		if (id == Block.redstoneRepeaterActive.blockID) {
//			int meta = world.getBlockMetadata( x, y, z);
//			return side == (meta & 3);
//		}
//		return false;
//	}
//
	public static boolean isPowerProviderOrWire( IBlockAccess world, int x, int y, int z, int dir) {
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
	public void breakBlock( World world, int x, int y, int z, int id, int meta) {
		if ((meta & 8) > 0) {
			world.notifyBlocksOfNeighborChange( x, y, z, blockID);
			switch (meta & 7) {
				case 1:
					world.notifyBlocksOfNeighborChange( x - 1, y, z, blockID);
					break;
				case 2:
					world.notifyBlocksOfNeighborChange( x + 1, y, z, blockID);
					break;
				case 3:
					world.notifyBlocksOfNeighborChange( x, y, z - 1, blockID);
					break;
				case 4:
					world.notifyBlocksOfNeighborChange( x, y, z + 1, blockID);
					break;
				case 5:
				case 6:
					world.notifyBlocksOfNeighborChange( x, y - 1, z, blockID);
					break;
				case 0:
				case 7:
					world.notifyBlocksOfNeighborChange( x, y + 1, z, blockID);
					break;
			}
		}
		super.breakBlock( world, x, y, z, id, meta);
	}

	@Override
	public boolean canPlaceBlockAt( World world, int x, int y, int z) {
		return world.isBlockSolidOnSide( x - 1, y, z, EAST) || world.isBlockSolidOnSide( x + 1, y, z, WEST)
			|| world.isBlockSolidOnSide( x, y, z - 1, SOUTH) || world.isBlockSolidOnSide( x, y, z + 1, NORTH)
			|| world.isBlockSolidOnSide( x, y - 1, z, UP) || world.isBlockSolidOnSide( x, y + 1, z, DOWN);
	}

	@Override
	public boolean canPlaceBlockOnSide( World world, int x, int y, int z, int side) {
		switch (ForgeDirection.getOrientation( side)) {
			case EAST:
				return world.isBlockSolidOnSide( x - 1, y, z, EAST);
			case WEST:
				return world.isBlockSolidOnSide( x + 1, y, z, WEST);
			case SOUTH:
				return world.isBlockSolidOnSide( x, y, z - 1, SOUTH);
			case NORTH:
				return world.isBlockSolidOnSide( x, y, z + 1, NORTH);
			case UP:
				return world.isBlockSolidOnSide( x, y - 1, z, UP);
			case DOWN:
				return world.isBlockSolidOnSide( x, y + 1, z, DOWN);
			default:
				return false;
		}
	}

	@Override
	public boolean canProvidePower() {
		return mProvidePower;
	}

	@Override
	@SideOnly( Side.CLIENT)
	public int colorMultiplier( IBlockAccess world, int x, int y, int z) {
		int meta = world.getBlockMetadata( x, y, z);
		return (meta & 8) == 8 ? 0xFFFFFF : 0x7F7F7F;
	}

	@Override
	public Icon getBlockTexture( IBlockAccess world, int x, int y, int z, int side) {
//		TileEntityRedWire redWire = (TileEntityRedWire) world.getBlockTileEntity( x, y, z);
//		return redWire.isPowered ? Block.blockRedstone.getIcon( 0, 0) : Block.stone.getIcon( 0, 0);
		return Block.blockRedstone.getIcon( 0, 0);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool( World world, int x, int y, int z) {
		return null;
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
	public int isProvidingStrongPower( IBlockAccess world, int x, int y, int z, int dir) {
		if (!mProvidePower) {
			return 0;
		}
		int meta = world.getBlockMetadata( x, y, z);
		if ((meta & 8) == 0) {
			return 0;
		}
		int side = meta & 7;
		switch (ForgeDirection.getOrientation( dir)) {
			case EAST:
				return side == 1 ? 15 : 0;
			case WEST:
				return side == 2 ? 15 : 0;
			case SOUTH:
				return side == 3 ? 15 : 0;
			case NORTH:
				return side == 4 ? 15 : 0;
			case UP:
				return side == 5 || side == 6 ? 15 : 0;
			case DOWN:
				return side == 0 || side == 7 ? 15 : 0;
			default:
				return 0;
		}
	}

	@Override
	public int isProvidingWeakPower( IBlockAccess world, int x, int y, int z, int dir) {
		if (!mProvidePower) {
			return 0;
		}
		int meta = world.getBlockMetadata( x, y, z);
		return (meta & 8) == 8 ? 15 : 0;
	}

	@Override
	public int onBlockPlaced( World world, int x, int y, int z, int dir, float hitX, float hitY, float hitZ, int meta) {
		int power = meta & 8;
		int off = -1;
		switch (dir) {
			case 0:
				if (world.isBlockSolidOnSide( x, y + 1, z, DOWN)) {
					off = 0;
				}
				break;
			case 1:
				if (world.isBlockSolidOnSide( x, y - 1, z, UP)) {
					off = 5;
				}
				break;
			case 2:
				if (world.isBlockSolidOnSide( x, y, z + 1, NORTH)) {
					off = 4;
				}
				break;
			case 3:
				if (world.isBlockSolidOnSide( x, y, z - 1, SOUTH)) {
					off = 3;
				}
				break;
			case 4:
				if (world.isBlockSolidOnSide( x + 1, y, z, WEST)) {
					off = 2;
				}
				break;
			case 5:
				if (world.isBlockSolidOnSide( x - 1, y, z, EAST)) {
					off = 1;
				}
				break;
		}
		return power + off;
	}

	@Override
	public void onBlockPlacedBy( World world, int x, int y, int z, EntityLiving entity, ItemStack stack) {
		int meta = world.getBlockMetadata( x, y, z);
		int dir = meta & 7;
		int power = meta & 8;
		if (dir == BlockLever.invertMetadata( 1)) {
			if ((MathHelper.floor_double( entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 1) == 0) {
				world.setBlockMetadataWithNotify( x, y, z, 5 | power, 2);
			}
			else {
				world.setBlockMetadataWithNotify( x, y, z, 6 | power, 2);
			}
		}
		else if (dir == BlockLever.invertMetadata( 0)) {
			if ((MathHelper.floor_double( entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 1) == 0) {
				world.setBlockMetadataWithNotify( x, y, z, 7 | power, 2);
			}
			else {
				world.setBlockMetadataWithNotify( x, y, z, 0 | power, 2);
			}
		}
//		if (!world.isRemote) {
////			TileEntityRedWire redWire = (TileEntityRedWire) world.getBlockTileEntity( x, y, z);
//			TileEntity wire = world.getBlockTileEntity( x, y, z);
//			if (wire != null && !world.isRemote) {
////				wire.updateMasks();
////				wire.scanAndUpdate();
//			}
//		}
//		world.markBlockForUpdate( x, y, z);
	}

	@Override
	public void onNeighborBlockChange( World world, int x, int y, int z, int id) {
		if (canPlaceBlockAt( world, x, y, z)) {
			int dir = world.getBlockMetadata( x, y, z) & 7;
			switch (dir) {
				case 1:
					if (!world.isBlockSolidOnSide( x - 1, y, z, EAST)) {
						dropBlock( world, x, y, z);
					}
					break;
				case 2:
					if (!world.isBlockSolidOnSide( x + 1, y, z, WEST)) {
						dropBlock( world, x, y, z);
					}
					break;
				case 3:
					if (!world.isBlockSolidOnSide( x, y, z - 1, SOUTH)) {
						dropBlock( world, x, y, z);
					}
					break;
				case 4:
					if (!world.isBlockSolidOnSide( x, y, z + 1, NORTH)) {
						dropBlock( world, x, y, z);
					}
					break;
				case 5:
				case 6:
					if (!world.isBlockSolidOnSide( x, y - 1, z, UP)) {
						dropBlock( world, x, y, z);
					}
					break;
				case 0:
				case 7:
					if (!world.isBlockSolidOnSide( x, y + 1, z, DOWN)) {
						dropBlock( world, x, y, z);
					}
					break;
			}
		}
		else {
			dropBlock( world, x, y, z);
		}
//		if (!world.isRemote) {
//		boolean power = world.isBlockIndirectlyGettingPowered( x, y, z);
//		if (mPower != power) {
//			mPower = power;
//			world.markBlockForUpdate( x, y, z);
//		}
//	}
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

	@Override
	public void setBlockBoundsBasedOnState( IBlockAccess world, int x, int y, int z) {
//		int dir = world.getBlockMetadata( x, y, z) & 7;
//		float f = 0.1875F;
//		if (dir == 1) {
//			setBlockBounds( 0.0F, 0.2F, 0.5F - f, f * 2.0F, 0.8F, 0.5F + f);
//		}
//		else if (dir == 2) {
//			setBlockBounds( 1.0F - f * 2.0F, 0.2F, 0.5F - f, 1.0F, 0.8F, 0.5F + f);
//		}
//		else if (dir == 3) {
//			setBlockBounds( 0.5F - f, 0.2F, 0.0F, 0.5F + f, 0.8F, f * 2.0F);
//		}
//		else if (dir == 4) {
//			setBlockBounds( 0.5F - f, 0.2F, 1.0F - f * 2.0F, 0.5F + f, 0.8F, 1.0F);
//		}
//		else if (dir != 5 && dir != 6) {
//			if (dir == 0 || dir == 7) {
//				f = 0.25F;
//				setBlockBounds( 0.5F - f, 0.4F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
//			}
//		}
//		else {
//			f = 0.25F;
//			setBlockBounds( 0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.6F, 0.5F + f);
//		}
		super.setBlockBoundsBasedOnState( world, x, y, z);
	}

	private void dropBlock( World world, int x, int y, int z) {
		int meta = world.getBlockMetadata( x, y, z);
		dropBlockAsItem( world, x, y, z, meta, 0);
		world.setBlockToAir( x, y, z);
	}
}
