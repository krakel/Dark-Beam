/**
 * Dark Beam
 * ItemRedWire.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core.block.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.krakel.darkbeam.lib.FStrings;
import de.krakel.darkbeam.lib.FTextures;

// TODO remove
public class ItemBlockRedWire extends ItemBlock {
	private Icon mTexture;

	public ItemBlockRedWire( int id) {
		super( id);
	}

	@Override
	public Icon getIconFromDamage( int meta) {
		return mTexture;
	}

	@Override
	@SideOnly( Side.CLIENT)
	public void registerIcons( IconRegister reg) {
		mTexture = reg.registerIcon( FTextures.PATH_DEFAULT + FStrings.BLOCK_RED_WIRE_NAME);
	}
}
