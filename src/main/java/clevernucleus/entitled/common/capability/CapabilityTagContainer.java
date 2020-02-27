package clevernucleus.entitled.common.capability;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemNameTag;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class CapabilityTagContainer extends ItemStackHandler implements ICapabilityTag {
	private final static int SLOT = 1;
	private boolean changed, blockEvents = false;
	private EntityLivingBase entity;
	
	public CapabilityTagContainer() {
		super(SLOT);
	}
	
	@Override
	public void setSize(int par0) {
		if(par0 < SLOT) par0 = SLOT;
		
		super.setSize(par0);
	}
	
	@Override
	public boolean isItemValidForSlot(int par0, ItemStack par1, EntityLivingBase par2) {
		if(par1 == null || par1.isEmpty()) return false;
		
		return par1.getItem() instanceof ItemNameTag;
	}
	
	@Override
	public void setStackInSlot(int par0, ItemStack par1) {
		if(par1 == null || par1.isEmpty() || this.isItemValidForSlot(par0, par1, this.entity)) {
			super.setStackInSlot(par0, par1);
		}
	}
	
	@Override
	public ItemStack insertItem(int par0, ItemStack par1, boolean par2) {
		if(!this.isItemValidForSlot(par0, par1, this.entity)) return par1;
		
		return super.insertItem(par0, par1, par2);
	}
	
	@Override
	public boolean isEventBlocked() {
		return this.blockEvents;
	}
	
	@Override
	public void setEventBlocked(boolean par0) {
		this.blockEvents = par0;
	}
	
	@Override
	protected void onContentsChanged(int par0) {
		setChanged(par0, true);
	}
	
	@Override
	public boolean isChanged(int par0) {
		return this.changed;
	}
	
	@Override
	public void setChanged(int par0, boolean par1) {
		this.changed = par1;
	}
	
	@Override
	public void setPlayer(EntityLivingBase par0) {
		this.entity = par0;
	}
}
