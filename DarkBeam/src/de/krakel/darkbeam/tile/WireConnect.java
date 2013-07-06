/**
 * Dark Beam
 * WireConnect.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.tile;

import de.krakel.darkbeam.core.ASectionWire;
import de.krakel.darkbeam.core.IMaterial;
import de.krakel.darkbeam.core.ISection;

public class WireConnect extends AConnect {
	private IMaterial mInsu;

	public WireConnect( ASectionWire wire, IMaterial insu) {
		super( wire);
		mInsu = insu;
	}

	@Override
	protected boolean canConnect( IConnectable other) {
		if (other.isAllowed( mWire, mInsu)) {
			return true;
		}
		return Math.abs( getLevel() - other.getLevel()) == 1;
	}

	@Override
	public boolean isAllowed( ISection sec, IMaterial mat) {
		return mWire.equals( sec) && mInsu.equals( mat);
	}
}
