/**
 * Dark Beam
 * TestItemBlockPanel.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.krakel.darkbeam.block.ModBlocks;
import de.krakel.darkbeam.core.DarkLib;
import de.krakel.darkbeam.core.IDirection;
import de.krakel.darkbeam.core.Position;

public class TestItemBlockPanel extends ItemBlock implements IDirection {
	public TestItemBlockPanel( int id) {
		super( id);
		setMaxDamage( 0);
		setHasSubtypes( true);
	}

	@Override
	@SideOnly( Side.CLIENT)
	public boolean canPlaceItemBlockOnSide( World world, int x, int y, int z, int side, EntityPlayer player, ItemStack stk) {
		int meta = world.getBlockMetadata( x, y, z);
		int subID = DarkLib.panelSubID( meta);
		boolean isTop = (meta & 8) != 0;
		if (side == DIR_UP && !isTop) { // || side == DIR_DOWN && isTop) {
			if (isHalfBlock( stk, world, x, y, z, subID)) {
				return true;
			}
		}
//		if (canPlaceItemBlockOnSide0( world, x, y, z, side, player, stk)) {
//			return true;
//		}
		return super.canPlaceItemBlockOnSide( world, x, y, z, side, player, stk);
	}

	@SuppressWarnings( "unused")
	private boolean canPlaceItemBlockOnSide0( World world, int x, int y, int z, int side, EntityPlayer player, ItemStack stk) {
		x += Position.relX( side);
		y += Position.relY( side);
		z += Position.relZ( side);
		int posMeta = world.getBlockMetadata( x, y, z);
		int posSubID = DarkLib.panelSubID( posMeta);
		if (isHalfBlock( stk, world, x, y, z, posSubID)) {
			return true;
		}
		return false;
	}

	@Override
	@SideOnly( Side.CLIENT)
	public Icon getIconFromDamage( int dmg) {
		return Block.blocksList[itemID].getIcon( DIR_NORTH, dmg);
	}

	@Override
	public int getMetadata( int meta) {
		return meta;
	}

	@Override
	public String getUnlocalizedName( ItemStack stk) {
		int dmg = stk.getItemDamage();
		return getUnlocalizedName() + "." + DarkLib.panelSubName( dmg);
	}

	private boolean isHalfBlock( ItemStack stk, World world, int x, int y, int z, int posSubID) {
		int posID = world.getBlockId( x, y, z);
		return posID == getBlockID() && posSubID == stk.getItemDamage();
	}

	@Override
	public boolean onItemUse( ItemStack stk, EntityPlayer player, World world, int x, int y, int z, int side, float dx, float dy, float dz) {
		if (stk.stackSize == 0) {
			return false;
		}
		if (!player.canPlayerEdit( x, y, z, side, stk)) {
			return false;
		}
		int posMeta = world.getBlockMetadata( x, y, z);
		boolean isTop = (posMeta & 8) != 0;
		if (side == DIR_UP && !isTop) { // || side == DIR_DOWN && isTop) {
			int posSubID = DarkLib.panelSubID( posMeta);
			if (isHalfBlock( stk, world, x, y, z, posSubID)) {
				ModBlocks.sTestPanel.placeBlock( stk, world, x, y, z, posSubID);
				return true;
			}
		}
//		if (onItemUse0( stk, player, world, x, y, z, side)) {
//			return true;
//		}
		return super.onItemUse( stk, player, world, x, y, z, side, dx, dy, dz);
	}

	@SuppressWarnings( "unused")
	private boolean onItemUse0( ItemStack stk, EntityPlayer player, World world, int x, int y, int z, int side) {
		x += Position.relX( side);
		y += Position.relY( side);
		z += Position.relZ( side);
		int posMeta = world.getBlockMetadata( x, y, z);
		int posSubID = DarkLib.panelSubID( posMeta);
		if (isHalfBlock( stk, world, x, y, z, posSubID)) {
			ModBlocks.sTestPanel.placeBlock( stk, world, x, y, z, posSubID);
			return true;
		}
		return false;
	}
}
