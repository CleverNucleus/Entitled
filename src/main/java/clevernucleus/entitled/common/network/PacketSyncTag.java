package clevernucleus.entitled.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketSyncTag extends PacketBase<PacketSyncTag> {
	private NBTTagCompound tag;
	
	public PacketSyncTag() {}
	
	public PacketSyncTag(NBTTagCompound par0) {
		this.tag = par0;
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
	public void handleClientSide(PacketSyncTag par0, EntityPlayer par1) {
		if(par0.tag != null && par1 != null) {
			
		}
	}
	
	@Override
	public void handleServerSide(PacketSyncTag par0, EntityPlayer par1) {}
}
