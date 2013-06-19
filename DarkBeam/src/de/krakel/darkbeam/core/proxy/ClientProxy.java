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

import de.krakel.darkbeam.client.renderer.BlockMaskingRender;
import de.krakel.darkbeam.client.renderer.DrawBlockHighlightHandler;

public class ClientProxy extends CommonProxy {
	@SuppressWarnings( "unused")
	private void handleTileEntityPacket( int x, int y, int z, ForgeDirection orientation, byte state, String customName) {
	}

	@SuppressWarnings( "unused")
	private void handleTileWithItemPacket( int x, int y, int z, ForgeDirection orientation, byte state, String customName, int itemID, int metaData, int stackSize, int color) {
	}

	@Override
	public void init() {
		regeisterRendering();
	}

	@Override
	public void preInit() {
	}

	@SuppressWarnings( "static-method")
	private void regeisterRendering() {
		RenderingRegistry.registerBlockHandler( new BlockMaskingRender());
		MinecraftForge.EVENT_BUS.register( new DrawBlockHighlightHandler());
	}

	@SuppressWarnings( "unused")
	private void registerDrawBlockHighlightHandler() {
	}

	@SuppressWarnings( "unused")
	private void registerKeyBindingHandler() {
	}

	@SuppressWarnings( "unused")
	private void registerRenderTickHandler() {
	}

	@SuppressWarnings( "unused")
	private void registerSoundHandler() {
	}

	@SuppressWarnings( "unused")
	private void registerTileEntities() {
	}

	@SuppressWarnings( "unused")
	private void sendRequestEventPacket( byte eventType, int originX, int originY, int originZ, byte sideHit, byte rangeX, byte rangeY, byte rangeZ, String data) {
	}

	@SuppressWarnings( "unused")
	private void setKeyBinding( String name, int value) {
	}

	@SuppressWarnings( "unused")
	private void transmuteBlock( ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int sideHit) {
	}
}
