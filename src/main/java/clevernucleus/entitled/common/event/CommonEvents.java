package clevernucleus.entitled.common.event;

import clevernucleus.entitled.common.Entitled;
import clevernucleus.entitled.common.init.capability.CapabilityProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Repository for common events on the MOD bus.
 */
@Mod.EventBusSubscriber(modid = Entitled.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonEvents {
	
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
}
