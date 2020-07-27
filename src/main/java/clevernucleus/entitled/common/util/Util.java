package clevernucleus.entitled.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import clevernucleus.entitled.common.init.Registry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;

/**
 * Holder for useful methods.
 */
public class Util {
	
	/**
	 * Safely searches through the players tag capability for name tags and colour data.
	 * @param par0 Input player.
	 * @return A compound tag with the players UUID, tag name and colour.
	 */
	public static CompoundNBT fromPlayer(final PlayerEntity par0) {
		if(par0 == null) return new CompoundNBT();
		
		CompoundNBT var0 = new CompoundNBT();
		
		var0.putUniqueId("UUID", par0.getUniqueID());
		par0.getCapability(Registry.TAG, null).ifPresent(var1 -> {
			String var2 = "";
			int var3 = 0xFFFFFF;
			
			if(!var1.isEmpty()) {
				ItemStack var4 = var1.getStackInSlot(0);
				
				if(var4.hasTag()) {
					if(var4.getTag().contains("display")) {
						String var5 = var4.getTag().getCompound("display").getString("Name");
						
						var2 = var5.substring(9, var5.length() - 2);
					}
					
					if(var4.getTag().contains("colour")) {
						ItemStack var5 = ItemStack.read(var4.getTag().getCompound("colour"));
						
						var3 = DyeColor.getColor(var5).getColorValue();
					}
				}
			}
			
			Display.make(var2, var3).write(var0);
		});
		
		return var0;
	}
	
	/**
	 * Loops through the input tag's inner list nbt to produce a map of the data.
	 * @param par0 Input nbt.
	 * @return A map of UUID to Display's.
	 */
	public static Map<UUID, Display> fromTagList(final CompoundNBT par0) {
		if(par0.isEmpty() || !par0.contains("tag")) return new HashMap<UUID, Display>();
		
		ListNBT var0 = par0.getList("tag", 10);
		Map<UUID, Display> var1 = new HashMap<UUID, Display>();
		
		for(int var = 0; var < var0.size(); var++) {
			CompoundNBT var2 = var0.getCompound(var);
			UUID var3 = var2.getUniqueId("UUID");
			Display var4 = Display.read(var2);
			
			var1.put(var3, var4);
		}
		
		return var1;
	}
}
