/**
 * Dark Beam
 * PacketTypeHandler.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.network.INetworkManager;
import cpw.mods.fml.common.network.Player;

import de.krakel.darkbeam.core.helper.LogHelper;

public enum PacketTypeHandler {
//    KEY(PacketKeyPressed.class), 
//    TILE(PacketTileUpdate.class),
//    REQUEST_EVENT(PacketRequestEvent.class),
//    SPAWN_PARTICLE(PacketSpawnParticle.class), 
//    SOUND_EVENT(PacketSoundEvent.class), 
//    ITEM_UPDATE(PacketItemUpdate.class), 
//    TILE_WITH_ITEM(PacketTileWithItemUpdate.class)
	UNKOWN( Unknown.class);
	private Class<? extends PacketDB> mCls;

	private PacketTypeHandler( Class<? extends PacketDB> cls) {
		mCls = cls;
	}

	public static PacketDB buildPacket( byte[] data) {
		ByteArrayInputStream bis = new ByteArrayInputStream( data);
		int sel = bis.read();
		DataInputStream dis = new DataInputStream( bis);
		try {
			PacketDB packet = toPacket( sel).mCls.newInstance();
			packet.readPopulate( dis);
			return packet;
		}
		catch (Exception ex) {
			LogHelper.severe( ex, "exception on packte handler");
		}
		return new Unknown();
	}

	public static PacketTypeHandler toPacket( int sel) {
		try {
			return values()[sel];
		}
		catch (IndexOutOfBoundsException ex) {
			return UNKOWN;
		}
	}

	private static class Unknown extends PacketDB {
		@Override
		public void execute( INetworkManager manager, Player player) {
			LogHelper.severe( "unknown packte handler");
		}

		@Override
		public void readPopulate( DataInputStream dis) {
			LogHelper.severe( "unknown packte handler");
		}
	}
}
