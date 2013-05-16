package de.grayal.darkbeam;

import java.io.File;
import java.util.logging.Level;

import net.minecraft.creativetab.CreativeTabs;
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

import de.grayal.darkbeam.core.block.ModBlocks;
import de.grayal.darkbeam.core.handler.ConfigurationHandler;
import de.grayal.darkbeam.core.handler.LocalizationHandler;
import de.grayal.darkbeam.core.helper.LogHelper;
import de.grayal.darkbeam.core.proxy.CommonProxy;
import de.grayal.darkbeam.lib.FReferences;
import de.grayal.darkbeam.lib.FStrings;
import de.grayal.darkbeam.network.PacketHandler;

@Mod(
	modid = FReferences.MOD_ID,
	name = FReferences.MOD_NAME,
	version = FReferences.VERSION,
	dependencies = FReferences.DEPENDENCIES,
	certificateFingerprint = FReferences.FINGERPRINT)
@NetworkMod(
	channels = {
		FReferences.MOD_CHANNEL
	},
	clientSideRequired = true,
	serverSideRequired = false,
	packetHandler = PacketHandler.class)
public class DarkBeam {
	@Instance( FReferences.MOD_ID)
	public static DarkBeam sInstance;

	@SidedProxy(
		clientSide = FReferences.CLASS_CLIENT_PROXY,
		serverSide = FReferences.CLASS_SERVER_PROXY)
	public static CommonProxy sProxy;

	public static CreativeTabs sTabDB = new CreativeTabDB( CreativeTabs.getNextID(), FReferences.MOD_ID);

	@Init
	public void init( FMLInitializationEvent event) {
		NetworkRegistry.instance().registerGuiHandler( sInstance, sProxy);
//		MinecraftForge.EVENT_BUS.register( new PlayerDestroyItemHandler());
//		MinecraftForge.EVENT_BUS.register( new ItemEventHandler());
//		MinecraftForge.EVENT_BUS.register( new EntityLivingHandler());
//		MinecraftForge.EVENT_BUS.register( new ActionRequestHandler());
//		MinecraftForge.EVENT_BUS.register( new WorldTransmutationHandler());
//		GameRegistry.registerCraftingHandler( new CraftingHandler());
		sProxy.registerDrawBlockHighlightHandler();
		sProxy.registerTileEntities();
		sProxy.initRenderingAndTextures();
//		RecipesTransmutationStone.init();
//		CraftingManager.getInstance().getRecipeList().add( new RecipesAlchemicalBagDyes());
//		GameRegistry.registerFuelHandler( new FuelHandler());
	}

	@FingerprintWarning
	public void invalidFingerprint( FMLFingerprintViolationEvent event) {
		LogHelper.log( Level.SEVERE, FStrings.INVALID_FINGERPRINT);
	}

	@PostInit
	public void postInit( FMLPostInitializationEvent event) {
//        AddonHandler.init();
	}

	@PreInit
	public void preInit( FMLPreInitializationEvent event) {
		LogHelper.init();
		LocalizationHandler.load();
		File confDir = new File( event.getModConfigurationDirectory(), FReferences.MOD_CHANNEL);
		File confFile = new File( confDir, FReferences.MOD_ID + ".cfg");
		ConfigurationHandler.init( confFile);
//		VersionHelper.execute();
//        TickRegistry.registerTickHandler(new VersionCheckTickHandler(), Side.CLIENT);
		sProxy.registerRenderTickHandler();
		sProxy.registerKeyBindingHandler();
		sProxy.registerSoundHandler();
		ModBlocks.init();
//        ModItems.init();
	}

	@ServerStarting
	public void serverStarting( FMLServerStartingEvent event) {
//		CommandHandler.initCommands( event);
	}
}
