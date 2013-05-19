/**
 * Dark Beam
 * ModItems.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core.item;

import net.minecraft.item.Item;

import de.krakel.darkbeam.lib.FItemIds;

public class ModItems {
	public static Item sItemDarkening;

	public static void init() {
		sItemDarkening = new ItemDarkening( FItemIds.sItemDarkeningID);
	}
}
