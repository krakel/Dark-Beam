/**
 * Dark Beam
 * Mask.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import net.minecraft.block.Block;

public class Mask {
	public final String mName;

	Mask( String name) {
		mName = name;
	}

	public String getUnlocalizedName( Block blk) {
		return mName + "." + blk.getUnlocalizedName2();
	}
}
