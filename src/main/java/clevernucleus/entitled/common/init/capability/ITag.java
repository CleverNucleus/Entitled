package clevernucleus.entitled.common.init.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandlerModifiable;

/**
 * Tag capability interface.
 */
public interface ITag extends IItemHandlerModifiable, INBTSerializable<CompoundNBT> {
	
	/**
	 * Checks to see if the slot has {@link ItemStack#EMPTY}.
	 * @return True if the slot contains {@link ItemStack#EMPTY}; otherwise false.
	 */
	boolean isEmpty();
	
	/**
	 * Sets the contained stack to {@link ItemStack#EMPTY}.
	 */
	void clear();
}
