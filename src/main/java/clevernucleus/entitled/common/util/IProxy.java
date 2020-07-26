package clevernucleus.entitled.common.util;

import net.minecraft.entity.player.PlayerEntity;

/**
 * Used to abstract over from client/server for networking.
 */
public interface IProxy {
	
	/**
	 * Gets a player entity instance.
	 * @return If called on the client, returns the client instance of the player entity; otherwise returns null.
	 */
	PlayerEntity clientPlayer();
}
