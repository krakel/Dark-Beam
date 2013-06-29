/**
 * Dark Beam
 * SectionRedWire.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import net.minecraft.block.Block;
import net.minecraft.util.Icon;

import de.krakel.darkbeam.client.renderer.SectionRedWireRender;

class SectionRedWire extends ASectionWire {
	public SectionRedWire() {
		super( "db.redwire", new SectionRedWireRender());
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
	public int getLevel() {
		return 1;
	}

	@Override
	public String getSectionName( int dmg) {
		return "tile." + getName();
	}

	@Override
	public boolean isRedwire() {
		return true;
	}
}
