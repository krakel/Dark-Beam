/**
 * Dark Beam
 * Section.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import net.minecraft.util.MovingObjectPosition;

import de.krakel.darkbeam.client.renderer.IMaskRenderer;
import de.krakel.darkbeam.tile.TileSection;

public class Section implements IDirection {
	public final String mName;
	public final int mSecID;
	public final IMaskRenderer mRenderer;

	Section( int secID, String name, IMaskRenderer renderer) {
		mSecID = secID;
		mName = name;
		mRenderer = renderer;
	}

	public int getBlockID( int dmg) {
		return mRenderer.getBlockID( dmg);
	}

	public String getSectionKey() {
		return "db.section." + mName;
	}

	public String getSectionName( int dmg) {
		return mRenderer.getNameForSection( this, dmg);
	}

	public boolean hasMaterials() {
		return mRenderer.hasMaterials();
	}

	public boolean isValid( TileSection tile, int area) {
		return mRenderer.isValid( area, tile);
	}

	public void oppositeArea( MovingObjectPosition pos) {
		pos.subHit = mRenderer.getOpposite( pos.sideHit, pos.subHit);
	}

	public int toDmg() {
		return mSecID << 8;
	}

	public void updateArea( MovingObjectPosition pos) {
		double dx = pos.hitVec.xCoord - pos.blockX;
		double dy = pos.hitVec.yCoord - pos.blockY;
		double dz = pos.hitVec.zCoord - pos.blockZ;
		pos.subHit = mRenderer.getArea( pos.sideHit, dx, dy, dz);
	}
}
