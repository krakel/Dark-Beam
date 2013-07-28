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

public enum AreaType {
	//@formatter:off
	DOWN( 0, -1, 0), 
	UP( 0, 1, 0), 
	NORTH( 0, 0, -1), 
	SOUTH( 0, 0, 1), 
	WEST( -1, 0, 0), 
	EAST( 1, 0, 0),
	// edges
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
	// corner
	DOWN_NORTH_WEST( -1, -1, -1),
	DOWN_NORTH_EAST( 1, -1, -1),
	DOWN_SOUTH_WEST( -1, -1, 1),
	DOWN_SOUTH_EAST( 1, -1, 1),
	UP_NORTH_WEST( -1, 1, -1),
	UP_NORTH_EAST( 1, 1, -1),
	UP_SOUTH_WEST( -1, 1, 1),
	UP_SOUTH_EAST( 1, 1, 1),
	// axis
	DOWN_UP( 0, -1, 0),
	NORTH_SOUTH( 0, 0, -1),
	WEST_EAST( -1, 0, 0),
	//
	CENTER( 0, 0, 0),
	UNKNOWN( 0, 0, 0);
	//@formatter:on
	private static final AreaType[] EMPTY = {};
	private static final AreaType[] ANTI_SIDE = {
		UP, // DOWN
		DOWN, // UP
		SOUTH, // NORTH
		NORTH, // SOUTH
		EAST, // WEST
		WEST, // EAST
		UP_SOUTH, // DOWN_NORTH
		UP_NORTH, // DOWN_SOUTH
		UP_EAST, // DOWN_WEST
		UP_WEST, // DOWN_EAST
		DOWN_SOUTH, // UP_NORTH
		DOWN_NORTH, // UP_SOUTH
		DOWN_EAST, // UP_WEST
		DOWN_WEST, // UP_EAST
		SOUTH_EAST, // NORTH_WEST
		SOUTH_WEST, // NORTH_EAST
		NORTH_EAST, // SOUTH_WEST
		NORTH_WEST, // SOUTH_EAST
		UP_SOUTH_EAST, // DOWN_NORTH_WEST
		UP_SOUTH_WEST, // DOWN_NORTH_EAST
		UP_NORTH_EAST, // DOWN_SOUTH_WEST
		UP_NORTH_WEST, // DOWN_SOUTH_EAST
		DOWN_SOUTH_EAST, // UP_NORTH_WEST
		DOWN_SOUTH_WEST, // UP_NORTH_EAST
		DOWN_NORTH_EAST, // UP_SOUTH_WEST
		DOWN_NORTH_WEST, // UP_SOUTH_EAST
		UNKNOWN, // DOWN_UP
		UNKNOWN, // NORTH_SOUTH
		UNKNOWN, // WEST_EAST
		UNKNOWN, // CENTER
		UNKNOWN
	};
	public static final AreaType[] SIDES_DOWN_UP = {
		NORTH, SOUTH, WEST, EAST
	};
	public static final AreaType[] SIDES_NORTH_SOUTH = {
		DOWN, UP, WEST, EAST
	};
	public static final AreaType[] SIDES_WEST_EAST = {
		DOWN, UP, NORTH, SOUTH
	};
	private static final AreaType[][] SIDES_OF_SIDE = {
		SIDES_DOWN_UP, // DOWN
		SIDES_DOWN_UP, // UP
		SIDES_NORTH_SOUTH, // NORTH
		SIDES_NORTH_SOUTH, // SOUTH
		SIDES_WEST_EAST, // WEST
		SIDES_WEST_EAST, // EAST
		EMPTY
	};
	private static final AreaType[][] EDGE_OF_SIDES = {
		{
			UNKNOWN, UNKNOWN, DOWN_NORTH, DOWN_SOUTH, DOWN_WEST, DOWN_EAST
		}, // DOWN
		{
			UNKNOWN, UNKNOWN, UP_NORTH, UP_SOUTH, UP_WEST, UP_EAST
		}, // UP
		{
			DOWN_NORTH, UP_NORTH, UNKNOWN, UNKNOWN, NORTH_WEST, NORTH_EAST
		}, // NORTH
		{
			DOWN_SOUTH, UP_SOUTH, UNKNOWN, UNKNOWN, SOUTH_WEST, SOUTH_EAST
		}, // SOUTH
		{
			DOWN_WEST, UP_WEST, NORTH_WEST, SOUTH_WEST, UNKNOWN, UNKNOWN
		}, // WEST
		{
			DOWN_EAST, UP_EAST, NORTH_EAST, SOUTH_EAST, UNKNOWN, UNKNOWN
		}, // EAST
		EMPTY
	};
	private static final AreaType[] SIDE_A = {
		UNKNOWN, // DOWN
		UNKNOWN, // UP
		UNKNOWN, // NORTH
		UNKNOWN, // SOUTH
		UNKNOWN, // WEST
		UNKNOWN, // EAST
		DOWN, // DOWN_NORTH
		DOWN, // DOWN_SOUTH
		DOWN, // DOWN_WEST
		DOWN, // DOWN_EAST
		UP, // UP_NORTH
		UP, // UP_SOUTH
		UP, // UP_WEST
		UP, // UP_EAST
		NORTH, // NORTH_WEST
		NORTH, // NORTH_EAST
		SOUTH, // SOUTH_WEST
		SOUTH, // SOUTH_EAST
		UNKNOWN
	};
	private static final AreaType[] SIDE_B = {
		UNKNOWN, // DOWN
		UNKNOWN, // UP
		UNKNOWN, // NORTH
		UNKNOWN, // SOUTH
		UNKNOWN, // WEST
		UNKNOWN, // EAST
		SOUTH, // DOWN_NORTH
		NORTH, // DOWN_SOUTH
		WEST, // DOWN_WEST
		EAST, // DOWN_EAST
		SOUTH, // UP_NORTH
		NORTH, // UP_SOUTH
		WEST, // UP_WEST
		EAST, // UP_EAST
		WEST, // NORTH_WEST
		EAST, // NORTH_EAST
		WEST, // SOUTH_WEST
		EAST, // SOUTH_EAST
		UNKNOWN
	};
	private static final AreaType[] REDSTONE_TO = {
		UP, NORTH, EAST, SOUTH, WEST
	};
	private static final int[] REDSTONE_FROM = {
		-1, -1, 0, 2, 3, 1, -1
	};
	private static final int EDGE_OFFSETS_OF_DOWN = toMask( DOWN_NORTH, DOWN_SOUTH, DOWN_WEST, DOWN_EAST);
	private static final int EDGE_OFFSETS_OF_UP = toMask( UP_NORTH, UP_SOUTH, UP_WEST, UP_EAST);
	private static final int EDGE_OFFSETS_OF_NORTH = toMask( DOWN_NORTH, UP_NORTH, NORTH_WEST, NORTH_EAST);
	private static final int EDGE_OFFSETS_OF_SOUTH = toMask( DOWN_SOUTH, UP_SOUTH, SOUTH_WEST, SOUTH_EAST);
	private static final int EDGE_OFFSETS_OF_WEST = toMask( DOWN_WEST, UP_WEST, NORTH_WEST, SOUTH_WEST);
	private static final int EDGE_OFFSETS_OF_EAST = toMask( DOWN_EAST, UP_EAST, NORTH_EAST, SOUTH_EAST);
	private static final int[] EDGE_OFFSETS = {
		EDGE_OFFSETS_OF_DOWN, // DOWN
		EDGE_OFFSETS_OF_UP, // UP
		EDGE_OFFSETS_OF_NORTH, // NORTH
		EDGE_OFFSETS_OF_SOUTH, // SOUTH
		EDGE_OFFSETS_OF_WEST, // WEST
		EDGE_OFFSETS_OF_EAST, // EAST
		0
	};
	private static final Iterable<AreaType> ITERABLE_SIDES = new Iterable<AreaType>() {
		@Override
		public Iterator<AreaType> iterator() {
			return new AreaIterator( DOWN, EAST);
		}
	};
	private static final Iterable<AreaType> ITERABBLE_EDGES = new Iterable<AreaType>() {
		@Override
		public Iterator<AreaType> iterator() {
			return new AreaIterator( DOWN_NORTH, SOUTH_EAST);
		}
	};
	private static final Iterable<AreaType> ITERABLE_CORNERS = new Iterable<AreaType>() {
		@Override
		public Iterator<AreaType> iterator() {
			return new AreaIterator( DOWN_NORTH_WEST, UP_SOUTH_EAST);
		}
	};
	private static final Iterable<AreaType> ITERABLE_AXIS = new Iterable<AreaType>() {
		@Override
		public Iterator<AreaType> iterator() {
			return new AreaIterator( DOWN_UP, WEST_EAST);
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

	public static AreaType redstoneToSide( int side) {
		try {
			return REDSTONE_TO[side + 1];
		}
		catch (IndexOutOfBoundsException ex) {
			return UNKNOWN;
		}
	}

	public static AreaType toArea( int side) {
		try {
			return AreaType.values()[side];
		}
		catch (IndexOutOfBoundsException ex) {
			return UNKNOWN;
		}
	}

	public static int toMask( AreaType... areas) {
		int result = 0;
		for (AreaType at : areas) {
			result |= at.mMask;
		}
		return result;
	}

	public static Iterable<AreaType> valuesAxis() {
		return ITERABLE_AXIS;
	}

	public static Iterable<AreaType> valuesCorner() {
		return ITERABLE_CORNERS;
	}

	public static Iterable<AreaType> valuesEdge() {
		return ITERABBLE_EDGES;
	}

	public static Iterable<AreaType> valuesSide() {
		return ITERABLE_SIDES;
	}

	public AreaType anti() {
		try {
			return ANTI_SIDE[ordinal()];
		}
		catch (IndexOutOfBoundsException ex) {
			return UNKNOWN;
		}
	}

	public AreaType edge( AreaType sideB) {
		try {
			return EDGE_OF_SIDES[ordinal()][sideB.ordinal()];
		}
		catch (IndexOutOfBoundsException ex) {
			return UNKNOWN;
		}
	}

	public int offEdges() {
		try {
			return EDGE_OFFSETS[ordinal()];
		}
		catch (IndexOutOfBoundsException ex) {
			return 0;
		}
	}

	public int redstone() {
		try {
			return REDSTONE_FROM[ordinal()];
		}
		catch (IndexOutOfBoundsException ex) {
			return -1;
		}
	}

	public AreaType sideA() {
		try {
			return SIDE_A[ordinal()];
		}
		catch (IndexOutOfBoundsException ex) {
			return UNKNOWN;
		}
	}

	public AreaType sideB() {
		try {
			return SIDE_B[ordinal()];
		}
		catch (IndexOutOfBoundsException ex) {
			return UNKNOWN;
		}
	}

	public AreaType[] sides() {
		try {
			return SIDES_OF_SIDE[ordinal()];
		}
		catch (IndexOutOfBoundsException ex) {
			return EMPTY;
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
