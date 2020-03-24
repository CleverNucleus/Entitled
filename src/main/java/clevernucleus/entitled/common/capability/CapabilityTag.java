package clevernucleus.entitled.common.capability;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class CapabilityTag {
	
	@CapabilityInject(ITag.class)
	public static final Capability<ITag> TAG = null;
	
	public static void init() {
		CapabilityManager.INSTANCE.register(ITag.class, new Capability.IStorage<ITag>() {
			
			@Override
			public NBTBase writeNBT(Capability<ITag> par0, ITag par1, EnumFacing par2) {
				return par1.get();
			}
			
			@Override
			public void readNBT(Capability<ITag> par0, ITag par1, EnumFacing par2, NBTBase par3) {
				par1.set((NBTTagCompound)par3);
			}
		}, () -> new Tag(null));
	}
	
	@Nullable
	public static <T> T getCapability(@Nullable ICapabilityProvider par0, Capability<T> par1, @Nullable EnumFacing par2) {
		return par0 != null && par0.hasCapability(par1, par2) ? par0.getCapability(par1, par2) : null;
	}
	
	@Nullable
	public static ITag tag(EntityLivingBase par0) {
		return getCapability(par0, TAG, null);
	}
	
	public static ICapabilityProvider createProvider(ITag par0) {
		return new CapabilityTagProvider<>(TAG, null, par0);
	}
}
