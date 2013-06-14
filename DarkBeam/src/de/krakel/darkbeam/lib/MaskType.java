/**
 * Dark Beam
 * MaskType.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.lib;

import net.minecraft.block.Block;

public enum MaskType {
	Cover( "tile.maskCover"), Panel( "tile.maskPanel"), Slab( "tile.maskSlab"), Unknow( "tile.maskUnknow");
	public static final MaskType[] VALID_MASKS = {
		Cover, Panel, Slab
	};
	public final String mName;

	private MaskType( String name) {
		mName = name;
	}

	public static MaskType get( int maskID) {
		try {
			return VALID_MASKS[maskID];
		}
		catch (IndexOutOfBoundsException ex) {
			return Unknow;
		}
	}

	public static MaskType getForMeta( int meta) {
		return get( maskID( meta));
	}

	public static boolean isValid( int maskID) {
		try {
			return VALID_MASKS[maskID] != null;
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

	public String getUnlocalizedName( Block blk) {
		return mName + "." + blk.getUnlocalizedName2();
	}
}
