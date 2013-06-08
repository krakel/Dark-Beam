/**
 * Dark Beam
 * ModItems.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.item;

import de.krakel.darkbeam.lib.ItemType;

public class ModItems {
	public static ItemDarkening sItemDarkening = ItemType.Darkening.create( ItemDarkening.class);

	private ModItems() {
	}

	public static void preInit() {
		for (ItemType type : ItemType.values()) {
			type.register();
		}
	}
}
