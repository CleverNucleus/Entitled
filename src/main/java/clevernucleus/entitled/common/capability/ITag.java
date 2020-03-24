package clevernucleus.entitled.common.capability;

import net.minecraft.nbt.NBTTagCompound;

public interface ITag {
	void set(NBTTagCompound par0);
	
	NBTTagCompound get();
}
