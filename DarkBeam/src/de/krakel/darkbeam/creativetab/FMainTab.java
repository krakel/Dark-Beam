/**
 * Dark Beam
 * CreativeTabDB.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.krakel.darkbeam.lib.BlockType;

final class FMainTab extends CreativeTabs {
	public FMainTab( String name) {
		super( name);
	}

	@Override
	@SideOnly( Side.CLIENT)
	public int getTabIconItemIndex() {
		return BlockType.STAGE.getId();
	}
}
