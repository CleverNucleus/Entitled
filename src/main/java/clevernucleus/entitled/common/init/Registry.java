package clevernucleus.entitled.common.init;

import java.util.function.Function;

import javax.annotation.Nonnull;

import clevernucleus.entitled.common.Entitled;
import clevernucleus.entitled.common.init.capability.ITag;
import clevernucleus.entitled.common.init.capability.TagHandler;
import clevernucleus.entitled.common.init.network.*;
import clevernucleus.entitled.common.util.NameTagRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.RegistryEvent;
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
	
	public static final IRecipeSerializer<NameTagRecipe> CRAFTING_SPECIAL_NAME_TAG = register("name_tag_dye", new SpecialRecipeSerializer<>(NameTagRecipe::new));
	
	/**
	 * Used to pass a special recipe serializer and its registry name through to a list and returned again.
	 * @param par0 The registry name.
	 * @param par1 The recipe serializer object.
	 * @return The recipe serializer object, with its registry name set.
	 */
	private static <S extends IRecipeSerializer<T>, T extends IRecipe<?>> S register(final @Nonnull String par0, @Nonnull S par1) {
		par1.setRegistryName(new ResourceLocation(Entitled.MODID, par0));
		
		return par1;
	}
	
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
		NETWORK.registerMessage(1, SyncMapPacket.class, SyncMapPacket::encode, SyncMapPacket::decode, SyncMapPacket::handle);
	}
	
	/**
	 * Event handling the registration of special recipe serializers.
	 * @param par0
	 */
	@SubscribeEvent
	public static void registerRecipeSerializers(final RegistryEvent.Register<IRecipeSerializer<?>> par0) {
		par0.getRegistry().register(CRAFTING_SPECIAL_NAME_TAG);
	}
}
