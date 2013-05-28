/**
 * Dark Beam
 * ItemUnit.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.krakel.darkbeam.DarkBeam;
import de.krakel.darkbeam.block.ModBlocks;

public class ItemUnit extends ItemBlock {
	public ItemUnit( int id) {
		super( id);
		setMaxDamage( 0);
		setHasSubtypes( true);
	}

	@Override
	@SideOnly( Side.CLIENT)
	public boolean canPlaceItemBlockOnSide( World world, int x, int y, int z, int side, EntityPlayer player, ItemStack stack) {
		return true;
	}

	@Override
	@SideOnly( Side.CLIENT)
	public CreativeTabs getCreativeTab() {
		return DarkBeam.sMainTab;
	}

	@Override
	@SideOnly( Side.CLIENT)
	@SuppressWarnings( {
		"rawtypes", "unchecked"
	})
	public void getSubItems( int itemID, CreativeTabs tab, List lst) {
		if (tab == DarkBeam.sMainTab) {
			lst.add( new ItemStack( ModBlocks.sBlockUnit, 1, 0));
		}
		super.getSubItems( itemID, tab, lst);
	}

	@Override
	public boolean onItemUse( ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		return super.onItemUse( stack, player, world, x, y, z, side, hitX, hitY, hitZ);
	}
}
