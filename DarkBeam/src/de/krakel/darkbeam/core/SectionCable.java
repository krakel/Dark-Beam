/**
 * Dark Beam
 * SectionCable.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import net.minecraft.block.Block;
import net.minecraft.util.Icon;

import de.krakel.darkbeam.client.renderer.SectionCableRenderer;
import de.krakel.darkbeam.tile.CableConnect;
import de.krakel.darkbeam.tile.IConnectable;

public class SectionCable extends ASectionWire {
	SectionCable() {
		super( "db.cable", new SectionCableRenderer());
	}

	@Override
	public IConnectable createConnect() {
		return new CableConnect( this);
	}

	@Override
	public int getBlockID( int dmg) {
		return Block.sandStone.blockID;
	}

	@Override
	public Icon getIcon( int side, int dmg) {
		return Block.sandStone.getIcon( side, 0); // TODO
	}

	@Override
	public int getLevel() {
		return 3;
	}

	@Override
	public String getSectionName( int dmg) {
		return "tile." + getName();
	}

	@Override
	public boolean isRedwire() {
		return false;
	}
}
