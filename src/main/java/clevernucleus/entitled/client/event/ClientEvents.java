package clevernucleus.entitled.client.event;

import clevernucleus.entitled.common.Entitled;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
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
		
		if(var0.getItem() == Items.NAME_TAG) {
			if(var0.hasTag()) {
				if(var0.getTag().contains("Colour")) {
					CompoundNBT var1 = var0.getTag().getCompound("Colour");
					ItemStack var2 = ItemStack.read(var1);
					
					par0.getToolTip().add(new StringTextComponent(TextFormatting.GRAY + " -" + var2.getDisplayName().getFormattedText()));
				}
			} else {
				ItemStack var1 = new ItemStack(Items.WHITE_DYE);
				
				par0.getToolTip().add(new StringTextComponent(TextFormatting.GRAY + " -" + var1.getDisplayName().getFormattedText()));
			}
		}
	}
}
