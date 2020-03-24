package clevernucleus.entitled.common.network;

import clevernucleus.entitled.common.util.Util;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandle {
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Util.MODID);
	
	public static void init() {
		INSTANCE.registerMessage(PacketSyncTag.class, PacketSyncTag.class, 0, Side.CLIENT);
		INSTANCE.registerMessage(PacketSyncMap.class, PacketSyncMap.class, 1, Side.CLIENT);
	}
}
