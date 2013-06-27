/**
 * Dark Beam
 * Cube.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

public class Cube implements IArea {
	private static final int[] EMPTY = {};
	private static final int[][] EAGES_OF_AREA = {
		{ // SIDE_DOWN
			EDGE_DOWN_NORTH, EDGE_DOWN_WEST, EDGE_DOWN_SOUTH, EDGE_DOWN_EAST
		}, { // SIDE_UP
			EDGE_UP_NORTH, EDGE_UP_WEST, EDGE_UP_SOUTH, EDGE_UP_EAST
		}, { // SIDE_NORTH
			EDGE_DOWN_NORTH, EDGE_NORTH_WEST, EDGE_UP_NORTH, EDGE_NORTH_EAST
		}, { // SIDE_SOUTH
			EDGE_DOWN_SOUTH, EDGE_SOUTH_WEST, EDGE_UP_SOUTH, EDGE_SOUTH_EAST
		}, { // SIDE_WEST
			EDGE_DOWN_WEST, EDGE_NORTH_WEST, EDGE_UP_WEST, EDGE_SOUTH_WEST
		}, { // SIDE_EAST
			EDGE_DOWN_EAST, EDGE_NORTH_EAST, EDGE_UP_EAST, EDGE_SOUTH_EAST
		}
	};
	private static final int[][] EAGE_OF_AREAS = {
		{ // SIDE_DOWN
			-1, -1, EDGE_DOWN_NORTH, EDGE_DOWN_SOUTH, EDGE_DOWN_WEST, EDGE_DOWN_EAST
		}, { // SIDE_UP
			-1, -1, EDGE_UP_NORTH, EDGE_UP_SOUTH, EDGE_UP_WEST, EDGE_UP_EAST
		}, { // SIDE_NORTH
			EDGE_DOWN_NORTH, EDGE_UP_NORTH, -1, -1, EDGE_NORTH_WEST, EDGE_NORTH_EAST
		}, { // SIDE_SOUTH
			EDGE_DOWN_SOUTH, EDGE_UP_SOUTH, -1, -1, EDGE_SOUTH_WEST, EDGE_SOUTH_EAST
		}, { // SIDE_WEST
			EDGE_DOWN_WEST, EDGE_UP_WEST, EDGE_NORTH_WEST, EDGE_SOUTH_WEST, -1, -1
		}, { // SIDE_EAST
			EDGE_DOWN_EAST, EDGE_UP_EAST, EDGE_NORTH_EAST, EDGE_SOUTH_EAST, -1, -1
		}
	};
	private static final int[] OFFSETS_OF_AREA = {
		DN | DW | DS | DE, // SIDE_DOWN
		UN | UW | US | UE, // SIDE_UP
		DN | NW | UN | NE, // SIDE_NORTH
		DS | SW | US | SE, // SIDE_SOUTH
		DW | NW | UW | SW, // SIDE_WEST
		DE | NE | UE | SE // SIDE_EAST
	};
	private static final int[][] AREAS_OF_AREA = {
		{ // SIDE_DOWN
			SIDE_NORTH, SIDE_WEST, SIDE_SOUTH, SIDE_EAST
		}, { // SIDE_UP
			SIDE_NORTH, SIDE_WEST, SIDE_SOUTH, SIDE_EAST
		}, { // SIDE_NORTH
			SIDE_DOWN, SIDE_WEST, SIDE_UP, SIDE_EAST
		}, { // SIDE_SOUTH
			SIDE_DOWN, SIDE_WEST, SIDE_UP, SIDE_EAST
		}, { // SIDE_WEST
			SIDE_DOWN, SIDE_NORTH, SIDE_UP, SIDE_SOUTH
		}, { // SIDE_EAST
			SIDE_DOWN, SIDE_NORTH, SIDE_UP, SIDE_SOUTH
		}
	};
	// DN DS DW DE UN US UW UE NW NE SW SE
	private static final int[] EDGE_X = {
		0, 0, -1, 1, 0, 0, -1, 1, -1, 1, -1, 1
	};
	private static final int[] EDGE_Y = {
		-1, -1, -1, -1, 1, 1, 1, 1, 0, 0, 0, 0
	};
	private static final int[] EDGE_Z = {
		-1, 1, 0, 0, -1, 1, 0, 0, -1, -1, 1, 1
	};
	private static final int[] AREA_A = {
		SIDE_DOWN, SIDE_DOWN, SIDE_DOWN, SIDE_DOWN, SIDE_UP, SIDE_UP, SIDE_UP, SIDE_UP, SIDE_NORTH, SIDE_NORTH,
		SIDE_SOUTH, SIDE_SOUTH
	};
	private static final int[] AREA_B = {
		SIDE_NORTH, SIDE_SOUTH, SIDE_WEST, SIDE_EAST, SIDE_NORTH, SIDE_SOUTH, SIDE_WEST, SIDE_EAST, SIDE_WEST,
		SIDE_EAST, SIDE_WEST, SIDE_EAST
	};
	private static final int[] ANTI = {
		EDGE_UP_SOUTH, EDGE_UP_NORTH, EDGE_UP_EAST, EDGE_UP_WEST, EDGE_DOWN_SOUTH, EDGE_DOWN_NORTH, EDGE_DOWN_EAST,
		EDGE_DOWN_WEST, EDGE_SOUTH_EAST, EDGE_SOUTH_WEST, EDGE_NORTH_EAST, EDGE_NORTH_WEST
	};

	private Cube() {
	}

	public static int anti( int edge) {
		try {
			return ANTI[edge];
		}
		catch (IndexOutOfBoundsException ex) {
			return 0;
		}
	}

	public static int areaA( int edge) {
		try {
			return AREA_A[edge];
		}
		catch (IndexOutOfBoundsException ex) {
			return 0;
		}
	}

	public static int areaB( int edge) {
		try {
			return AREA_B[edge];
		}
		catch (IndexOutOfBoundsException ex) {
			return 0;
		}
	}

	public static int[] areas( int area) {
		try {
			return AREAS_OF_AREA[area];
		}
		catch (IndexOutOfBoundsException ex) {
			return EMPTY;
		}
	}

	public static int eage( int areaA, int areaB) {
		try {
			return EAGE_OF_AREAS[areaA][areaB];
		}
		catch (IndexOutOfBoundsException ex) {
			return 0;
		}
	}

	public static int[] eages( int area) {
		try {
			return EAGES_OF_AREA[area];
		}
		catch (IndexOutOfBoundsException ex) {
			return EMPTY;
		}
	}

	public static int edgeX( int edge) {
		try {
			return EDGE_X[edge];
		}
		catch (IndexOutOfBoundsException ex) {
			return 0;
		}
	}

	public static int edgeY( int edge) {
		try {
			return EDGE_Y[edge];
		}
		catch (IndexOutOfBoundsException ex) {
			return 0;
		}
	}

	public static int edgeZ( int edge) {
		try {
			return EDGE_Z[edge];
		}
		catch (IndexOutOfBoundsException ex) {
			return 0;
		}
	}

	public static int offsets( int area) {
		try {
			return OFFSETS_OF_AREA[area];
		}
		catch (IndexOutOfBoundsException ex) {
			return 0;
		}
	}
}
