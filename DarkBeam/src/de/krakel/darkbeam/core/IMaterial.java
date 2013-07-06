/**
 * Dark Beam
 * IMaterial.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import net.minecraft.util.Icon;

public interface IMaterial {
	void addStringLocalization( ISection sec, String lang);

	Icon getIcon( int side);

	int getID();

	String getName( ISection sec);

	boolean isIsolation();

	int toDmg();
}
