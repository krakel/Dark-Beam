/**
 * Dark Beam
 * Mask.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;

import de.krakel.darkbeam.client.renderer.IItemRenderer;

public class Mask {
	public final String mName;
	public final int mMaskID;
	private IItemRenderer mRenderer;

	Mask( int maskID, String name, IItemRenderer renderer) {
		mMaskID = maskID;
		mName = name;
		mRenderer = renderer;
	}

	public String getUnlocalizedName( Block blk) {
		return mName + "." + blk.getUnlocalizedName2();
	}

	public void renderItem( Block blk, int meta, RenderBlocks rndr) {
		mRenderer.render( blk, meta, rndr);
	}
}
