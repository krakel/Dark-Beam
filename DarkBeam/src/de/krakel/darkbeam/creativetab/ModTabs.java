/**
 * Dark Beam
 * ModTabs.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.creativetab;

import net.minecraft.creativetab.CreativeTabs;

public class ModTabs {
	public static CreativeTabs sTabMain = new FMainTab( "tabMain");
	public static CreativeTabs sSubTabSection = new FSubTabSection( "tabSubSection");

	private ModTabs() {
	}

	public static void preInit() {
	}
}
