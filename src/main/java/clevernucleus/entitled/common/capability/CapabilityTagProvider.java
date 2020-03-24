package clevernucleus.entitled.common.capability;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class CapabilityTagProvider<HANDLER> implements ICapabilitySerializable<NBTBase> {
	private final Capability<HANDLER> CAPABILITY;
	private final EnumFacing FACING;
	private final HANDLER INSTANCE;
	
	/**
	 * Constructs the capability without the handler implementation.
	 * @param par0 Capability HANDLER capability
	 * @param par1 @Nullable EnumFacing facing, usually null
	 */
	public CapabilityTagProvider(Capability<HANDLER> par0, @Nullable EnumFacing par1) {
		this(par0, par1, par0.getDefaultInstance());
	}
	
	/**
	 * Constructs the capability with the handler implementation.
	 * @param par0 Capability HANDLER capability
	 * @param par1 @Nullable EnumFacing facing, usually null
	 * @param par2 HANDLER capability handler
	 */
	public CapabilityTagProvider(Capability<HANDLER> par0, @Nullable EnumFacing par1, HANDLER par2) {
		this.CAPABILITY = par0;
		this.INSTANCE = par2;
		this.FACING = par1;
	}
	
	@Override
	public boolean hasCapability(Capability<?> par0, EnumFacing par1) {
		return par0 == getCapability();
	}
	
	@Override
	@Nullable
	public <T> T getCapability(Capability<T> par0, EnumFacing par1) {
		if(par0 == getCapability()) {
			return getCapability().cast(getInstance());
		}
		
		return null;
	}
	
	@Override
	public NBTBase serializeNBT() {
		return getCapability().writeNBT(getInstance(), getFacing());
	}
	
	@Override
	public void deserializeNBT(NBTBase par0) {
		getCapability().readNBT(getInstance(), getFacing(), par0);
	}
	
	public final Capability<HANDLER> getCapability() {
		return CAPABILITY;
	}
	
	@Nullable
	public EnumFacing getFacing() {
		return FACING;
	}
	
	public final HANDLER getInstance() {
		return INSTANCE;
	}
}
