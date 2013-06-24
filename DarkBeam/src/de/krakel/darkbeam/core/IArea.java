/**
 * Dark Beam
 * IArea.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

public interface IArea {
	static final int SIDE_DOWN = 0;
	static final int SIDE_UP = 1;
	static final int SIDE_NORTH = 2;
	static final int SIDE_SOUTH = 3;
	static final int SIDE_WEST = 4;
	static final int SIDE_EAST = 5;
	//
	static final int EDGE_DOWN_NORTH = 6;
	static final int EDGE_DOWN_SOUTH = 7;
	static final int EDGE_DOWN_WEST = 8;
	static final int EDGE_DOWN_EAST = 9;
	static final int EDGE_UP_NORTH = 10;
	static final int EDGE_UP_SOUTH = 11;
	static final int EDGE_UP_WEST = 12;
	static final int EDGE_UP_EAST = 13;
	static final int EDGE_NORTH_WEST = 14;
	static final int EDGE_NORTH_EAST = 15;
	static final int EDGE_SOUTH_WEST = 16;
	static final int EDGE_SOUTH_EAST = 17;
	//
	static final int CORNER_DOWN_NORTH_WEST = 18;
	static final int CORNER_UP_NORTH_WEST = 19;
	static final int CORNER_DOWN_SOUTH_WEST = 20;
	static final int CORNER_UP_SOUTH_WEST = 21;
	static final int CORNER_DOWN_NORTH_EAST = 22;
	static final int CORNER_UP_NORTH_EAST = 23;
	static final int CORNER_DOWN_SOUTH_EAST = 24;
	static final int CORNER_UP_SOUTH_EAST = 25;
	//
	static final int AXIS_DOWN_UP = 26;
	static final int AXIS_NORTH_SOUTH = 27;
	static final int AXIS_WEST_EAST = 28;
	//
	static final int MAX_DIR = 6;
	static final int MAX_AREA = 29;
	//
	static final int D = 1 << SIDE_DOWN;
	static final int U = 1 << SIDE_UP;
	static final int N = 1 << SIDE_NORTH;
	static final int S = 1 << SIDE_SOUTH;
	static final int W = 1 << SIDE_WEST;
	static final int E = 1 << SIDE_EAST;
	//
	static final int DN = 1 << EDGE_DOWN_NORTH;
	static final int DS = 1 << EDGE_DOWN_SOUTH;
	static final int DW = 1 << EDGE_DOWN_WEST;
	static final int DE = 1 << EDGE_DOWN_EAST;
	static final int UN = 1 << EDGE_UP_NORTH;
	static final int US = 1 << EDGE_UP_SOUTH;
	static final int UW = 1 << EDGE_UP_WEST;
	static final int UE = 1 << EDGE_UP_EAST;
	static final int NW = 1 << EDGE_NORTH_WEST;
	static final int NE = 1 << EDGE_NORTH_EAST;
	static final int SW = 1 << EDGE_SOUTH_WEST;
	static final int SE = 1 << EDGE_SOUTH_EAST;
	//
	static final int DNW = 1 << CORNER_DOWN_NORTH_WEST;
	static final int DNE = 1 << CORNER_DOWN_NORTH_EAST;
	static final int DSW = 1 << CORNER_DOWN_SOUTH_WEST;
	static final int DSE = 1 << CORNER_DOWN_SOUTH_EAST;
	static final int UNW = 1 << CORNER_UP_NORTH_WEST;
	static final int UNE = 1 << CORNER_UP_NORTH_EAST;
	static final int USW = 1 << CORNER_UP_SOUTH_WEST;
	static final int USE = 1 << CORNER_UP_SOUTH_EAST;
	//
	static final int DU = 1 << AXIS_DOWN_UP;
	static final int NS = 1 << AXIS_NORTH_SOUTH;
	static final int WE = 1 << AXIS_WEST_EAST;
}
