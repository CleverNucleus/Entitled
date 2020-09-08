package clevernucleus.entitled.server;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import clevernucleus.entitled.common.util.Display;
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
	
	@Override
	public Map<UUID, Display> getMap() {
		return new HashMap<UUID, Display>();
	}

	@Override
	public void setMap(Map<UUID, Display> par0) {}
}
