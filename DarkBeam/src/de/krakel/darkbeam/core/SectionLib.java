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
	private static final Section UNKNOWN = new Section( -1, "unknown", new MaskCoverRenderer( 1));
	private static Section[] sData = new Section[64];
	private static Iterable<Section> sIter = new MaskIterable();
	private static int sNextID = 0;
	public static Section sRedwire;
	public static Section sInsuwire;

	private SectionLib() {
	}

	private static Section add( String name, IMaskRenderer renderer) {
		try {
			int id = nextID();
			Section sec = new Section( id, name, renderer);
			sData[id] = sec;
			return sec;
		}
		catch (IndexOutOfBoundsException ex) {
			LogHelper.severe( ex, "caught an exception during access section");
		}
		return null;
	}

	public static Section get( int secID) {
		try {
			Section sec = sData[secID];
			return sec != null ? sec : UNKNOWN;
		}
		catch (IndexOutOfBoundsException ex) {
			LogHelper.severe( ex, "illegal section id %d", secID);
			return UNKNOWN;
		}
	}

	public static Section getForDmg( int dmg) {
		return get( secID( dmg));
	}

	public static IMaskRenderer getRenderer( int secID) {
		return get( secID).mRenderer;
	}

	public static IMaskRenderer getRendererForDmg( int meta) {
		return getRenderer( secID( meta));
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
		sRedwire = add( "db.redwire", new MaskRedWireRender());
		sInsuwire = add( "insuwire", new MaskInsulatedRenderer());
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

	private static int nextID() {
		return sNextID++;
	}

	public static int secID( int dmg) {
		return dmg >> 8;
	}

	public static Iterable<Section> values() {
		return sIter;
	}

	private static final class MaskIterable implements Iterable<Section> {
		@Override
		public Iterator<Section> iterator() {
			return new MaskIterator();
		}
	}

	private static final class MaskIterator implements Iterator<Section> {
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
		public Section next() {
			if (mCursor >= sData.length) {
				throw new NoSuchElementException( "No more elements");
			}
			Section mat = sData[mCursor++];
			findNext();
			return mat;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException( "Removal not supported");
		}
	}
}
