package clevernucleus.entitled.common.handler;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import clevernucleus.entitled.common.Entitled;
import clevernucleus.entitled.common.IConstants;
import clevernucleus.entitled.common.capability.CapabilityTag;
import clevernucleus.entitled.common.capability.CapabilityTagContainer;
import clevernucleus.entitled.common.capability.CapabilityTagProvider;
import clevernucleus.entitled.common.capability.ICapabilityTag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class PlayerHandler {
	private HashMap<UUID, ItemStack> sync = new HashMap<UUID, ItemStack>();
	
	@SubscribeEvent
	public void onPlayerClone(PlayerEvent.Clone par0) {
		try {
			CapabilityTagContainer var0 = (CapabilityTagContainer) Entitled.tag(par0.getOriginal());
			NBTTagCompound var1 = var0.serializeNBT();
			CapabilityTagContainer var2 = (CapabilityTagContainer) Entitled.tag(par0.getEntityPlayer());
			
			var2.deserializeNBT(var1);
		} catch(Exception parE) {}
	}
	
	@SubscribeEvent
	public void onCapabilityAttachEntity(AttachCapabilitiesEvent<Entity> par0) {
		if(par0.getObject() instanceof EntityPlayer) {
			par0.addCapability(new ResourceLocation(IConstants.MODID, "tag"), new CapabilityTagProvider(new CapabilityTagContainer()));
		}
	}
	
	@SubscribeEvent
	public void onRightClick(net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem par0) {
		EntityPlayer var0 = par0.getEntityPlayer();
		ItemStack var1 = par0.getItemStack();
		ICapabilityTag var2 = var0.getCapability(CapabilityTag.TAG, null);
		
		if(var0.isSneaking() && (var1.getItem() == Items.NAME_TAG)) {
			if(!var0.world.isRemote) {
				if(var2.getStackInSlot(CapabilityTag.SLOT).getItem() == Items.NAME_TAG) {
					EntityItem var3 = new EntityItem(var0.world, var0.posX, var0.posY, var0.posZ, var2.getStackInSlot(CapabilityTag.SLOT).copy());
					
					var0.world.spawnEntity(var3);
					var2.setStackInSlot(CapabilityTag.SLOT, ItemStack.EMPTY);
				} else {
					if(var1.hasTagCompound()) {
						var2.setStackInSlot(CapabilityTag.SLOT, var1.copy());
						var1.shrink(1);
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent par0) {
		Entity var0 = par0.getEntity();
		
		if(var0 instanceof EntityPlayerMP) {
			EntityPlayerMP var1 = (EntityPlayerMP) var0;
			
			syncSlots(var1, Collections.singletonList(var1));
		}
	}
	
	@SubscribeEvent
	public void onStartTracking(PlayerEvent.StartTracking par0) {
		Entity var0 = par0.getTarget();
		
		if(var0 instanceof EntityPlayerMP) {
			syncSlots((EntityPlayer) var0, Collections.singletonList(par0.getEntityPlayer()));
		}
	}
	
	@SubscribeEvent
	public void onPlayerLoggedOut(PlayerLoggedOutEvent par0) {
		sync.remove(par0.player.getUniqueID());
	}
	
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent par0) {
		if(par0.phase == TickEvent.Phase.END) {
			EntityPlayer var0 = par0.player;
			ICapabilityTag var1 = Entitled.tag(var0);
			ItemStack var2 = var1.getStackInSlot(CapabilityTag.SLOT);
			NBTTagList var3 = new NBTTagList();
			
			for(EntityPlayer var : par0.player.world.playerEntities) {
				ICapabilityTag var4 = Entitled.tag(var);
				NBTTagCompound var5 = new NBTTagCompound();
				String var6 = "";
				
				if(var4 != null) {
					if(var4.getStackInSlot(CapabilityTag.SLOT).getItem() == Items.NAME_TAG) {
						if(var4.getStackInSlot(CapabilityTag.SLOT).hasTagCompound()) {
							var6 = var4.getStackInSlot(CapabilityTag.SLOT).getTagCompound().getCompoundTag("display").getString("Name");
						}
					}
				}
				
				var5.setUniqueId(IConstants.UUID, var.getUniqueID());
				var5.setString(IConstants.DISPLAY, var6);
				var3.appendTag(var5);
			}
			
			if(!var0.world.isRemote) {
				NetworkHandler.INSTANCE.sendTo(new MessageSyncTag(var3), (EntityPlayerMP) var0);
				
				syncTag(var0, var1);
			}
		}
	}
	
	private void syncTag(EntityPlayer par0, ICapabilityTag par1) {
		ItemStack var0 = sync.get(par0.getUniqueID());
		
		if(var0 == null) {
			var0 = ItemStack.EMPTY;
			
			sync.put(par0.getUniqueID(), var0);
		}
		
		Set<EntityPlayer> var1 = null;
		ItemStack var2 = par1.getStackInSlot(CapabilityTag.SLOT);
		
		if(par1.isChanged(CapabilityTag.SLOT) || !ItemStack.areItemStacksEqual(var2, var0)) {
			if(var1 == null) {
				var1 = new HashSet<>(((WorldServer) par0.world).getEntityTracker().getTrackingPlayers(par0));
				var1.add(par0);
			}
			
			syncSlot(par0, CapabilityTag.SLOT, var2, var1);
			par1.setChanged(CapabilityTag.SLOT, false);
			var0 = var2 == null ? ItemStack.EMPTY : var2.copy();
		}
	}
	
	private void syncSlots(EntityPlayer par0, Collection<? extends EntityPlayer> par1) {
		ICapabilityTag var0 = Entitled.tag(par0);
		
		syncSlot(par0, CapabilityTag.SLOT, var0.getStackInSlot(CapabilityTag.SLOT), par1);
	}
	
	private void syncSlot(EntityPlayer par0, int par1, ItemStack par2, Collection<? extends EntityPlayer> par3) {
		MessageSync var0 = new MessageSync(par0, par2);
		
		for(EntityPlayer var : par3) {
			NetworkHandler.INSTANCE.sendTo(var0, (EntityPlayerMP)var);
		}
	}
}
