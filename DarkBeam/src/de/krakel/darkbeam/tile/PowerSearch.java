/**
 * Dark Beam
 * PowerSearch.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.tile;

import java.util.HashSet;
import java.util.LinkedList;

import net.minecraft.block.Block;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

import de.krakel.darkbeam.core.AreaType;
import de.krakel.darkbeam.core.DarkLib;
import de.krakel.darkbeam.lib.BlockType;

public class PowerSearch {
	public static final int MAX_STRENGTH = 255;
	private static LinkedList<ChunkPosition> sSearch = new LinkedList<ChunkPosition>();
	private static HashSet<ChunkPosition> sSearchCheck = new HashSet<ChunkPosition>();
	private static HashSet<ChunkPosition> sUpdates = new HashSet<ChunkPosition>();
	private static boolean sSearching = false;

	private PowerSearch() {
	}

	private static void addPosition( ChunkPosition pos) {
		if (!sSearchCheck.contains( pos)) {
			sSearchCheck.add( pos);
			sSearch.addLast( pos);
		}
	}

	private static void addSearchBlocks( int xCoord, int yCoord, int zCoord, IConnectable con) {
		for (AreaType side : AreaType.valuesSide()) {
			if (con.isSided( side.offEdges())) {
				int x = xCoord + side.mDx;
				int y = yCoord + side.mDy;
				int z = zCoord + side.mDz;
				addPosition( new ChunkPosition( x, y, z));
			}
		}
		for (AreaType edge : AreaType.valuesEdge()) {
			if (con.isEdged( edge)) {
				int x = xCoord + edge.mDx;
				int y = yCoord + edge.mDy;
				int z = zCoord + edge.mDz;
				addPosition( new ChunkPosition( x, y, z));
			}
		}
	}

	private static void addUpdateBlock( int xCoord, int yCoord, int zCoord) {
		for (AreaType area : AreaType.valuesSphere()) {
			int x = xCoord + area.mDx;
			int y = yCoord + area.mDy;
			int z = zCoord + area.mDz;
			sUpdates.add( new ChunkPosition( x, y, z));
		}
		for (AreaType side : AreaType.valuesSide()) {
			int x = xCoord + (side.mDx << 1);
			int y = yCoord + (side.mDy << 1);
			int z = zCoord + (side.mDz << 1);
			sUpdates.add( new ChunkPosition( x, y, z));
		}
	}

	private static int cableEdge( TileStage tile, int cable) {
		IConnectable con = tile.getConnect();
		for (AreaType edge : AreaType.valuesEdge()) {
			if (con.isEdged( edge)) {
				int x = tile.xCoord + edge.mDx;
				int y = tile.yCoord + edge.mDy;
				int z = tile.zCoord + edge.mDz;
				if (con.isWired( edge.sideA())) {
					cable = Math.max( cablePower( tile.worldObj, x, y, z, edge.sideA().ordinal()), cable);
				}
				if (con.isWired( edge.sideB())) {
					cable = Math.max( cablePower( tile.worldObj, x, y, z, edge.sideB().ordinal()), cable);
				}
			}
		}
		return cable;
	}

	private static int cablePower( TileStage tile) {
		int cable = -1;
		cable = cableSide( tile, cable);
		cable = cableEdge( tile, cable);
		return cable;
	}

	private static int cablePower( World world, int x, int y, int z, int side) {
		TileStage tile = DarkLib.getTileEntity( world, x, y, z, TileStage.class);
		if (tile != null) {
			return tile.getConnect().getPower();
		}
		return 0;
	}

	private static int cableSide( TileStage tile, int cable) {
		IConnectable con = tile.getConnect();
		for (AreaType side : AreaType.valuesSide()) {
			if (con.isSided( side.offEdges())) {
//				LogHelper.info( "powerSide: %s, %s", side.name(), toString());
				int x = tile.xCoord + side.mDx;
				int y = tile.yCoord + side.mDy;
				int z = tile.zCoord + side.mDz;
				cable = Math.max( cableSideValue( tile.worldObj, x, y, z, side.ordinal()), cable);
			}
		}
		return cable;
	}

	private static int cableSideValue( World world, int x, int y, int z, int side) {
		int id = world.getBlockId( x, y, z);
		if (id == 0) {
			return -1;
		}
		if (id == Block.redstoneWire.blockID) {
			int meta = world.getBlockMetadata( x, y, z);
			return meta > 0 ? meta : -1;
		}
		return cablePower( world, x, y, z, side);
	}

	@SuppressWarnings( "unused")
	private static int indirectEdge( TileStage tile, int indirect) {
//		LogHelper.info( "powerEdge: %s", tile.toString());
		IConnectable con = tile.getConnect();
		for (AreaType edge : AreaType.valuesEdge()) {
			if (con.isEdged( edge)) {
				int x = tile.xCoord + edge.mDx;
				int y = tile.yCoord + edge.mDy;
				int z = tile.zCoord + edge.mDz;
				if (con.isWired( edge.sideA())) {
					indirect = Math.max( indirectSideWeak( tile.worldObj, x, y, z, edge.sideA().ordinal()), indirect);
				}
				if (con.isWired( edge.sideB())) {
					indirect = Math.max( indirectSideWeak( tile.worldObj, x, y, z, edge.sideB().ordinal()), indirect);
				}
			}
		}
		return indirect;
	}

	private static int indirectPower( TileStage tile) {
		int indirect = 0;
		indirect = indirectSide( tile, indirect);
//		indirect = indirectEdge( tile, indirect);
		return indirect;
	}

	private static int indirectSide( TileStage tile, int indirect) {
//		LogHelper.info( "powerSide: %s", tile.toString());
		IConnectable con = tile.getConnect();
		for (AreaType side : AreaType.valuesSide()) {
			if (con.isSided( side.offEdges())) {
//				LogHelper.info( "powerSide: %s, %s", side.name(), toString());
				int x = tile.xCoord + side.mDx;
				int y = tile.yCoord + side.mDy;
				int z = tile.zCoord + side.mDz;
				indirect = Math.max( indirectSideWeak( tile.worldObj, x, y, z, side.ordinal()), indirect);
			}
		}
		return indirect;
	}

	private static int indirectSideStrong( World world, int x, int y, int z, int side) {
		int id = world.getBlockId( x, y, z);
		if (DarkLib.isWireBlock( id)) {
			return 0;
		}
		Block blk = Block.blocksList[id];
		if (blk == null) {
			return 0;
		}
		return blk.isProvidingStrongPower( world, x, y, z, side);
	}

	private static int indirectSideWeak( World world, int x, int y, int z, int side) {
		int id = world.getBlockId( x, y, z);
		if (DarkLib.isWireBlock( id)) {
			return 0;
		}
		Block blk = Block.blocksList[id];
		if (blk == null) {
			return 0;
		}
		if (blk.isBlockNormalCube( world, x, y, z)) {
			return indirectWeak( world, x, y, z);
		}
		return blk.isProvidingWeakPower( world, x, y, z, side);
	}

	private static int indirectWeak( World world, int x, int y, int z) {
		int pwr = 0;
		for (AreaType side : AreaType.valuesSide()) {
			int x1 = x + side.mDx;
			int y1 = y + side.mDy;
			int z1 = z + side.mDz;
			pwr = Math.max( pwr, indirectSideStrong( world, x1, y1, z1, side.ordinal()));
			if (pwr >= 15) {
				break;
			}
		}
		return pwr;
	}

	private static void notifyBlock( World world, int x, int y, int z, int blkID) {
		int id = world.getBlockId( x, y, z);
		Block blk = Block.blocksList[id];
		if (blk != null) {
			blk.onNeighborBlockChange( world, x, y, z, blkID);
		}
	}

	public static void update( World world, int x, int y, int z) {
		addPosition( new ChunkPosition( x, y, z));
		if (sSearching) {
			return;
		}
		sSearching = true;
		while (!sSearch.isEmpty()) {
			ChunkPosition pos = sSearch.removeFirst();
			sSearchCheck.remove( pos);
			TileStage tile = DarkLib.getTileEntity( world, pos.x, pos.y, pos.z, TileStage.class);
			if (tile != null) {
				updatePower( tile);
			}
		}
		sSearchCheck.clear();
		sSearching = false;
		HashSet<ChunkPosition> updates = sUpdates;
		sUpdates = new HashSet<ChunkPosition>();
		int id = BlockType.STAGE.getId();
		for (ChunkPosition pos : updates) {
			notifyBlock( world, pos.x, pos.y, pos.z, id);
			world.markBlockForUpdate( pos.x, pos.y, pos.z);
		}
	}

	private static void updatePower( TileStage tile) {
		IConnectable con = tile.getConnect();
		int power = con.getPower();
		int cable = cablePower( tile);
		int indirect = indirectPower( tile);
		if (power == 0 && cable == 0 && indirect == 0) {
			con.setPower( power);
		}
		else if (power == cable - 1 && cable > indirect) {
			con.setPower( power);
		}
		else if (power == indirect && cable <= indirect) {
			con.setPower( power);
		}
		else {
			if (cable > indirect || cable > power) {
				power = Math.max( 0, cable - 1);
			}
			else if (indirect >= power) {
				power = indirect;
			}
			else {
				power = 0;
			}
			addUpdateBlock( tile.xCoord, tile.yCoord, tile.zCoord);
			addSearchBlocks( tile.xCoord, tile.yCoord, tile.zCoord, con);
			con.setPower( power);
		}
	}
}
