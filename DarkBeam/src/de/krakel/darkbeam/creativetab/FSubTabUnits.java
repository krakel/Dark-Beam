/**
 * Dark Beam
 * CreativeTabUnits.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.krakel.darkbeam.lib.BlockType;

final class FSubTabUnits extends CreativeTabs {
	public FSubTabUnits( String label) {
		super( label);
	}

	@Override
	@SideOnly( Side.CLIENT)
	public int getTabIconItemIndex() {
		return BlockType.Units.getId();
	}
}
