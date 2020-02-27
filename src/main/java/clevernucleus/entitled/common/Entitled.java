package clevernucleus.entitled.common;

import clevernucleus.entitled.common.capability.CapabilityTag;
import clevernucleus.entitled.common.capability.CapabilityTagContainer;
import clevernucleus.entitled.common.capability.ICapabilityTag;
import clevernucleus.entitled.common.handler.NetworkHandler;
import clevernucleus.entitled.common.handler.PlayerHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class Entitled implements IConstants {
	
	@Mod(modid = MODID, name = NAME, version = VERSION)
	public static class Core {
		
		@SidedProxy(clientSide = PROXY_CLIENT, serverSide = PROXY_SERVER)
		public static IProxy proxy;
		
		@EventHandler
		public void preInit(FMLPreInitializationEvent par0) {
			CapabilityManager.INSTANCE.register(ICapabilityTag.class, new CapabilityTag.CapabilityTagHandler<>(), CapabilityTagContainer.class);
			MinecraftForge.EVENT_BUS.register(new PlayerHandler());
			
			NetworkHandler.init();
			
			proxy.preInit(par0);
		}
	}
	
	public static ICapabilityTag tag(EntityPlayer par0) {
		ICapabilityTag var0 = par0.getCapability(CapabilityTag.TAG, null);
		
		return var0;
	}
}
