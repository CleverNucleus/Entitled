package clevernucleus.entitled.client.event;

import com.mojang.blaze3d.matrix.MatrixStack;

import clevernucleus.entitled.common.Entitled;
import clevernucleus.entitled.common.util.Display;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Client events repository on the FORGE event bus.
 */
@Mod.EventBusSubscriber(modid = Entitled.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientEvents {
	
	/**
	 * Event drawing item tooltips.
	 * @param par0
	 */
	@SubscribeEvent
	public static void onDrawTooltip(final ItemTooltipEvent par0) {
		ItemStack var0 = par0.getItemStack();
		
		if(!isShiftDown()) return;
		if(var0.getItem() == Items.NAME_TAG) {
			if(var0.hasTag()) {
				if(var0.getTag().contains("colour")) {
					CompoundNBT var1 = var0.getTag().getCompound("colour");
					ItemStack var2 = ItemStack.of(var1);
					
					par0.getToolTip().add(new StringTextComponent(TextFormatting.GRAY + " -" + var2.getDisplayName().getString()));
				} else {
					ItemStack var1 = new ItemStack(Items.WHITE_DYE);
					
					par0.getToolTip().add(new StringTextComponent(TextFormatting.GRAY + " -" + var1.getDisplayName().getString()));
				}
			} else {
				ItemStack var1 = new ItemStack(Items.WHITE_DYE);
				
				par0.getToolTip().add(new StringTextComponent(TextFormatting.GRAY + " -" + var1.getDisplayName().getString()));
			}
		}
	}
	
	/**
	 * Main render event to used to draw nameplates.
	 * @param par0
	 */
	@SubscribeEvent
	public static void onRenderType(RenderLivingEvent.Post<LivingEntity, EntityModel<LivingEntity>> par0) {
		if(par0.getEntity() instanceof PlayerEntity) {
			PlayerEntity var0 = (PlayerEntity)par0.getEntity();
			
			try {
				if(!var0.isInvisible()) {
					Display var1 = Entitled.PROXY.getMap().getOrDefault(var0.getUUID(), Display.make("", 0xFFFFFF));
					
					if(var1.getName().equals("")) return;
					
					renderTag(par0.getRenderer(), var0, var1.getName(), par0.getMatrixStack(), par0.getBuffers(), par0.getLight(), var1.getColour());
				}
			} catch(NullPointerException parE) {}
		}
	}
	
	/**
	 * Draws a nameplate above the entity (but below a player's nametag).
	 * @param par0
	 * @param par1
	 * @param par2
	 * @param par3
	 * @param par4
	 * @param par5
	 * @param par6
	 */
	private static <T extends Entity> void renderTag(EntityRenderer<T> par0, T par1, String par2, MatrixStack par3, IRenderTypeBuffer par4, int par5, int par6) {
		double var0 = par0.getDispatcher().distanceToSqr(par1);
		
		if(!(var0 > 4096)) {
			boolean var1 = !par1.isCrouching();
			float var2 = par1.getBbHeight() + 0.3F;
			
			par3.pushPose();
			par3.translate(0.0D, (double)var2, 0.0D);
			par3.mulPose(par0.getDispatcher().cameraOrientation());
			par3.scale(-0.025F, -0.025F, -0.025F);
			
			Matrix4f var3 = par3.last().pose();
			
			float var4 = Minecraft.getInstance().options.getBackgroundOpacity(0.25F);
			int var5 = (int)(var4 * 255F) << 24;
			
			FontRenderer var6 = par0.getFont();
			
			float var7 = (float)(-var6.width(par2) / 2);
			
			var6.drawInBatch(par2, var7, 0F, par6, false, var3, par4, var1, var5, par5);
			
			if(var1) {
				var6.drawInBatch(par2, var7, 0F, par6, false, var3, par4, false, 0, par5);
			}
			
			par3.popPose();
		}
	}
	
	/** Tests to see if LShift or RShift is being pressed. */
	private static boolean isShiftDown() {
		return InputMappings.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 340) || InputMappings.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 344);
	}
}
