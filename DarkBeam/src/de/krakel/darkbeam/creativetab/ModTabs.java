/**
 * Dark Beam
 * ModTabs.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.creativetab;

import net.minecraft.creativetab.CreativeTabs;

import de.krakel.darkbeam.lib.Strings;

public class ModTabs {
	public static CreativeTabs sTabMain = new FMainTab( Strings.TAB_MAIN);
	public static CreativeTabs sSubTabUnit = new FSubTabUnits( Strings.TAB_SUB_UNIT);

	private ModTabs() {
	}

	public static void preInit() {
	}
}
