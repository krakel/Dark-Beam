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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import de.krakel.darkbeam.IDirection;
import de.krakel.darkbeam.block.BlockRedWire;

public class DarkLib implements IDirection {
	private DarkLib() {
	}

	private static boolean canUnitAdd( World world, MovingObjectPosition pos, int subID) {
//		if (world.canPlaceEntityOnSide( blockCoverPlate.blockID, pos.blockX, pos.blockY, pos.blockZ, false, pos.sideHit, null)) {
//			return true;
//		}
//		ICoverable var3 = getTileEntity( world, pos.blockX, pos.blockY, pos.blockZ);
//		if (var3 != null) {
//			return var3.canAddCover( pos.subHit, subID);
//		}
		return false;
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

	public static MovingObjectPosition getPosition( World world, MovingObjectPosition hit, int subID) {
		MovingObjectPosition pos = new MovingObjectPosition( hit.blockX, hit.blockY, hit.blockZ, hit.sideHit, hit.hitVec);
		int hitArea = unitSide( hit);
		if (isInside( hit.subHit, hit.sideHit)) {
			if (hitArea == pos.sideHit) {
				pos.subHit = hitArea ^ 1;
				if (canUnitAdd( world, pos, subID)) {
					return pos;
				}
				pos.subHit = hitArea;
			}
			else {
				pos.subHit = hitArea;
				if (canUnitAdd( world, pos, subID)) {
					return pos;
				}
				movePosition( pos);
			}
		}
		else if (hitArea == pos.sideHit) {
			pos.subHit = hitArea;
			if (canUnitAdd( world, pos, subID)) {
				return pos;
			}
			pos.subHit = hitArea ^ 1;
			movePosition( pos);
		}
		else {
			pos.subHit = hitArea;
			movePosition( pos);
		}
		return canUnitAdd( world, pos, subID) ? pos : null;
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

	private static void movePosition( MovingObjectPosition pos) {
		switch (pos.sideHit) {
			case DIR_DOWN:
				--pos.blockY;
				break;
			case DIR_UP:
				++pos.blockY;
				break;
			case DIR_NORTH:
				--pos.blockZ;
				break;
			case DIR_SOUTH:
				++pos.blockZ;
				break;
			case DIR_WEST:
				--pos.blockX;
				break;
			case DIR_EAST:
				++pos.blockX;
				break;
			default:
				--pos.blockY;
		}
	}

	public static MovingObjectPosition retraceBlock( World world, EntityLiving player, int x, int y, int z) {
		Vec3 headVec = Vec3.createVectorHelper( player.posX, player.posY + 1.62D - player.yOffset, player.posZ);
		Vec3 lookVec = player.getLook( 1.0F);
		double reach = BlockRedWire.getBlockReachDistance( player);
		Vec3 endVec = headVec.addVector( lookVec.xCoord * reach, lookVec.yCoord * reach, lookVec.zCoord * reach);
		Block blk = Block.blocksList[world.getBlockId( x, y, z)];
		if (blk == null) {
			return null;
		}
		return blk.collisionRayTrace( world, x, y, z, headVec, endVec);
	}

	private static int unitSide( MovingObjectPosition hit) {
		double dx = hit.hitVec.xCoord - hit.blockX;
		double dy = hit.hitVec.yCoord - hit.blockY;
		double dz = hit.hitVec.zCoord - hit.blockZ;
		double min = 0.25D;
		double max = 0.75D;
		switch (hit.sideHit) {
			case DIR_DOWN:
			case DIR_UP:
				if (min < dz && dz < max && min < dx && dx < max) {
					return hit.sideHit;
				}
				if (dz > dx) {
					return dz + dx > 1D ? DIR_SOUTH : DIR_WEST;
				}
				return dz + dx > 1D ? DIR_EAST : DIR_NORTH;
			case DIR_NORTH:
			case DIR_SOUTH:
				if (min < dy && dy < max && min < dx && dx < max) {
					return hit.sideHit;
				}
				if (dy > dx) {
					return dy + dx > 1D ? DIR_UP : DIR_WEST;
				}
				return dy + dx > 1D ? DIR_EAST : DIR_DOWN;
			case DIR_WEST:
			case DIR_EAST:
				if (min < dy && dy < max && min < dz && dz < max) {
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

	public static boolean validString( String value) {
		return value != null && !"".equals( value);
	}
}
