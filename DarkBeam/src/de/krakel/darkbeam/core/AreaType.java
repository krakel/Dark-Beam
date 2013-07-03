/**
 * Dark Beam
 * AreaType.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import java.util.Iterator;
import java.util.NoSuchElementException;

import net.minecraft.world.World;

public enum AreaType {
	DOWN( 0, -1, 0), UP( 0, 1, 0), NORTH( 0, 0, -1), SOUTH( 0, 0, 1), WEST( -1, 0, 0), EAST( 1, 0, 0),
	//
	DOWN_NORTH( 0, -1, -1),
	DOWN_SOUTH( 0, -1, 1),
	DOWN_WEST( -1, -1, 0),
	DOWN_EAST( 1, -1, 0),
	UP_NORTH( 0, 1, -1),
	UP_SOUTH( 0, 1, 1),
	UP_WEST( -1, 1, 0),
	UP_EAST( 1, 1, 0),
	NORTH_WEST( -1, 0, -1),
	NORTH_EAST( 1, 0, -1),
	SOUTH_WEST( -1, 0, 1),
	SOUTH_EAST( 1, 0, 1),
	//
	DOWN_NORTH_WEST( -1, -1, -1),
	DOWN_NORTH_EAST( 1, -1, -1),
	DOWN_SOUTH_WEST( -1, -1, 1),
	DOWN_SOUTH_EAST( 1, -1, 1),
	UP_NORTH_WEST( -1, 1, -1),
	UP_NORTH_EAST( 1, 1, -1),
	UP_SOUTH_WEST( -1, 1, 1),
	UP_SOUTH_EAST( 1, 1, 1),
	//
	DOWN_UP( 0, -1, 0),
	NORTH_SOUTH( 0, 0, -1),
	WEST_EAST( -1, 0, 0),
	//
	UNKNOWN( 0, 0, 0);
	private static final AreaType[] EMPTY = {};
	private static final AreaType[] SIDES_WEST_EAST = {
		DOWN, UP, NORTH, SOUTH
	};
	private static final AreaType[] SIDES_NORTH_SOUTH = {
		DOWN, UP, WEST, EAST
	};
	private static final AreaType[] SIDES_DOWN_UP = {
		NORTH, SOUTH, WEST, EAST
	};
	private static final AreaType[] REDSTONE = {
		UP, NORTH, EAST, SOUTH, WEST
	};
	private static final int EDGE_OFFSETS_OF_EAST = toMask( DOWN_EAST, UP_EAST, NORTH_EAST, SOUTH_EAST);
	private static final int EDGE_OFFSETS_OF_WEST = toMask( DOWN_WEST, UP_WEST, NORTH_WEST, SOUTH_WEST);
	private static final int EDGE_OFFSETS_OF_SOUTH = toMask( DOWN_SOUTH, UP_SOUTH, SOUTH_WEST, SOUTH_EAST);
	private static final int EDGE_OFFSETS_OF_NORTH = toMask( DOWN_NORTH, UP_NORTH, NORTH_WEST, NORTH_EAST);
	private static final int EDGE_OFFSETS_OF_UP = toMask( UP_NORTH, UP_SOUTH, UP_WEST, UP_EAST);
	private static final int EDGE_OFFSETS_OF_DOWN = toMask( DOWN_NORTH, DOWN_SOUTH, DOWN_WEST, DOWN_EAST);
	private static final Iterable<AreaType> ITERABLE_AXIS = new Iterable<AreaType>() {
		@Override
		public Iterator<AreaType> iterator() {
			return new AreaIterator( DOWN_UP, WEST_EAST);
		}
	};
	private static final Iterable<AreaType> ITERABLE_CORNERS = new Iterable<AreaType>() {
		@Override
		public Iterator<AreaType> iterator() {
			return new AreaIterator( DOWN_NORTH_WEST, UP_SOUTH_EAST);
		}
	};
	private static final Iterable<AreaType> ITERABBLE_EDGES = new Iterable<AreaType>() {
		@Override
		public Iterator<AreaType> iterator() {
			return new AreaIterator( DOWN_NORTH, SOUTH_EAST);
		}
	};
	private static final Iterable<AreaType> ITERABLE_SIDES = new Iterable<AreaType>() {
		@Override
		public Iterator<AreaType> iterator() {
			return new AreaIterator( DOWN, EAST);
		}
	};
	public final int mMask;
	public final int mDx, mDy, mDz;

	private AreaType( int dx, int dy, int dz) {
		mMask = 1 << ordinal();
		mDx = dx;
		mDy = dy;
		mDz = dz;
	}

	public static AreaType anti( AreaType area) {
		switch (area) {
			case DOWN:
				return UP;
			case UP:
				return DOWN;
			case NORTH:
				return SOUTH;
			case SOUTH:
				return NORTH;
			case WEST:
				return EAST;
			case EAST:
				return WEST;
			case DOWN_NORTH:
				return UP_SOUTH;
			case DOWN_SOUTH:
				return UP_NORTH;
			case DOWN_WEST:
				return UP_EAST;
			case DOWN_EAST:
				return UP_WEST;
			case UP_NORTH:
				return DOWN_SOUTH;
			case UP_SOUTH:
				return DOWN_NORTH;
			case UP_WEST:
				return DOWN_EAST;
			case UP_EAST:
				return DOWN_WEST;
			case NORTH_WEST:
				return SOUTH_EAST;
			case NORTH_EAST:
				return SOUTH_WEST;
			case SOUTH_WEST:
				return NORTH_EAST;
			case SOUTH_EAST:
				return NORTH_WEST;
			case DOWN_NORTH_WEST:
				return UP_SOUTH_EAST;
			case DOWN_NORTH_EAST:
				return UP_SOUTH_WEST;
			case DOWN_SOUTH_WEST:
				return UP_NORTH_EAST;
			case DOWN_SOUTH_EAST:
				return UP_NORTH_WEST;
			case UP_NORTH_WEST:
				return DOWN_SOUTH_EAST;
			case UP_NORTH_EAST:
				return DOWN_SOUTH_WEST;
			case UP_SOUTH_WEST:
				return DOWN_NORTH_EAST;
			case UP_SOUTH_EAST:
				return DOWN_NORTH_WEST;
			default:
				return UNKNOWN;
		}
	}

	public static Iterable<AreaType> axis() {
		return ITERABLE_AXIS;
	}

	public static Iterable<AreaType> corners() {
		return ITERABLE_CORNERS;
	}

	public static AreaType edge( AreaType sideA, AreaType sideB) {
		switch (sideA) {
			case DOWN:
				switch (sideB) {
					case NORTH:
						return DOWN_NORTH;
					case SOUTH:
						return DOWN_SOUTH;
					case WEST:
						return DOWN_WEST;
					case EAST:
						return DOWN_EAST;
					default:
						return UNKNOWN;
				}
			case UP:
				switch (sideB) {
					case NORTH:
						return UP_NORTH;
					case SOUTH:
						return UP_SOUTH;
					case WEST:
						return UP_WEST;
					case EAST:
						return UP_EAST;
					default:
						return UNKNOWN;
				}
			case NORTH:
				switch (sideB) {
					case DOWN:
						return DOWN_NORTH;
					case UP:
						return UP_NORTH;
					case WEST:
						return NORTH_WEST;
					case EAST:
						return NORTH_EAST;
					default:
						return UNKNOWN;
				}
			case SOUTH:
				switch (sideB) {
					case DOWN:
						return DOWN_SOUTH;
					case UP:
						return UP_SOUTH;
					case WEST:
						return SOUTH_WEST;
					case EAST:
						return SOUTH_EAST;
					default:
						return UNKNOWN;
				}
			case WEST:
				switch (sideB) {
					case DOWN:
						return DOWN_WEST;
					case UP:
						return UP_WEST;
					case NORTH:
						return NORTH_WEST;
					case SOUTH:
						return SOUTH_WEST;
					default:
						return UNKNOWN;
				}
			case EAST:
				switch (sideB) {
					case DOWN:
						return DOWN_EAST;
					case UP:
						return UP_EAST;
					case NORTH:
						return NORTH_EAST;
					case SOUTH:
						return SOUTH_EAST;
					default:
						return UNKNOWN;
				}
			default:
				return UNKNOWN;
		}
	}

	public static Iterable<AreaType> edges() {
		return ITERABBLE_EDGES;
	}

	public static int offEdges( AreaType side) {
		switch (side) {
			case DOWN:
				return EDGE_OFFSETS_OF_DOWN;
			case UP:
				return EDGE_OFFSETS_OF_UP;
			case NORTH:
				return EDGE_OFFSETS_OF_NORTH;
			case SOUTH:
				return EDGE_OFFSETS_OF_SOUTH;
			case WEST:
				return EDGE_OFFSETS_OF_WEST;
			case EAST:
				return EDGE_OFFSETS_OF_EAST;
			default:
				return 0;
		}
	}

	public static AreaType redstone( int side) {
		try {
			return REDSTONE[side + 1];
		}
		catch (IndexOutOfBoundsException ex) {
			return UNKNOWN;
		}
	}

	public static AreaType sideA( AreaType edge) {
		switch (edge) {
			case DOWN_NORTH:
			case DOWN_SOUTH:
			case DOWN_WEST:
			case DOWN_EAST:
				return DOWN;
			case UP_NORTH:
			case UP_SOUTH:
			case UP_WEST:
			case UP_EAST:
				return UP;
			case NORTH_WEST:
			case NORTH_EAST:
				return NORTH;
			case SOUTH_WEST:
			case SOUTH_EAST:
				return SOUTH;
			default:
				return UNKNOWN;
		}
	}

	public static AreaType sideB( AreaType edge) {
		switch (edge) {
			case DOWN_WEST:
			case UP_WEST:
			case NORTH_WEST:
			case SOUTH_WEST:
				return WEST;
			case DOWN_EAST:
			case UP_EAST:
			case NORTH_EAST:
			case SOUTH_EAST:
				return EAST;
			case DOWN_NORTH:
			case UP_NORTH:
				return NORTH;
			case DOWN_SOUTH:
			case UP_SOUTH:
				return SOUTH;
			default:
				return UNKNOWN;
		}
	}

	public static Iterable<AreaType> sides() {
		return ITERABLE_SIDES;
	}

	public static AreaType[] sides( AreaType side) {
		switch (side) {
			case DOWN:
			case UP:
				return SIDES_DOWN_UP;
			case NORTH:
			case SOUTH:
				return SIDES_NORTH_SOUTH;
			case WEST:
			case EAST:
				return SIDES_WEST_EAST;
			default:
				return EMPTY;
		}
	}

	public static int toMask( AreaType... areas) {
		int result = 0;
		for (AreaType at : areas) {
			result |= at.mMask;
		}
		return result;
	}

	public static AreaType toSide( int relate) {
		switch (relate) {
			case -1:
				return DOWN;
			case 0:
				return NORTH;
			case 1:
				return WEST;
			case 2:
				return SOUTH;
			case 3:
				return EAST;
			default:
				return UNKNOWN;
		}
	}

	public static void updateAll( World world, int x, int y, int z, int blockID) {
		updateNeighbor( world, x, y, z, blockID);
		updateEdges( world, x, y, z, blockID);
		updateNeighbor2( world, x, y, z, blockID);
	}

	public static void updateEdges( World world, int x, int y, int z, int blockID) {
		for (AreaType edge : edges()) {
			int x1 = x + edge.mDx;
			int y1 = y + edge.mDy;
			int z1 = z + edge.mDz;
			world.notifyBlockOfNeighborChange( x1, y1, z1, blockID);
		}
	}

	public static void updateNeighbor( World world, int x, int y, int z, int blockID) {
		for (AreaType side : sides()) {
			int x1 = x + side.mDx;
			int y1 = y + side.mDy;
			int z1 = z + side.mDz;
			world.notifyBlockOfNeighborChange( x1, y1, z1, blockID);
		}
	}

	public static void updateNeighbor2( World world, int x, int y, int z, int blockID) {
		for (AreaType side : sides()) {
			int x1 = x + (side.mDx << 1);
			int y1 = y + (side.mDy << 1);
			int z1 = z + (side.mDz << 1);
			world.notifyBlockOfNeighborChange( x1, y1, z1, blockID);
		}
	}

	private static final class AreaIterator implements Iterator<AreaType> {
		private int mIndex;
		private int mLast;
		private AreaType[] mValues = AreaType.values();

		private AreaIterator( AreaType first, AreaType last) {
			mIndex = first.ordinal();
			mLast = last.ordinal();
		}

		@Override
		public boolean hasNext() {
			return mIndex <= mLast;
		}

		@Override
		public AreaType next() {
			if (mIndex > mLast) {
				throw new NoSuchElementException( "No more elements");
			}
			return mValues[mIndex++];
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException( "Removal not supported");
		}
	}
}
