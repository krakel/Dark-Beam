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

	private static void addSearchBlocks( ChunkPosition pos, IConnectable con) {
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

	private static void addUpdateBlock( ChunkPosition pos) {
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

	private static int cablePower( IConnectable con, World world, ChunkPosition pos, IMaterial insu) {
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

	@SuppressWarnings( "unused")
	private static int indirectEdge( IConnectable con, World world, ChunkPosition pos, int indirect) {
		for (AreaType edge : AreaType.valuesEdge()) {
			if (con.isValidEdgeCon( edge)) {
				int x = pos.x + edge.mDx;
				int y = pos.y + edge.mDy;
				int z = pos.z + edge.mDz;
				if (con.isWired( edge.sideA())) {
					indirect = Math.max( indirectSideWeak( world, x, y, z, edge.sideA().ordinal()), indirect);
				}
				if (con.isWired( edge.sideB())) {
					indirect = Math.max( indirectSideWeak( world, x, y, z, edge.sideB().ordinal()), indirect);
				}
			}
		}
		return indirect;
	}

	public static int indirectPower( IConnectable con, World world, ChunkPosition pos) {
		int indirect = 0;
		indirect = indirectSide( con, world, pos, indirect);
//		indirect = indirectEdge( con, world, pos, indirect);
		return indirect;
	}

	private static int indirectSide( IConnectable con, World world, ChunkPosition pos, int indirect) {
		for (AreaType side : AreaType.valuesSide()) {
			if (con.isValidSideCon( side)) {
//				LogHelper.info( "powerSide: %s, %s", side.name(), toString());
				int x = pos.x + side.mDx;
				int y = pos.y + side.mDy;
				int z = pos.z + side.mDz;
				indirect = Math.max( indirectSideWeak( world, x, y, z, side.ordinal()), indirect);
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
				tile.getConnect().updatePower( world, pos);
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

	public static int updatePower( IConnectable con, World world, ChunkPosition pos, IMaterial insu) {
		int power = con.getPower( insu);
		int cable = cablePower( con, world, pos, insu);
		int indirect = con.indirectPower( world, pos);
		if (power == 0 && cable == 0 && indirect == 0) {
			return power;
		}
		if (power == cable - 1 && cable > indirect) {
			return power;
		}
		if (power == indirect && cable <= indirect) {
			return power;
		}
		if (cable > indirect || cable > power) {
			power = Math.max( 0, cable - 1);
		}
		else if (indirect >= power) {
			power = indirect;
		}
		else {
			power = 0;
		}
		addUpdateBlock( pos);
		addSearchBlocks( pos, con);
		return power;
	}
}
