/**
 * Dark Beam
 * ItemDarkening.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.testing.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.krakel.darkbeam.testing.lib.References;
import de.krakel.darkbeam.testing.lib.Textures;

public class ItemDarkening extends Item {
	public ItemDarkening( int id) {
		super( id - References.SHIFTED_ID_RANGE);
		setMaxStackSize( 64);
		setNoRepair();
	}

	@Override
	@SideOnly( Side.CLIENT)
	public void registerIcons( IconRegister reg) {
		itemIcon = reg.registerIcon( Textures.PATH_DEFAULT + Textures.getItemName( this));
	}
}
