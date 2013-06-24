/**
 * Dark Beam
 * RedWireSection.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import net.minecraft.block.Block;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;

import de.krakel.darkbeam.client.renderer.AMaskRenderer;
import de.krakel.darkbeam.client.renderer.MaskRedWireRender;
import de.krakel.darkbeam.tile.TileStage;

public class RedWireSection extends ASection {
	private MaskRedWireRender mRenderer;

	public RedWireSection() {
		super( "db.redwire");
		mRenderer = new MaskRedWireRender();
	}

	@Override
	public int getBlockID( int dmg) {
		return mRenderer.getBlockID( dmg);
	}

	@Override
	public Icon getIcon( int side, int dmg) {
		return Block.blockRedstone.getIcon( side, 0);
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
