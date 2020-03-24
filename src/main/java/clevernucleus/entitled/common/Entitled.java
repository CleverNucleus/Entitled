package clevernucleus.entitled.common;

import clevernucleus.entitled.common.capability.CapabilityTag;
import clevernucleus.entitled.common.event.CommonHandler;
import clevernucleus.entitled.common.network.NetworkHandle;
import clevernucleus.entitled.common.util.IProxy;
import clevernucleus.entitled.common.util.Util;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Util.MODID, name = Util.NAME, version = Util.VERSION)
public class Entitled {
	
	@SidedProxy(clientSide = Util.PROXY_CLIENT, serverSide = Util.PROXY_SERVER)
	public static IProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent par0) {
		CapabilityTag.init();
		NetworkHandle.init();
		
		MinecraftForge.EVENT_BUS.register(new CommonHandler());
	}
}
