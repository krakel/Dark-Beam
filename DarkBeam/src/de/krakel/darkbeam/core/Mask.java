/**
 * Dark Beam
 * Mask.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import net.minecraft.util.MovingObjectPosition;

import de.krakel.darkbeam.client.renderer.IMaskRenderer;
import de.krakel.darkbeam.tile.TileMasking;

public class Mask implements IDirection {
	final String mName;
	public final int mMaskID;
	public final IMaskRenderer mRenderer;

	Mask( int maskID, String name, IMaskRenderer renderer) {
		mMaskID = maskID;
		mName = name;
		mRenderer = renderer;
	}

	public int getSubHit( MovingObjectPosition pos) {
		double dx = pos.hitVec.xCoord - pos.blockX;
		double dy = pos.hitVec.yCoord - pos.blockY;
		double dz = pos.hitVec.zCoord - pos.blockZ;
		return mRenderer.getSubHit( pos.sideHit, dx, dy, dz);
	}

	public String getUnlocalizedName() {
		return "db.mask." + mName;
	}

	public boolean isValid( TileMasking tile, int area) {
		return mRenderer.isValid( tile, area);
	}

	public int toDmg() {
		return mMaskID << 8;
	}
}
