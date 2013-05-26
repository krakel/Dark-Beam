/**
 * Dark Beam
 * ItemRedWire.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core.block.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.StepSound;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.krakel.darkbeam.DarkBeam;
import de.krakel.darkbeam.lib.FStrings;
import de.krakel.darkbeam.lib.FTextures;
import de.krakel.darkbeam.tileentity.TileRedWire;

public class ItemBlockRedWire extends ItemBlock {
	private Icon mTexture;

	public ItemBlockRedWire( int id) {
		super( id);
	}

	@Override
	@SideOnly( Side.CLIENT)
	public boolean canPlaceItemBlockOnSide( World world, int x, int y, int z, int side, EntityPlayer entity, ItemStack stack) {
		int id = world.getBlockId( x, y, z);
		if (id == Block.snow.blockID) {
			side = 1;
		}
		else if (!isBlockReplaceable( world, x, y, z, id)) {
			ForgeDirection dir = ForgeDirection.getOrientation( side);
			x += dir.offsetX;
			y += dir.offsetY;
			z += dir.offsetZ;
		}
		return world.canPlaceEntityOnSide( getBlockID(), x, y, z, false, side, (Entity) null, stack);
	}

	private boolean canSupportWire( World world, int x, int y, int z, ForgeDirection dir) {
		if (!world.blockExists( x, y, z)) {
			return true;
		}
		if (world.isBlockSolidOnSide( x, y, z, dir)) {
			return true;
		}
		if (world.isBlockNormalCube( x, y, z)) {
			return true;
		}
		int id = world.getBlockId( x, y, z);
		if (id == Block.pistonMoving.blockID) {
			return true;
		}
		if (id == Block.pistonStickyBase.blockID || id == Block.pistonBase.blockID) {
			int o = BlockPistonBase.getOrientation( world.getBlockMetadata( x, y, z));
			return o < 6 && o != dir.ordinal();
		}
		TileRedWire tile = DarkBeam.getTileEntity( world, x, y, z);
		if (tile != null) {
			return tile.isNormal( dir);
		}
		return false;
	}

	@Override
	@SideOnly( Side.CLIENT)
	public Icon getIconFromDamage( int meta) {
		return mTexture;
	}

	@Override
	public int getMetadata( int meta) {
		return meta;
	}

	private boolean isBlockReplaceable( World world, int x, int y, int z, int id) {
		if (id == Block.vine.blockID || id == Block.tallGrass.blockID || id == Block.deadBush.blockID) {
			return true;
		}
		Block block = Block.blocksList[id];
		return block != null && block.isBlockReplaceable( world, x, y, z);
	}

	@Override
	public boolean onItemUse( ItemStack stack, EntityPlayer entity, World world, int x, int y, int z, int side, float xOff, float yOff, float zOff) {
		if (stack.stackSize == 0 || entity == null || entity.isSneaking()) {
			return false;
		}
		ForgeDirection dir = ForgeDirection.getOrientation( side);
		if (!canSupportWire( world, x, y, z, dir)) {
			return false;
		}
		x += dir.offsetX;
		y += dir.offsetY;
		z += dir.offsetZ;
		if (world.getBlockId( x, y, z) != stack.itemID) {
			if (!world.canPlaceEntityOnSide( stack.itemID, x, y, z, false, side, entity, stack)) {
				return false;
			}
			if (!world.setBlock( x, y, z, stack.itemID, stack.getItemDamage(), 2)) {
				return true;
			}
			TileRedWire tile = DarkBeam.getTileEntity( world, x, y, z);
			if (tile == null) {
				return false;
			}
			tile.setConnection( side);
		}
		else {
			TileRedWire tile = DarkBeam.getTileEntity( world, x, y, z);
			if (tile == null) {
				return false;
			}
			if (tile.isConnected( side)) {
				return false;
			}
			tile.setConnection( side);
			world.markBlockForUpdate( x, y, z);
		}
		--stack.stackSize;
		playSound( world, x, y, z, stack.itemID);
		world.notifyBlocksOfNeighborChange( x, y, z, stack.itemID);
		return true;
	}

	private void playSound( World world, int x, int y, int z, int id) {
		Block blk = Block.blocksList[id];
		if (blk != null) {
			StepSound stepSound = blk.stepSound;
			world.playSoundEffect( x + 0.5F, y + 0.5F, z + 0.5F, stepSound.getPlaceSound(), stepSound.getVolume() * 0.5F + 0.F, stepSound.getPitch() * 0.8F);
		}
	}

	@Override
	@SideOnly( Side.CLIENT)
	public void registerIcons( IconRegister reg) {
		mTexture = reg.registerIcon( FTextures.PATH_DEFAULT + FStrings.BLOCK_RED_WIRE_NAME);
	}
}
