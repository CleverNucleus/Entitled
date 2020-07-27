package clevernucleus.entitled.common.init.capability;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.item.NameTagItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemHandlerHelper;

/**
 * Object handler implementation of the ITag capability interface.
 */
public class TagHandler implements ITag {
	private NonNullList<ItemStack> stack;
	
	public TagHandler() {
		this.stack = NonNullList.withSize(1, ItemStack.EMPTY);
	}
	
	/**
	 * Comparative method.
	 * @param par0 Slot index.
	 * @param par1 Input stack.
	 * @return performs {@link Math#min(int, int)} on the input index slot's limit and the input stack's max stack size.
	 */
	private int getStackLimit(final int par0, @Nonnull ItemStack par1) {
		return Math.min(this.getSlotLimit(par0), par1.getMaxStackSize());
    }
	
	/**
	 * Fired when a change in the inventory is detected. Syncs to the server.
	 * @param par0 Slot index.
	 */
	public void onContentsChanged(int par0) {}
	
	@Override
	public void setStackInSlot(final int par0, @Nonnull ItemStack par1) {
        this.stack.set(0, par1);
        this.onContentsChanged(0);
	}
	
	@Override
	public int getSlots() {
		return this.stack.size();
	}
	
	@Override
	public ItemStack getStackInSlot(final int par0) {
		return this.stack.get(0);
	}
	
	@Override
    @Nonnull
    public ItemStack insertItem(final int par0, @Nonnull ItemStack par1, final boolean par2) {
		if(stack.isEmpty()) return ItemStack.EMPTY;
		if(!isItemValid(par0, par1)) return par1;
		
		ItemStack var0 = this.stack.get(par0);
		
		int var1 = this.getStackLimit(par0, par1);
		
        if(!var0.isEmpty()) {
        	if(!ItemHandlerHelper.canItemStacksStack(par1, var0)) return par1;
        	
        	var1 -= var0.getCount();
        }
        
        if(var1 <= 0) return par1;
        
        boolean var2 = par1.getCount() > var1;
        
        if(!par2) {
        	if(var0.isEmpty()) {
        		this.stack.set(par0, var2 ? ItemHandlerHelper.copyStackWithSize(par1, var1) : par1);
            } else {
            	var0.grow(var2 ? var1 : par1.getCount());
            }
        	
        	this.onContentsChanged(0);
        }
        
        return var2 ? ItemHandlerHelper.copyStackWithSize(par1, par1.getCount() - var1) : ItemStack.EMPTY;
    }
	
	@Override
    @Nonnull
    public ItemStack extractItem(final int par0, final int par1, final boolean par2) {
        if(par1 == 0) return ItemStack.EMPTY;
        
        ItemStack var0 = this.stack.get(par0);
        
        if(var0.isEmpty()) return ItemStack.EMPTY;
        
        int var1 = Math.min(par1, var0.getMaxStackSize());
        
        if(var0.getCount() <= var1) {
        	if(!par2) {
        		this.stack.set(par0, ItemStack.EMPTY);
        		this.onContentsChanged(0);
        		
        		return var0;
            } else {
            	return var0.copy();
            }
        } else {
        	if(!par2) {
        		this.stack.set(par0, ItemHandlerHelper.copyStackWithSize(var0, var0.getCount() - var1));
        		this.onContentsChanged(0);
            }
        	
            return ItemHandlerHelper.copyStackWithSize(var0, var1);
        }
    }
	
	@Override
	public int getSlotLimit(final int par0) {
		return 1;
	}
	
	@Override
	public boolean isItemValid(final int par0, @Nonnull ItemStack par1) {
		return par1.getItem() instanceof NameTagItem;
	}
	
	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT var0 = new CompoundNBT();
		CompoundNBT var1 = new CompoundNBT();
		
        this.stack.get(0).write(var0);
        var1.put("Tag", var0);
        
        return var1;
	}
	
	@Override
	public void deserializeNBT(CompoundNBT par0) {
		if(!par0.contains("Tag")) return;
		
		this.stack.set(0, ItemStack.read(par0.getCompound("Tag")));
	}

	@Override
	public boolean isEmpty() {
		return this.getStackInSlot(0) == ItemStack.EMPTY;
	}
	
	@Override
	public void clear() {
		this.stack.set(0, ItemStack.EMPTY);
	}
}
