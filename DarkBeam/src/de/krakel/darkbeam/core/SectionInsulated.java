/**
 * Dark Beam
 * SectionInsulated.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import net.minecraft.util.Icon;

import de.krakel.darkbeam.client.renderer.SectionInsulatedRenderer;
import de.krakel.darkbeam.tile.IConnectable;
import de.krakel.darkbeam.tile.WireConnect;

public class SectionInsulated extends ASectionWire {
	SectionInsulated() {
		super( "insuwire", new SectionInsulatedRenderer());
	}

	@Override
	public IConnectable createConnect( IMaterial mat) {
		return new WireConnect( this, mat);
	}

	@Override
	public IMaterial getForDmg( int dmg) {
		return InsulateLib.getForDmg( dmg);
	}

	@Override
	public Icon getIcon( int side, int dmg) {
		Insulate insu = InsulateLib.getForDmg( dmg);
		return insu.getIcon( side);
	}

	@Override
	public int getLevel() {
		return 11;
	}

	@Override
	public String getSectionName( int dmg) {
		Insulate insu = InsulateLib.getForDmg( dmg);
		return insu.getName( this);
	}

	@Override
	public boolean isRedwire() {
		return true;
	}
}
