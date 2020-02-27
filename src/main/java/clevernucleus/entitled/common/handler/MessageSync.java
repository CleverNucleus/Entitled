package clevernucleus.entitled.common.handler;

import clevernucleus.entitled.common.Entitled;
import clevernucleus.entitled.common.capability.CapabilityTag;
import clevernucleus.entitled.common.capability.ICapabilityTag;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class MessageSync extends MessageBase<MessageSync> {
	private int player;
	private ItemStack tag;
	
	public MessageSync() {}
	
	public MessageSync(EntityPlayer par0, ItemStack par1) {
		this.player = par0.getEntityId();
		this.tag = par1;
	}
	
	@Override
	public void toBytes(ByteBuf par0) {
		par0.writeInt(player);
		ByteBufUtils.writeItemStack(par0, tag);
	}
	
	@Override
	public void fromBytes(ByteBuf par0) {
		player = par0.readInt();
		tag = ByteBufUtils.readItemStack(par0);
	}
	
	@Override
	public void handleClientSide(MessageSync par0, EntityPlayer par1) {
		if(par1 != null && par1.world != null) {
			Entity var0 = par1.world.getEntityByID(par0.player);
			
			if(var0 != null && var0 instanceof EntityPlayer) {
				ICapabilityTag var1 = Entitled.tag((EntityPlayer)var0);
				
				var1.setStackInSlot(CapabilityTag.SLOT, par0.tag);
			}
		}
	}
	
	@Override
	public void handleServerSide(MessageSync par0, EntityPlayer par1) {}
}
