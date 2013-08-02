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
import de.krakel.darkbeam.core.IMaterial;
import de.krakel.darkbeam.lib.BlockType;

class PowerSearch {
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

	static void addSearchBlocks( ChunkPosition pos, IConnectable con) {
		for (AreaType side : AreaType.valuesSide()) {
			if (con.isValidSideCon( side)) {
				int x = pos.x + side.mDx;
				int y = pos.y + side.mDy;
				int z = pos.z + side.mDz;
				addPosition( new ChunkPosition( x, y, z));
			}
		}
		for (AreaType edge : AreaType.valuesEdge()) {
			if (con.isValidEdgeCon( edge)) {
				int x = pos.x + edge.mDx;
				int y = pos.y + edge.mDy;
				int z = pos.z + edge.mDz;
				addPosition( new ChunkPosition( x, y, z));
			}
		}
	}

	static void addUpdateBlock( ChunkPosition pos) {
		for (AreaType area : AreaType.valuesSphere()) {
			int x = pos.x + area.mDx;
			int y = pos.y + area.mDy;
			int z = pos.z + area.mDz;
			sUpdates.add( new ChunkPosition( x, y, z));
		}
		for (AreaType side : AreaType.valuesSide()) {
			int x = pos.x + (side.mDx << 1);
			int y = pos.y + (side.mDy << 1);
			int z = pos.z + (side.mDz << 1);
			sUpdates.add( new ChunkPosition( x, y, z));
		}
	}

	private static int cableEdge( IConnectable con, World world, ChunkPosition pos, int cable, IMaterial insu) {
		for (AreaType edge : AreaType.valuesEdge()) {
			if (con.isValidEdgeCon( edge)) {
				int x = pos.x + edge.mDx;
				int y = pos.y + edge.mDy;
				int z = pos.z + edge.mDz;
				if (con.isWired( edge.sideA())) {
					cable = Math.max( cablePower( world, x, y, z, edge.sideA().ordinal(), insu), cable);
				}
				if (con.isWired( edge.sideB())) {
					cable = Math.max( cablePower( world, x, y, z, edge.sideB().ordinal(), insu), cable);
				}
			}
		}
		return cable;
	}

	static int cablePower( IConnectable con, World world, ChunkPosition pos, IMaterial insu) {
		int cable = -1;
		cable = cableSide( con, world, pos, cable, insu);
		cable = cableEdge( con, world, pos, cable, insu);
		return cable;
	}

	private static int cablePower( World world, int x, int y, int z, int side, IMaterial insu) {
		TileStage tile = DarkLib.getTileEntity( world, x, y, z, TileStage.class);
		if (tile != null) {
			return tile.getConnect().getPower( insu);
		}
		return 0;
	}

	private static int cableSide( IConnectable con, World world, ChunkPosition pos, int cable, IMaterial insu) {
		for (AreaType side : AreaType.valuesSide()) {
			if (con.isValidSideCon( side)) {
//				LogHelper.info( "powerSide: %s, %s", side.name(), toString());
				int x = pos.x + side.mDx;
				int y = pos.y + side.mDy;
				int z = pos.z + side.mDz;
				cable = Math.max( cableSideValue( world, x, y, z, side.ordinal(), insu), cable);
			}
		}
		return cable;
	}

	private static int cableSideValue( World world, int x, int y, int z, int side, IMaterial insu) {
		int id = world.getBlockId( x, y, z);
		if (id == 0) {
			return -1;
		}
		if (id == Block.redstoneWire.blockID) {
			int meta = world.getBlockMetadata( x, y, z);
			return meta > 0 ? meta : -1;
		}
		return cablePower( world, x, y, z, side, insu);
	}

	private static void notifyBlock( World world, int x, int y, int z, int blkID) {
		int id = world.getBlockId( x, y, z);
		Block blk = Block.blocksList[id];
		if (blk != null) {
			blk.onNeighborBlockChange( world, x, y, z, blkID);
		}
	}

	public static void update( World world, ChunkPosition pos) {
		addPosition( pos);
		if (sSearching) {
			return;
		}
		sSearching = true;
		while (!sSearch.isEmpty()) {
			ChunkPosition akt = sSearch.removeFirst();
			sSearchCheck.remove( akt);
			TileStage tile = DarkLib.getTileEntity( world, akt.x, akt.y, akt.z, TileStage.class);
			if (tile != null) {
				tile.getConnect().updatePower( world, akt);
			}
		}
		sSearchCheck.clear();
		sSearching = false;
		HashSet<ChunkPosition> updates = sUpdates;
		sUpdates = new HashSet<ChunkPosition>();
		int id = BlockType.STAGE.getId();
		for (ChunkPosition akt : updates) {
			notifyBlock( world, akt.x, akt.y, akt.z, id);
			world.markBlockForUpdate( akt.x, akt.y, akt.z);
		}
	}
}
