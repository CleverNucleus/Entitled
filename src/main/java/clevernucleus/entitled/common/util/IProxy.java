package clevernucleus.entitled.common.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface IProxy {
	String I18n(String par0);
	EntityPlayer getPlayer();
}
