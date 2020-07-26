package clevernucleus.entitled.server;

import clevernucleus.entitled.common.util.IProxy;
import net.minecraft.entity.player.PlayerEntity;

/**
 * Server implementation for the proxy system.
 */
public class ServerProxy implements IProxy {
	
	@Override
	public PlayerEntity clientPlayer() {
		return null;
	}
}
