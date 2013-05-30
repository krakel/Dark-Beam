/**
 * Dark Beam
 * BlockWire.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.block;

import static net.minecraftforge.common.ForgeDirection.UNKNOWN;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.krakel.darkbeam.DarkBeam;
import de.krakel.darkbeam.client.renderer.BlockRedWireRender;
import de.krakel.darkbeam.core.DarkLib;
import de.krakel.darkbeam.core.helper.LogHelper;
import de.krakel.darkbeam.lib.Textures;
import de.krakel.darkbeam.tile.TileRedWire;

public class BlockRedWire extends BlockContainer {
//	public static final float MIN = 0.0625F;
	public static final float THICK = 2F / 16F;

//	private boolean mProvidePower = true;
	public BlockRedWire( int id, String name) {
		super( id, DarkBeam.MAT_DARK);
		setUnlocalizedName( name);
		setCreativeTab( DarkBeam.sMainTab);
		setBlockBounds( 0.0F, 0.0F, 0.0F, 1.0F, THICK, 1.0F);
		disableStats();
	}

	@Override
	public void breakBlock( World world, int x, int y, int z, int id, int meta) {
		LogHelper.info( "{0}, x={1}, y={2}, z={3}, id={4}, meta={5}", world.isRemote, x, y, z, id, meta);
		TileRedWire tile = DarkLib.getTileEntity( world, x, y, z, TileRedWire.class);
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			if (TileRedWire.isSet( tile.mSurfaces, dir)) {
//			if (TileRedWire.isSet( tile.mConnections, dir)) {
				world.notifyBlocksOfNeighborChange( x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, blockID);
			}
		}
		super.breakBlock( world, x, y, z, id, meta);
	}

	@Override
	public boolean canPlaceBlockAt( World world, int x, int y, int z) {
		LogHelper.info( "{0}, x={1}, y={2}, z={3}", world.isRemote, x, y, z);
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			if (world.isBlockSolidOnSide( x - dir.offsetX, y - dir.offsetY, z - dir.offsetZ, dir)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean canPlaceBlockOnSide( World world, int x, int y, int z, int side) {
		LogHelper.info( "{0}, x={1}, y={2}, z={3}, side={4}", world.isRemote, x, y, z, side);
		ForgeDirection dir = ForgeDirection.getOrientation( side);
		if (dir == UNKNOWN) {
			return false;
		}
		return world.isBlockSolidOnSide( x - dir.offsetX, y - dir.offsetY, z - dir.offsetZ, dir);
	}

	@Override
	public boolean canProvidePower() {
		return true;
	}

	@Override
	@SideOnly( Side.CLIENT)
	public int colorMultiplier( IBlockAccess world, int x, int y, int z) {
		TileRedWire tile = DarkLib.getTileEntity( world, x, y, z, TileRedWire.class);
		return tile.isPowered() ? 0xFFFFFF : 0x7F7F7F;
	}

	@Override
	public TileEntity createNewTileEntity( World world) {
//		return new TileRedWire();
		return null;
	}

	@Override
	public TileEntity createTileEntity( World world, int meta) {
		return super.createTileEntity( world, meta);
//		try {
//			return (TileEntity) tileEntityMap[var2].getDeclaredConstructor( new Class[0]).newInstance( new Object[0]);
//		}
//		catch (Exception var5) {
//			return null;
//		}
	}

	@Override
	public int damageDropped( int value) {
		return value;
	}

	@Override
	public Icon getBlockTexture( IBlockAccess world, int x, int y, int z, int side) {
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
	public int isProvidingStrongPower( IBlockAccess world, int x, int y, int z, int side) {
		return super.isProvidingStrongPower( world, x, y, z, side);
	}

	@Override
	public int isProvidingWeakPower( IBlockAccess world, int x, int y, int z, int side) {
		return super.isProvidingWeakPower( world, x, y, z, side);
	}

	//	@Override
//	public int isProvidingStrongPower( IBlockAccess world, int x, int y, int z, int dir) {
//		int meta = world.getBlockMetadata( x, y, z);
//		if ((meta & 8) == 0) {
//			return 0;
//		}
//		int side = meta & 7;
//		switch (ForgeDirection.getOrientation( dir)) {
//			case EAST:
//				return side == 1 ? 15 : 0;
//			case WEST:
//				return side == 2 ? 15 : 0;
//			case SOUTH:
//				return side == 3 ? 15 : 0;
//			case NORTH:
//				return side == 4 ? 15 : 0;
//			case UP:
//				return side == 5 || side == 6 ? 15 : 0;
//			case DOWN:
//				return side == 0 || side == 7 ? 15 : 0;
//			default:
//				return 0;
//		}
//	}
//
//	@Override
//	public int isProvidingWeakPower( IBlockAccess world, int x, int y, int z, int dir) {
//		int meta = world.getBlockMetadata( x, y, z);
//		return (meta & 8) == 8 ? 15 : 0;
//	}
//	
	@Override
	public void onBlockPlacedBy( World world, int x, int y, int z, EntityLiving player, ItemStack stack) {
		LogHelper.info( "{0}, x={1}, y={2}, z={3}", world.isRemote, x, y, z);
		if (!world.isRemote) {
			TileRedWire tile = DarkLib.getTileEntity( world, x, y, z, TileRedWire.class);
			tile.updateOnPlace();
		}
		world.markBlockForUpdate( x, y, z);
	}

	@Override
	public void onEntityCollidedWithBlock( World world, int x, int y, int z, Entity entity) {
		super.onEntityCollidedWithBlock( world, x, y, z, entity);
	}

	@Override
	public void onNeighborBlockChange( World world, int x, int y, int z, int id) {
		if (!world.isRemote) {
			TileRedWire tile = DarkLib.getTileEntity( world, x, y, z, TileRedWire.class);
			tile.updateOnNeighbor();
		}
		world.markBlockForUpdate( x, y, z);
	}

	@Override
	@SideOnly( Side.CLIENT)
	public void randomDisplayTick( World world, int x, int y, int z, Random rand) {
		super.randomDisplayTick( world, x, y, z, rand);
	}

	@Override
	@SideOnly( Side.CLIENT)
	public void registerIcons( IconRegister reg) {
		blockIcon = reg.registerIcon( Textures.PATH_DEFAULT + getUnlocalizedName2());
	}

	@Override
	public boolean removeBlockByPlayer( World world, EntityPlayer player, int x, int y, int z) {
		if (world.isRemote) {
			return true;
		}
		MovingObjectPosition pos = DarkLib.retraceBlock( world, player, x, y, z);
		if (pos == null) {
			return false;
		}
		if (pos.typeOfHit != EnumMovingObjectType.TILE) {
			return false;
		}
		TileRedWire tile = DarkLib.getTileEntity( world, x, y, z, TileRedWire.class);
		if (tile != null) {
			tile.onHarvestPart( player, pos.subHit);
		}
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
//
//	private void dropBlock( World world, int x, int y, int z) {
//		int meta = world.getBlockMetadata( x, y, z);
//		dropBlockAsItem( world, x, y, z, meta, 0);
//		world.setBlockToAir( x, y, z);
//	}
}
