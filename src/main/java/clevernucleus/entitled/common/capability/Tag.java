package clevernucleus.entitled.common.capability;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;

public class Tag implements ITag {
	private NBTTagCompound tag;
	private EntityLivingBase entity;
	
	public Tag(@Nullable EntityLivingBase par0) {
		this.entity = par0;
		this.tag = new NBTTagCompound();
	}
	
	@Override
	public void set(NBTTagCompound par0) {
		this.tag = par0;
	}
	
	@Override
	public NBTTagCompound get() {
		return this.tag;
	}
}
