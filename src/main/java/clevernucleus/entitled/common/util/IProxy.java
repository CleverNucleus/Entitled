package clevernucleus.entitled.common.util;

import java.util.Map;
import java.util.UUID;

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
	
	/**
	 * Gets the player map for displays.
	 * @return If called on the client, returns the map instance of all players on the server; otherwise returns an empty map.
	 */
	Map<UUID, Display> getMap();
	
	/**
	 * Sets the player map for displays.
	 * @param par0 If called on the client, sets the map instance to this input. Else, does nothing.
	 */
	void setMap(final Map<UUID, Display> par0);
}
