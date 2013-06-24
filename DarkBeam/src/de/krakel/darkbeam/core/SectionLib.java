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

import de.krakel.darkbeam.client.renderer.IMaskRenderer;
import de.krakel.darkbeam.client.renderer.MaskCornerRenderer;
import de.krakel.darkbeam.client.renderer.MaskCoverRenderer;
import de.krakel.darkbeam.client.renderer.MaskHollowRenderer;
import de.krakel.darkbeam.client.renderer.MaskInsulatedRenderer;
import de.krakel.darkbeam.client.renderer.MaskRedWireRender;
import de.krakel.darkbeam.client.renderer.MaskStripRenderer;
import de.krakel.darkbeam.core.helper.LogHelper;

public class SectionLib {
	private static final ISection UNKNOWN = new UnknownSection();
	private static ISection[] sData = new ISection[64];
	private static Iterable<ISection> sIter = new MaskIterable();
	private static int sNextID = 0;
	public static ISection sRedwire;
	public static ISection sInsuwire;

	private SectionLib() {
	}

	private static void add( ISection sec) {
		try {
			sData[sec.getID()] = sec;
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

	public static IMaskRenderer getRenderer( int secID) {
		return get( secID).getRenderer();
	}

	public static IMaskRenderer getRendererForDmg( int meta) {
		return getRenderer( secID( meta));
	}

	public static void init() {
		for (int i = 1; i < 8; ++i) {
			add( new CoverSection( "cover." + i, new MaskCoverRenderer( i)));
		}
		for (int i = 1; i < 8; ++i) {
			add( new StripSection( "strip." + i, new MaskStripRenderer( i)));
		}
		for (int i = 1; i < 8; ++i) {
			add( new CornerSection( "corner." + i, new MaskCornerRenderer( i)));
		}
		for (int i = 1; i < 8; ++i) {
			add( new HollowSection( "hollow." + i, new MaskHollowRenderer( i)));
		}
		sRedwire = new RedWireSection( "db.redwire", new MaskRedWireRender());
		add( sRedwire);
		sInsuwire = new InsulatedSection( "insuwire", new MaskInsulatedRenderer());
		add( sInsuwire);
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

	public static int nextID() {
		return sNextID++;
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

	private static class UnknownSection extends ASection {
		public UnknownSection() {
			super( 255, "unknown", new MaskCoverRenderer( 1));
		}
	}
}
