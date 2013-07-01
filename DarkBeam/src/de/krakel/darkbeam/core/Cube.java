/**
 * Dark Beam
 * Cube.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import net.minecraft.world.World;

public class Cube implements IArea {
	private static final int[] EMPTY = {};
	private static final int[][] EAGES_OF_SIDE = {
		{ // SIDE_DOWN
			EDGE_DOWN_NORTH, EDGE_DOWN_SOUTH, EDGE_DOWN_WEST, EDGE_DOWN_EAST
		}, { // SIDE_UP
			EDGE_UP_NORTH, EDGE_UP_SOUTH, EDGE_UP_WEST, EDGE_UP_EAST
		}, { // SIDE_NORTH
			EDGE_DOWN_NORTH, EDGE_UP_NORTH, EDGE_NORTH_WEST, EDGE_NORTH_EAST
		}, { // SIDE_SOUTH
			EDGE_DOWN_SOUTH, EDGE_UP_SOUTH, EDGE_SOUTH_WEST, EDGE_SOUTH_EAST
		}, { // SIDE_WEST
			EDGE_DOWN_WEST, EDGE_UP_WEST, EDGE_NORTH_WEST, EDGE_SOUTH_WEST
		}, { // SIDE_EAST
			EDGE_DOWN_EAST, EDGE_UP_EAST, EDGE_NORTH_EAST, EDGE_SOUTH_EAST
		}
	};
	private static final int[][] SIDES_OF_SIDE = {
		{ // SIDE_DOWN
			SIDE_NORTH, SIDE_SOUTH, SIDE_WEST, SIDE_EAST
		}, { // SIDE_UP
			SIDE_NORTH, SIDE_SOUTH, SIDE_WEST, SIDE_EAST
		}, { // SIDE_NORTH
			SIDE_DOWN, SIDE_UP, SIDE_WEST, SIDE_EAST
		}, { // SIDE_SOUTH
			SIDE_DOWN, SIDE_UP, SIDE_WEST, SIDE_EAST
		}, { // SIDE_WEST
			SIDE_DOWN, SIDE_UP, SIDE_NORTH, SIDE_SOUTH
		}, { // SIDE_EAST
			SIDE_DOWN, SIDE_UP, SIDE_NORTH, SIDE_SOUTH
		}
	};
	private static final int[][] EDGE_OFFSETS_OF_SIDES = {
		{ // SIDE_DOWN
			0, 0, DN, DS, DW, DE
		}, { // SIDE_UP
			0, 0, UN, US, UW, UE
		}, { // SIDE_NORTH
			DN, UN, 0, 0, NW, NE
		}, { // SIDE_SOUTH
			DS, US, 0, 0, SW, SE
		}, { // SIDE_WEST
			DW, UW, NW, SW, 0, 0
		}, { // SIDE_EAST
			DE, UE, NE, SE, 0, 0
		}
	};
	private static final int[] EDGE_OFFSETS_OF_SIDE = {
		DN | DS | DW | DE, // SIDE_DOWN 
		UN | US | UW | UE, // SIDE_UP
		DN | UN | NW | NE, // SIDE_NORTH
		DS | US | SW | SE, // SIDE_SOUTH
		DW | UW | NW | SW, // SIDE_WEST
		DE | UE | NE | SE // SIDE_EAST
	};
	private static final int[] REL_X = {
		0, 0, 0, 0, -1, 1
	};
	private static final int[] REL_Y = {
		-1, 1, 0, 0, 0, 0
	};
	private static final int[] REL_Z = {
		0, 0, -1, 1, 0, 0
	};
	// DN DS DW DE UN US UW UE NW NE SW SE
	private static final int[] REL_EDGE_X = {
		0, 0, -1, 1, 0, 0, -1, 1, -1, 1, -1, 1
	};
	private static final int[] REL_EDGE_Y = {
		-1, -1, -1, -1, 1, 1, 1, 1, 0, 0, 0, 0
	};
	private static final int[] REL_EDGE_Z = {
		-1, 1, 0, 0, -1, 1, 0, 0, -1, -1, 1, 1
	};
	private static final int[] SIDE_OF_EDGE_A = {
		SIDE_DOWN, SIDE_DOWN, SIDE_DOWN, SIDE_DOWN, SIDE_UP, SIDE_UP, SIDE_UP, SIDE_UP, SIDE_NORTH, SIDE_NORTH,
		SIDE_SOUTH, SIDE_SOUTH
	};
	private static final int[] SIDE_OF_EDGE_B = {
		SIDE_NORTH, SIDE_SOUTH, SIDE_WEST, SIDE_EAST, SIDE_NORTH, SIDE_SOUTH, SIDE_WEST, SIDE_EAST, SIDE_WEST,
		SIDE_EAST, SIDE_WEST, SIDE_EAST
	};
	private static final int[] ANTI_OFFSET_EDGE = {
		US, UN, UE, UW, DS, DN, DE, DW, SE, SW, NE, NW
	};
	private static final int[] REDSTONE = {
		SIDE_UP, SIDE_NORTH, SIDE_EAST, SIDE_SOUTH, SIDE_WEST
	};
	private static final String[] NAMES = {
		"DOWN", "UP", "NORTH", "SOUTH", "WEST", "EAST", "DOWN_NORTH", "DOWN_SOUTH", "DOWN_WEST", "DOWN_EAST",
		"UP_NORTH", "UP_SOUTH", "UP_WEST", "UP_EAST", "NORTH_WEST", "NORTH_EAST", "SOUTH_WEST", "SOUTH_EAST",
		"DOWN_NORTH_WEST", "UP_NORTH_WEST", "DOWN_SOUTH_WEST", "UP_SOUTH_WEST", "DOWN_NORTH_EAST", "UP_NORTH_EAST",
		"DOWN_SOUTH_EAST", "UP_SOUTH_EAST", "DOWN_UP", "NORTH_SOUTH", "WEST_EAST"
	};

	private Cube() {
	}

	public static int[] edges( int side) {
		try {
			return EAGES_OF_SIDE[side];
		}
		catch (IndexOutOfBoundsException ex) {
			return EMPTY;
		}
	}

	public static int offAnti( int edge) {
		try {
			return ANTI_OFFSET_EDGE[edge - MIN_EDGE];
		}
		catch (IndexOutOfBoundsException ex) {
			return 0;
		}
	}

	public static int offEdge( int sideA, int sideB) {
		try {
			return EDGE_OFFSETS_OF_SIDES[sideA][sideB];
		}
		catch (IndexOutOfBoundsException ex) {
			return 0;
		}
	}

	public static int offEdges( int side) {
		try {
			return EDGE_OFFSETS_OF_SIDE[side];
		}
		catch (IndexOutOfBoundsException ex) {
			return 0;
		}
	}

	public static int redstone( int side) {
		try {
			return REDSTONE[side + 1];
		}
		catch (IndexOutOfBoundsException ex) {
			return 0;
		}
	}

	public static int rel2X( int side) {
		return relX( side) << 1;
	}

	public static int rel2Y( int side) {
		return relY( side) << 1;
	}

	public static int rel2Z( int side) {
		return relZ( side) << 1;
	}

	public static int relEdgeX( int edge) {
		try {
			return REL_EDGE_X[edge - MIN_EDGE];
		}
		catch (IndexOutOfBoundsException ex) {
			return 0;
		}
	}

	public static int relEdgeY( int edge) {
		try {
			return REL_EDGE_Y[edge - MIN_EDGE];
		}
		catch (IndexOutOfBoundsException ex) {
			return 0;
		}
	}

	public static int relEdgeZ( int edge) {
		try {
			return REL_EDGE_Z[edge - MIN_EDGE];
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

	public static int sideA( int edge) {
		try {
			return SIDE_OF_EDGE_A[edge - MIN_EDGE];
		}
		catch (IndexOutOfBoundsException ex) {
			return 0;
		}
	}

	public static int sideB( int edge) {
		try {
			return SIDE_OF_EDGE_B[edge - MIN_EDGE];
		}
		catch (IndexOutOfBoundsException ex) {
			return 0;
		}
	}

	public static int[] sides( int side) {
		try {
			return SIDES_OF_SIDE[side];
		}
		catch (IndexOutOfBoundsException ex) {
			return EMPTY;
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

	public static void updateAll( World world, int x, int y, int z, int blockID) {
		updateNeighbor( world, x, y, z, blockID);
		updateEdges( world, x, y, z, blockID);
		updateNeighbor2( world, x, y, z, blockID);
	}

	public static void updateEdges( World world, int x, int y, int z, int blockID) {
		for (int edge = MIN_EDGE; edge < MAX_EDGE; ++edge) {
			int x1 = x + relEdgeX( edge);
			int y1 = y + relEdgeY( edge);
			int z1 = z + relEdgeZ( edge);
			world.notifyBlockOfNeighborChange( x1, y1, z1, blockID);
		}
	}

	public static void updateNeighbor( World world, int x, int y, int z, int blockID) {
		for (int side = MIN_SIDE; side < MAX_SIDE; ++side) {
			int x1 = x + relX( side);
			int y1 = y + relY( side);
			int z1 = z + relZ( side);
			world.notifyBlockOfNeighborChange( x1, y1, z1, blockID);
		}
	}

	public static void updateNeighbor2( World world, int x, int y, int z, int blockID) {
		for (int side = MIN_SIDE; side < MAX_SIDE; ++side) {
			int x1 = x + rel2X( side);
			int y1 = y + rel2Y( side);
			int z1 = z + rel2Z( side);
			world.notifyBlockOfNeighborChange( x1, y1, z1, blockID);
		}
	}
}
