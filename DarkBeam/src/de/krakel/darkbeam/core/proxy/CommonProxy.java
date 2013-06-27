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

import com.sun.istack.internal.Nullable;

import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler {
	@Override
	@Nullable
	public Object getClientGuiElement( int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	@Nullable
	public Object getServerGuiElement( int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	public void init() {
	}

	public void preInit() {
	}
}
