/**
 * Dark Beam
 * DarkBeam.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.testing;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

import de.krakel.darkbeam.testing.block.ModBlocks;
import de.krakel.darkbeam.testing.core.handler.ConfigurationHandler;
import de.krakel.darkbeam.testing.core.handler.LocalizationHandler;
import de.krakel.darkbeam.testing.core.helper.LogHelper;
import de.krakel.darkbeam.testing.core.proxy.CommonProxy;
import de.krakel.darkbeam.testing.creativetab.ModTabs;
import de.krakel.darkbeam.testing.item.ModItems;
import de.krakel.darkbeam.testing.lib.References;
import de.krakel.darkbeam.testing.lib.Strings;
import de.krakel.darkbeam.testing.network.PacketHandler;
import de.krakel.darkbeam.testing.tile.TestTileSimple;
import de.krakel.darkbeam.testing.tile.TileRedWire;

@Mod(
	modid = References.MOD_ID,
	name = References.MOD_NAME,
	version = References.VERSION,
	dependencies = References.DEPENDENCIES,
	certificateFingerprint = References.FINGERPRINT)
@NetworkMod( channels = {
	References.MOD_CHANNEL
}, clientSideRequired = true, serverSideRequired = false, packetHandler = PacketHandler.class)
public class DarkBeamTesting {
	public static final Material MAT_DARK = new Material( MapColor.woodColor);
	@Instance( References.MOD_ID)
	public static DarkBeamTesting sInstance;
	@SidedProxy( clientSide = References.CLASS_CLIENT_PROXY, serverSide = References.CLASS_SERVER_PROXY)
	public static CommonProxy sProxy;

	@Init
	public void init( FMLInitializationEvent event) {
		NetworkRegistry.instance().registerGuiHandler( sInstance, sProxy);
//		MinecraftForge.EVENT_BUS.register( new PlayerDestroyItemHandler());
//		MinecraftForge.EVENT_BUS.register( new ItemEventHandler());
//		MinecraftForge.EVENT_BUS.register( new EntityLivingHandler());
//		MinecraftForge.EVENT_BUS.register( new ActionRequestHandler());
//		MinecraftForge.EVENT_BUS.register( new WorldTransmutationHandler());
//		GameRegistry.registerCraftingHandler( new CraftingHandler());
		GameRegistry.registerTileEntity( TileRedWire.class, Strings.TE_REDWIRE_NAME);
		GameRegistry.registerTileEntity( TestTileSimple.class, Strings.TE_SIMPLE_NAME);
		sProxy.init();
//		RecipesTransmutationStone.init();
//		CraftingManager.getInstance().getRecipeList().add( new RecipesAlchemicalBagDyes());
//		GameRegistry.registerFuelHandler( new FuelHandler());
	}

	@PostInit
	public void postInit( FMLPostInitializationEvent event) {
//        AddonHandler.init();
	}

	@PreInit
	public void preInit( FMLPreInitializationEvent event) {
		LogHelper.preInit();
		LocalizationHandler.preInit();
		ConfigurationHandler.preInit( event);
		sProxy.preInit();
		ModTabs.preInit();
		ModItems.preInit();
		ModBlocks.preInit();
	}

	@ServerStarting
	public void serverStarting( FMLServerStartingEvent event) {
//		CommandHandler.initCommands( event);
	}
}
