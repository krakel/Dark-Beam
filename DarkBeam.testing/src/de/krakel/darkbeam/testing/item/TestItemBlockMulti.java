/**
 * Dark Beam
 * TestItemBlockMulti.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.testing.item;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.krakel.darkbeam.testing.block.ModBlocks;
import de.krakel.darkbeam.testing.core.DarkLib;

public class TestItemBlockMulti extends ItemBlock {
	public TestItemBlockMulti( int id) {
		super( id);
		setMaxDamage( 0);
		setHasSubtypes( true);
	}

	@Override
	@SideOnly( Side.CLIENT)
	public Icon getIconFromDamage( int dmg) {
		return ModBlocks.sTestMulti.getIcon( 2, DarkLib.colorSubID( dmg));
	}

	@Override
	public int getMetadata( int meta) {
		return meta;
	}

	@Override
	public String getUnlocalizedName( ItemStack stk) {
		int dmg = stk.getItemDamage();
		return super.getUnlocalizedName() + "." + DarkLib.colorSubName( dmg);
	}
}
