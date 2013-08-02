/**
 * Dark Beam
 * InsulateLib.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import java.util.Iterator;
import java.util.NoSuchElementException;

import de.krakel.darkbeam.core.handler.LocalizationHandler;
import de.krakel.darkbeam.core.helper.LogHelper;

public class InsulateLib {
	public static final Insulate UNKNOWN = new Insulate( 255, "unkown");
	private static Insulate[] sData = new Insulate[32];
	private static Iterable<Insulate> sIter = new InsuIterable();

	private InsulateLib() {
	}

	private static void add( int insuID, String name) {
		try {
			if (sData[insuID] == null) {
				sData[insuID] = new Insulate( insuID, name);
				LocalizationHandler.addInsulate( sData[insuID]);
			}
			else {
				LogHelper.warning( "Insulate already initialized");
			}
		}
		catch (IndexOutOfBoundsException ex) {
			LogHelper.severe( ex, "caught an exception during access Insulate");
		}
	}

	public static Insulate get( int insuID) {
		try {
			Insulate insu = sData[insuID];
			return insu != null ? insu : UNKNOWN;
		}
		catch (IndexOutOfBoundsException ex) {
			LogHelper.severe( ex, "illegal Insulate id %d", insuID);
			return UNKNOWN;
		}
	}

	public static Insulate getForDmg( int dmg) {
		return get( insuID( dmg));
	}

	public static void init() {
		for (int i = 0; i < 16; ++i) {
			add( i, "woll." + i);
		}
	}

	public static int insuID( int meta) {
		return meta & 0xFF;
	}

	public static boolean isValid( int insuID) {
		try {
			return sData[insuID] != null;
		}
		catch (IndexOutOfBoundsException ex) {
			return false;
		}
	}

	public static boolean isValidForMeta( int dmg) {
		return isValid( insuID( dmg));
	}

	public static Iterable<Insulate> values() {
		return sIter;
	}

	private static final class InsuIterable implements Iterable<Insulate> {
		@Override
		public Iterator<Insulate> iterator() {
			return new InsuIterator();
		}
	}

	private static final class InsuIterator implements Iterator<Insulate> {
		private int mCursor;

		private InsuIterator() {
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
		public Insulate next() {
			if (mCursor >= sData.length) {
				throw new NoSuchElementException( "No more elements");
			}
			Insulate insu = sData[mCursor++];
			findNext();
			return insu;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException( "Removal not supported");
		}
	}
}
