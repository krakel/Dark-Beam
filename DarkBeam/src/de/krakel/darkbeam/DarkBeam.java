/**
 * Dark Beam
 * DarkBeam.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.FingerprintWarning;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLFingerprintViolationEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

import de.krakel.darkbeam.block.ModBlocks;
import de.krakel.darkbeam.core.InsulateLib;
import de.krakel.darkbeam.core.SectionLib;
import de.krakel.darkbeam.core.MaterialLib;
import de.krakel.darkbeam.core.handler.ConfigurationHandler;
import de.krakel.darkbeam.core.handler.LocalizationHandler;
import de.krakel.darkbeam.core.handler.VersionCheckTickHandler;
import de.krakel.darkbeam.core.helper.LogHelper;
import de.krakel.darkbeam.core.helper.VersionHelper;
import de.krakel.darkbeam.core.proxy.CommonProxy;
import de.krakel.darkbeam.creativetab.ModTabs;
import de.krakel.darkbeam.item.ModItems;
import de.krakel.darkbeam.lib.References;
import de.krakel.darkbeam.lib.Strings;
import de.krakel.darkbeam.network.PacketHandler;
import de.krakel.darkbeam.tile.TileSection;

@Mod( modid = References.MOD_ID, name = References.MOD_NAME, version = References.VERSION,
	dependencies = References.DEPENDENCIES, certificateFingerprint = References.FINGERPRINT)
@NetworkMod( channels = {
	References.MOD_CHANNEL
}, clientSideRequired = true, serverSideRequired = false, packetHandler = PacketHandler.class)
public class DarkBeam {
	public static final Material MAT_DARK = new Material( MapColor.woodColor);
	@Instance( References.MOD_ID)
	public static DarkBeam sInstance;
	@SidedProxy( clientSide = References.CLASS_CLIENT_PROXY, serverSide = References.CLASS_SERVER_PROXY)
	public static CommonProxy sProxy;

	@Init
	public void init( FMLInitializationEvent event) {
		NetworkRegistry.instance().registerGuiHandler( sInstance, sProxy);
		GameRegistry.registerTileEntity( TileSection.class, Strings.TE_SECTION_NAME);
		sProxy.init();
		SectionLib.init();
		MaterialLib.init();
		InsulateLib.init();
	}

	@FingerprintWarning
	public void invalidFingerprint( FMLFingerprintViolationEvent event) {
		LogHelper.severe( Strings.INVALID_FINGERPRINT);
	}

	@PostInit
	public void postInit( FMLPostInitializationEvent event) {
	}

	@PreInit
	public void preInit( FMLPreInitializationEvent event) {
		LogHelper.preInit();
		LocalizationHandler.preInit();
		ConfigurationHandler.preInit( event);
		VersionHelper.execute();
		TickRegistry.registerTickHandler( new VersionCheckTickHandler(), Side.CLIENT);
		sProxy.preInit();
		ModTabs.preInit();
		ModItems.preInit();
		ModBlocks.preInit();
	}

	@ServerStarting
	public void serverStarting( FMLServerStartingEvent event) {
	}
}
