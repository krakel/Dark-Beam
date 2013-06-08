/**
 * Dark Beam
 * UnitType.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.lib;

import net.minecraft.block.Block;

public enum UnitType {
	Cover( "tile.unitCover"), Panel( "tile.unitPanel"), Slab( "tile.unitSlab"), Unknow( "tile.unitUnknow");
	public static final UnitType[] VALID_UNITS = {
		Cover, Panel, Slab
	};
	public final String mName;

	private UnitType( String name) {
		mName = name;
	}

	public static UnitType unit( int unitID) {
		try {
			return VALID_UNITS[unitID];
		}
		catch (IndexOutOfBoundsException ex) {
			return Unknow;
		}
	}

	public String getUnlocalizedName( Block blk) {
		return mName + "." + blk.getUnlocalizedName2();
	}
}
