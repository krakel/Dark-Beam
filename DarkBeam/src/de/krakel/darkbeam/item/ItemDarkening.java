/**
 * Dark Beam
 * ItemDarkening.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.krakel.darkbeam.DarkBeam;
import de.krakel.darkbeam.lib.FReferences;
import de.krakel.darkbeam.lib.FStrings;
import de.krakel.darkbeam.lib.FTextures;

public class ItemDarkening extends Item {
	public ItemDarkening( int id) {
		super( id - FReferences.SHIFTED_ID_RANGE);
		setUnlocalizedName( FStrings.ITEM_DARKENING_NAME);
		setCreativeTab( DarkBeam.sMainTab);
		setMaxStackSize( 64);
		setNoRepair();
	}

	@Override
	@SideOnly( Side.CLIENT)
	public void registerIcons( IconRegister reg) {
		itemIcon = reg.registerIcon( FTextures.PATH_DEFAULT + FTextures.getItemName( this));
	}
}
