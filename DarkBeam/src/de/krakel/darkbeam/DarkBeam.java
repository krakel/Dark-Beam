/**
 * Dark Beam
 * DarkBeam.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
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

import de.krakel.darkbeam.core.block.ModBlocks;
import de.krakel.darkbeam.core.handler.ConfigurationHandler;
import de.krakel.darkbeam.core.handler.LocalizationHandler;
import de.krakel.darkbeam.core.handler.VersionCheckTickHandler;
import de.krakel.darkbeam.core.helper.LogHelper;
import de.krakel.darkbeam.core.helper.VersionHelper;
import de.krakel.darkbeam.core.item.ModItems;
import de.krakel.darkbeam.core.proxy.CommonProxy;
import de.krakel.darkbeam.lib.FReferences;
import de.krakel.darkbeam.lib.FStrings;
import de.krakel.darkbeam.network.PacketHandler;
import de.krakel.darkbeam.tileentity.TileRedWire;

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
	public static CreativeTabs sMainTab = new MainTab( FStrings.TAB_MAIN);

	@SuppressWarnings( "unchecked")
	public static <T extends TileEntity> T getTileEntity( IBlockAccess world, int x, int y, int z) {
		try {
			return (T) world.getBlockTileEntity( x, y, z);
		}
		catch (ClassCastException ex) {
			LogHelper.severe( ex, "wrong tile entity");
		}
		return null;
	}

	@Init
	public void init( FMLInitializationEvent event) {
		NetworkRegistry.instance().registerGuiHandler( sInstance, sProxy);
//		MinecraftForge.EVENT_BUS.register( new PlayerDestroyItemHandler());
//		MinecraftForge.EVENT_BUS.register( new ItemEventHandler());
//		MinecraftForge.EVENT_BUS.register( new EntityLivingHandler());
//		MinecraftForge.EVENT_BUS.register( new ActionRequestHandler());
//		MinecraftForge.EVENT_BUS.register( new WorldTransmutationHandler());
//		GameRegistry.registerCraftingHandler( new CraftingHandler());
		GameRegistry.registerTileEntity( TileRedWire.class, FStrings.TE_REDWIRE_NAME);
		sProxy.init();
//		RecipesTransmutationStone.init();
//		CraftingManager.getInstance().getRecipeList().add( new RecipesAlchemicalBagDyes());
//		GameRegistry.registerFuelHandler( new FuelHandler());
	}

	@FingerprintWarning
	public void invalidFingerprint( FMLFingerprintViolationEvent event) {
		LogHelper.severe( FStrings.INVALID_FINGERPRINT);
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
		VersionHelper.execute();
		TickRegistry.registerTickHandler( new VersionCheckTickHandler(), Side.CLIENT);
		sProxy.preInit();
		ModBlocks.preInit();
		ModItems.preInit();
	}

	@ServerStarting
	public void serverStarting( FMLServerStartingEvent event) {
//		CommandHandler.initCommands( event);
	}
}
