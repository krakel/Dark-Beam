/**
 * Dark Beam
 * Material.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import net.minecraft.block.Block;

public class Material {
	public final int mMatID;
	public final Block mBlock;
	public final int mSubID;
	private String mName;

	Material( int matID, String name, Block blk, int subID) {
		mMatID = matID;
		mName = name;
		mBlock = blk;
		mSubID = subID;
	}

	public String getUnlocalizedName() {
		return "mat." + mName;
	}

	public String getUnlocalizedName( Mask mask) {
		return mask.mName + "." + mName;
	}
}
