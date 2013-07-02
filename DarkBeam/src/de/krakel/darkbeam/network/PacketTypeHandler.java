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

public enum PacketTypeHandler {
//    KEY(PacketKeyPressed.class), 
//    TILE(PacketTileUpdate.class),
//    REQUEST_EVENT(PacketRequestEvent.class),
//    SPAWN_PARTICLE(PacketSpawnParticle.class), 
//    SOUND_EVENT(PacketSoundEvent.class), 
//    ITEM_UPDATE(PacketItemUpdate.class), 
//    TILE_WITH_ITEM(PacketTileWithItemUpdate.class)
	;
	private Class<? extends PacketDB> mCls;

	private PacketTypeHandler( Class<? extends PacketDB> cls) {
		mCls = cls;
	}

	public static PacketDB buildPacket( byte[] data) {
		ByteArrayInputStream bis = new ByteArrayInputStream( data);
		int sel = bis.read();
		DataInputStream dis = new DataInputStream( bis);
		try {
			PacketDB packet = values()[sel].mCls.newInstance();
			packet.readPopulate( dis);
			return packet;
		}
		catch (Exception ex) {
			ex.printStackTrace( System.err);
		}
		return null;
	}
}
