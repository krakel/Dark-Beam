/**
 * Dark Beam
 * AStructureRenderer.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.client.renderer;

import net.minecraft.util.Icon;

import de.krakel.darkbeam.core.Section;
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
	public String getNameForSection( Section sec, int dmg) {
		Material mat = MaterialLib.getForDmg( dmg);
		return mat.getMatName( sec);
	}

	@Override
	public boolean hasMaterials() {
		return true;
	}
}
