package de.krakel.darkbeam.core.handler;

import java.util.EnumSet;

import net.minecraftforge.common.Configuration;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import de.krakel.darkbeam.core.helper.VersionHelper;
import de.krakel.darkbeam.lib.FConfiguration;
import de.krakel.darkbeam.lib.FReferences;

public class VersionCheckTickHandler implements ITickHandler {
	private static boolean sInitialized = false;

	@Override
	public String getLabel() {
		return FReferences.MOD_NAME + ": " + getClass().getSimpleName();
	}

	@Override
	public void tickEnd( EnumSet<TickType> type, Object... tickData) {
		if (FConfiguration.sDisplayVersionResult && !sInitialized) {
			for (TickType tt : type) {
				if (tt == TickType.CLIENT && FMLClientHandler.instance().getClient().currentScreen == null && VersionHelper.isInitialized()) {
					sInitialized = true;
					if (VersionHelper.isOutdated()) {
						FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage( VersionHelper.getMessageForClient());
						ConfigurationHandler.set( Configuration.CATEGORY_GENERAL, FConfiguration.DISPLAY_VERSION_RESULT_NAME, Boolean.FALSE.toString());
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
