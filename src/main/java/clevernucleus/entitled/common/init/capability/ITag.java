package clevernucleus.entitled.common.init.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandlerModifiable;

public interface ITag extends IItemHandlerModifiable, INBTSerializable<CompoundNBT> {
	
}
