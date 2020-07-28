package clevernucleus.entitled.common.init.capability;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandlerModifiable;

/**
 * Tag capability interface.
 */
public interface ITag extends IItemHandlerModifiable, INBTSerializable<CompoundNBT> {
	
	/**
	 * @return The player's locked status.
	 */
	boolean locked();
	
	/**
	 * Checks to see if the slot has {@link ItemStack#EMPTY}.
	 * @return True if the slot contains {@link ItemStack#EMPTY}; otherwise false.
	 */
	boolean isEmpty();
	
	/**
	 * Sets the contained stack to {@link ItemStack#EMPTY}.
	 */
	void clear();
	
	/**
	 * The same as {@link ITag#clear}, but drops the nametag item.
	 */
	void drop(PlayerEntity par0);
	
	/**
	 * Syncs the tag capability of the input player from the server to the client. 
	 * @param par0 Input player.
	 */
	void sync(PlayerEntity par0);
	
	/**
	 * Sets the player's locked status to the input boolean.
	 * @param par0 Input boolean.
	 */
	void setLocked(boolean par0);
}
