/**
 * Dark Beam
 * FItemIds.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.lib;

import java.lang.reflect.Constructor;

import net.minecraft.item.Item;
import net.minecraftforge.common.Configuration;

import de.krakel.darkbeam.DarkBeam;
import de.krakel.darkbeam.core.helper.LogHelper;

public enum ItemType {
	Darkening( "itemDarkening");
	private static int sItemIds = 27000;
	private int mDefault;
	private int mId;
	private Item mItem;
	public final String mName;

	private ItemType( String name) {
		mDefault = nextID();
		mName = name;
	}

	private static int nextID() {
		return sItemIds++;
	}

	public <T extends Item> T create( Class<T> cls) {
		try {
			Constructor<T> ctor = cls.getConstructor( int.class);
			T item = ctor.newInstance( mId);
			item.setUnlocalizedName( mName);
			item.setCreativeTab( DarkBeam.sMainTab);
			mItem = item;
			return item;
		}
		catch (Exception ex) {
			LogHelper.severe( ex, "Caught an exception during item creation");
		}
		return null;
	}

	public int getId() {
		return mId;
	}

	public Item getItem() {
		return mItem;
	}

	public void register() {
	}

	public void updateID( Configuration config) {
		mId = config.getItem( mName, mDefault).getInt( mDefault);
	}
}
