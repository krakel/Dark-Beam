/**
 * Dark Beam
 * MaskCover1Renderer.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.client.renderer;

import de.krakel.darkbeam.core.AreaType;
import de.krakel.darkbeam.core.IArea;

public class MaskCover1Renderer extends ACoverRenderer implements IArea {
	private static final float THICKNESS = 1F / 16F;
	private static final int MASK_D = D | DN | DS | DW | DE | DNW | DNE | DSW | DSE | DU;
	private static final int MASK_U = U | UN | US | UW | UE | UNW | UNE | USW | USE | DU;
	private static final int MASK_N = N | DN | UN | NW | NE | DNW | DNE | UNW | UNE | NS;
	private static final int MASK_S = S | DS | US | SW | SE | UNW | UNE | USW | USE | NS;
	private static final int MASK_W = W | DW | UW | NW | SW | DNW | DSW | UNW | USW | WE;
	private static final int MASK_E = E | DE | UE | NE | SE | DNE | DSE | UNE | USE | WE;

	public MaskCover1Renderer() {
		super( THICKNESS);
	}

	@Override
	public boolean validate( int[] arr, AreaType area) {
		switch (area) {
			case SideDown:
				if (arr[AreaType.SideDown.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.EdgeDownNorth.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.EdgeDownSouth.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.EdgeDownWest.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.EdgeDownEast.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.CornerDownNorthEast.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.CornerDownNorthWest.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.CornerDownSouthEast.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.CornerDownSouthWest.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.AxisDownUp.ordinal()] < 0) {
					return false;
				}
				return true;
			case SideUp:
				if (arr[AreaType.SideUp.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.EdgeUpNorth.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.EdgeUpSouth.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.EdgeUpWest.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.EdgeUpEast.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.CornerUpNorthEast.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.CornerUpNorthWest.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.CornerUpSouthEast.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.CornerUpSouthWest.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.AxisDownUp.ordinal()] < 0) {
					return false;
				}
				return true;
			case SideNorth:
				if (arr[AreaType.SideNorth.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.EdgeDownNorth.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.EdgeUpNorth.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.EdgeNorthEast.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.EdgeNorthWest.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.CornerDownNorthEast.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.CornerDownNorthWest.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.CornerUpNorthEast.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.CornerUpNorthWest.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.AxisNorthSouth.ordinal()] < 0) {
					return false;
				}
				return true;
			case SideSouth:
				if (arr[AreaType.SideSouth.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.EdgeDownSouth.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.EdgeUpSouth.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.EdgeSouthEast.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.EdgeSouthWest.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.CornerDownSouthEast.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.CornerDownSouthWest.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.CornerUpSouthEast.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.CornerUpSouthWest.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.AxisNorthSouth.ordinal()] < 0) {
					return false;
				}
				return true;
			case SideWest:
				if (arr[AreaType.SideWest.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.EdgeDownWest.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.EdgeUpWest.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.EdgeNorthWest.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.EdgeSouthWest.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.CornerDownNorthWest.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.CornerDownSouthWest.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.CornerUpNorthWest.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.CornerUpSouthWest.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.AxisWestEast.ordinal()] < 0) {
					return false;
				}
				return true;
			case SideEast:
				if (arr[AreaType.SideEast.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.EdgeDownEast.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.EdgeUpEast.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.EdgeNorthEast.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.EdgeSouthEast.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.CornerDownNorthEast.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.CornerDownSouthEast.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.CornerUpNorthEast.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.CornerUpSouthEast.ordinal()] < 0) {
					return false;
				}
				if (arr[AreaType.AxisWestEast.ordinal()] < 0) {
					return false;
				}
				return true;
			default:
				return false;
		}
	}
}
