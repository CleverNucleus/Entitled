package clevernucleus.entitled.common.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class CapabilityTag {
	public static final int SLOT = 0;
	
	@CapabilityInject(ICapabilityTag.class)
	public static final Capability<ICapabilityTag> TAG = null;
	
	public static class CapabilityTagHandler<T extends ICapabilityTag> implements IStorage<ICapabilityTag> {
		
		@Override
		public NBTBase writeNBT(Capability<ICapabilityTag> par0, ICapabilityTag par1, EnumFacing par2) {
			return null;
		}
		
		@Override
		public void readNBT(Capability<ICapabilityTag> par0, ICapabilityTag par1, EnumFacing par2, NBTBase par3) {}
	}
}
