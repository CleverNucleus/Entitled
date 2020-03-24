package clevernucleus.entitled.common.network;

import clevernucleus.entitled.common.Entitled;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public abstract class PacketBase<Req extends IMessage> implements IMessage, IMessageHandler<Req, Req> {
	
	@Override
	public Req onMessage(Req par0, MessageContext par1) {
		if(par1.side == Side.SERVER) {
			handleServerSide(par0, par1.getServerHandler().player);
		} else {
			handleClientSide(par0, Entitled.proxy.getPlayer());
		}
		
		return null;
	}
	
	@Override
	public void fromBytes(ByteBuf par0) {}
	
	@Override
	public void toBytes(ByteBuf par0) {}
	
	public abstract void handleClientSide(Req par0, EntityPlayer par1);
	
	public abstract void handleServerSide(Req par0, EntityPlayer par1);
}
