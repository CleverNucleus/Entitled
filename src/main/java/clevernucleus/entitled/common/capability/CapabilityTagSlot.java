package clevernucleus.entitled.common.capability;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class CapabilityTagSlot extends SlotItemHandler {
	private EntityPlayer player;
	private int slot;
	
	public CapabilityTagSlot(EntityPlayer par0, ICapabilityTag par1, int par2, int par3, int par4) {
		super(par1, par2, par3, par4);
		
		this.slot = par2;
		this.player = par0;
	}
	
	@Override
	public boolean isItemValid(ItemStack par0) {
		return ((ICapabilityTag)getItemHandler()).isItemValidForSlot(this.slot, par0, this.player);
	}
	
	@Override
	public boolean canTakeStack(EntityPlayer par0) {
		ItemStack var0 = getStack();
		
		if(var0.isEmpty()) return false;
		
		return true;
	}
	
	@Override
	public ItemStack onTake(EntityPlayer par0, ItemStack par1) {
		if(!getHasStack() && !((ICapabilityTag)getItemHandler()).isEventBlocked()) {}
		
		super.onTake(par0, par1);
		
		return par1;
	}
	
	@Override
	public void putStack(ItemStack par0) {
		if(getHasStack() && !ItemStack.areItemStacksEqual(par0, getStack()) && !((ICapabilityTag)getItemHandler()).isEventBlocked()) {}
		
		ItemStack var0 = getStack().copy();
		
		super.putStack(par0);
		
		if(getHasStack() && !ItemStack.areItemStacksEqual(var0, getStack()) && !((ICapabilityTag)getItemHandler()).isEventBlocked()) {}
	}
	
	@Override
	public int getSlotStackLimit() {
		return 1;
	}
}
