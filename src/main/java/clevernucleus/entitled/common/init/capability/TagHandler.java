package clevernucleus.entitled.common.init.capability;

import javax.annotation.Nonnull;

import clevernucleus.entitled.common.init.Registry;
import clevernucleus.entitled.common.util.Util;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.NameTagItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.items.ItemHandlerHelper;

/**
 * Object handler implementation of the ITag capability interface.
 */
public class TagHandler implements ITag {
	private NonNullList<ItemStack> stack;
	private boolean locked;
	
	public TagHandler() {
		this.stack = NonNullList.withSize(1, ItemStack.EMPTY);
		this.locked = false;
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
		
        this.stack.get(0).save(var0);
        var1.put("Tag", var0);
        var1.putBoolean("Locked", this.locked);
        
        return var1;
	}
	
	@Override
	public void deserializeNBT(CompoundNBT par0) {
		if(!par0.contains("Tag") || !par0.contains("Locked")) return;
		
		this.stack.set(0, ItemStack.of(par0.getCompound("Tag")));
		this.locked = par0.getBoolean("Locked");
	}
	
	@Override
	public boolean locked() {
		return this.locked;
	}
	
	@Override
	public boolean isEmpty() {
		return this.getStackInSlot(0) == ItemStack.EMPTY;
	}
	
	@Override
	public void clear() {
		this.stack.set(0, ItemStack.EMPTY);
	}
	
	@Override
	public void drop(final PlayerEntity par0) {
		ItemStack var0 = this.getStackInSlot(0);
		ItemEntity var1 = new ItemEntity(par0.level, par0.xo, par0.yo, par0.zo, var0);
		
		par0.level.addFreshEntity(var1);
	}
	
	@Override
	public void sync(final PlayerEntity par0) {
		if(par0.level.isClientSide) return;
		
		CompoundNBT var0 = new CompoundNBT();
		ListNBT var1 = new ListNBT();
		
		for(PlayerEntity var2 : par0.level.getServer().getPlayerList().getPlayers()) {
			var1.add(Util.fromPlayer(var2));
		}
		
		var0.put("tag", var1);
		
		if(var0 != null) {
			par0.level.getServer().getPlayerList().getPlayers().forEach(var -> {
				Registry.NETWORK.sendTo(new SyncTagPacket(var0), ((ServerPlayerEntity)var).connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
			});
		}
	}
	
	@Override
	public void setLocked(boolean par0) {
		this.locked = par0;
	}
}
