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

import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;

import de.krakel.darkbeam.client.renderer.AMaskRenderer;
import de.krakel.darkbeam.client.renderer.MaskCoverRenderer;
import de.krakel.darkbeam.core.helper.LogHelper;
import de.krakel.darkbeam.tile.TileStage;

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

	public static void init() {
		for (int i = 1; i < 8; ++i) {
			add( new CoverSection( i));
		}
		for (int i = 1; i < 8; ++i) {
			add( new StripSection( i));
		}
		for (int i = 1; i < 8; ++i) {
			add( new CornerSection( i));
		}
		for (int i = 1; i < 8; ++i) {
			add( new HollowSection( i));
		}
		sRedwire = new RedWireSection();
		add( sRedwire);
		sInsuwire = new InsulatedSection();
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
		protected static final int VALID_D = D | DN | DS | DW | DE | DNW | DNE | DSW | DSE;
		protected static final int VALID_U = U | UN | US | UW | UE | UNW | UNE | USW | USE;
		protected static final int VALID_N = N | DN | UN | NW | NE | DNW | DNE | UNW | UNE;
		protected static final int VALID_S = S | DS | US | SW | SE | USW | USE | USW | USE;
		protected static final int VALID_W = W | DW | UW | NW | SW | DNW | DSW | UNW | USW;
		protected static final int VALID_E = E | DE | UE | NE | SE | DNE | DSE | UNE | USE;
		private MaskCoverRenderer mRenderer;

		public UnknownSection() {
			super( 255, "unknown");
			mRenderer = new MaskCoverRenderer( 1);
		}

		@Override
		public int getBlockID( int dmg) {
			Material mat = MaterialLib.getForDmg( dmg);
			return mat.mBlock.blockID;
		}

		@Override
		public Icon getIcon( int side, int dmg) {
			Material mat = MaterialLib.getForDmg( dmg);
			return mat.getIcon( side);
		}

		@Override
		public AMaskRenderer getRenderer() {
			return mRenderer;
		}

		@Override
		public String getSectionName( int dmg) {
			Material mat = MaterialLib.getForDmg( dmg);
			return mat.getMatName( this);
		}

		@Override
		public boolean hasMaterials() {
			return true;
		}

		@Override
		public boolean isValid( TileStage tile, int area) {
			switch (area) {
				case SIDE_DOWN:
					return tile.isValid( VALID_D);
				case SIDE_UP:
					return tile.isValid( VALID_U);
				case SIDE_NORTH:
					return tile.isValid( VALID_N);
				case SIDE_SOUTH:
					return tile.isValid( VALID_S);
				case SIDE_WEST:
					return tile.isValid( VALID_W);
				case SIDE_EAST:
					return tile.isValid( VALID_E);
				default:
					return false;
			}
		}

		@Override
		public void oppositeArea( MovingObjectPosition pos) {
			if (pos.subHit == pos.sideHit) {
				pos.subHit = pos.subHit ^= 1;
			}
			else {
				pos.subHit = pos.subHit;
			}
		}

		@Override
		public void updateArea( MovingObjectPosition pos) {
			double dx = pos.hitVec.xCoord - pos.blockX;
			double dy = pos.hitVec.yCoord - pos.blockY;
			double dz = pos.hitVec.zCoord - pos.blockZ;
			pos.subHit = CoverSection.getArea( pos.sideHit, dx, dy, dz);
		}
	}
}
