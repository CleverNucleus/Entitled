package clevernucleus.entitled.common.capability;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

public interface ICapabilityTag extends IItemHandlerModifiable {
	boolean isItemValidForSlot(int par0, ItemStack par1, EntityLivingBase par2);
	boolean isEventBlocked();
	boolean isChanged(int par0);
	
	void setEventBlocked(boolean par0);
	void setChanged(int par0, boolean par1);
	void setPlayer(EntityLivingBase par0);
}
