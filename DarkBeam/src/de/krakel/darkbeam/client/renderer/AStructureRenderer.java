/**
 * Dark Beam
 * AStructureRenderer.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.client.renderer;

import net.minecraft.util.Icon;

import de.krakel.darkbeam.core.Mask;
import de.krakel.darkbeam.core.Material;
import de.krakel.darkbeam.core.MaterialLib;

abstract class AStructureRenderer extends AMaskRenderer {
	protected AStructureRenderer( int base) {
		super( base);
	}

	@Override
	public int getBlockID( int dmg) {
		Material mat = MaterialLib.getForDmg( dmg);
		return mat.mBlock.blockID;
	}

	@Override
	public Icon getIcon( int side, int dmg) {
		Material mat = MaterialLib.getForDmg( dmg);
		return mat.getIcon( side);
	}

	@Override
	public String getNameForMask( Mask mask, int dmg) {
		Material mat = MaterialLib.getForDmg( dmg);
		return mat.getMatName( mask);
	}

	@Override
	public boolean hasMaterials() {
		return true;
	}
}
