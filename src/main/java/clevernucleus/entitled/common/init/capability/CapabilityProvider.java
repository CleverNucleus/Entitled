package clevernucleus.entitled.common.init.capability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import clevernucleus.entitled.common.init.Registry;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

/**
 * Capability helper class used to provide the IO abilities.
 */
public class CapabilityProvider implements ICapabilitySerializable<INBT> {
	private final LazyOptional<ITag> optional;
	private final ITag data;
	
	public CapabilityProvider() {
		this.data = new TagHandler();
		this.optional = LazyOptional.of(() -> data);
	}
	
	@Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nullable Capability<T> par0, Direction par1) {
    	return Registry.TAG.orEmpty(par0, optional);
    }
    
    @Override
    public INBT serializeNBT() {
    	return Registry.TAG.writeNBT(data, null);
    }
    
    @Override
    public void deserializeNBT(INBT par0) {
    	Registry.TAG.readNBT(data, null, par0);
    }
}
