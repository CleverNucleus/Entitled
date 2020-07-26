package clevernucleus.entitled.common.util;

import java.util.List;
import java.util.stream.Collectors;

import clevernucleus.entitled.common.init.Registry;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

/**
 * Special object for the name tag dye recipe.
 */
public class NameTagRecipe extends SpecialRecipe {
	public NameTagRecipe(ResourceLocation par0) {
		super(par0);
	}
	
	/**
	 * Takes the input inventory instance and loops over its' contents.
	 * @param par0 Input inventory.
	 * @return A list containing the inventory's contents.
	 */
	private NonNullList<ItemStack> wrapInv(final IInventory par0) {
		NonNullList<ItemStack> var0 = NonNullList.withSize(par0.getSizeInventory(), ItemStack.EMPTY);
		
		for(int var = 0; var < par0.getSizeInventory(); var++) {
			ItemStack var1 = par0.getStackInSlot(var);
			
			var0.set(var, var1);
		}
		
		return var0;
	}
	
	@Override
	public boolean matches(CraftingInventory par0, World par1) {
		if(!this.canFit(par0.getWidth(), par0.getHeight())) return false;
		
		List<Item> var0 = wrapInv(par0).stream().filter(var -> !var.isEmpty()).map(ItemStack::getItem).collect(Collectors.toList());
		
		boolean var1 = false;
		
		for(Item var : var0) {
			if(Tags.Items.DYES.contains(var)) {
				var1 = (var0.contains(Items.NAME_TAG) && var0.size() == 2);
				
				break;
			}
		}
		
		return var1;
	}
	
	@Override
	public ItemStack getCraftingResult(CraftingInventory par0) {
		ItemStack var0 = ItemStack.EMPTY, var1 = ItemStack.EMPTY;
		
		for(int var = 0; var < par0.getSizeInventory(); var++) {
			ItemStack var2 = par0.getStackInSlot(var);
			
			if(Tags.Items.DYES.contains(var2.getItem())) {
				var0 = var2;
			} else if(var2.getItem() == Items.NAME_TAG) {
				var1 = var2.copy();
			}
		}
		
		if(var0 != ItemStack.EMPTY && var1 != ItemStack.EMPTY) {
			if(!var1.hasTag()) {
				var1.setTag(new CompoundNBT());
			}
			
			CompoundNBT var2 = new CompoundNBT();
			
			var0.write(var2);
			var1.getTag().put("Colour", var2);
			
			return var1;
		}
		
		return ItemStack.EMPTY;
	}
	
	@Override
	public boolean canFit(int par0, int par1) {
		return par0 >= 2 && par1 >= 2;
	}
	
	@Override
	public IRecipeSerializer<?> getSerializer() {
		return Registry.CRAFTING_SPECIAL_NAME_TAG;
	}
}
