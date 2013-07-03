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
	SIDE_DOWN( 0, -1, 0),
	SIDE_UP( 0, 1, 0),
	SIDE_NORTH( 0, 0, -1),
	SIDE_SOUTH( 0, 0, 1),
	SIDE_WEST( -1, 0, 0),
	SIDE_EAST( 1, 0, 0),
	//
	EDGE_DOWN_NORTH( 0, -1, -1),
	EDGE_DOWN_SOUTH( 0, -1, 1),
	EDGE_DOWN_WEST( -1, -1, 0),
	EDGE_DOWN_EAST( 1, -1, 0),
	EDGE_UP_NORTH( 0, 1, -1),
	EDGE_UP_SOUTH( 0, 1, 1),
	EDGE_UP_WEST( -1, 1, 0),
	EDGE_UP_EAST( 1, 1, 0),
	EDGE_NORTH_WEST( -1, 0, -1),
	EDGE_NORTH_EAST( 1, 0, -1),
	EDGE_SOUTH_WEST( -1, 0, 1),
	EDGE_SOUTH_EAST( 1, 0, 1),
	//
	CORNER_DOWN_NORTH_WEST( -1, -1, -1),
	CORNER_DOWN_NORTH_EAST( 1, -1, -1),
	CORNER_DOWN_SOUTH_WEST( -1, -1, 1),
	CORNER_DOWN_SOUTH_EAST( 1, -1, 1),
	CORNER_UP_NORTH_WEST( -1, 1, -1),
	CORNER_UP_NORTH_EAST( 1, 1, -1),
	CORNER_UP_SOUTH_WEST( -1, 1, 1),
	CORNER_UP_SOUTH_EAST( 1, 1, 1),
	//
	AXIS_DOWN_UP( 0, -1, 0),
	AXIS_NORTH_SOUTH( 0, 0, -1),
	AXIS_WEST_EAST( -1, 0, 0),
	//
	UNKNOWN( 0, 0, 0);
	private static final AreaType[] EMPTY = {};
	private static final AreaType[] SIDES_WEST_EAST = {
		SIDE_DOWN, SIDE_UP, SIDE_NORTH, SIDE_SOUTH
	};
	private static final AreaType[] SIDES_NORTH_SOUTH = {
		SIDE_DOWN, SIDE_UP, SIDE_WEST, SIDE_EAST
	};
	private static final AreaType[] SIDES_DOWN_UP = {
		SIDE_NORTH, SIDE_SOUTH, SIDE_WEST, SIDE_EAST
	};
	private static final AreaType[] REDSTONE = {
		SIDE_UP, SIDE_NORTH, SIDE_EAST, SIDE_SOUTH, SIDE_WEST
	};
	private static final int EDGE_OFFSETS_OF_EAST = toMask( EDGE_DOWN_EAST, EDGE_UP_EAST, EDGE_NORTH_EAST, EDGE_SOUTH_EAST);
	private static final int EDGE_OFFSETS_OF_WEST = toMask( EDGE_DOWN_WEST, EDGE_UP_WEST, EDGE_NORTH_WEST, EDGE_SOUTH_WEST);
	private static final int EDGE_OFFSETS_OF_SOUTH = toMask( EDGE_DOWN_SOUTH, EDGE_UP_SOUTH, EDGE_SOUTH_WEST, EDGE_SOUTH_EAST);
	private static final int EDGE_OFFSETS_OF_NORTH = toMask( EDGE_DOWN_NORTH, EDGE_UP_NORTH, EDGE_NORTH_WEST, EDGE_NORTH_EAST);
	private static final int EDGE_OFFSETS_OF_UP = toMask( EDGE_UP_NORTH, EDGE_UP_SOUTH, EDGE_UP_WEST, EDGE_UP_EAST);
	private static final int EDGE_OFFSETS_OF_DOWN = toMask( EDGE_DOWN_NORTH, EDGE_DOWN_SOUTH, EDGE_DOWN_WEST, EDGE_DOWN_EAST);
	private static final Iterable<AreaType> ITERABLE_AXIS = new Iterable<AreaType>() {
		@Override
		public Iterator<AreaType> iterator() {
			return new AreaIterator( AXIS_DOWN_UP, AXIS_WEST_EAST);
		}
	};
	private static final Iterable<AreaType> ITERABLE_CORNERS = new Iterable<AreaType>() {
		@Override
		public Iterator<AreaType> iterator() {
			return new AreaIterator( CORNER_DOWN_NORTH_WEST, CORNER_UP_SOUTH_EAST);
		}
	};
	private static final Iterable<AreaType> ITERABBLE_EDGES = new Iterable<AreaType>() {
		@Override
		public Iterator<AreaType> iterator() {
			return new AreaIterator( EDGE_DOWN_NORTH, EDGE_SOUTH_EAST);
		}
	};
	private static final Iterable<AreaType> ITERABLE_SIDES = new Iterable<AreaType>() {
		@Override
		public Iterator<AreaType> iterator() {
			return new AreaIterator( SIDE_DOWN, SIDE_EAST);
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
			case SIDE_DOWN:
				return SIDE_UP;
			case SIDE_UP:
				return SIDE_DOWN;
			case SIDE_NORTH:
				return SIDE_SOUTH;
			case SIDE_SOUTH:
				return SIDE_NORTH;
			case SIDE_WEST:
				return SIDE_EAST;
			case SIDE_EAST:
				return SIDE_WEST;
			case EDGE_DOWN_NORTH:
				return EDGE_UP_SOUTH;
			case EDGE_DOWN_SOUTH:
				return EDGE_UP_NORTH;
			case EDGE_DOWN_WEST:
				return EDGE_UP_EAST;
			case EDGE_DOWN_EAST:
				return EDGE_UP_WEST;
			case EDGE_UP_NORTH:
				return EDGE_DOWN_SOUTH;
			case EDGE_UP_SOUTH:
				return EDGE_DOWN_NORTH;
			case EDGE_UP_WEST:
				return EDGE_DOWN_EAST;
			case EDGE_UP_EAST:
				return EDGE_DOWN_WEST;
			case EDGE_NORTH_WEST:
				return EDGE_SOUTH_EAST;
			case EDGE_NORTH_EAST:
				return EDGE_SOUTH_WEST;
			case EDGE_SOUTH_WEST:
				return EDGE_NORTH_EAST;
			case EDGE_SOUTH_EAST:
				return EDGE_NORTH_WEST;
			case CORNER_DOWN_NORTH_WEST:
				return CORNER_UP_SOUTH_EAST;
			case CORNER_DOWN_NORTH_EAST:
				return CORNER_UP_SOUTH_WEST;
			case CORNER_DOWN_SOUTH_WEST:
				return CORNER_UP_NORTH_EAST;
			case CORNER_DOWN_SOUTH_EAST:
				return CORNER_UP_NORTH_WEST;
			case CORNER_UP_NORTH_WEST:
				return CORNER_DOWN_SOUTH_EAST;
			case CORNER_UP_NORTH_EAST:
				return CORNER_DOWN_SOUTH_WEST;
			case CORNER_UP_SOUTH_WEST:
				return CORNER_DOWN_NORTH_EAST;
			case CORNER_UP_SOUTH_EAST:
				return CORNER_DOWN_NORTH_WEST;
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
			case SIDE_DOWN:
				switch (sideB) {
					case SIDE_NORTH:
						return EDGE_DOWN_NORTH;
					case SIDE_SOUTH:
						return EDGE_DOWN_SOUTH;
					case SIDE_WEST:
						return EDGE_DOWN_WEST;
					case SIDE_EAST:
						return EDGE_DOWN_EAST;
					default:
						return UNKNOWN;
				}
			case SIDE_UP:
				switch (sideB) {
					case SIDE_NORTH:
						return EDGE_UP_NORTH;
					case SIDE_SOUTH:
						return EDGE_UP_SOUTH;
					case SIDE_WEST:
						return EDGE_UP_WEST;
					case SIDE_EAST:
						return EDGE_UP_EAST;
					default:
						return UNKNOWN;
				}
			case SIDE_NORTH:
				switch (sideB) {
					case SIDE_DOWN:
						return EDGE_DOWN_NORTH;
					case SIDE_UP:
						return EDGE_UP_NORTH;
					case SIDE_WEST:
						return EDGE_NORTH_WEST;
					case SIDE_EAST:
						return EDGE_NORTH_EAST;
					default:
						return UNKNOWN;
				}
			case SIDE_SOUTH:
				switch (sideB) {
					case SIDE_DOWN:
						return EDGE_DOWN_SOUTH;
					case SIDE_UP:
						return EDGE_UP_SOUTH;
					case SIDE_WEST:
						return EDGE_SOUTH_WEST;
					case SIDE_EAST:
						return EDGE_SOUTH_EAST;
					default:
						return UNKNOWN;
				}
			case SIDE_WEST:
				switch (sideB) {
					case SIDE_DOWN:
						return EDGE_DOWN_WEST;
					case SIDE_UP:
						return EDGE_UP_WEST;
					case SIDE_NORTH:
						return EDGE_NORTH_WEST;
					case SIDE_SOUTH:
						return EDGE_SOUTH_WEST;
					default:
						return UNKNOWN;
				}
			case SIDE_EAST:
				switch (sideB) {
					case SIDE_DOWN:
						return EDGE_DOWN_EAST;
					case SIDE_UP:
						return EDGE_UP_EAST;
					case SIDE_NORTH:
						return EDGE_NORTH_EAST;
					case SIDE_SOUTH:
						return EDGE_SOUTH_EAST;
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
			case SIDE_DOWN:
				return EDGE_OFFSETS_OF_DOWN;
			case SIDE_UP:
				return EDGE_OFFSETS_OF_UP;
			case SIDE_NORTH:
				return EDGE_OFFSETS_OF_NORTH;
			case SIDE_SOUTH:
				return EDGE_OFFSETS_OF_SOUTH;
			case SIDE_WEST:
				return EDGE_OFFSETS_OF_WEST;
			case SIDE_EAST:
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
			case EDGE_DOWN_NORTH:
			case EDGE_DOWN_SOUTH:
			case EDGE_DOWN_WEST:
			case EDGE_DOWN_EAST:
				return SIDE_DOWN;
			case EDGE_UP_NORTH:
			case EDGE_UP_SOUTH:
			case EDGE_UP_WEST:
			case EDGE_UP_EAST:
				return SIDE_UP;
			case EDGE_NORTH_WEST:
			case EDGE_NORTH_EAST:
				return SIDE_NORTH;
			case EDGE_SOUTH_WEST:
			case EDGE_SOUTH_EAST:
				return SIDE_SOUTH;
			default:
				return UNKNOWN;
		}
	}

	public static AreaType sideB( AreaType edge) {
		switch (edge) {
			case EDGE_DOWN_WEST:
			case EDGE_UP_WEST:
			case EDGE_NORTH_WEST:
			case EDGE_SOUTH_WEST:
				return SIDE_WEST;
			case EDGE_DOWN_EAST:
			case EDGE_UP_EAST:
			case EDGE_NORTH_EAST:
			case EDGE_SOUTH_EAST:
				return SIDE_EAST;
			case EDGE_DOWN_NORTH:
			case EDGE_UP_NORTH:
				return SIDE_NORTH;
			case EDGE_DOWN_SOUTH:
			case EDGE_UP_SOUTH:
				return SIDE_SOUTH;
			default:
				return UNKNOWN;
		}
	}

	public static Iterable<AreaType> sides() {
		return ITERABLE_SIDES;
	}

	public static AreaType[] sides( AreaType side) {
		switch (side) {
			case SIDE_DOWN:
			case SIDE_UP:
				return SIDES_DOWN_UP;
			case SIDE_NORTH:
			case SIDE_SOUTH:
				return SIDES_NORTH_SOUTH;
			case SIDE_WEST:
			case SIDE_EAST:
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
				return SIDE_DOWN;
			case 0:
				return SIDE_NORTH;
			case 1:
				return SIDE_WEST;
			case 2:
				return SIDE_SOUTH;
			case 3:
				return SIDE_EAST;
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
