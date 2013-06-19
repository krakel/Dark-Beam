/**
 * Dark Beam DrawBlockHighlightHandler.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.testing.client.renderer;

import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;

import org.lwjgl.opengl.GL11;

import de.krakel.darkbeam.testing.core.DarkLib;

public class DrawBlockHighlightHandler {
//	@ForgeSubscribe
	public void onHighlightEvent( DrawBlockHighlightEvent event) {
		if (event.currentItem != null /*
									 * && event.currentItem ==
									 * ItemIds.sItemDarkeningID
									 */
			&& event.target.typeOfHit == EnumMovingObjectType.TILE) {
			onHighlightEvent( event.context, event.player, event.target, event.subID, event.currentItem, event.partialTicks);
		}
	}

	@SuppressWarnings( "static-method")
	private void onHighlightEvent( RenderGlobal context, EntityPlayer player, MovingObjectPosition target, int subID, ItemStack stack, float ticks) {
		World world = player.worldObj;
		int id = world.getBlockId( target.blockX, target.blockY, target.blockZ);
		if (id > 0) {
			GL11.glEnable( GL11.GL_BLEND);
			GL11.glDisable( GL11.GL_TEXTURE_2D);
			GL11.glBlendFunc( GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glColor4f( 0F, 0F, 0F, 0.8F);
			GL11.glLineWidth( 3F);
			GL11.glDepthMask( false);
			double minX = 0D;
			double maxX = 1D;
			double posY = -DarkLib.BOX_BORDER_HEIGHT;
			double minZ = 0D;
			double maxZ = 1D;
			Tessellator tess = Tessellator.instance;
			tess.startDrawing( 3);
			tess.addVertex( maxX, posY, minZ);
			tess.addVertex( minX, posY, minZ);
			tess.addVertex( minX, posY, maxZ);
			tess.addVertex( maxX, posY, maxZ);
			tess.draw();
			GL11.glDepthMask( true);
			GL11.glEnable( GL11.GL_TEXTURE_2D);
			GL11.glDisable( GL11.GL_BLEND);
		}
	}
}
