/**
 * Dark Beam
 * SectionLib.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import java.util.Iterator;
import java.util.NoSuchElementException;

import de.krakel.darkbeam.client.renderer.SectionCoverRenderer;
import de.krakel.darkbeam.core.helper.LogHelper;

public class SectionLib {
	public static final ISection UNKNOWN = new UnknownSection();
	private static ISection[] sData = new ISection[64];
	private static Iterable<ISection> sIter = new MaskIterable();
	public static ISection sRedwire;
	public static ISection sInsuwire;
	public static ISection sCable;

	private SectionLib() {
	}

	private static void add( ISection sec) {
		try {
			if (sData[sec.getID()] == null) {
				sData[sec.getID()] = sec;
			}
			else {
				LogHelper.warning( "material already initialized");
			}
		}
		catch (IndexOutOfBoundsException ex) {
			LogHelper.severe( ex, "caught an exception during access section");
		}
	}

	public static ISection get( int secID) {
		try {
			ISection sec = sData[secID];
			return sec != null ? sec : UNKNOWN;
		}
		catch (IndexOutOfBoundsException ex) {
			LogHelper.severe( ex, "illegal section id %d", secID);
			return UNKNOWN;
		}
	}

	public static ISection getForDmg( int dmg) {
		return get( secID( dmg));
	}

	public static void init() {
		for (int i = 1; i < 8; ++i) {
			add( new SectionCover( i));
		}
		for (int i = 1; i < 8; ++i) {
			add( new SectionStrip( i));
		}
		for (int i = 1; i < 8; ++i) {
			add( new SectionCorner( i));
		}
		for (int i = 1; i < 8; ++i) {
			add( new SectionHollow( i));
		}
		sRedwire = new SectionRedWire();
		add( sRedwire);
		sInsuwire = new SectionInsulated();
		add( sInsuwire);
		sCable = new SectionCable();
		add( sCable);
	}

	public static boolean isValid( int secID) {
		try {
			return sData[secID] != null;
		}
		catch (IndexOutOfBoundsException ex) {
			return false;
		}
	}

	public static boolean isValidForMeta( int meta) {
		return isValid( secID( meta));
	}

	public static int secID( int dmg) {
		return dmg >> 8;
	}

	public static Iterable<ISection> values() {
		return sIter;
	}

	private static final class MaskIterable implements Iterable<ISection> {
		@Override
		public Iterator<ISection> iterator() {
			return new MaskIterator();
		}
	}

	private static final class MaskIterator implements Iterator<ISection> {
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
		public ISection next() {
			if (mCursor >= sData.length) {
				throw new NoSuchElementException( "No more elements");
			}
			ISection mat = sData[mCursor++];
			findNext();
			return mat;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException( "Removal not supported");
		}
	}

	static class UnknownSection extends ASectionCover {
		UnknownSection() {
			super( 255, "unknown", new SectionCoverRenderer( 1));
		}

		@Override
		public IMaterial getForDmg( int dmg) {
			return MaterialLib.UNKNOWN;
		}

		@Override
		public int getID() {
			return 0;
		}

		@Override
		public int toDmg() {
			return 0;
		}
	}
}
