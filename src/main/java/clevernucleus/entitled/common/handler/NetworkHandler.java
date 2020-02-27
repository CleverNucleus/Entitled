package clevernucleus.entitled.common.handler;

import clevernucleus.entitled.common.IConstants;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(IConstants.MODID);
	
	public static void init() {
		INSTANCE.registerMessage(MessageSync.class, MessageSync.class, 0, Side.CLIENT);
		INSTANCE.registerMessage(MessageSyncTag.class, MessageSyncTag.class, 1, Side.CLIENT);
	}
}
