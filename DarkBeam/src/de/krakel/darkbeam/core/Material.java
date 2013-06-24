/**
 * Dark Beam
 * Material.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import net.minecraft.block.Block;
import net.minecraft.util.Icon;

public class Material {
	public final int mMatID;
	final String mName;
	public final Block mBlock;
	public final int mSubID;

	Material( int matID, String name, Block blk, int subID) {
		mMatID = matID;
		mName = name;
		mBlock = blk;
		mSubID = subID;
	}

	public Icon getIcon( int side) {
		return mBlock.getIcon( side, mSubID);
	}

	public String getMatKey() {
		return "db.mat." + mName;
	}

	public String getMatName( ISection sec) {
		return "tile." + sec.getName() + "." + mName;
	}

	public int toDmg( ISection sec) {
		return sec.toDmg() | mMatID;
	}
}
