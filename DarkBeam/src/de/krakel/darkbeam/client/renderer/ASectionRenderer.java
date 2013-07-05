/**
 * Dark Beam
 * ASectionRenderer.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;

import org.lwjgl.opengl.GL11;

import de.krakel.darkbeam.core.AreaType;
import de.krakel.darkbeam.core.IDirection;
import de.krakel.darkbeam.tile.TileStage;

public abstract class ASectionRenderer {
	protected float mThickness;
	protected float mSize;

	protected ASectionRenderer( int base) {
		mThickness = base / 16F;
		mSize = mThickness + mThickness;
	}

	protected static void renderStandard( RenderBlocks rndrBlk, Block blk, int side, int meta, int x, int y, int z) {
		rndrBlk.setRenderBoundsFromBlock( blk);
		Icon icon = rndrBlk.getBlockIconFromSideAndMetadata( blk, side, meta);
		rndrBlk.setOverrideBlockTexture( icon);
		rndrBlk.renderStandardBlock( blk, x, y, z);
		rndrBlk.clearOverrideBlockTexture();
	}

	protected static void renderStandardInventory( RenderBlocks rndrBlk, Block blk, int meta) {
		GL11.glTranslatef( -0.5F, -0.5F, -0.5F);
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.setNormal( 0F, -1F, 0F);
		rndrBlk.renderFaceYNeg( blk, 0D, 0D, 0D, rndrBlk.getBlockIconFromSideAndMetadata( blk, IDirection.DIR_UP, meta));
		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal( 0F, 1F, 0F);
		rndrBlk.renderFaceYPos( blk, 0D, 0D, 0D, rndrBlk.getBlockIconFromSideAndMetadata( blk, IDirection.DIR_DOWN, meta));
		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal( 0F, 0F, -1F);
		rndrBlk.renderFaceZNeg( blk, 0D, 0D, 0D, rndrBlk.getBlockIconFromSideAndMetadata( blk, IDirection.DIR_NORTH, meta));
		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal( 0F, 0F, 1F);
		rndrBlk.renderFaceZPos( blk, 0D, 0D, 0D, rndrBlk.getBlockIconFromSideAndMetadata( blk, IDirection.DIR_SOUTH, meta));
		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal( -1F, 0F, 0F);
		rndrBlk.renderFaceXNeg( blk, 0D, 0D, 0D, rndrBlk.getBlockIconFromSideAndMetadata( blk, IDirection.DIR_WEST, meta));
		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal( 1F, 0F, 0F);
		rndrBlk.renderFaceXPos( blk, 0D, 0D, 0D, rndrBlk.getBlockIconFromSideAndMetadata( blk, IDirection.DIR_EAST, meta));
		tess.draw();
		GL11.glTranslatef( 0.5F, 0.5F, 0.5F);
	}

	public abstract void renderItem( RenderBlocks rndrBlk, Block blk, int meta);

	public abstract void renderSide( RenderBlocks rndrBlk, AreaType area, Block blk, int x, int y, int z, TileStage tile);

	public abstract void setSectionBounds( AreaType area, Block blk, TileStage tile);
}
