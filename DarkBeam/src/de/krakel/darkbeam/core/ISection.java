/**
 * Dark Beam
 * ISection.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;

import de.krakel.darkbeam.client.renderer.IMaskRenderer;
import de.krakel.darkbeam.tile.TileStage;

public interface ISection {
	int getBlockID( int dmg);

	Icon getIcon( int side, int dmg);

	int getID();

	String getName();

	IMaskRenderer getRenderer();

	String getSectionKey();

	String getSectionName( int dmg);

	boolean hasMaterials();

	boolean isValid( TileStage tile, int area);

	void oppositeArea( MovingObjectPosition pos);

	int toDmg();

	void updateArea( MovingObjectPosition pos);
}
