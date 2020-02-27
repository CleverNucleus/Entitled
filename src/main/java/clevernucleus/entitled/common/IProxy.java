package clevernucleus.entitled.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface IProxy {
	void preInit(FMLPreInitializationEvent par0);
	void setPlayerMap(NBTTagList par0);
	
	EntityPlayer getPlayer();
	NBTTagList getPlayerMap();
}
