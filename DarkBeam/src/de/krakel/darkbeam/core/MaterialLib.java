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
	public static final Material UNKNOWN = new Material( 255, "unkown", Block.bedrock, 0);
	private static Material[] sData = new Material[256];
	private static Iterable<Material> sIter = new MatIterable();

	private MaterialLib() {
	}

	private static void add( int matID, String name, Block blk) {
		add( matID, name, blk, 0);
	}

	private static void add( int matID, String name, Block blk, int subID) {
		try {
			if (sData[matID] == null) {
				sData[matID] = new Material( matID, name, blk, subID);
				LocalizationHandler.addMaterial( sData[matID]);
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
			Material mat = sData[matID];
			return mat != null ? mat : UNKNOWN;
		}
		catch (IndexOutOfBoundsException ex) {
			LogHelper.severe( ex, "illegal material id %d", matID);
			return UNKNOWN;
		}
	}

	public static Material getForDmg( int dmg) {
		return get( matID( dmg));
	}

	public static void init() {
		add( 0, "stone", Block.stone); // 1
		add( 1, "dirt", Block.dirt); // 3
		add( 2, "cobbel", Block.cobblestone); // 4
		add( 3, "glass", Block.glass); // 20
		add( 4, "lapis", Block.blockLapis); // 22
		add( 5, "gold", Block.blockGold); // 41
		add( 6, "iron", Block.blockIron); // 42
		add( 7, "brick", Block.brick); // 45
		add( 8, "shelf", Block.bookShelf); // 47
		add( 9, "mossy", Block.cobblestoneMossy); // 48
		add( 10, "obsidian", Block.obsidian); // 49
		add( 11, "diamond", Block.blockDiamond); // 57
		add( 12, "snow", Block.blockSnow); // 80
		add( 13, "clay", Block.blockClay); // 82
		add( 14, "pumpkin", Block.pumpkin); // 86
		add( 15, "nether", Block.netherrack); // 87
		add( 16, "netherBrick", Block.netherBrick); // 112
		//
		add( 32, "blank.0", Block.planks, 0); // 5
		add( 33, "blank.1", Block.planks, 1);
		add( 34, "blank.2", Block.planks, 2);
		add( 35, "blank.3", Block.planks, 3);
		//
		add( 36, "wood.0", Block.wood, 0); // 17
		add( 37, "wood.1", Block.wood, 1);
		add( 38, "wood.2", Block.wood, 2);
		add( 39, "wood.3", Block.wood, 3);
		//
		add( 40, "sandStone.0", Block.sandStone, 0); // 24
		add( 41, "sandStone.1", Block.sandStone, 1);
		add( 42, "sandStone.2", Block.sandStone, 2);
//		add( 43, "sandStone.3", Block.sandStone, 3);
		//
		add( 44, "stoneBrick.0", Block.stoneBrick, 0); // 98
		add( 45, "stoneBrick.1", Block.stoneBrick, 1);
		add( 46, "stoneBrick.2", Block.stoneBrick, 2);
		add( 47, "stoneBrick.3", Block.stoneBrick, 3);
		//
		add( 48, "quartz.0", Block.blockNetherQuartz, 0); // 155
		add( 49, "quartz.1", Block.blockNetherQuartz, 1);
		add( 50, "quartz.2", Block.blockNetherQuartz, 2);
//		add( 51, "quartz.3", Block.blockNetherQuartz, 3);
	}

	public static boolean isValid( int matID) {
		try {
			return sData[matID] != null;
		}
		catch (IndexOutOfBoundsException ex) {
			return false;
		}
	}

	public static boolean isValidForMeta( int dmg) {
		return isValid( matID( dmg));
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
			while (mCursor < sData.length && sData[mCursor] == null) {
				++mCursor;
			}
		}

		@Override
		public boolean hasNext() {
			return mCursor < sData.length;
		}

		@Override
		public Material next() {
			if (mCursor >= sData.length) {
				throw new NoSuchElementException( "No more elements");
			}
			Material mat = sData[mCursor++];
			findNext();
			return mat;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException( "Removal not supported");
		}
	}
}
