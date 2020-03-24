package clevernucleus.entitled.server;

import clevernucleus.entitled.common.util.IProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ServerProxy implements IProxy {
	
	@Override
	public EntityPlayer getPlayer() {
		return null;
	}
	
	@Override
	public String I18n(String par0) {
		return par0;
	}
}
