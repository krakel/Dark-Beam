/**
 * Dark Beam
 * Position.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

public class Position {
	private static final int[] REL_X = {
		0, 0, 0, 0, -1, 1
	};
	private static final int[] REL_Y = {
		-1, 1, 0, 0, 0, 0
	};
	private static final int[] REL_Z = {
		0, 0, -1, 1, 0, 0
	};
	private static final int[] ANTI_X = {
		0, 0, 0, 0, 1, -1
	};
	private static final int[] ANTI_Y = {
		1, -1, 0, 0, 0, 0
	};
	private static final int[] ANTI_Z = {
		0, 0, 1, -1, 0, 0
	};

	private Position() {
	}

	public static int antiX( int side) {
		try {
			return ANTI_X[side];
		}
		catch (IndexOutOfBoundsException ex) {
			return 0;
		}
	}

	public static int antiY( int side) {
		try {
			return ANTI_Y[side];
		}
		catch (IndexOutOfBoundsException ex) {
			return 0;
		}
	}

	public static int antiZ( int side) {
		try {
			return ANTI_Z[side];
		}
		catch (IndexOutOfBoundsException ex) {
			return 0;
		}
	}

	public static int relX( int side) {
		try {
			return REL_X[side];
		}
		catch (IndexOutOfBoundsException ex) {
			return 0;
		}
	}

	public static int relY( int side) {
		try {
			return REL_Y[side];
		}
		catch (IndexOutOfBoundsException ex) {
			return 0;
		}
	}

	public static int relZ( int side) {
		try {
			return REL_Z[side];
		}
		catch (IndexOutOfBoundsException ex) {
			return 0;
		}
	}
}
