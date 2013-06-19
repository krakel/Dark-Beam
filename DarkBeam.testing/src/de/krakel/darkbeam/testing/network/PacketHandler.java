/**
 * Dark Beam
 * PacketHandler.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.testing.network;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler {
	@Override
	public void onPacketData( INetworkManager manager, Packet250CustomPayload packet, Player player) {
		PacketTesting pack = PacketTypeHandler.buildPacket( packet.data);
		pack.execute( manager, player);
	}
}
