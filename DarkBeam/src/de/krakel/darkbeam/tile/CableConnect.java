/**
 * Dark Beam
 * CableConnect.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.tile;

import de.krakel.darkbeam.core.ASectionWire;
import de.krakel.darkbeam.core.IMaterial;
import de.krakel.darkbeam.core.ISection;
import de.krakel.darkbeam.core.InsulateLib;

public class CableConnect extends AConnect {
	public CableConnect( ASectionWire wire) {
		super( wire);
	}

	@Override
	protected boolean canConnect( IConnectable other) {
		if (other.isAllowed( mWire, InsulateLib.UNKNOWN)) {
			return true;
		}
		return Math.abs( getLevel() - other.getLevel()) == 1;
	}

	@Override
	public boolean isAllowed( ISection sec, IMaterial mat) {
		return mWire.equals( sec);
	}
}
