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
	final String mName;
	public final int mMaskID;
	public IItemRenderer mRenderer;

	Mask( int maskID, String name, IItemRenderer renderer) {
		mMaskID = maskID;
		mName = name;
		mRenderer = renderer;
	}

	public String getUnlocalizedName() {
		return "db.mask." + mName;
	}

	public void renderInventoryItem( RenderBlocks rndr, Block blk, int meta) {
		mRenderer.setInventoryBounds( rndr);
		mRenderer.renderItem( rndr, blk, meta);
	}

	public void renderUnitSide( RenderBlocks rndr, int side, Block blk, int meta, int x, int y, int z) {
		mRenderer.setBounds( rndr, side);
		mRenderer.renderSide( rndr, side, blk, meta, x, y, z);
	}

	public int toDmg() {
		return mMaskID << 8;
	}
}
