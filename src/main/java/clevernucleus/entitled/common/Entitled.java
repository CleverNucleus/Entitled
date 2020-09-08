package clevernucleus.entitled.common;

import clevernucleus.entitled.common.util.IProxy;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

/**
 * Mod init class; holds the modid.
 */
@Mod(Entitled.MODID)
public class Entitled {
	
	/** The modid used to identify entitled. */
	public static final String MODID = "entitled";
	
	/** Proxy instance to get side specific methods. */
	public static final IProxy PROXY = DistExecutor.safeRunForDist(() -> clevernucleus.entitled.client.ClientProxy::new, () -> clevernucleus.entitled.server.ServerProxy::new);
	
	public Entitled() {}
}
