package clevernucleus.entitled.common.event;

import java.util.Objects;

import clevernucleus.entitled.common.Entitled;
import clevernucleus.entitled.common.init.Registry;
import clevernucleus.entitled.common.init.capability.CapabilityProvider;
import clevernucleus.entitled.common.init.network.SyncMapPacket;
import clevernucleus.entitled.common.init.network.SyncTagPacket;
import clevernucleus.entitled.common.util.Util;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkDirection;

/**
 * Repository for common events on the MOD bus.
 */
@Mod.EventBusSubscriber(modid = Entitled.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonEvents {
	
	/** Server player tag. */
	private static CompoundNBT tag = new CompoundNBT();
	
	/**
	 * Syncs the tag capability of the input player from the server to the client. 
	 * @param par0 Input player.
	 */
	private static void syncTag(final PlayerEntity par0) {
		if(par0.world.isRemote) return;
		
		par0.getCapability(Registry.TAG, null).ifPresent(var -> {
			Registry.NETWORK.sendTo(new SyncTagPacket(var.serializeNBT()), ((ServerPlayerEntity)par0).connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
		});
		
		if(tag != null) {
			Registry.NETWORK.sendTo(new SyncMapPacket(tag), ((ServerPlayerEntity)par0).connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
		}
	}
	
	/**
	 * Event ticking the players in the world.
	 * @param par0
	 */
	@SubscribeEvent
    public static void onPlayerTick(net.minecraftforge.event.TickEvent.PlayerTickEvent par0) {
		if(!par0.player.world.isRemote && par0.phase == net.minecraftforge.event.TickEvent.PlayerTickEvent.Phase.END) {
			CompoundNBT var0 = new CompoundNBT();
			ListNBT var1 = new ListNBT();
			
			for(PlayerEntity var2 : par0.player.world.getServer().getPlayerList().getPlayers()) {
				var1.add(Util.fromPlayer(var2));
			}
			
			var0.put("tag", var1);
			
			tag = var0;
		}
	}
	
	/**
	 * Event firing when a player right-clicks an item.
	 * @param par0
	 */
	@SubscribeEvent
    public static void onPlayerRightClick(net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem par0) {
    	PlayerEntity var0 = par0.getPlayer();
    	ItemStack var1 = par0.getItemStack();
    	
    	if(!var0.world.isRemote && var0.isCrouching() && (var1.getItem() == Items.NAME_TAG)) {
        	var0.getCapability(Registry.TAG, null).ifPresent(var2 -> {
        		if(var2.isEmpty() && var1.hasTag()) {
        			var2.setStackInSlot(0, var1.copy());
    				var1.shrink(1);
        		} else {
        			ItemStack var3 = var2.getStackInSlot(0);
        			ItemEntity var4 = new ItemEntity(var0.world, var0.prevPosX, var0.prevPosY, var0.prevPosZ, var3);
        			
        			var0.world.addEntity(var4);
        			var2.clear();
        		}
        	});
    	}
    }
	
	/**
	 * Event for attaching capabilities.
	 * @param par0
	 */
	@SubscribeEvent
    public static void onCapabilityAttachEntity(final net.minecraftforge.event.AttachCapabilitiesEvent<Entity> par0) {
		if(par0.getObject() instanceof PlayerEntity) {
			par0.addCapability(new ResourceLocation(Entitled.MODID, "tag"), new CapabilityProvider());
		}
	}
	
	/**
	 * Event firing when the player gets cloned.
	 * @param par0
	 */
	@SubscribeEvent
    public static void onPlayerEntityCloned(net.minecraftforge.event.entity.player.PlayerEvent.Clone par0) {
		try {
			Registry.TAG_FROM_PLAYER.apply(par0.getPlayer()).ifPresent(var0 -> {
				Registry.TAG_FROM_PLAYER.apply(par0.getOriginal()).ifPresent(var1 -> {
					var0.deserializeNBT(var1.serializeNBT());
				});
			});
		} catch(Exception parE) {}
	}
	
	/**
	 * Event firing when a player changes dimensions.
	 * @param par0
	 */
	@SubscribeEvent
    public static void onPlayerChangedDimension(net.minecraftforge.event.entity.player.PlayerEvent.PlayerChangedDimensionEvent par0) {
		syncTag(Objects.requireNonNull(par0.getPlayer()));
	}
	
	/**
	 * Event firing when the player respawns.
	 * @param par0
	 */
	@SubscribeEvent
	public static void onPlayerRespawn(net.minecraftforge.event.entity.player.PlayerEvent.PlayerRespawnEvent par0) {
		syncTag(Objects.requireNonNull(par0.getPlayer()));
	}
	
	/**
	 * Event firing when a player logs in.
	 * @param par0
	 */
	@SubscribeEvent
	public static void onPlayerLoggedIn(net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent par0) {
		syncTag(Objects.requireNonNull(par0.getPlayer()));
	}
}
