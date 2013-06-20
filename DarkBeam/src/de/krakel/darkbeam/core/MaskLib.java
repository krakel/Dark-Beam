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
import de.krakel.darkbeam.client.renderer.MaskCoverRenderer;
import de.krakel.darkbeam.core.helper.LogHelper;

public class MaskLib {
	private static final Mask UNKNOWN = new Mask( -1, "unknown", new MaskCoverRenderer( 1));
	private static Mask[] sData = new Mask[32];
	private static Iterable<Mask> sIter = new MaskIterable();

	private MaskLib() {
	}

	private static void add( int maskID, String name, IMaskRenderer renderer) {
		try {
			if (sData[maskID] == null) {
				sData[maskID] = new Mask( maskID, name, renderer);
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
		add( 0, "cover.1", new MaskCoverRenderer( 1F));
		add( 1, "cover.2", new MaskCoverRenderer( 2F));
		add( 2, "cover.3", new MaskCoverRenderer( 3F));
		add( 3, "cover.4", new MaskCoverRenderer( 4F));
		add( 4, "cover.5", new MaskCoverRenderer( 5F));
		add( 5, "cover.6", new MaskCoverRenderer( 6F));
		add( 6, "cover.7", new MaskCoverRenderer( 7F));
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
