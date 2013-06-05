/**
 * Dark Beam
 * ItemRedWire.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.item;

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

import de.krakel.darkbeam.block.ModBlocks;
import de.krakel.darkbeam.core.DarkLib;
import de.krakel.darkbeam.core.Position;
import de.krakel.darkbeam.lib.Textures;
import de.krakel.darkbeam.tile.TileRedWire;

public class ItemBlockRedWire extends ItemBlock {
	private Icon mTexture;

	public ItemBlockRedWire( int id) {
		super( id);
	}

	private static boolean canSupportWire( World world, int x, int y, int z, ForgeDirection dir) {
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
		TileRedWire tile = DarkLib.getTileEntity( world, x, y, z, TileRedWire.class);
		if (tile != null) {
			return tile.isNormal( dir);
		}
		return false;
	}

	private static boolean isBlockReplaceable( World world, int x, int y, int z, int id) {
		if (id == Block.vine.blockID || id == Block.tallGrass.blockID || id == Block.deadBush.blockID) {
			return true;
		}
		Block block = Block.blocksList[id];
		return block != null && block.isBlockReplaceable( world, x, y, z);
	}

	private static void playSound( World world, int x, int y, int z, int id) {
		Block blk = Block.blocksList[id];
		if (blk != null) {
			StepSound stepSound = blk.stepSound;
			world.playSoundEffect( x + 0.5F, y + 0.5F, z + 0.5F, stepSound.getPlaceSound(), stepSound.getVolume() * 0.5F + 0.F, stepSound.getPitch() * 0.8F);
		}
	}

	@Override
	@SideOnly( Side.CLIENT)
	public boolean canPlaceItemBlockOnSide( World world, int x, int y, int z, int side, EntityPlayer entity, ItemStack stack) {
		int id = world.getBlockId( x, y, z);
		if (id == Block.snow.blockID) {
			side = 1;
		}
		else if (!isBlockReplaceable( world, x, y, z, id)) {
			x += Position.relX( side);
			y += Position.relY( side);
			z += Position.relZ( side);
		}
		return world.canPlaceEntityOnSide( getBlockID(), x, y, z, false, side, (Entity) null, stack);
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

	@Override
	public boolean onItemUse( ItemStack stack, EntityPlayer entity, World world, int x, int y, int z, int side, float xOff, float yOff, float zOff) {
		if (stack.stackSize == 0 || entity == null || entity.isSneaking()) {
			return false;
		}
		ForgeDirection dir = ForgeDirection.getOrientation( side);
		if (!canSupportWire( world, x, y, z, dir)) {
			return false;
		}
		x += Position.relX( side);
		y += Position.relY( side);
		z += Position.relZ( side);
		if (world.getBlockId( x, y, z) != stack.itemID) {
			if (!world.canPlaceEntityOnSide( stack.itemID, x, y, z, false, side, entity, stack)) {
				return false;
			}
			if (!world.setBlock( x, y, z, stack.itemID, stack.getItemDamage(), 2)) {
				return true;
			}
			TileRedWire tile = DarkLib.getTileEntity( world, x, y, z, TileRedWire.class);
			if (tile == null) {
				return false;
			}
			tile.mSurfaces = TileRedWire.set( tile.mSurfaces, dir);
//			tile.mConnections = TileRedWire.set( tile.mConnections, dir);
		}
		else {
			TileRedWire tile = DarkLib.getTileEntity( world, x, y, z, TileRedWire.class);
			if (tile == null) {
				return false;
			}
			if (TileRedWire.isSet( tile.mSurfaces, dir)) {
//			if (TileRedWire.isSet( tile.mConnections, dir)) {
				return false;
			}
			tile.mSurfaces = TileRedWire.set( tile.mSurfaces, dir);
//			tile.mConnections = TileRedWire.set( tile.mConnections, dir);
			world.markBlockForUpdate( x, y, z);
		}
		--stack.stackSize;
		playSound( world, x, y, z, stack.itemID);
		world.notifyBlocksOfNeighborChange( x, y, z, stack.itemID);
		return true;
	}

	@Override
	@SideOnly( Side.CLIENT)
	public void registerIcons( IconRegister reg) {
		mTexture = reg.registerIcon( Textures.PATH_DEFAULT + ModBlocks.sRedWire.getUnlocalizedName2());
	}
}
