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
import de.krakel.darkbeam.client.renderer.BlockStageRender;
import de.krakel.darkbeam.core.AreaType;
import de.krakel.darkbeam.core.DarkLib;
import de.krakel.darkbeam.core.ISection;
import de.krakel.darkbeam.core.SectionLib;
import de.krakel.darkbeam.core.helper.LogHelper;
import de.krakel.darkbeam.tile.TileStage;

public class BlockStage extends Block {
	public BlockStage( int id) {
		super( id, DarkBeam.MAT_DARK);
		setHardness( 0.1F);
		disableStats();
	}

	@Override
	public boolean canConnectRedstone( IBlockAccess world, int x, int y, int z, int side) {
		TileStage tile = DarkLib.getTileEntity( world, x, y, z, TileStage.class);
		if (tile == null) {
			return false;
		}
		return tile.isUsed( AreaType.DOWN) && tile.getSection( AreaType.DOWN).isWire();
	}

	@Override
	public boolean canProvidePower() {
		return true;
	}

	@Override
	public MovingObjectPosition collisionRayTrace( World world, int x, int y, int z, Vec3 start, Vec3 end) {
		TileStage tile = DarkLib.getTileEntity( world, x, y, z, TileStage.class);
		if (tile == null) {
			return null;
		}
		double min = 0.0D;
		MovingObjectPosition result = null;
		for (AreaType i : tile) {
			tile.setSectionBounds( i, this);
			MovingObjectPosition hit = super.collisionRayTrace( world, x, y, z, start, end);
			if (hit != null) {
				double dist = hit.hitVec.squareDistanceTo( start);
				if (result == null || dist < min) {
					result = hit;
					result.subHit = i.ordinal();
					min = dist;
				}
			}
		}
		if (result != null) {
			tile.setSectionBounds( AreaType.toArea( result.subHit), this);
		}
		return result;
	}

	@Override
	public int colorMultiplier( IBlockAccess world, int x, int y, int z) {
		TileStage tile = DarkLib.getTileEntity( world, x, y, z, TileStage.class);
		if (tile == null) {
			return super.colorMultiplier( world, x, y, z);
		}
		if (tile.getConnect().isPowerd()) {
			return 0xFFFFFF;
		}
//		return super.colorMultiplier( world, x, y, z);
		return 0x000000;
	}

	@Override
	public TileEntity createTileEntity( World world, int meta) {
//		LogHelper.info( "createTileEntity: %s, %d", LogHelper.toString( world), meta);
		return new TileStage();
	}

//	@Override
//	@SideOnly( Side.CLIENT)
//	public Icon getBlockTexture( IBlockAccess world, int x, int y, int z, int side) {
//		TileStage tile = DarkLib.getTileEntity( world, x, y, z, TileStage.class);
//		if (tile == null) {
//			return super.getBlockTexture( world, x, y, z, side);
//		}
//		return super.getBlockTexture( world, x, y, z, side);
//	}
//
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool( World world, int x, int y, int z) {
		return null;
	}

	@Override
	@SideOnly( Side.CLIENT)
	public Icon getIcon( int side, int dmg) {
		ISection sec = SectionLib.getForDmg( dmg);
		return sec.getIcon( side, dmg);
	}

//	@Override
//	public int getMixedBrightnessForBlock( IBlockAccess world, int x, int y, int z) {
//		TileStage tile = DarkLib.getTileEntity( world, x, y, z, TileStage.class);
//		if (tile == null) {
//			return super.getMixedBrightnessForBlock( world, x, y, z);
//		}
//		return 100;
//	}
//
	@Override
	public int getRenderType() {
		return BlockStageRender.ID;
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
	public int isProvidingStrongPower( IBlockAccess world, int x, int y, int z, int side) {
		return isProvidingWeakPower( world, x, y, z, side);
	}

	@Override
	public int isProvidingWeakPower( IBlockAccess world, int x, int y, int z, int side) {
		TileStage tile = DarkLib.getTileEntity( world, x, y, z, TileStage.class);
		if (tile == null) {
			return 0;
		}
		return tile.getConnect().getProvidingPower( AreaType.toArea( side).anti());
	}

	@Override
	public void onBlockClicked( World world, int x, int y, int z, EntityPlayer player) {
		LogHelper.info( "onBlockClicked: %s, %s", LogHelper.toString( world), LogHelper.toString( x, y, z));
		super.onBlockClicked( world, x, y, z, player);
	}

	@Override
	public void onNeighborBlockChange( World world, int x, int y, int z, int blockID) {
		LogHelper.info( "onNeighborBlockChange: %s, %s", LogHelper.toString( world), LogHelper.toString( x, y, z));
		TileStage tile = DarkLib.getTileEntity( world, x, y, z, TileStage.class);
		if (tile != null) {
			tile.refresh();
			tile.markForUpdate();
		}
		else {
			world.setBlockToAir( x, y, z);
		}
	}

	@Override
	@SideOnly( Side.CLIENT)
	public void registerIcons( IconRegister reg) {
	}

	@Override
	public boolean removeBlockByPlayer( World world, EntityPlayer player, int x, int y, int z) {
		LogHelper.info( "removeBlockByPlayer: %s, %s", LogHelper.toString( world), LogHelper.toString( x, y, z));
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
		int meta = tile.tryRemove( AreaType.toArea( pos.subHit));
		if (meta >= 0) {
			tile.dropItem( meta);
			if (tile.isEmpty()) {
				tile.invalidate();
				world.setBlockToAir( x, y, z);
			}
			else {
				tile.refresh();
				tile.markForUpdate();
			}
			tile.notifyAllChange();
		}
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
}
