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
	SIDE_DOWN, SIDE_UP, SIDE_NORTH, SIDE_SOUTH, SIDE_WEST, SIDE_EAST,
	//
	EDGE_DOWN_NORTH,
	EDGE_DOWN_SOUTH,
	EDGE_DOWN_WEST,
	EDGE_DOWN_EAST,
	EDGE_UP_NORTH,
	EDGE_UP_SOUTH,
	EDGE_UP_WEST,
	EDGE_UP_EAST,
	EDGE_NORTH_WEST,
	EDGE_NORTH_EAST,
	EDGE_SOUTH_WEST,
	EDGE_SOUTH_EAST,
	//
	CORNER_DOWN_NORTH_WEST,
	CORNER_DOWN_NORTH_EAST,
	CORNER_DOWN_SOUTH_WEST,
	CORNER_DOWN_SOUTH_EAST,
	CORNER_UP_NORTH_WEST,
	CORNER_UP_NORTH_EAST,
	CORNER_UP_SOUTH_WEST,
	CORNER_UP_SOUTH_EAST,
	//
	AXIS_DOWN_UP,
	AXIS_NORTH_SOUTH,
	AXIS_WEST_EAST;
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

	private AreaType() {
		mMask = 1 << ordinal();
	}

	public static Iterable<AreaType> axis() {
		return ITERABLE_AXIS;
	}

	public static Iterable<AreaType> corners() {
		return ITERABLE_CORNERS;
	}

	public static Iterable<AreaType> edges() {
		return ITERABBLE_EDGES;
	}

	public static Iterable<AreaType> sides() {
		return ITERABLE_SIDES;
	}

	public static int toMask( AreaType... areas) {
		int result = 0;
		for (AreaType at : areas) {
			result |= at.mMask;
		}
		return result;
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
