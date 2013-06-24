/**
 * Dark Beam
 * ASection.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import net.minecraft.util.MovingObjectPosition;

import de.krakel.darkbeam.client.renderer.IMaskRenderer;
import de.krakel.darkbeam.tile.TileSection;

abstract class ASection implements ISection {
	public final String mName;
	public final int mSecID;
	public final IMaskRenderer mRenderer;

	protected ASection( int secID, String name, IMaskRenderer renderer) {
		mSecID = secID;
		mName = name;
		mRenderer = renderer;
	}

	@Override
	public int getBlockID( int dmg) {
		return mRenderer.getBlockID( dmg);
	}

	@Override
	public String getName() {
		return mName;
	}

	@Override
	public IMaskRenderer getRenderer() {
		return mRenderer;
	}

	@Override
	public String getSectionKey() {
		return "db.section." + mName;
	}

	@Override
	public String getSectionName( int dmg) {
		return mRenderer.getNameForSection( this, dmg);
	}

	@Override
	public boolean hasMaterials() {
		return mRenderer.hasMaterials();
	}

	@Override
	public boolean isValid( TileSection tile, int area) {
		return mRenderer.isValid( area, tile);
	}

	@Override
	public void oppositeArea( MovingObjectPosition pos) {
		pos.subHit = mRenderer.getOpposite( pos.sideHit, pos.subHit);
	}

	@Override
	public int toDmg() {
		return mSecID << 8;
	}

	@Override
	public void updateArea( MovingObjectPosition pos) {
		double dx = pos.hitVec.xCoord - pos.blockX;
		double dy = pos.hitVec.yCoord - pos.blockY;
		double dz = pos.hitVec.zCoord - pos.blockZ;
		pos.subHit = mRenderer.getArea( pos.sideHit, dx, dy, dz);
	}
}
