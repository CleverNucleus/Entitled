package clevernucleus.entitled.common.init.network;

import java.util.function.Supplier;

import javax.annotation.Nonnull;

import clevernucleus.entitled.common.Entitled;
import clevernucleus.entitled.common.util.Util;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

/**
 * Network packet responsible for syncing server entity display data to the client.
 */
public class SyncMapPacket {
	private CompoundNBT tag;
	
	public SyncMapPacket() {}
	
	/**
	 * Constructor.
	 * @param par0 Compound tag to send.
	 */
	public SyncMapPacket(final @Nonnull CompoundNBT par0) {
		this.tag = par0;
	}
	
	/**
	 * Receives a packet and writes the contents to the input buffer.
	 * @param par0 Input packet.
	 * @param par1 Input buffer
	 */
	public static void encode(SyncMapPacket par0, PacketBuffer par1) {
		par1.writeCompoundTag(par0.tag);
	}
	
	/**
	 * Receives an input buffer and retrieves the contents to write to a new packet instance.
	 * @param par0 Input buffer.
	 * @return A new Packet instance.
	 */
	public static SyncMapPacket decode(PacketBuffer par0) {
		return new SyncMapPacket(par0.readCompoundTag());
	}
	
	/**
	 * Handles the tag capability syncing to the client.
	 * @param par0 Packet input.
	 * @param par1 Network context.
	 */
	public static void handle(SyncMapPacket par0, Supplier<NetworkEvent.Context> par1) {
		if(par1.get().getDirection().getReceptionSide().isClient()) {
			par1.get().enqueueWork(() -> {
				if(par0.tag == null) return;
				
				Entitled.PROXY.setMap(Util.fromTagList(par0.tag));
			});
			
			par1.get().setPacketHandled(true);
		}
	}
}
