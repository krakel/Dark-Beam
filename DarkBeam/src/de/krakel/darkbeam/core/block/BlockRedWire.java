/**
 * Dark Beam
 * BlockWire.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core.block;

import static net.minecraftforge.common.ForgeDirection.UNKNOWN;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.krakel.darkbeam.DarkBeam;
import de.krakel.darkbeam.client.renderer.block.BlockRedWireRender;
import de.krakel.darkbeam.core.helper.LogHelper;
import de.krakel.darkbeam.lib.FTextures;
import de.krakel.darkbeam.tileentity.TileRedWire;

public class BlockRedWire extends BlockContainer {
//	public static final float MIN = 0.0625F;
	public static final float THICK = 2F / 16F;

//	private boolean mProvidePower = true;
	public BlockRedWire( int id, String name) {
		super( id, Material.circuits);
		setUnlocalizedName( name);
		setCreativeTab( DarkBeam.sMainTab);
		setBlockBounds( 0.0F, 0.0F, 0.0F, 1.0F, THICK, 1.0F);
		disableStats();
	}

	private static double getBlockReachDistance( EntityLiving player) {
		try {
			EntityPlayerMP p = (EntityPlayerMP) player;
			return p.theItemInWorldManager.getBlockReachDistance();
		}
		catch (ClassCastException ex) {
			return 5.0D;
		}
	}

	public static MovingObjectPosition retraceBlock( World world, EntityLiving player, int x, int y, int z) {
		Vec3 headVec = Vec3.createVectorHelper( player.posX, player.posY + 1.62D - player.yOffset, player.posZ);
		Vec3 lookVec = player.getLook( 1.0F);
		double reach = getBlockReachDistance( player);
		Vec3 endVec = headVec.addVector( lookVec.xCoord * reach, lookVec.yCoord * reach, lookVec.zCoord * reach);
		Block blk = Block.blocksList[world.getBlockId( x, y, z)];
		if (blk == null) {
			return null;
		}
		return blk.collisionRayTrace( world, x, y, z, headVec, endVec);
	}

	@Override
	public void breakBlock( World world, int x, int y, int z, int id, int meta) {
		LogHelper.debug( "breakBlock", world.isRemote, x, y, z, id, meta);
		TileRedWire tile = DarkBeam.getTileEntity( world, x, y, z);
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
		LogHelper.debug( "canPlaceBlockAt", world.isRemote, x, y, z);
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			if (world.isBlockSolidOnSide( x - dir.offsetX, y - dir.offsetY, z - dir.offsetZ, dir)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean canPlaceBlockOnSide( World world, int x, int y, int z, int side) {
		LogHelper.debug( "canPlaceBlockOnSide", world.isRemote, x, y, z, side);
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
		TileRedWire tile = DarkBeam.getTileEntity( world, x, y, z);
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

	@Override
	public boolean onBlockActivated( World world, int x, int y, int z, EntityPlayer player, int side, float xOff, float yOff, float zOff) {
		return super.onBlockActivated( world, x, y, z, player, side, xOff, yOff, zOff);
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
		LogHelper.debug( "onBlockPlacedBy", world.isRemote, x, y, z);
		if (!world.isRemote) {
			TileRedWire tile = DarkBeam.getTileEntity( world, x, y, z);
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
			TileRedWire tile = DarkBeam.getTileEntity( world, x, y, z);
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
		blockIcon = reg.registerIcon( FTextures.PATH_DEFAULT + getUnlocalizedName2());
	}

	@Override
	public boolean removeBlockByPlayer( World world, EntityPlayer player, int x, int y, int z) {
		if (world.isRemote) {
			return true;
		}
		MovingObjectPosition pos = retraceBlock( world, player, x, y, z);
		if (pos == null) {
			return false;
		}
		if (pos.typeOfHit != EnumMovingObjectType.TILE) {
			return false;
		}
		TileRedWire tile = DarkBeam.getTileEntity( world, x, y, z);
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
