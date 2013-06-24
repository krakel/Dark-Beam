/**
 * Dark Beam
 * Insulate.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import net.minecraft.block.Block;
import net.minecraft.util.Icon;

public class Insulate {
	public final int mInsuID;
	final String mName;

	Insulate( int insuID, String name) {
		mInsuID = insuID;
		mName = name;
	}

	public Icon getIcon( int side) {
		return Block.cloth.getIcon( 0, mInsuID);
	}

	public String getInsuKey() {
		return "db.insulated." + mName;
	}

	public String getInsuName( Section sec) {
		return "tile." + sec.mName + "." + mName;
	}

	public int toDmg( Section sec) {
		return sec.toDmg() | mInsuID;
	}
}
