package clevernucleus.entitled.common.handler;

import clevernucleus.entitled.common.Entitled;
import clevernucleus.entitled.common.Entitled.Core;
import clevernucleus.entitled.common.capability.CapabilityTag;
import clevernucleus.entitled.common.capability.ICapabilityTag;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class MessageSyncTag extends MessageBase<MessageSyncTag> {
	private final String id = "tag";
	private NBTTagCompound tag;
	
	public MessageSyncTag() {}
	
	public MessageSyncTag(NBTTagList par0) {
		this.tag = new NBTTagCompound();
		this.tag.setTag(id, par0);
	}
	
	@Override
	public void toBytes(ByteBuf par0) {
		ByteBufUtils.writeTag(par0, tag);
	}
	
	@Override
	public void fromBytes(ByteBuf par0) {
		tag = ByteBufUtils.readTag(par0);
	}
	
	@Override
	public void handleClientSide(MessageSyncTag par0, EntityPlayer par1) {
		if(par0.tag != null && par1 != null) {
			Core.proxy.setPlayerMap(par0.tag.getTagList(id, 10));
		}
	}
	
	@Override
	public void handleServerSide(MessageSyncTag par0, EntityPlayer par1) {}
}
