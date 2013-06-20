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

import de.krakel.darkbeam.client.renderer.IMaskRenderer;
import de.krakel.darkbeam.client.renderer.MaskCornerRenderer;
import de.krakel.darkbeam.client.renderer.MaskCoverRenderer;
import de.krakel.darkbeam.client.renderer.MaskHollowRenderer;
import de.krakel.darkbeam.client.renderer.MaskStripRenderer;
import de.krakel.darkbeam.core.helper.LogHelper;

public class MaskLib {
	private static final Mask UNKNOWN = new Mask( -1, "unknown", new MaskCoverRenderer( 1));
	private static Mask[] sData = new Mask[32];
	private static Iterable<Mask> sIter = new MaskIterable();
	private static int sNextID = 0;

	private MaskLib() {
	}

	private static void add( String name, IMaskRenderer renderer) {
		try {
			int id = nextID();
			sData[id] = new Mask( id, name, renderer);
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
			LogHelper.severe( ex, "illegal mask id %d", maskID);
			return UNKNOWN;
		}
	}

	public static Mask getForDmg( int dmg) {
		return get( maskID( dmg));
	}

	public static IMaskRenderer getRenderer( int maskID) {
		return get( maskID).mRenderer;
	}

	public static IMaskRenderer getRendererForDmg( int meta) {
		return getRenderer( maskID( meta));
	}

	public static void init() {
		for (int i = 1; i < 8; ++i) {
			add( "cover." + i, new MaskCoverRenderer( i));
		}
		for (int i = 1; i < 8; ++i) {
			add( "strip." + i, new MaskStripRenderer( i));
		}
		for (int i = 1; i < 8; ++i) {
			add( "corner." + i, new MaskCornerRenderer( i));
		}
		for (int i = 1; i < 8; ++i) {
			add( "hollow." + i, new MaskHollowRenderer( i));
		}
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

	public static int maskID( int dmg) {
		return dmg >> 8;
	}

	private static int nextID() {
		return sNextID++;
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
