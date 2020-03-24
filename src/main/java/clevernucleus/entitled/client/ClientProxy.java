package clevernucleus.entitled.client;

import clevernucleus.entitled.common.util.IProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class ClientProxy implements IProxy {
	
	@Override
	public EntityPlayer getPlayer() {
		return Minecraft.getMinecraft().player;
	}
	
	@Override
	public String I18n(String par0) {
		return net.minecraft.client.resources.I18n.format(par0);
	}
}
