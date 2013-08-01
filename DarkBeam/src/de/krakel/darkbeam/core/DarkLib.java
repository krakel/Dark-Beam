/**
 * Dark Beam
 * DarkLib.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import de.krakel.darkbeam.lib.BlockType;

public class DarkLib {
	public static final double BOX_BORDER_HEIGHT = 1D / 512D;

	private DarkLib() {
	}

	public static boolean canBreakPower( int id) {
		Block blk = Block.blocksList[id];
		return blk != null && blk.blockMaterial.isOpaque() && blk.renderAsNormalBlock();
	}

	public static boolean canPowered( int id) {
		return id == Block.pistonBase.blockID || id == Block.pistonStickyBase.blockID
			|| id == Block.pistonMoving.blockID || id == Block.dispenser.blockID
			|| id == Block.redstoneLampIdle.blockID || id == Block.redstoneLampActive.blockID;
	}

	// canProvidePower()
	// BlockPressurePlate         -> pressurePlateStone, pressurePlatePlanks
	// BlockPressurePlateWeighted -> pressurePlateGold, pressurePlateIron
	// BlockButtonStone           -> stoneButton
	// BlockButtonWood            -> woodenButton
	// BlockChest                 -> chest, chestTrapped
	// BlockDaylightDetector      -> daylightSensor
	// BlockDetectorRail          -> railDetector
	// BlockLever                 -> lever
	// BlockPoweredOre            -> blockRedstone
	// BlockComparator            -> redstoneComparatorActive, redstoneComparatorIdle
	// BlockRedstoneRepeater      -> redstoneRepeaterActive, redstoneRepeaterIdle
	// BlockRedstoneTorch         -> torchRedstoneActive, torchRedstoneIdle
	// BlockRedstoneWire          -> redstoneWire
	// BlockTripWireSource        -> tripWireSource
	public static boolean canProvidePower( int id) {
		Block blk = Block.blocksList[id];
		return blk != null && blk.canProvidePower();
	}

	public static boolean canProvidePowerEdged( int id) {
		Block blk = Block.blocksList[id];
		return blk != null && blk.canProvidePower() && blk.renderAsNormalBlock();
	}

	public static <T> boolean different( T obj1, T obj2) {
		if (obj1 == null) {
			return obj2 != null;
		}
		return !obj1.equals( obj2);
	}

	public static void dropItem( World world, int x, int y, int z, ItemStack stk) {
		if (world.isRemote) {
			return;
		}
		double delta = 0.7D;
		double dx = world.rand.nextFloat() * delta + (1.0D - delta) * 0.5D;
		double dy = world.rand.nextFloat() * delta + (1.0D - delta) * 0.5D;
		double dz = world.rand.nextFloat() * delta + (1.0D - delta) * 0.5D;
		EntityItem entity = new EntityItem( world, x + dx, y + dy, z + dz, stk);
		entity.delayBeforeCanPickup = 10;
		world.spawnEntityInWorld( entity);
	}

	public static <T> boolean equals( T obj1, T obj2) {
		if (obj1 == null) {
			return obj2 == null;
		}
		return obj1.equals( obj2);
	}

	public static String format( String msg, Object... data) {
		if (data == null || data.length == 0) {
			return msg;
		}
		try {
			return String.format( msg, data);
		}
		catch (IllegalArgumentException ex) {
			return msg;
		}
	}

	private static double getBlockReachDistance( EntityPlayer player) {
		try {
			EntityPlayerMP p = (EntityPlayerMP) player;
			return p.theItemInWorldManager.getBlockReachDistance();
		}
		catch (ClassCastException ex) {
			return 5.0D;
		}
	}

	@SuppressWarnings( "unchecked")
	public static <T extends TileEntity> T getTileEntity( IBlockAccess world, int x, int y, int z, Class<T> type) {
		TileEntity tile = world.getBlockTileEntity( x, y, z);
		if (type.isInstance( tile)) {
			return (T) tile;
		}
		return null;
	}

	public static boolean isWireBlock( int id) {
		if (id == Block.redstoneWire.blockID) {
			return true;
		}
		if (id == BlockType.STAGE.getId()) {
			return true;
		}
		return false;
	}

	public static void notifyCubeChange( World world, int x, int y, int z, int blockID) {
		for (AreaType side : AreaType.valuesCube()) {
			int x1 = x + side.mDx;
			int y1 = y + side.mDy;
			int z1 = z + side.mDz;
			world.notifyBlockOfNeighborChange( x1, y1, z1, blockID);
		}
	}

	public static void notifyNeighborChange2( World world, int x, int y, int z, int blockID) {
		for (AreaType side : AreaType.valuesSide()) {
			int x1 = x + (side.mDx << 1);
			int y1 = y + (side.mDy << 1);
			int z1 = z + (side.mDz << 1);
			world.notifyBlockOfNeighborChange( x1, y1, z1, blockID);
		}
	}

	public static void placeNoise( World world, int x, int y, int z, int id) {
		Block blk = Block.blocksList[id];
		if (blk != null) {
			world.playSoundEffect( x + 0.5F, y + 0.5F, z + 0.5F, "step.stone", (blk.stepSound.getVolume() + 1F) * 0.5F, blk.stepSound.getPitch() * 0.8F);
		}
	}

	public static MovingObjectPosition retraceBlock( World world, EntityPlayer player, int x, int y, int z) {
		int id = world.getBlockId( x, y, z);
		Block blk = Block.blocksList[id];
		if (blk == null) {
			return null;
		}
		Vec3 headVec = Vec3.createVectorHelper( player.posX, player.posY + 1.62D - player.yOffset, player.posZ);
		Vec3 lookVec = player.getLook( 1.0F);
		double dist = getBlockReachDistance( player);
		Vec3 endVec = headVec.addVector( lookVec.xCoord * dist, lookVec.yCoord * dist, lookVec.zCoord * dist);
		return blk.collisionRayTrace( world, x, y, z, headVec, endVec);
	}

	public static int toDmg( ISection sec, IMaterial mat) {
		return sec.toDmg() | mat.toDmg();
	}

	public static boolean validString( String value) {
		return value != null && !"".equals( value);
	}
}
