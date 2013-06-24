/**
 * Dark Beam
 * SectionInsulated.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.Icon;

import de.krakel.darkbeam.client.renderer.ASectionRenderer;
import de.krakel.darkbeam.client.renderer.SectionInsulatedRenderer;

class SectionInsulated extends ASectionWire {
	private SectionInsulatedRenderer mRenderer;

	public SectionInsulated() {
		super( "insuwire");
		mRenderer = new SectionInsulatedRenderer();
	}

	@Override
	public int getBlockID( int dmg) {
		return Item.dyePowder.itemID;
	}

	@Override
	public Icon getIcon( int side, int dmg) {
		Insulate insu = InsulateLib.getForDmg( dmg);
		return insu.getIcon( side);
	}

	@Override
	public ASectionRenderer getRenderer() {
		return mRenderer;
	}

	@Override
	public String getSectionName( int dmg) {
		Insulate insu = InsulateLib.getForDmg( dmg);
		return insu.getInsuName( this);
	}

	@Override
	public void setSectionBounds( int area, Block blk) {
		mRenderer.setSectionBounds( area, blk);
	}
}
