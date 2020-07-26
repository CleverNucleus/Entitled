package clevernucleus.entitled.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
	
	/** Logger instance for tracking issues. */
	public static final Logger LOGGER = LogManager.getLogger();
	
	/** Proxy instance to get side specific methods. */
	public static final IProxy PROXY = DistExecutor.runForDist(() -> clevernucleus.entitled.client.ClientProxy::new, () -> clevernucleus.entitled.server.ServerProxy::new);
	
	public Entitled() {}
}
