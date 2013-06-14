/**
 * Dark Beam
 * ItemMask.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.item;

import net.minecraft.block.Block;
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
import de.krakel.darkbeam.core.helper.LogHelper;
import de.krakel.darkbeam.creativetab.ModTabs;
import de.krakel.darkbeam.lib.BlockType;
import de.krakel.darkbeam.tile.TileMasking;

public class ItemMask extends ItemBlock {
	public ItemMask( int id) {
		super( id);
		setMaxDamage( 0);
		setHasSubtypes( true);
		setCreativeTab( ModTabs.sSubTabMask);
	}

	@Override
	@SideOnly( Side.CLIENT)
	public boolean canPlaceItemBlockOnSide( World world, int x, int y, int z, int side, EntityPlayer player, ItemStack stack) {
		return true;
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
	public String getUnlocalizedName( ItemStack stk) {
		int meta = stk.getItemDamage();
		Material mat = MaterialLib.getForMeta( meta);
		Mask type = MaskLib.getForMeta( meta);
		return type.getUnlocalizedName( mat.mBlock);
	}

	@Override
	public boolean onItemUse( ItemStack stk, EntityPlayer player, World world, int x, int y, int z, int dir, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			return false;
		}
		LogHelper.info( "a: {0}, {1}", world.isRemote, LogHelper.toString( x, y, z, dir, hitX, hitY, hitZ, null));
		if (player.isSneaking()) {
			return false;
		}
		if (!player.canPlayerEdit( x, y, z, dir, stk)) {
			return false;
		}
		int meta = stk.getItemDamage();
		if (MaskLib.isValidForMeta( meta)) {
			MovingObjectPosition pos = DarkLib.retraceBlock( world, player, x, y, z); // hit position view beam
			if (pos == null) {
				return false;
			}
			LogHelper.info( "b: {0}, {1}", world.isRemote, LogHelper.toString( pos));
			if (pos.typeOfHit != EnumMovingObjectType.TILE) {
				return false;
			}
			MovingObjectPosition hit = DarkLib.getPosition( world, pos, meta, stk);
			if (hit == null) {
				return false;
			}
			LogHelper.info( "c: {0}, {1}", world.isRemote, LogHelper.toString( hit));
			if (world.canPlaceEntityOnSide( stk.itemID, hit.blockX, hit.blockY, hit.blockZ, false, dir, player, stk)) {
				world.setBlock( hit.blockX, hit.blockY, hit.blockZ, BlockType.Masking.getId(), 0, 2);
			}
			TileMasking tile = DarkLib.getTileEntity( world, x, y, z, TileMasking.class);
			if (tile != null && tile.tryAddMask( hit.subHit, 0)) {
				--stk.stackSize;
				Material mat = MaterialLib.getForMeta( meta);
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
