/**
 * Dark Beam
 * ItemRedWire.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core.block.item;

import net.minecraft.block.Block;
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

import de.krakel.darkbeam.lib.FStrings;
import de.krakel.darkbeam.lib.FTextures;

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
	public boolean onItemUse( ItemStack stack, EntityPlayer entity, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		int id = world.getBlockId( x, y, z);
		if (id == Block.snow.blockID && (world.getBlockMetadata( x, y, z) & 7) == 0) {
			side = 1;
		}
		else if (!isBlockReplaceable( world, x, y, z, id)) {
			ForgeDirection dir = ForgeDirection.getOrientation( side);
			x += dir.offsetX;
			y += dir.offsetY;
			z += dir.offsetZ;
		}
		if (stack.stackSize == 0) {
			return false;
		}
		if (!entity.canPlayerEdit( x, y, z, side, stack)) {
			return false;
		}
		if (y == 255 && Block.blocksList[getBlockID()].blockMaterial.isSolid()) {
			return false;
		}
		if (world.canPlaceEntityOnSide( getBlockID(), x, y, z, false, side, entity, stack)) {
			Block block = Block.blocksList[getBlockID()];
			int meta = getMetadata( stack.getItemDamage());
			int blkMeta = block.onBlockPlaced( world, x, y, z, side, hitX, hitY, hitZ, meta);
			if (placeBlockAt( stack, entity, world, x, y, z, side, hitX, hitY, hitZ, blkMeta)) {
				world.playSoundEffect( x + 0.5F, y + 0.5F, z + 0.5F, block.stepSound.getPlaceSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
				--stack.stackSize;
			}
			return true;
		}
		return false;
	}

	@Override
	@SideOnly( Side.CLIENT)
	public void registerIcons( IconRegister reg) {
		mTexture = reg.registerIcon( FTextures.PATH_DEFAULT + FStrings.BLOCK_RED_WIRE_NAME);
	}

	private boolean isBlockReplaceable( World world, int x, int y, int z, int id) {
		if (id == Block.vine.blockID || id == Block.tallGrass.blockID || id == Block.deadBush.blockID) {
			return true;
		}
		Block block = Block.blocksList[id];
		return block != null && block.isBlockReplaceable( world, x, y, z);
	}
}
