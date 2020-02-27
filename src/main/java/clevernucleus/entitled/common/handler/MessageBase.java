package clevernucleus.entitled.common.handler;

import clevernucleus.entitled.common.Entitled.Core;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public abstract class MessageBase<REQ extends IMessage> implements IMessage, IMessageHandler<REQ, REQ> {
	
	@Override
	public REQ onMessage(REQ par0, MessageContext par1) {
		if(par1.side == Side.SERVER) {
			handleServerSide(par0, par1.getServerHandler().player);
		} else {
			handleClientSide(par0, Core.proxy.getPlayer());
		}
		
		return null;
	}
	
	@Override
	public void fromBytes(ByteBuf par0) {}
	
	@Override
	public void toBytes(ByteBuf par0) {}
	
	/**
	 * Handles a packet on the client side. This occurs after decoding has completed.
	 * @param par0 REQ Message
	 * @param par1 EntityPlayer The player (client)
	 */
	public abstract void handleClientSide(REQ par0, EntityPlayer par1);
	
	/**
	 * Handles a packet on the server side. This occurs after decoding has completed.
	 * @param par0 REQ Message
	 * @param par1 EntityPlayer The player (server)
	 */
	public abstract void handleServerSide(REQ par0, EntityPlayer par1);
}
