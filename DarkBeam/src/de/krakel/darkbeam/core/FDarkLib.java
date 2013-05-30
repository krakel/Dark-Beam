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
import de.krakel.darkbeam.core.helper.LogHelper;

public class FDarkLib implements IDirection {
	private FDarkLib() {
	}

	private static boolean canAddCover( World world, MovingObjectPosition pos, int dmg) {
//		if (world.canPlaceEntityOnSide( blockCoverPlate.blockID, pos.blockX, pos.blockY, pos.blockZ, false, pos.sideHit, null)) {
//			return true;
//		}
//		ICoverable var3 = getTileEntity( world, pos.blockX, pos.blockY, pos.blockZ);
//		if (var3 != null) {
//			return var3.canAddCover( pos.subHit, dmg);
//		}
		return false;
	}

	private static boolean clickInside( MovingObjectPosition pos) {
		if (pos.subHit < 0) {
			return false;
		}
		if (pos.subHit < DIR_MAX) {
			return pos.sideHit != pos.subHit;
		}
//		if (pos.subHit < 14) {
//			int sub = pos.subHit - DIR_MAX;
//			sub = sub >> 2 | (sub & 3) << 1;
//			return !(((pos.sideHit ^ sub >> (pos.sideHit >> 1)) & 1) == 0);
//		}
//		if (pos.subHit < 26) {
//			int sub = pos.subHit - 14;
//			sub = stripToCoverMask( sub);
//			return !((sub & 1 << (pos.sideHit ^ 1)) <= 0);
//		}
//		return !(pos.subHit < 29 ? true : pos.subHit == 29);
		return false;
	}

	private static int coverSide( MovingObjectPosition hit) {
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

	public static MovingObjectPosition getPlacement( World world, MovingObjectPosition hit, int dmg) {
		MovingObjectPosition pos = new MovingObjectPosition( hit.blockX, hit.blockY, hit.blockZ, hit.sideHit, hit.hitVec);
		int side = coverSide( hit);
		if (side != pos.sideHit) { // on border area
			pos.subHit = side;
			if (clickInside( hit) && canAddCover( world, pos, dmg)) {
				return pos;
			}
			move( pos);
		}
		else if (clickInside( hit)) {
			pos.subHit = side ^ 1;
		}
		else {
			pos.subHit = side;
			if (canAddCover( world, pos, dmg)) {
				return pos;
			}
			move( pos);
			pos.subHit = side ^ 1;
		}
		return canAddCover( world, pos, dmg) ? pos : null;
	}

	@SuppressWarnings( "unchecked")
	public static <T extends TileEntity> T getTileEntity( IBlockAccess world, int x, int y, int z) {
		try {
			return (T) world.getBlockTileEntity( x, y, z);
		}
		catch (ClassCastException ex) {
			LogHelper.severe( ex, "wrong tile entity");
		}
		return null;
	}

	private static void move( MovingObjectPosition pos) {
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

	public static boolean validString( String value) {
		return value != null && !"".equals( value);
	}
}
