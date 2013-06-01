/**
 * Dark Beam
 * FItemIds.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.lib;

import net.minecraftforge.common.Configuration;

public enum ItemType {
	ItemDarkening( "itemDarkening");
	private static int sItemIds = 27000;
	//
	private int mId;
	public final int mDefault;
	public final String mName;

	private ItemType( String name) {
		mDefault = nextID();
		mName = name;
	}

	private static int nextID() {
		return sItemIds++;
	}

	public int getId() {
		return mId;
	}

	public void updateID( Configuration config) {
		mId = config.getItem( mName, mDefault).getInt( mDefault);
	}
}
