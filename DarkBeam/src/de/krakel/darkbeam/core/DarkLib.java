/**
 * Dark Beam
 * DarkLib.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import de.krakel.darkbeam.lib.BlockType;

public class DarkLib implements IDirection {
	public static final double BOX_BORDER_MIN = 1D / 4D;
	public static final double BOX_BORDER_MAX = 1D - BOX_BORDER_MIN;
	public static final double BOX_BORDER_HEIGHT = 1D / 512D;
	public static final String[] COLOR_NAMES = new String[] {
		"white", "orange", "magenta", "lightBlue", "yellow", "lime", "pink", "gray", "lightGray", "cyan", "purple",
		"blue", "brown", "green", "red", "black"
	};
	public static final String[] PANEL_NAMES = new String[] {
		"stone", "sand", "wood", "cobble", "brick", "smoothStoneBrick", "netherBrick", "quartz"
	};

	private DarkLib() {
	}

	private static boolean canMaskAdd( World world, MovingObjectPosition hit, int subID, ItemStack stk) {
		if (world.canPlaceEntityOnSide( BlockType.Masking.getId(), hit.blockX, hit.blockY, hit.blockZ, false, hit.sideHit, null, stk)) {
			return true;
		}
//		ICoverable var3 = getTileEntity( world, pos.blockX, pos.blockY, pos.blockZ);
//		if (var3 != null) {
//			return var3.canAddCover( pos.subHit, subID);
//		}
		return false;
	}

	public static int colorSubID( int mate) {
		return mate & 15;
	}

	public static String colorSubName( int meta) {
		return COLOR_NAMES[meta & 15];
	}

	public static <T> boolean different( T obj1, T obj2) {
		if (obj1 != null) {
			return !obj1.equals( obj2);
		}
		return obj2 != null;
	}

	public static <T> boolean equals( T obj1, T obj2) {
		if (obj1 != null) {
			return obj1.equals( obj2);
		}
		return obj2 == null;
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

	private static double getBlockReachDistance( EntityLiving player) {
		try {
			EntityPlayerMP p = (EntityPlayerMP) player;
			return p.theItemInWorldManager.getBlockReachDistance();
		}
		catch (ClassCastException ex) {
			return 5.0D;
		}
	}

	public static MovingObjectPosition getPosition( World world, MovingObjectPosition pos, int subID, ItemStack stack) {
		MovingObjectPosition hit = new MovingObjectPosition( pos.blockX, pos.blockY, pos.blockZ, pos.sideHit, pos.hitVec);
		int hitArea = maskSide( pos);
		if (isInside( pos.subHit, pos.sideHit)) {
			if (hitArea == hit.sideHit) {
				hit.subHit = hitArea ^ 1;
				if (canMaskAdd( world, hit, subID, stack)) {
					return hit;
				}
				hit.subHit = hitArea;
			}
			else {
				hit.subHit = hitArea;
				if (canMaskAdd( world, hit, subID, stack)) {
					return hit;
				}
				Position.move( hit);
			}
		}
		else if (hitArea == hit.sideHit) {
			hit.subHit = hitArea;
			if (canMaskAdd( world, hit, subID, stack)) {
				return hit;
			}
			hit.subHit = hitArea ^ 1;
			Position.move( hit);
		}
		else {
			hit.subHit = hitArea;
			Position.move( hit);
		}
		return canMaskAdd( world, hit, subID, stack) ? hit : null;
	}

	@SuppressWarnings( "unchecked")
	public static <T extends TileEntity> T getTileEntity( IBlockAccess world, int x, int y, int z, Class<T> type) {
		TileEntity tile = world.getBlockTileEntity( x, y, z);
		if (type.isInstance( tile)) {
			return (T) tile;
		}
		return null;
	}

	private static boolean isInside( int subHit, int sideHit) {
		if (subHit < 0) {
			return false;
		}
		if (subHit < DIR_MAX) {
			return (sideHit ^ subHit) == 1;
		}
//		if (subHit < 14) {
//			int sub = subHit - DIR_MAX;
//			sub = sub >> 2 | (sub & 3) << 1;
//			return ((sideHit ^ sub >> (sideHit >> 1)) & 1) == 1;
//		}
//		if (subHit < 26) {
//			int sub = subHit - 14;
//			sub = stripToCoverMask( sub);
//			return !((sub & 1 << (sideHit ^ 1)) <= 0);
//		}
//		return subHit < 29 ? false : subHit != 29;
		return false;
	}

	private static int maskSide( MovingObjectPosition hit) {
		double dx = hit.hitVec.xCoord - hit.blockX;
		double dy = hit.hitVec.yCoord - hit.blockY;
		double dz = hit.hitVec.zCoord - hit.blockZ;
		switch (hit.sideHit) {
			case DIR_DOWN:
			case DIR_UP:
				if (BOX_BORDER_MIN < dz && dz < BOX_BORDER_MAX && BOX_BORDER_MIN < dx && dx < BOX_BORDER_MAX) {
					return hit.sideHit;
				}
				if (dz > dx) {
					return dz + dx > 1D ? DIR_SOUTH : DIR_WEST;
				}
				return dz + dx > 1D ? DIR_EAST : DIR_NORTH;
			case DIR_NORTH:
			case DIR_SOUTH:
				if (BOX_BORDER_MIN < dy && dy < BOX_BORDER_MAX && BOX_BORDER_MIN < dx && dx < BOX_BORDER_MAX) {
					return hit.sideHit;
				}
				if (dy > dx) {
					return dy + dx > 1D ? DIR_UP : DIR_WEST;
				}
				return dy + dx > 1D ? DIR_EAST : DIR_DOWN;
			case DIR_WEST:
			case DIR_EAST:
				if (BOX_BORDER_MIN < dy && dy < BOX_BORDER_MAX && BOX_BORDER_MIN < dz && dz < BOX_BORDER_MAX) {
					return hit.sideHit;
				}
				if (dy > dz) {
					return dy + dz > 1D ? DIR_UP : DIR_NORTH;
				}
				return dy + dz > 1D ? DIR_SOUTH : DIR_DOWN;
			default:
				return DIR_DOWN;
		}
	}

	public static int panelSubID( int meta) {
		return meta & 7;
	}

	public static String panelSubName( int meta) {
		return PANEL_NAMES[meta & 7];
	}

	public static void placeNoise( World world, int x, int y, int z, int id) {
		Block blk = Block.blocksList[id];
		if (blk != null) {
			world.playSoundEffect( x + 0.5F, y + 0.5F, z + 0.5F, "step.stone", (blk.stepSound.getVolume() + 1F) * 0.5F, blk.stepSound.getPitch() * 0.8F);
		}
	}

	public static MovingObjectPosition retraceBlock( World world, EntityLiving player, int x, int y, int z) {
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

	public static boolean validString( String value) {
		return value != null && !"".equals( value);
	}
}
