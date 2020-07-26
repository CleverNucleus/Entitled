package clevernucleus.entitled.client;

import clevernucleus.entitled.common.util.IProxy;
import net.minecraft.entity.player.PlayerEntity;

/**
 * Client implementation for the proxy system.
 */
public class ClientProxy implements IProxy {
	
	@Override
	public PlayerEntity clientPlayer() {
		return net.minecraft.client.Minecraft.getInstance().player;
	}
}
