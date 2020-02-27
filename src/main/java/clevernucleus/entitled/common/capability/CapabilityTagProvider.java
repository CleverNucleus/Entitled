package clevernucleus.entitled.common.capability;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

public class CapabilityTagProvider implements INBTSerializable<NBTTagCompound>, ICapabilityProvider {
	private final CapabilityTagContainer container;
	
	public CapabilityTagProvider(CapabilityTagContainer par0) {
		this.container = par0;
	}
	
	@Override
	public boolean hasCapability(Capability<?> par0, EnumFacing par1) {
		return par0 == CapabilityTag.TAG;
	}
	
	@Override
	public <T> T getCapability(Capability<T> par0, EnumFacing par1) {
		if(par0 == CapabilityTag.TAG) return (T) this.container;
		
		return null;
	}
	
	@Override
	public NBTTagCompound serializeNBT() {
		return this.container.serializeNBT();
	}
	
	@Override
	public void deserializeNBT(NBTTagCompound par0) {
		this.container.deserializeNBT(par0);
	}
}
