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
import de.krakel.darkbeam.tile.IConnectable;
import de.krakel.darkbeam.tile.WireConnect;

public class SectionRedWire extends ASectionWire {
	SectionRedWire() {
		super( "db.redwire", new SectionRedWireRender());
	}

	@Override
	public boolean canDock( ISection other) {
		return other.getID() == SectionLib.sInsuwire.getID();
	}

	@Override
	public IConnectable createConnect( IMaterial mat) {
		return new WireConnect( this, mat);
	}

	@Override
	public IMaterial getForDmg( int dmg) {
		return InsulateLib.UNKNOWN;
	}

	@Override
	public Icon getIcon( int side, int dmg) {
		return Block.blockRedstone.getIcon( side, 0);
	}

	@Override
	public String getSectionName( int dmg) {
		return "tile." + getName();
	}

	@Override
	public boolean isWire() {
		return true;
	}
}
