/**
 * Dark Beam
 * ModItems.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.item;

import net.minecraft.item.Item;

import de.krakel.darkbeam.lib.ItemType;

public class ModItems {
	public static Item sItemDarkening = new ItemDarkening( ItemType.ItemDarkening);

	public static void preInit() {
	}
}
