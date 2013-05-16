package de.krakel.darkbeam.core.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class ClientProxy extends CommonProxy {
	public void handleTileEntityPacket( int x, int y, int z, ForgeDirection orientation, byte state, String customName) {
//		TileEntity tileEntity = FMLClientHandler.instance().getClient().theWorld.getBlockTileEntity( x, y, z);
//		if (tileEntity != null) {
//			if (tileEntity instanceof TileEE) {
//				((TileEE) tileEntity).setOrientation( orientation);
//				((TileEE) tileEntity).setState( state);
//				((TileEE) tileEntity).setCustomName( customName);
//			}
//		}
	}

	public void handleTileWithItemPacket( int x, int y, int z, ForgeDirection orientation, byte state, String customName, int itemID, int metaData, int stackSize, int color) {
//		TileEntity tileEntity = FMLClientHandler.instance().getClient().theWorld.getBlockTileEntity( x, y, z);
//		handleTileEntityPacket( x, y, z, orientation, state, customName);
//		if (tileEntity != null) {
//			if (tileEntity instanceof TileGlassBell) {
//				ItemStack itemStack = new ItemStack( itemID, stackSize, metaData);
//				ItemHelper.setColor( itemStack, color);
//				((TileGlassBell) tileEntity).setInventorySlotContents( 0, itemStack);
//			}
//		}
	}

	@Override
	public void initRenderingAndTextures() {
//		RenderIds.calcinatorRenderId = RenderingRegistry.getNextAvailableRenderId();
//		RenderIds.aludelRenderId = RenderingRegistry.getNextAvailableRenderId();
//		RenderIds.alchemicalChestRenderId = RenderingRegistry.getNextAvailableRenderId();
//		RenderIds.glassBellId = RenderingRegistry.getNextAvailableRenderId();
//		MinecraftForgeClient.registerItemRenderer( BlockIds.CALCINATOR, new ItemCalcinatorRenderer());
//		MinecraftForgeClient.registerItemRenderer( BlockIds.ALUDEL_BASE, new ItemAludelRenderer());
//		MinecraftForgeClient.registerItemRenderer( BlockIds.ALCHEMICAL_CHEST, new ItemAlchemicalChestRenderer());
//		MinecraftForgeClient.registerItemRenderer( BlockIds.GLASS_BELL, new ItemGlassBellRenderer());
	}

	@Override
	public void registerDrawBlockHighlightHandler() {
//		MinecraftForge.EVENT_BUS.register( new DrawBlockHighlightHandler());
	}

	@Override
	public void registerKeyBindingHandler() {
//		KeyBindingRegistry.registerKeyBinding( new KeyBindingHandler());
	}

	@Override
	public void registerRenderTickHandler() {
//		TickRegistry.registerTickHandler( new TransmutationTargetOverlayHandler(), Side.CLIENT);
	}

	@Override
	public void registerSoundHandler() {
//		MinecraftForge.EVENT_BUS.register( new SoundHandler());
	}

	@Override
	public void registerTileEntities() {
		super.registerTileEntities();
//		ClientRegistry.bindTileEntitySpecialRenderer( TileCalcinator.class, new TileEntityCalcinatorRenderer());
//		ClientRegistry.bindTileEntitySpecialRenderer( TileAludel.class, new TileEntityAludelRenderer());
//		ClientRegistry.bindTileEntitySpecialRenderer( TileAlchemicalChest.class, new TileEntityAlchemicalChestRenderer());
//		ClientRegistry.bindTileEntitySpecialRenderer( TileGlassBell.class, new TileEntityGlassBellRenderer());
	}

	public void sendRequestEventPacket( byte eventType, int originX, int originY, int originZ, byte sideHit, byte rangeX, byte rangeY, byte rangeZ, String data) {
//		PacketDispatcher.sendPacketToServer( PacketTypeHandler.populatePacket( new PacketRequestEvent( eventType, originX, originY, originZ, sideHit, rangeX, rangeY, rangeZ, data)));
	}

	public void setKeyBinding( String name, int value) {
//		KeyBindingHelper.addKeyBinding( name, value);
//		KeyBindingHelper.addIsRepeating( false);
	}

	public void transmuteBlock( ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int sideHit) {
//		if (TransmutationHelper.targetBlockStack != null) {
//			if (itemStack != null) {
//				int pnX = 1;
//				int pnY = 1;
//				int pnZ = 1;
//				if (itemStack.getItem() instanceof IChargeable) {
//					int charge = ((IChargeable) itemStack.getItem()).getCharge( itemStack) * 2;
//					switch (ForgeDirection.getOrientation( sideHit)) {
//						case UP: {
//							pnX = 1 + charge;
//							pnZ = 1 + charge;
//							break;
//						}
//						case DOWN: {
//							pnX = 1 + charge;
//							pnZ = 1 + charge;
//							break;
//						}
//						case NORTH: {
//							pnX = 1 + charge;
//							pnY = 1 + charge;
//							break;
//						}
//						case SOUTH: {
//							pnX = 1 + charge;
//							pnY = 1 + charge;
//							break;
//						}
//						case EAST: {
//							pnY = 1 + charge;
//							pnZ = 1 + charge;
//							break;
//						}
//						case WEST: {
//							pnY = 1 + charge;
//							pnZ = 1 + charge;
//							break;
//						}
//						case UNKNOWN: {
//							pnX = 0;
//							pnY = 0;
//							pnZ = 0;
//							break;
//						}
//						default:
//							break;
//					}
//				}
//				EquivalentExchange3.proxy.sendRequestEventPacket( ActionTypes.TRANSMUTATION, x, y, z, (byte) sideHit, (byte) pnX, (byte) pnY, (byte) pnZ, TransmutationHelper.formatTargetBlockInfo( TransmutationHelper.targetBlockStack));
//			}
//		}
	}
}
