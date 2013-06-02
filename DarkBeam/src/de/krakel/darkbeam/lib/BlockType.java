/**
 * Dark Beam
 * FBlockIds.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.lib;

import net.minecraftforge.common.Configuration;

public enum BlockType {
	OreDarkening( "oreDarkening"),
	OreBeaming( "oreBeaming"),
	BlockRedWire( "blockRedWire"),
	BlockUnits( "blockUnits"),
	TestBlockSimple( "testBlockSimple"),
	TestBlockItem( "testBlockItem"),
	TestBlockMulti( "testBlockMulti");
	private static int sBlockIds = 2500;
	//
	private int mId;
	public final int mDefault;
	public final String mName;

	private BlockType( String name) {
		mDefault = nextID();
		mName = name;
	}

	private static int nextID() {
		return sBlockIds++;
	}

	public int getId() {
		return mId;
	}

	public boolean isId( int id) {
		return mId == id;
	}

	public void updateID( Configuration config) {
		mId = config.getBlock( mName, mDefault).getInt( mDefault);
	}
}
