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

import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

import de.krakel.darkbeam.core.AreaType;
import de.krakel.darkbeam.core.DarkLib;

public class PowerSearch {
	public static final int MAX_STRENGTH = 255;
	private static HashSet<ChunkPosition> sFound = new HashSet<ChunkPosition>();
	private static LinkedList<ChunkPosition> sFront = new LinkedList<ChunkPosition>();

	private PowerSearch() {
	}

	private static void addPosition( ChunkPosition pos) {
		if (!sFound.contains( pos)) {
			sFound.add( pos);
			sFront.add( pos);
		}
	}

	private static void powerEdge( TileStage tile) {
//		LogHelper.info( "powerEdge: %s", tile.toString());
		int mPower = 0;
		IConnectable con = tile.getConnect();
		for (AreaType edge : AreaType.valuesEdge()) {
			if (con.isEdged( edge)) {
				int x = tile.xCoord + edge.mDx;
				int y = tile.yCoord + edge.mDy;
				int z = tile.zCoord + edge.mDz;
				TileStage other = DarkLib.getTileEntity( tile.worldObj, x, y, z, TileStage.class);
				if (other != null) {
					addPosition( new ChunkPosition( x, y, z));
				}
				else {
					if (con.isWired( edge.sideA())) {
						mPower = MathHelper.clamp_int( powerSideWeak( tile.worldObj, x, y, z, edge.sideA().ordinal()), mPower, PowerSearch.MAX_STRENGTH);
					}
					if (con.isWired( edge.sideB())) {
						mPower = MathHelper.clamp_int( powerSideWeak( tile.worldObj, x, y, z, edge.sideB().ordinal()), mPower, PowerSearch.MAX_STRENGTH);
					}
				}
			}
		}
	}

	private static void powerSide( TileStage tile) {
//		LogHelper.info( "powerSide: %s", tile.toString());
		int mPower = 0;
		IConnectable con = tile.getConnect();
		for (AreaType side : AreaType.valuesSide()) {
			if (con.isSided( side.offEdges())) {
//				LogHelper.info( "powerSide: %s, %s", side.name(), toString());
				int x = tile.xCoord + side.mDx;
				int y = tile.yCoord + side.mDy;
				int z = tile.zCoord + side.mDz;
				TileStage other = DarkLib.getTileEntity( tile.worldObj, x, y, z, TileStage.class);
				if (other != null) {
					addPosition( new ChunkPosition( x, y, z));
				}
				else {
					mPower = MathHelper.clamp_int( powerSideWeak( tile.worldObj, x, y, z, side.ordinal()), mPower, MAX_STRENGTH);
				}
			}
		}
	}

	private static int powerSideWeak( World world, int x, int y, int z, int ordinal) {
		return 0;
	}

	public static void update( World world, int x, int y, int z) {
		addPosition( new ChunkPosition( x, y, z));
		while (!sFront.isEmpty()) {
			ChunkPosition pos = sFront.removeFirst();
			TileStage tile = DarkLib.getTileEntity( world, pos.x, pos.y, pos.z, TileStage.class);
			if (tile != null) {
//				tile.getConnect().power( tile);
				powerSide( tile);
				powerEdge( tile);
			}
		}
		sFound.clear();
	}
}
