package clevernucleus.entitled.common.init;

import java.util.function.Function;

import clevernucleus.entitled.common.Entitled;
import clevernucleus.entitled.common.init.capability.ITag;
import clevernucleus.entitled.common.init.capability.TagHandler;
import clevernucleus.entitled.common.init.network.SyncTagPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

/**
 * Registry holder for common events that handle registry elements.
 */
@Mod.EventBusSubscriber(modid = Entitled.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Registry {
	
	/** Tag capability instance. */
	@CapabilityInject(ITag.class)
	public static final Capability<ITag> TAG = null;
	
	/** Network instance. */
	public static final SimpleChannel NETWORK = NetworkRegistry.newSimpleChannel(new ResourceLocation(Entitled.MODID, "path"), () -> "1", "1"::equals, "1"::equals);
	
	/** Tag capability pass-through function. */
	public static final Function<PlayerEntity, LazyOptional<ITag>> TAG_FROM_PLAYER = var -> var.getCapability(TAG, null);
	
	/**
	 * Mod initialisation event.
	 * @param par0
	 */
	@SubscribeEvent
	public static void commonSetup(final FMLCommonSetupEvent par0) {
		CapabilityManager.INSTANCE.register(ITag.class, new Capability.IStorage<ITag>() {
			
			@Override
			public INBT writeNBT(Capability<ITag> par0, ITag par1, Direction par2) {
				return par1.serializeNBT();
			}
			
			@Override
			public void readNBT(Capability<ITag> par0, ITag par1, Direction par2, INBT par3) {
				par1.deserializeNBT((CompoundNBT)par3);
			}
		}, TagHandler::new);
		
		NETWORK.registerMessage(0, SyncTagPacket.class, SyncTagPacket::encode, SyncTagPacket::decode, SyncTagPacket::handle);
	}
}
