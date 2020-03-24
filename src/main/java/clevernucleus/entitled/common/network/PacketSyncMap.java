package clevernucleus.entitled.common.network;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import clevernucleus.entitled.client.TagOverlay;
import clevernucleus.entitled.common.util.Util;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class PacketSyncMap extends PacketBase<PacketSyncMap> {
private NBTTagCompound tag;
	
	public PacketSyncMap() {}
	
	public PacketSyncMap(NBTTagCompound par0) {
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
	public void handleClientSide(PacketSyncMap par0, EntityPlayer par1) {
		if(par0.tag != null && par1 != null) {
			NBTTagList var0 = par0.tag.getTagList(Util.MAP, 10);
			Map<UUID, String> var1 = new HashMap<>();
			Map<UUID, Byte> var2 = new HashMap<>();
			
			for(int var = 0; var < var0.tagCount(); var++) {
				NBTTagCompound var3 = var0.getCompoundTagAt(var);
				UUID var4 = var3.getUniqueId(Util.UUID);
				String var5 = var3.getString(Util.DISPLAY);
				
				byte var6 = var3.getByte(Util.COLOUR);
				
				var1.put(var4, var5);
				var2.put(var4, Byte.valueOf(var6));
			}
			
			TagOverlay.playerTagMap = var1;
			TagOverlay.colourTagMap = var2;
		}
	}
	
	@Override
	public void handleServerSide(PacketSyncMap par0, EntityPlayer par1) {}
}
