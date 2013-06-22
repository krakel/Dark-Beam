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

	private static boolean canMaskAdd( World world, MovingObjectPosition pos, Mask msk) {
		TileMasking tile = DarkLib.getTileEntity( world, pos.blockX, pos.blockY, pos.blockZ, TileMasking.class);
		if (tile == null) {
			return false;
		}
		return msk.isValid( tile, pos.subHit);
	}

	private static MovingObjectPosition toPlacePos( World world, MovingObjectPosition pos, ItemStack stk) {
		LogHelper.info( "toPlacePos: %b, %s", world.isRemote, LogHelper.toString( pos));
		int dmg = stk.getItemDamage();
		Mask msk = MaskLib.getForDmg( dmg);
		msk.updateArea( pos);
		if (world.canPlaceEntityOnSide( BlockType.Masking.getId(), pos.blockX, pos.blockY, pos.blockZ, false, pos.sideHit, null, stk)) {
			LogHelper.info( "toPlacePosA");
			return pos;
		}
		if (canMaskAdd( world, pos, msk)) {
			LogHelper.info( "toPlacePosB");
			return pos;
		}
		Position.move( pos); // next block
		msk.oppositeArea( pos);
		if (world.canPlaceEntityOnSide( BlockType.Masking.getId(), pos.blockX, pos.blockY, pos.blockZ, false, pos.sideHit, null, stk)) {
			LogHelper.info( "toPlacePosC");
			return pos;
		}
		if (canMaskAdd( world, pos, msk)) {
			LogHelper.info( "toPlacePosD");
			return pos;
		}
		return null;
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
				lst.add( new ItemStack( BlockType.Masking.getBlock(), 1, mat.toDmg( msk)));
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
	public boolean onItemUse( ItemStack stk, EntityPlayer player, World world, int x, int y, int z, int dir, float dx, float dy, float dz) {
//		LogHelper.info( "a: %b, %s", world.isRemote, LogHelper.toString( x, y, z, dir, dx, dy, dz, null));
		if (player.isSneaking()) {
			return false;
		}
		if (!player.canPlayerEdit( x, y, z, dir, stk)) {
			return false;
		}
		int dmg = stk.getItemDamage();
		if (MaskLib.isValidForMeta( dmg)) {
			MovingObjectPosition hit = DarkLib.retraceBlock( world, player, x, y, z); // hit position view beam
			if (hit == null) {
				return false;
			}
//			LogHelper.info( "b: %b, %s", world.isRemote, LogHelper.toString( pos));
			if (hit.typeOfHit != EnumMovingObjectType.TILE) {
				return false;
			}
			MovingObjectPosition pos = toPlacePos( world, hit, stk);
			if (pos == null) {
				return false;
			}
//			LogHelper.info( "c: %b, %s", world.isRemote, LogHelper.toString( hit));
			if (world.canPlaceEntityOnSide( stk.itemID, pos.blockX, pos.blockY, pos.blockZ, false, pos.sideHit, player, stk)) {
				world.setBlock( pos.blockX, pos.blockY, pos.blockZ, BlockType.Masking.getId(), 0, 2);
			}
			TileMasking tile = DarkLib.getTileEntity( world, pos.blockX, pos.blockY, pos.blockZ, TileMasking.class);
			if (tile != null && tile.tryAdd( pos.subHit, dmg)) {
//				LogHelper.info( "e: %b, %s", world.isRemote, tile);
				--stk.stackSize;
				Material mat = MaterialLib.getForDmg( dmg);
				DarkLib.placeNoise( world, pos.blockX, pos.blockY, pos.blockZ, mat.mBlock.blockID);
				world.notifyBlocksOfNeighborChange( pos.blockX, pos.blockY, pos.blockZ, BlockType.Masking.getId());
				world.markBlockForUpdate( pos.blockX, pos.blockY, pos.blockZ);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onItemUseFirst( ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int dir, float dx, float dy, float dz) {
		if (world.isRemote) {
			return false;
		}
		if (!player.isSneaking()) {
			return false;
		}
		return false;
	}
}
