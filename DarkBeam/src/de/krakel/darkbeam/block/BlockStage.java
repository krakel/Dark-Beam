/**
 * Dark Beam
 * BlockSection.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.krakel.darkbeam.DarkBeam;
import de.krakel.darkbeam.client.renderer.BlockSectionRender;
import de.krakel.darkbeam.core.DarkLib;
import de.krakel.darkbeam.core.ISection;
import de.krakel.darkbeam.core.SectionLib;
import de.krakel.darkbeam.core.helper.LogHelper;
import de.krakel.darkbeam.lib.BlockType;
import de.krakel.darkbeam.tile.TileStage;

public class BlockStage extends Block {
	public BlockStage( int id) {
		super( id, DarkBeam.MAT_DARK);
		setHardness( 0.1F);
		disableStats();
	}

	@Override
	public MovingObjectPosition collisionRayTrace( World world, int x, int y, int z, Vec3 start, Vec3 end) {
		TileStage tile = DarkLib.getTileEntity( world, x, y, z, TileStage.class);
		if (tile == null) {
			return null;
		}
		double min = 0.0D;
		MovingObjectPosition result = null;
		for (int i : tile) {
			ISection sec = tile.getSection( i);
			sec.getRenderer().setSectionBounds( i, this);
			MovingObjectPosition hit = super.collisionRayTrace( world, x, y, z, start, end);
			if (hit != null) {
				double dist = hit.hitVec.squareDistanceTo( start);
				if (result == null || dist < min) {
					result = hit;
					result.subHit = i;
					min = dist;
				}
			}
		}
		if (result != null) {
			int side = result.subHit;
			ISection sec = tile.getSection( side);
			sec.getRenderer().setSectionBounds( side, this);
		}
		return result;
	}

	@Override
	public TileEntity createTileEntity( World world, int meta) {
		LogHelper.info( "createTileEntity: %b, %d", world.isRemote, meta);
		return new TileStage();
	}

	@Override
	@SideOnly( Side.CLIENT)
	public Icon getBlockTexture( IBlockAccess world, int x, int y, int z, int side) {
		TileStage tile = DarkLib.getTileEntity( world, x, y, z, TileStage.class);
		if (tile == null) {
			return super.getBlockTexture( world, x, y, z, side);
		}
		return super.getBlockTexture( world, x, y, z, side);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool( World par1World, int par2, int par3, int par4) {
		return null;
	}

	@Override
	@SideOnly( Side.CLIENT)
	public Icon getIcon( int side, int dmg) {
		ISection sec = SectionLib.getForDmg( dmg);
		return sec.getIcon( side, dmg);
	}

	@Override
	public int getRenderType() {
		return BlockSectionRender.ID;
	}

	@Override
	public void harvestBlock( World world, EntityPlayer player, int x, int y, int z, int meta) {
	}

	@Override
	public boolean hasTileEntity( int metadata) {
		return true;
	}

	@Override
	public int idDropped( int meta, Random rand, int fortune) {
		return 0;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void onBlockClicked( World world, int x, int y, int z, EntityPlayer player) {
		LogHelper.info( "onBlockClicked: %b, %s", world.isRemote, LogHelper.toString( x, y, z));
		super.onBlockClicked( world, x, y, z, player);
	}

	@Override
	@SideOnly( Side.CLIENT)
	public void registerIcons( IconRegister reg) {
	}

	@Override
	public boolean removeBlockByPlayer( World world, EntityPlayer player, int x, int y, int z) {
		LogHelper.info( "removeBlockByPlayer: %b, %s", world.isRemote, LogHelper.toString( x, y, z));
//		if (world.isRemote) {
//			return false;
//		}
		MovingObjectPosition pos = DarkLib.retraceBlock( world, player, x, y, z);
		if (pos == null) {
			return false;
		}
		if (pos.typeOfHit != EnumMovingObjectType.TILE) {
			return false;
		}
		TileStage tile = DarkLib.getTileEntity( world, x, y, z, TileStage.class);
		if (tile == null) {
			return false;
		}
		int meta = tile.tryRemove( pos.subHit);
		if (meta >= 0) {
			ItemStack stk = new ItemStack( BlockType.STAGE.getBlock(), 1, meta);
			DarkLib.dropItem( world, x, y, z, stk);
			if (tile.isEmpty()) {
				world.setBlockToAir( x, y, z);
			}
			else {
				world.markBlockForUpdate( x, y, z);
//				tile.updateEntity();
			}
		}
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
}