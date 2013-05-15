package de.grayal.darkbeam;

import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.grayal.darkbeam.lib.FItemIds;

public class CreativeTabDB extends CreativeTabs {
	public CreativeTabDB( int id, String name) {
		super( id, name);
	}

	@Override
	@SideOnly( Side.CLIENT)
	public int getTabIconItemIndex() {
		return FItemIds.sMinimumShard;
	}
}
