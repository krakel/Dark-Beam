/**
 * Dark Beam
 * TestItemBlockItem.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.testing.item;

import net.minecraft.item.ItemBlock;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TestItemBlockItem extends ItemBlock {
	public TestItemBlockItem( int id) {
		super( id);
	}

	@Override
	@SideOnly( Side.CLIENT)
	public Icon getIconFromDamage( int meta) {
		return null;
	}
}
