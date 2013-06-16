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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

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

	@SuppressWarnings( "unchecked")
	public static <T extends TileEntity> T getTileEntity( IBlockAccess world, int x, int y, int z, Class<T> type) {
		TileEntity tile = world.getBlockTileEntity( x, y, z);
		if (type.isInstance( tile)) {
			return (T) tile;
		}
		return null;
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
