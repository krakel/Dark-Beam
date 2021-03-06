/**
 * Dark Beam
 * VersionCheckTickHandler.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.core.handler;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

import de.krakel.darkbeam.core.helper.VersionHelper;
import de.krakel.darkbeam.lib.Configs;
import de.krakel.darkbeam.lib.References;

public class VersionCheckTickHandler implements ITickHandler {
	private static boolean sInitialized = false;

	@Override
	public String getLabel() {
		return References.MOD_NAME + ": " + getClass().getSimpleName();
	}

	@Override
	public void tickEnd( EnumSet<TickType> type, Object... tickData) {
		if (Configs.sDisplayVersionResult && !sInitialized) {
			Minecraft client = FMLClientHandler.instance().getClient();
			for (TickType tt : type) {
				if (tt == TickType.CLIENT && client.currentScreen == null && VersionHelper.isInitialized()) {
					sInitialized = true;
					if (VersionHelper.isOutdated()) {
						client.ingameGUI.getChatGUI().printChatMessage( VersionHelper.getMessageForClient());
						ConfigurationHandler.set( Configuration.CATEGORY_GENERAL, Configs.DISPLAY_VERSION_RESULT_NAME, Boolean.FALSE.toString());
					}
				}
			}
		}
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of( TickType.CLIENT);
	}

	@Override
	public void tickStart( EnumSet<TickType> type, Object... tickData) {
	}
}
