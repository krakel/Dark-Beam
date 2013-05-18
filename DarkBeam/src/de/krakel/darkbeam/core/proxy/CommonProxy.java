/**
 * Dark Beam
 * CommonProxy.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler {
	@Override
	public Object getClientGuiElement( int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	public Object getServerGuiElement( int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	public void initRenderingAndTextures() {
	}

	public void registerDrawBlockHighlightHandler() {
	}

	public void registerKeyBindingHandler() {
	}

	public void registerRenderTickHandler() {
	}

	public void registerSoundHandler() {
	}

	public void registerTileEntities() {
//		GameRegistry.registerTileEntity( TileCalcinator.class, Strings.TE_CALCINATOR_NAME);
//		GameRegistry.registerTileEntity( TileAludel.class, Strings.TE_ALUDEL_NAME);
//		GameRegistry.registerTileEntity( TileAlchemicalChest.class, Strings.TE_ALCHEMICAL_CHEST_NAME);
//		GameRegistry.registerTileEntity( TileGlassBell.class, Strings.TE_GLASS_BELL_NAME);
	}
}
