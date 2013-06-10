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

import de.krakel.darkbeam.block.ModBlocks;
import de.krakel.darkbeam.core.DarkLib;
import de.krakel.darkbeam.core.MaterialLib;
import de.krakel.darkbeam.core.MaterialLib.Material;
import de.krakel.darkbeam.creativetab.ModTabs;
import de.krakel.darkbeam.lib.BlockType;
import de.krakel.darkbeam.lib.UnitType;
import de.krakel.darkbeam.tile.TileUnits;

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
	public CreativeTabs[] getCreativeTabs() {
		return new CreativeTabs[] {
			ModTabs.sSubTabUnit
		};
	}

	@Override
	@SideOnly( Side.CLIENT)
	@SuppressWarnings( {
		"rawtypes", "unchecked"
	})
	public void getSubItems( int itemID, CreativeTabs tab, List lst) {
		if (tab == ModTabs.sSubTabUnit) {
			for (int matID = 0; matID < 255; ++matID) {
				if (MaterialLib.isValid( matID)) {
					lst.add( new ItemStack( ModBlocks.sUnits, 1, matID));
				}
			}
		}
	}

	@Override
	public String getUnlocalizedName( ItemStack stk) {
		int dmg = stk.getItemDamage();
		Material mat = MaterialLib.get( dmg & 255);
		UnitType type = UnitType.unit( dmg >> 8);
		return type.getUnlocalizedName( mat.mBlock);
	}

	@Override
	public boolean onItemUse( ItemStack stk, EntityPlayer player, World world, int x, int y, int z, int dir, float hitX, float hitY, float hitZ) {
		if (player.isSneaking()) {
			return false;
		}
		if (!player.canPlayerEdit( x, y, z, dir, stk)) {
			return false;
		}
		int dmg = stk.getItemDamage();
		if (UnitType.isValid( dmg >> 8)) {
			MovingObjectPosition pos = DarkLib.retraceBlock( world, player, x, y, z); // hit position view beam
			if (pos == null) {
				return false;
			}
			if (pos.typeOfHit != EnumMovingObjectType.TILE) {
				return false;
			}
			MovingObjectPosition hit = DarkLib.getPosition( world, pos, dmg, stk);
			if (hit == null) {
				return false;
			}
			if (world.canPlaceEntityOnSide( stk.itemID, hit.blockX, hit.blockY, hit.blockZ, false, dir, player, stk)) {
				world.setBlock( hit.blockX, hit.blockY, hit.blockZ, BlockType.Units.getId(), 0, 2);
			}
			TileUnits tile = DarkLib.getTileEntity( world, x, y, z, TileUnits.class);
			if (tile != null && tile.tryAddUnit( hit.subHit, 0)) {
				--stk.stackSize;
				Material mat = MaterialLib.get( dmg & 255);
				DarkLib.placeNoise( world, hit.blockX, hit.blockY, hit.blockZ, mat.mBlock.blockID);
				world.notifyBlocksOfNeighborChange( hit.blockX, hit.blockY, hit.blockZ, BlockType.Units.getId());
				world.markBlockForUpdate( hit.blockX, hit.blockY, hit.blockZ);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onItemUseFirst( ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int dir, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			return false;
		}
		if (!player.isSneaking()) {
			return false;
		}
		return false;
	}
}
