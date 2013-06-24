/**
 * Dark Beam
 * CoverSection.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;

import de.krakel.darkbeam.client.renderer.AMaskRenderer;
import de.krakel.darkbeam.client.renderer.MaskCoverRenderer;
import de.krakel.darkbeam.tile.TileStage;

public class CoverSection extends ASection {
	private MaskCoverRenderer mRenderer;

	public CoverSection( int nr) {
		super( "cover." + nr);
		mRenderer = new MaskCoverRenderer( nr);
	}

	@Override
	public int getBlockID( int dmg) {
		return mRenderer.getBlockID( dmg);
	}

	@Override
	public Icon getIcon( int side, int dmg) {
		Material mat = MaterialLib.getForDmg( dmg);
		return mat.getIcon( side);
	}

	@Override
	public AMaskRenderer getRenderer() {
		return mRenderer;
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
	public boolean isValid( TileStage tile, int area) {
		return mRenderer.isValid( area, tile);
	}

	@Override
	public void oppositeArea( MovingObjectPosition pos) {
		pos.subHit = mRenderer.getOpposite( pos.sideHit, pos.subHit);
	}

	@Override
	public void updateArea( MovingObjectPosition pos) {
		double dx = pos.hitVec.xCoord - pos.blockX;
		double dy = pos.hitVec.yCoord - pos.blockY;
		double dz = pos.hitVec.zCoord - pos.blockZ;
		pos.subHit = mRenderer.getArea( pos.sideHit, dx, dy, dz);
	}
}
