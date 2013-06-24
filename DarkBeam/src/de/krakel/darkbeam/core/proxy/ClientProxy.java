/**
 * Dark Beam
 * ClientProxy.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.RenderingRegistry;

import de.krakel.darkbeam.client.renderer.BlockStageRender;
import de.krakel.darkbeam.client.renderer.DrawBlockHighlightHandler;

@SuppressWarnings( "unused")
public class ClientProxy extends CommonProxy {
	private static void regeisterRendering() {
		RenderingRegistry.registerBlockHandler( new BlockStageRender());
		MinecraftForge.EVENT_BUS.register( new DrawBlockHighlightHandler());
	}

	private void handleTileEntityPacket( int x, int y, int z, ForgeDirection orientation, byte state, String customName) {
	}

	private void handleTileWithItemPacket( int x, int y, int z, ForgeDirection orientation, byte state, String customName, int itemID, int metaData, int stackSize, int color) {
	}

	@Override
	public void init() {
		regeisterRendering();
	}

	@Override
	public void preInit() {
	}

	private void registerDrawBlockHighlightHandler() {
	}

	private void registerKeyBindingHandler() {
	}

	private void registerRenderTickHandler() {
	}

	private void registerSoundHandler() {
	}

	private void registerTileEntities() {
	}

	private void sendRequestEventPacket( byte eventType, int originX, int originY, int originZ, byte sideHit, byte rangeX, byte rangeY, byte rangeZ, String data) {
	}

	private void setKeyBinding( String name, int value) {
	}

	private void transmuteBlock( ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int sideHit) {
	}
}
