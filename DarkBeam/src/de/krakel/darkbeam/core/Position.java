/**
 * Dark Beam
 * Position.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.common.ForgeDirection;

public class Position implements IDirection {
	private static final int[] REL_X = {
		0, 0, 0, 0, -1, 1
	};
	private static final int[] REL_Y = {
		-1, 1, 0, 0, 0, 0
	};
	private static final int[] REL_Z = {
		0, 0, -1, 1, 0, 0
	};
	private static final int[] ANTI_X = {
		0, 0, 0, 0, 1, -1
	};
	private static final int[] ANTI_Y = {
		1, -1, 0, 0, 0, 0
	};
	private static final int[] ANTI_Z = {
		0, 0, 1, -1, 0, 0
	};
	private static final float[] NORM_X = {
		0F, 0F, 0F, 0F, -1F, 1F
	};
	private static final float[] NORM_Y = {
		-1F, 1F, 0F, 0F, 0F, 0F
	};
	private static final float[] NORM_Z = {
		0F, 0F, -1F, 1F, 0F, 0F
	};
	private static final int[] OFFSET = {
		0x0001, 0x0002, 0x0004, 0x0008, 0x0010, 0x0020
	};
	private static final ForgeDirection[] FORGE = {
		ForgeDirection.DOWN, ForgeDirection.UP, ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.WEST,
		ForgeDirection.EAST
	};
	private static final ForgeDirection[] OPPOSITES = {
		ForgeDirection.UP, ForgeDirection.DOWN, ForgeDirection.SOUTH, ForgeDirection.NORTH, ForgeDirection.EAST,
		ForgeDirection.WEST
	};
	private static final String[] NAMES = {
		"DOWN", "UP", "NORTH", "SOUTH", "WEST", "EAST"
	};
	private static final double BOX_BORDER_MIN = 1D / 4D;
	private static final double BOX_BORDER_MAX = 1D - BOX_BORDER_MIN;

	private Position() {
	}

	public static int antiX( int side) {
		try {
			return ANTI_X[side];
		}
		catch (IndexOutOfBoundsException ex) {
			return 0;
		}
	}

	public static int antiY( int side) {
		try {
			return ANTI_Y[side];
		}
		catch (IndexOutOfBoundsException ex) {
			return 0;
		}
	}

	public static int antiZ( int side) {
		try {
			return ANTI_Z[side];
		}
		catch (IndexOutOfBoundsException ex) {
			return 0;
		}
	}

	public static void move( MovingObjectPosition pos) {
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

	public static float normX( int side) {
		try {
			return NORM_X[side];
		}
		catch (IndexOutOfBoundsException ex) {
			return 0F;
		}
	}

	public static float normY( int side) {
		try {
			return NORM_Y[side];
		}
		catch (IndexOutOfBoundsException ex) {
			return 0F;
		}
	}

	public static float normZ( int side) {
		try {
			return NORM_Z[side];
		}
		catch (IndexOutOfBoundsException ex) {
			return 0F;
		}
	}

	// 1 << side
	public static int offset( int side) {
		try {
			return OFFSET[side];
		}
		catch (IndexOutOfBoundsException ex) {
			return 0;
		}
	}

	public static int relX( int side) {
		try {
			return REL_X[side];
		}
		catch (IndexOutOfBoundsException ex) {
			return 0;
		}
	}

	public static int relY( int side) {
		try {
			return REL_Y[side];
		}
		catch (IndexOutOfBoundsException ex) {
			return 0;
		}
	}

	public static int relZ( int side) {
		try {
			return REL_Z[side];
		}
		catch (IndexOutOfBoundsException ex) {
			return 0;
		}
	}

	public static ForgeDirection toForge( int side) {
		try {
			return FORGE[side];
		}
		catch (IndexOutOfBoundsException ex) {
			return ForgeDirection.UNKNOWN;
		}
	}

	public static ForgeDirection toOpposite( int side) {
		try {
			return OPPOSITES[side];
		}
		catch (IndexOutOfBoundsException ex) {
			return ForgeDirection.UNKNOWN;
		}
	}

	public static String toString( int side) {
		try {
			return NAMES[side];
		}
		catch (IndexOutOfBoundsException ex) {
			return "UNKNOWN" + side;
		}
	}

	public static int zone( int side, double dx, double dy, double dz) {
		switch (side) {
			case DIR_DOWN:
			case DIR_UP:
				if (BOX_BORDER_MIN < dz && dz < BOX_BORDER_MAX && BOX_BORDER_MIN < dx && dx < BOX_BORDER_MAX) {
					return side;
				}
				if (dz > dx) {
					return dz + dx > 1D ? DIR_SOUTH : DIR_WEST;
				}
				return dz + dx > 1D ? DIR_EAST : DIR_NORTH;
			case DIR_NORTH:
			case DIR_SOUTH:
				if (BOX_BORDER_MIN < dy && dy < BOX_BORDER_MAX && BOX_BORDER_MIN < dx && dx < BOX_BORDER_MAX) {
					return side;
				}
				if (dy > dx) {
					return dy + dx > 1D ? DIR_UP : DIR_WEST;
				}
				return dy + dx > 1D ? DIR_EAST : DIR_DOWN;
			case DIR_WEST:
			case DIR_EAST:
				if (BOX_BORDER_MIN < dy && dy < BOX_BORDER_MAX && BOX_BORDER_MIN < dz && dz < BOX_BORDER_MAX) {
					return side;
				}
				if (dy > dz) {
					return dy + dz > 1D ? DIR_UP : DIR_NORTH;
				}
				return dy + dz > 1D ? DIR_SOUTH : DIR_DOWN;
			default:
				return DIR_DOWN;
		}
	}

	public static int zone( MovingObjectPosition pos) {
		double dx = pos.hitVec.xCoord - pos.blockX;
		double dy = pos.hitVec.yCoord - pos.blockY;
		double dz = pos.hitVec.zCoord - pos.blockZ;
		return zone( pos.sideHit, dx, dy, dz);
	}
}
