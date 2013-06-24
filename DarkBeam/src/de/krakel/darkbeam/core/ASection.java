/**
 * Dark Beam
 * ASection.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

abstract class ASection implements ISection, IDirection, IArea {
	public String mName;
	public int mSecID;

	protected ASection( int secID, String name) {
		mSecID = secID;
		mName = name;
	}

	protected ASection( String name) {
		mSecID = SectionLib.nextID();
		mName = name;
	}

	@Override
	public int getID() {
		return mSecID;
	}

	@Override
	public String getName() {
		return mName;
	}

	@Override
	public String getSectionKey() {
		return "db.section." + mName;
	}

	@Override
	public int toDmg() {
		return mSecID << 8;
	}
}
