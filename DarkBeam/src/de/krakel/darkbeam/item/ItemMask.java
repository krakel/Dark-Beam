/**
 * Dark Beam
 * ItemMask.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.krakel.darkbeam.block.ModBlocks;
import de.krakel.darkbeam.core.DarkLib;
import de.krakel.darkbeam.core.IDirection;
import de.krakel.darkbeam.core.Mask;
import de.krakel.darkbeam.core.MaskLib;
import de.krakel.darkbeam.core.Material;
import de.krakel.darkbeam.core.MaterialLib;
import de.krakel.darkbeam.core.helper.LogHelper;
import de.krakel.darkbeam.creativetab.ModTabs;
import de.krakel.darkbeam.lib.BlockType;
import de.krakel.darkbeam.tile.TileMasking;

public class ItemMask extends ItemBlock {
	public ItemMask( int id) {
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
		return null;
	}

	@Override
	public CreativeTabs[] getCreativeTabs() {
		return new CreativeTabs[] {
			ModTabs.sSubTabMask
		};
	}

	@Override
	@SideOnly( Side.CLIENT)
	public Icon getIconFromDamage( int dmg) {
		Block blk = Block.blocksList[itemID];
		if (blk == null) {
			return null;
		}
		return blk.getIcon( IDirection.DIR_NORTH, dmg);
	}

	@Override
	@SideOnly( Side.CLIENT)
	@SuppressWarnings( {
		"rawtypes", "unchecked"
	})
	public void getSubItems( int blkID, CreativeTabs tab, List lst) {
//		if (tab == ModTabs.sSubTabMask) {
		for (Material mat : MaterialLib.values()) {
			for (Mask msk : MaskLib.values()) {
				lst.add( new ItemStack( ModBlocks.sMasking, 1, mat.toDmg( msk)));
			}
		}
//		}
	}

	@Override
	public String getUnlocalizedName( ItemStack stk) {
		int dmg = stk.getItemDamage();
		Material mat = MaterialLib.getForDmg( dmg);
		Mask msk = MaskLib.getForDmg( dmg);
		return mat.getUnlocalizedName( msk);
	}

	@Override
	public boolean onItemUse( ItemStack stk, EntityPlayer player, World world, int x, int y, int z, int dir, float hitX, float hitY, float hitZ) {
//		if (world.isRemote) {
//			return false;
//		}
		LogHelper.info( "a: %b, %s", world.isRemote, LogHelper.toString( x, y, z, dir, hitX, hitY, hitZ, null));
		if (player.isSneaking()) {
			return false;
		}
		if (!player.canPlayerEdit( x, y, z, dir, stk)) {
			return false;
		}
		int dmg = stk.getItemDamage();
		if (MaskLib.isValidForMeta( dmg)) {
			MovingObjectPosition pos = DarkLib.retraceBlock( world, player, x, y, z); // hit position view beam
			if (pos == null) {
				return false;
			}
			LogHelper.info( "b: %b, %s", world.isRemote, LogHelper.toString( pos));
			if (pos.typeOfHit != EnumMovingObjectType.TILE) {
				return false;
			}
			MovingObjectPosition hit = DarkLib.getPosition( world, pos, dmg, stk);
			if (hit == null) {
				return false;
			}
			LogHelper.info( "c: %b, %s", world.isRemote, LogHelper.toString( hit));
			if (world.canPlaceEntityOnSide( stk.itemID, hit.blockX, hit.blockY, hit.blockZ, false, dir, player, stk)) {
				world.setBlock( hit.blockX, hit.blockY, hit.blockZ, BlockType.Masking.getId(), 0, 2);
			}
			TileMasking tile = DarkLib.getTileEntity( world, hit.blockX, hit.blockY, hit.blockZ, TileMasking.class);
			if (tile != null && tile.tryAdd( hit.subHit, 0)) {
				LogHelper.info( "e: %b, %s", world.isRemote, tile);
				--stk.stackSize;
				Material mat = MaterialLib.getForDmg( dmg);
				DarkLib.placeNoise( world, hit.blockX, hit.blockY, hit.blockZ, mat.mBlock.blockID);
				world.notifyBlocksOfNeighborChange( hit.blockX, hit.blockY, hit.blockZ, BlockType.Masking.getId());
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
