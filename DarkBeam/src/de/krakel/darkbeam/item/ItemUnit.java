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
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.krakel.darkbeam.DarkBeam;
import de.krakel.darkbeam.block.ModBlocks;
import de.krakel.darkbeam.core.DarkLib;

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
			lst.add( new ItemStack( ModBlocks.sUnits, 1, 0));
		}
		super.getSubItems( itemID, tab, lst);
	}

	@Override
	public boolean onItemUse( ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (player == null || player.isSneaking()) {
			return false;
		}
		MovingObjectPosition pos = DarkLib.retraceBlock( world, player, x, y, z);
		if (pos == null) {
			return false;
		}
		if (pos.typeOfHit != EnumMovingObjectType.TILE) {
			return false;
		}
		MovingObjectPosition hit = DarkLib.getPosition( world, pos, stack.getItemDamage(), stack);
		if (hit == null) {
			return false;
		}
		if (world.canPlaceEntityOnSide( stack.itemID, hit.blockX, hit.blockY, hit.blockZ, false, side, player, stack)) {
			world.setBlock( hit.blockX, hit.blockY, hit.blockZ, ModBlocks.sUnits.blockID, 0, 2);
		}
//		TileUnit tile = DarkLib.getTileEntity( world, x, y, z, TileUnit.class);
//		if (tile != null && tile.tryAddUnit( hit.subHit, 0)) {
//			--stack.stackSize;
//			DarkLib.placeNoise( world, hit.blockX, hit.blockY, hit.blockZ, BlockIds.sBlockUnitsID);
//			world.notifyBlocksOfNeighborChange( hit.blockX, hit.blockY, hit.blockZ, BlockIds.sBlockUnitsID);
//			world.markBlockForUpdate( hit.blockX, hit.blockY, hit.blockZ);
//			return true;
//		}
		return false;
	}
}
