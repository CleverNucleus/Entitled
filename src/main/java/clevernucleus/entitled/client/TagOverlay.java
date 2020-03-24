package clevernucleus.entitled.client;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import clevernucleus.entitled.common.util.Util;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT)
public class TagOverlay {
	public static Map<UUID, String> playerTagMap = new HashMap<>();
	public static Map<UUID, Byte> colourTagMap = new HashMap<>();
	
	@SubscribeEvent
	public static void renderEntityOverlay(RenderLivingEvent.Post<EntityLivingBase> par0) {
		EntityLivingBase var0 = par0.getEntity();
		
		if(var0 instanceof EntityPlayer) {
			Entity var1 = par0.getRenderer().getRenderManager().renderViewEntity;
			EntityPlayer var2 = (EntityPlayer) var0;
			
			double var3 = (var0.lastTickPosX + (var0.posX - var0.lastTickPosX) * (double) par0.getPartialRenderTick());
			double var4 = (var0.lastTickPosY + (var0.posY - var0.lastTickPosY) * (double) par0.getPartialRenderTick());
			double var5 = (var0.lastTickPosZ + (var0.posZ - var0.lastTickPosZ) * (double) par0.getPartialRenderTick());
			double var6 = (var1.lastTickPosX + (var1.posX - var1.lastTickPosX) * (double) par0.getPartialRenderTick());
			double var7 = (var1.lastTickPosY + (var1.posY - var1.lastTickPosY) * (double) par0.getPartialRenderTick());
			double var8 = (var1.lastTickPosZ + (var1.posZ - var1.lastTickPosZ) * (double) par0.getPartialRenderTick());
			double var9 = var3 - var6;
			double var10 = var4 - var7;
			double var11 = var5 - var8;
			
			if(!var0.isInvisible()) {
				try {
					String var12 = playerTagMap.containsKey(var2.getUniqueID()) ? playerTagMap.get(var2.getUniqueID()) : "";
					EnumDyeColor var13 = EnumDyeColor.byDyeDamage(colourTagMap.containsKey(var2.getUniqueID()) ? (int)colourTagMap.get(var2.getUniqueID()) : 0);
					
					if(!var12.isEmpty() && !var2.isSneaking()) {
						renderNamePlate(var0, par0.getRenderer().getRenderManager(), var12, var9, var10 + var0.height + 0.3, var11, var13.getColorValue());
					}
				} catch(NullPointerException parE) {}
			}
		}
	}
	
	private static void renderNamePlate(EntityLivingBase par0, RenderManager par1, String par2, double par3, double par4, double par5, int par6) {
		double var0 = par0.getDistance(par1.renderViewEntity);
		
		if(var0 > 24) return;
		
		FontRenderer var1 = par1.getFontRenderer();
		
		float var2 = par1.playerViewY;
		float var3 = par1.playerViewX;
		
		GlStateManager.pushMatrix();
        GlStateManager.translate(par3, par4, par5);
        GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-var2, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(var3, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(-0.025F, -0.025F, 0.025F);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        
        int var4 = var1.getStringWidth(par2) / 2;
        
        GlStateManager.disableTexture2D();
        
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)(-var4 - 1), -1.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        bufferbuilder.pos((double)(-var4 - 1), 8.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        bufferbuilder.pos((double)(var4 + 1), 8.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        bufferbuilder.pos((double)(var4 + 1), -1.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        tessellator.draw();
        
        GlStateManager.enableTexture2D();
        
        var1.drawString(par2, -var1.getStringWidth(par2) / 2, 0, par6);
        
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        
        var1.drawString(par2, -var1.getStringWidth(par2) / 2, 0, par6);
        
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }
}
