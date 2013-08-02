/**
 * Dark Beam
 * ASectionStructure.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import net.minecraft.util.Icon;

import de.krakel.darkbeam.client.renderer.ASectionRenderer;
import de.krakel.darkbeam.tile.IConnectable;

public abstract class ASectionStructure extends ASection {
	ASectionStructure( int secID, String name, ASectionRenderer renderer) {
		super( secID, name, renderer);
	}

	protected ASectionStructure( String name, ASectionRenderer renderer) {
		super( name, renderer);
	}

	@Override
	public IConnectable createConnect( IMaterial mat) {
		return IConnectable.NO_CONNECT;
	}

	@Override
	public IMaterial getForDmg( int dmg) {
		return MaterialLib.getForDmg( dmg);
	}

	@Override
	public Icon getIcon( int side, int dmg) {
		Material mat = MaterialLib.getForDmg( dmg);
		return mat.getIcon( side);
	}

	@Override
	public String getSectionName( int dmg) {
		Material mat = MaterialLib.getForDmg( dmg);
		return mat.getName( this);
	}

	@Override
	public boolean isStructure() {
		return true;
	}

	@Override
	public boolean isWire() {
		return false;
	}
}
