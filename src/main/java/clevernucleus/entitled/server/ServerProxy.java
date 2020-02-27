package clevernucleus.entitled.server;

import clevernucleus.entitled.common.IProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ServerProxy implements IProxy {
	
	@Override
	public void preInit(FMLPreInitializationEvent par0) {}
	
	@Override
	public void setPlayerMap(NBTTagList par0) {}
	
	@Override
	public EntityPlayer getPlayer() {
		return null;
	}
	
	@Override
	public NBTTagList getPlayerMap() {
		return null;
	}
}