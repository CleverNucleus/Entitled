package clevernucleus.entitled.client;

import java.util.HashMap;
import java.util.Map;

import clevernucleus.entitled.common.Entitled.Core;
import clevernucleus.entitled.common.IConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT)
public class TagOverlay {
	
	@SubscribeEvent
	public static void renderEntityOverlay(RenderLivingEvent.Post<EntityLivingBase> par0) {
		EntityLivingBase var0 = par0.getEntity();
		
		if(var0 instanceof EntityPlayer) {
			Entity var1 = par0.getRenderer().getRenderManager().renderViewEntity;
			EntityPlayer player = (EntityPlayer) var0;
			
			double entityX = (var0.lastTickPosX + (var0.posX - var0.lastTickPosX) * (double) par0.getPartialRenderTick());
			double entityY = (var0.lastTickPosY + (var0.posY - var0.lastTickPosY) * (double) par0.getPartialRenderTick());
			double entityZ = (var0.lastTickPosZ + (var0.posZ - var0.lastTickPosZ) * (double) par0.getPartialRenderTick());
			double viewingX = (var1.lastTickPosX + (var1.posX - var1.lastTickPosX) * (double) par0.getPartialRenderTick());
			double viewingY = (var1.lastTickPosY + (var1.posY - var1.lastTickPosY) * (double) par0.getPartialRenderTick());
			double viewingZ = (var1.lastTickPosZ + (var1.posZ - var1.lastTickPosZ) * (double) par0.getPartialRenderTick());
			double x = entityX - viewingX;
			double y = entityY - viewingY;
			double z = entityZ - viewingZ;
			
			net.minecraft.nbt.NBTTagList tagList = Core.proxy.getPlayerMap();
			Map<EntityPlayer, NBTTagCompound> mapList = new HashMap<>();
			
			for(int var = 0; var < tagList.tagCount(); var++) {
				NBTTagCompound tag = tagList.getCompoundTagAt(var);
				EntityPlayer entity = Minecraft.getMinecraft().world.getPlayerEntityByUUID(tag.getUniqueId(IConstants.UUID));
				
				mapList.put(entity, tag);
			}
			
			if(!var0.isInvisible()) {
				try {
					String tag = mapList.get(player).getString(IConstants.DISPLAY);
					
					if(!tag.isEmpty() && !player.isSneaking()) {
						renderNamePlate(var0, par0.getRenderer().getRenderManager(), tag, x, y + var0.height + 0.3, z, 24);
					}
				} catch(NullPointerException parE) {}
			}
		}
	}
	
	private static void renderNamePlate(EntityLivingBase par0, RenderManager par1, String par2, double par3, double par4, double par5, int par6) {
		double var0 = par0.getDistance(par1.renderViewEntity);
		
		if(var0 > par6) return;
		
		FontRenderer var1 = par1.getFontRenderer();
		
		float var2 = par1.playerViewY;
		float var3 = par1.playerViewX;
		
		EntityRenderer.drawNameplate(var1, par2, (float) par3, (float) par4, (float) par5, 0, var2, var3, false, false);
	}
}
