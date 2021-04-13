package clevernucleus.entitled.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

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
	 * Creates a new Map.
	 * @param par0 The input function will be applied to the output map.
	 * @return A new HashMap.
	 */
	public static <K, V> Map<K, V> map(final Consumer<Map<K, V>> par0) {
		Map<K, V> var0 = new HashMap<K, V>();
		
		par0.accept(var0);
		
		return var0;
	}
	
	/**
	 * Allows for the safe usage of the input stack's nbt compound.
	 * @param par0 Input stack.
	 * @param par1 Compound usage.
	 */
	public static void safeTag(ItemStack par0, final Consumer<CompoundNBT> par1) {
		if(par0.isEmpty()) return;
		if(!par0.hasTag()) {
			par0.setTag(new CompoundNBT());
		}
		
		par1.accept(par0.getTag());
	}
	
	/**
	 * Safely searches through the players tag capability for name tags and colour data.
	 * @param par0 Input player.
	 * @return A compound tag with the players UUID, tag name and colour.
	 */
	public static CompoundNBT fromPlayer(final PlayerEntity par0) {
		if(par0 == null) return new CompoundNBT();
		
		CompoundNBT var0 = new CompoundNBT();
		
		var0.putUUID("UUID", par0.getUUID());
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
						ItemStack var5 = ItemStack.of(var4.getTag().getCompound("colour"));
						
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
			UUID var3 = var2.getUUID("UUID");
			Display var4 = Display.read(var2);
			
			var1.put(var3, var4);
		}
		
		return var1;
	}
}
