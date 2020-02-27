package clevernucleus.entitled.client;

import clevernucleus.entitled.common.IProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy implements IProxy {
	private NBTTagList playerMap;
	
	@Override
	public void preInit(FMLPreInitializationEvent par0) {}
	
	@Override
	public void setPlayerMap(NBTTagList par0) {
		playerMap = par0;
	}
	
	@Override
	public EntityPlayer getPlayer() {
		return Minecraft.getMinecraft().player;
	}
	
	@Override
	public NBTTagList getPlayerMap() {
		return playerMap;
	}
}
