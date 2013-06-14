/**
 * Dark Beam
 * MaskLib.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import java.util.Iterator;
import java.util.NoSuchElementException;

import de.krakel.darkbeam.core.helper.LogHelper;

public class MaskLib {
	private static final Mask UNKNOWN = new Mask( "tile.maskUnknow");
	private static Mask[] sData = new Mask[32];
	private static Iterable<Mask> sIter = new MaskIterable();

	public MaskLib() {
	}

	private static void add( int maskID, String name) {
		try {
			if (sData[maskID] == null) {
				sData[maskID] = new Mask( name);
			}
			else {
				LogHelper.warning( "mask already initialized");
			}
		}
		catch (IndexOutOfBoundsException ex) {
			LogHelper.severe( ex, "caught an exception during access mask");
		}
	}

	public static Mask get( int maskID) {
		try {
			Mask mat = sData[maskID];
			return mat != null ? mat : UNKNOWN;
		}
		catch (IndexOutOfBoundsException ex) {
			LogHelper.severe( ex, "illegal mask id {0}", maskID);
			return UNKNOWN;
		}
	}

	public static Mask getForMeta( int meta) {
		return get( maskID( meta));
	}

	public static void init() {
		add( 0, "tile.maskCover");
		add( 1, "tile.maskPanel");
		add( 2, "tile.maskSlab");
	}

	public static boolean isValid( int maskID) {
		try {
			return sData[maskID] != null;
		}
		catch (IndexOutOfBoundsException ex) {
			return false;
		}
	}

	public static boolean isValidForMeta( int meta) {
		return isValid( maskID( meta));
	}

	public static int maskID( int meta) {
		return meta >> 8;
	}

	public static Iterable<Mask> values() {
		return sIter;
	}

	private static final class MaskIterable implements Iterable<Mask> {
		@Override
		public Iterator<Mask> iterator() {
			return new MaskIterator();
		}
	}

	private static final class MaskIterator implements Iterator<Mask> {
		private int mCursor;

		private MaskIterator() {
			findNext();
		}

		private void findNext() {
			while (mCursor < sData.length && sData[mCursor] == null) {
				++mCursor;
			}
		}

		@Override
		public boolean hasNext() {
			return mCursor < sData.length;
		}

		@Override
		public Mask next() {
			if (mCursor >= sData.length) {
				throw new NoSuchElementException( "No more elements");
			}
			Mask mat = sData[mCursor++];
			findNext();
			return mat;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException( "Removal not supported");
		}
	}
}
