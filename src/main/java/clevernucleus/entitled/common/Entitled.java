package clevernucleus.entitled.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;

/**
 * Mod init class; holds the modid.
 */
@Mod(Entitled.MODID)
public class Entitled {
	
	/** The modid used to identify entitled. */
	public static final String MODID = "entitled";
	public static final Logger LOGGER = LogManager.getLogger();
	
	public Entitled() {}
}
