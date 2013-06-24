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

import de.krakel.darkbeam.client.renderer.AMaskRenderer;
import de.krakel.darkbeam.client.renderer.MaskRedWireRender;

class RedWireSection extends AWireSection {
	private MaskRedWireRender mRenderer;

	public RedWireSection() {
		super( "db.redwire");
		mRenderer = new MaskRedWireRender();
	}

	@Override
	public int getBlockID( int dmg) {
		return Block.blockRedstone.blockID;
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
		return "tile." + getName();
	}

	@Override
	public void setSectionBounds( int area, Block blk) {
		mRenderer.setSectionBounds( area, blk);
	}
}
