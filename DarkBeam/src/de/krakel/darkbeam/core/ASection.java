/**
 * Dark Beam
 * ASection.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;

import de.krakel.darkbeam.client.renderer.ASectionRenderer;
import de.krakel.darkbeam.tile.TileStage;

abstract class ASection implements ISection, IDirection, IArea {
	static final double BOX_BORDER_MIN = 1D / 4D;
	static final double BOX_BORDER_MAX = 1D - BOX_BORDER_MIN;
	protected String mName;
	private int mSecID;
	private static int sNextID = 0;
	private ASectionRenderer mRenderer;

	ASection( int secID, String name, ASectionRenderer renderer) {
		mSecID = secID;
		mName = name;
		mRenderer = renderer;
	}

	protected ASection( String name, ASectionRenderer renderer) {
		mSecID = nextID();
		mName = name;
		mRenderer = renderer;
	}

	private static int nextID() {
		return sNextID++;
	}

	@Override
	public int getID() {
		return mSecID;
	}

	@Override
	public String getName() {
		return mName;
	}

	@Override
	public void renderItem( RenderBlocks rndrBlk, Block blk, int dmg) {
		mRenderer.renderItem( rndrBlk, blk, dmg);
	}

	@Override
	public void renderSide( RenderBlocks rndrBlk, int area, Block blk, int dmg, int x, int y, int z, TileStage tile) {
		mRenderer.renderSide( rndrBlk, area, blk, dmg, x, y, z, tile);
	}

	@Override
	public void setSectionBounds( int area, Block blk, TileStage tile) {
		mRenderer.setSectionBounds( area, blk, tile);
	}

	@Override
	public int toDmg() {
		return mSecID << 8;
	}
}
