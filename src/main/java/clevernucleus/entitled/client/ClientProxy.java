package clevernucleus.entitled.client;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import clevernucleus.entitled.common.util.Display;
import clevernucleus.entitled.common.util.IProxy;
import net.minecraft.entity.player.PlayerEntity;

/**
 * Client implementation for the proxy system.
 */
public class ClientProxy implements IProxy {
	
	/** Map that syncs global player stats from server to client. */
	private Map<UUID, Display> map = new HashMap<UUID, Display>();
	
	@Override
	public PlayerEntity clientPlayer() {
		return net.minecraft.client.Minecraft.getInstance().player;
	}
	
	@Override
	public Map<UUID, Display> getMap() {
		return this.map;
	}
	
	@Override
	public void setMap(Map<UUID, Display> par0) {
		this.map = par0;
	}
}
