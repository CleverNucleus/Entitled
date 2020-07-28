package clevernucleus.entitled.common.event;

import javax.annotation.Nonnull;

import clevernucleus.entitled.common.Entitled;
import clevernucleus.entitled.common.init.Registry;
import clevernucleus.entitled.common.init.capability.CapabilityProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

/**
 * Repository for common events on the MOD bus.
 */
@Mod.EventBusSubscriber(modid = Entitled.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonEvents {
	
	/**
	 * Sync event pass-through with safety functions.
	 * @param par0
	 */
	private static void syncTag(final @Nonnull PlayerEntity par0) {
		if(par0 == null) return;
		if(par0.world.isRemote) return;
		
		par0.getCapability(Registry.TAG, null).ifPresent(var -> {
			var.sync(par0);
		});
	}
	
	@SubscribeEvent
    public static void serverLoad(final FMLServerStartingEvent par0) {
        TitleCommand.register(par0.getCommandDispatcher());
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
        		if(var2.locked() && !var0.hasPermissionLevel(2)) {
        			ItemStack var3 = new ItemStack(Items.NAME_TAG);
        			
        			var0.sendMessage(new TranslationTextComponent("message.entitled.disallowed", TextFormatting.RED, var3.getDisplayName()));
        			
        			return;
        		}
        		if(var2.isEmpty() && var1.hasTag()) {
        			var2.setStackInSlot(0, var1.copy());
    				var1.shrink(1);
        		} else {
        			var2.drop(var0);
        			var2.clear();
        		}
        		
        		syncTag(var0);
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
		PlayerEntity var0 = par0.getPlayer();
		
		if(var0.world.isRemote) return;
		
		try {
			Registry.TAG_FROM_PLAYER.apply(var0).ifPresent(var1 -> {
				Registry.TAG_FROM_PLAYER.apply(par0.getOriginal()).ifPresent(var2 -> {
					var1.deserializeNBT(var2.serializeNBT());
				});
			});
		} catch(Exception parE) {}
		
		syncTag(var0);
	}
	
	/**
	 * Event firing when a player changes dimensions.
	 * @param par0
	 */
	@SubscribeEvent
    public static void onPlayerChangedDimension(net.minecraftforge.event.entity.player.PlayerEvent.PlayerChangedDimensionEvent par0) {
		syncTag(par0.getPlayer());
	}
	
	/**
	 * Event firing when the player respawns.
	 * @param par0
	 */
	@SubscribeEvent
	public static void onPlayerRespawn(net.minecraftforge.event.entity.player.PlayerEvent.PlayerRespawnEvent par0) {
		syncTag(par0.getPlayer());
	}
	
	/**
	 * Event firing when a player logs in.
	 * @param par0
	 */
	@SubscribeEvent
	public static void onPlayerLoggedIn(net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent par0) {
		syncTag(par0.getPlayer());
	}
}
