/**
 * Dark Beam
 * MaterialLib.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import java.util.Iterator;
import java.util.NoSuchElementException;

import net.minecraft.block.Block;

import de.krakel.darkbeam.core.handler.LocalizationHandler;
import de.krakel.darkbeam.core.helper.LogHelper;

public class MaterialLib {
	private static final Material UNKNOWN = new Material( -1, Block.bedrock, 0);
	private static Material[] sMats = new Material[256];
	private static Iterable<Material> sIter = new MatIterable();

	private MaterialLib() {
	}

	private static void add( int matID, Block blk) {
		add( matID, blk, 0);
	}

	private static void add( int matID, Block blk, int subID) {
		try {
			if (sMats[matID] == null) {
				sMats[matID] = new Material( matID, blk, subID);
				LocalizationHandler.addMask( blk.getUnlocalizedName2());
			}
			else {
				LogHelper.warning( "material already initialized");
			}
		}
		catch (IndexOutOfBoundsException ex) {
			LogHelper.severe( ex, "caught an exception during access material");
		}
	}

	public static Material get( int matID) {
		try {
			Material mat = sMats[matID];
			return mat != null ? mat : UNKNOWN;
		}
		catch (IndexOutOfBoundsException ex) {
			LogHelper.severe( ex, "illegal material id {0}", matID);
			return UNKNOWN;
		}
	}

	public static Material getForMeta( int meta) {
		return get( matID( meta));
	}

	public static void init() {
		add( 0, Block.stone); // 1
		add( 1, Block.dirt); // 3
		add( 2, Block.cobblestone); // 4
		add( 3, Block.glass); // 20
		add( 4, Block.blockLapis); // 22
		add( 5, Block.blockGold); // 41
		add( 6, Block.blockIron); // 42
		add( 7, Block.stoneSingleSlab); // 44
		add( 8, Block.brick); // 45
		add( 9, Block.bookShelf); // 47
		add( 10, Block.cobblestoneMossy); // 48
		add( 11, Block.obsidian); // 49
		add( 12, Block.blockDiamond); // 57
		add( 13, Block.blockSnow); // 80
		add( 14, Block.blockClay); // 82
		add( 15, Block.pumpkin); // 86
		add( 16, Block.netherrack); // 87
		add( 17, Block.slowSand); // 88
		add( 18, Block.netherBrick); // 112
		//
		add( 32, Block.planks, 0); // 5
		add( 33, Block.planks, 1);
		add( 34, Block.planks, 2);
		add( 35, Block.planks, 3);
		//
		add( 36, Block.wood, 0); // 17
		add( 37, Block.wood, 1);
		add( 38, Block.wood, 2);
		add( 39, Block.wood, 3);
		//
		add( 40, Block.sandStone, 0); // 24
		add( 41, Block.sandStone, 1);
		add( 42, Block.sandStone, 2);
//		add( 43, Block.sandStone, 3);
		//
		add( 44, Block.stoneBrick, 0); // 98
		add( 45, Block.stoneBrick, 1);
		add( 46, Block.stoneBrick, 2);
		add( 47, Block.stoneBrick, 3);
	}

	public static boolean isValid( int matID) {
		try {
			return sMats[matID] != null;
		}
		catch (IndexOutOfBoundsException ex) {
			return false;
		}
	}

	public static boolean isValidForMeta( int meta) {
		return isValid( matID( meta));
	}

	public static int matID( int meta) {
		return meta & 0xFF;
	}

	public static Iterable<Material> values() {
		return sIter;
	}

	private static final class MatIterable implements Iterable<Material> {
		@Override
		public Iterator<Material> iterator() {
			return new MatIterator();
		}
	}

	private static final class MatIterator implements Iterator<Material> {
		private int mCursor;

		private MatIterator() {
			findNext();
		}

		private void findNext() {
			while (mCursor < sMats.length && sMats[mCursor] == null) {
				++mCursor;
			}
		}

		@Override
		public boolean hasNext() {
			return mCursor < sMats.length;
		}

		@Override
		public Material next() {
			if (mCursor >= sMats.length) {
				throw new NoSuchElementException( "No more elements");
			}
			Material mat = sMats[mCursor++];
			findNext();
			return mat;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException( "Removal not supported");
		}
	}
}
