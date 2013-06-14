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
	public final Block mBlock;
	public final int mSubID;
	public final int mMatID;

	Material( int matID, Block blk, int subID) {
		mMatID = matID;
		mBlock = blk;
		mSubID = subID;
	}
}
