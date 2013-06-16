/**
 * Dark Beam
 * java
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
import de.krakel.darkbeam.core.Position;
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

	private static boolean canMaskAdd( World world, MovingObjectPosition hit, int side, ItemStack stk) {
		if (world.canPlaceEntityOnSide( BlockType.Masking.getId(), hit.blockX, hit.blockY, hit.blockZ, false, hit.sideHit, null, stk)) {
			return true;
		}
		TileMasking tile = DarkLib.getTileEntity( world, hit.blockX, hit.blockY, hit.blockZ, TileMasking.class);
		if (tile != null) {
			return !tile.isInUse( side);
		}
		return false;
	}

	private static MovingObjectPosition getPosition( World world, MovingObjectPosition pos, int subID, ItemStack stack) {
		MovingObjectPosition hit = new MovingObjectPosition( pos.blockX, pos.blockY, pos.blockZ, pos.sideHit, pos.hitVec);
		int hitArea = maskSide( pos);
		if (isInside( pos.subHit, pos.sideHit)) {
			if (hitArea == hit.sideHit) {
				hit.subHit = hitArea ^ 1;
				if (canMaskAdd( world, hit, subID, stack)) {
					return hit;
				}
				hit.subHit = hitArea;
			}
			else {
				hit.subHit = hitArea;
				if (canMaskAdd( world, hit, subID, stack)) {
					return hit;
				}
				Position.move( hit);
			}
		}
		else if (hitArea == hit.sideHit) {
			hit.subHit = hitArea;
			if (canMaskAdd( world, hit, subID, stack)) {
				return hit;
			}
			hit.subHit = hitArea ^ 1;
			Position.move( hit);
		}
		else {
			hit.subHit = hitArea;
			Position.move( hit);
		}
		return canMaskAdd( world, hit, subID, stack) ? hit : null;
	}

	private static boolean isInside( int subHit, int sideHit) {
		if (subHit < 0) {
			return false;
		}
		if (subHit < TileMasking.MAX_SIDE) {
			return (sideHit ^ subHit) == 1;
		}
		return false;
	}

	private static int maskSide( MovingObjectPosition hit) {
		double dx = hit.hitVec.xCoord - hit.blockX;
		double dy = hit.hitVec.yCoord - hit.blockY;
		double dz = hit.hitVec.zCoord - hit.blockZ;
		switch (hit.sideHit) {
			case IDirection.DIR_DOWN:
			case IDirection.DIR_UP:
				if (DarkLib.BOX_BORDER_MIN < dz && dz < DarkLib.BOX_BORDER_MAX && DarkLib.BOX_BORDER_MIN < dx
					&& dx < DarkLib.BOX_BORDER_MAX) {
					return hit.sideHit;
				}
				if (dz > dx) {
					return dz + dx > 1D ? IDirection.DIR_SOUTH : IDirection.DIR_WEST;
				}
				return dz + dx > 1D ? IDirection.DIR_EAST : IDirection.DIR_NORTH;
			case IDirection.DIR_NORTH:
			case IDirection.DIR_SOUTH:
				if (DarkLib.BOX_BORDER_MIN < dy && dy < DarkLib.BOX_BORDER_MAX && DarkLib.BOX_BORDER_MIN < dx
					&& dx < DarkLib.BOX_BORDER_MAX) {
					return hit.sideHit;
				}
				if (dy > dx) {
					return dy + dx > 1D ? IDirection.DIR_UP : IDirection.DIR_WEST;
				}
				return dy + dx > 1D ? IDirection.DIR_EAST : IDirection.DIR_DOWN;
			case IDirection.DIR_WEST:
			case IDirection.DIR_EAST:
				if (DarkLib.BOX_BORDER_MIN < dy && dy < DarkLib.BOX_BORDER_MAX && DarkLib.BOX_BORDER_MIN < dz
					&& dz < DarkLib.BOX_BORDER_MAX) {
					return hit.sideHit;
				}
				if (dy > dz) {
					return dy + dz > 1D ? IDirection.DIR_UP : IDirection.DIR_NORTH;
				}
				return dy + dz > 1D ? IDirection.DIR_SOUTH : IDirection.DIR_DOWN;
			default:
				return IDirection.DIR_DOWN;
		}
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
			MovingObjectPosition hit = getPosition( world, pos, dmg, stk);
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
