package clevernucleus.entitled.common.event;

import clevernucleus.entitled.common.Entitled;
import clevernucleus.entitled.common.capability.CapabilityTag;
import clevernucleus.entitled.common.capability.ITag;
import clevernucleus.entitled.common.capability.Tag;
import clevernucleus.entitled.common.network.NetworkHandle;
import clevernucleus.entitled.common.network.PacketSyncMap;
import clevernucleus.entitled.common.network.PacketSyncTag;
import clevernucleus.entitled.common.util.Util;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CommonHandler {
	
	@SubscribeEvent
	public void onDrawTooltip(net.minecraftforge.event.entity.player.ItemTooltipEvent par0) {
		if(par0.getEntityPlayer() == null) return;
		if(!par0.getEntityPlayer().world.isRemote) return;
		
		EntityPlayer var0 = par0.getEntityPlayer();
		ItemStack var1 = par0.getItemStack();
		
		if(var1.getItem() == Items.NAME_TAG && var1.hasTagCompound()) {
			if(var1.getTagCompound().hasKey("display") && var1.getTagCompound().getCompoundTag("display").hasKey(Util.COLOUR)) {
				if(org.lwjgl.input.Keyboard.isKeyDown(org.lwjgl.input.Keyboard.KEY_LSHIFT)) {
					EnumDyeColor var2 = EnumDyeColor.byDyeDamage(((int)par0.getItemStack().getTagCompound().getCompoundTag("display").getByte(Util.COLOUR)));
					
					par0.getToolTip().add(TextFormatting.GRAY + " - " + Entitled.proxy.I18n(("colour." + var2.getUnlocalizedName() + ".name")));
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onAnvilUse(net.minecraftforge.event.AnvilUpdateEvent par0) {
		if(par0.getLeft().getItem() == Items.NAME_TAG) {
			ItemStack var0 = par0.getLeft().copy();
			
			if(!var0.hasTagCompound()) {
				var0.setTagCompound(new NBTTagCompound());
			}
			
			NBTTagCompound var1 = new NBTTagCompound();
			String var2 = par0.getName().length() > 0 ? par0.getName() : var0.getDisplayName();
			ItemStack var3 = new ItemStack(Items.DYE, 1, 15);
			
			var1.setString("Name", var2);
			var0.getTagCompound().setInteger("RepairCost", 0);
			
			if(par0.getRight().getItem() instanceof ItemDye) {
				var1.setByte(Util.COLOUR, (byte)par0.getRight().getMetadata());
			}
			
			var0.getTagCompound().setTag("display", var1);
			par0.setCost(1);
			par0.setOutput(var0);
		}
	}
	
	@SubscribeEvent
    public void onPlayerRightClick(net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem par0) {
		EntityPlayer var0 = par0.getEntityPlayer();
		ItemStack var1 = par0.getItemStack();
		
		if(!var0.world.isRemote && var0.isSneaking() && (var1.getItem() == Items.NAME_TAG)) {
			ITag var2 = CapabilityTag.tag(var0);
			
			if(var2.get().hasNoTags()) {
				if(var1.hasTagCompound()) {
					var2.set(var1.getTagCompound().copy());
					var1.shrink(1);
				}
			} else {
				ItemStack var3 = new ItemStack(Items.NAME_TAG, 1);
				var3.setTagCompound(var2.get().copy());
				
				EntityItem var4 = new EntityItem(var0.world, var0.posX, var0.posY, var0.posZ, var3);
				
				var0.world.spawnEntity(var4);
				var2.set(new NBTTagCompound());
			}
			
			sync(var0);
		}
	}
	
	@SubscribeEvent
    public void onPlayerTick(net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent par0) {
		if(!par0.player.world.isRemote && par0.phase == net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent.Phase.END) {
			NBTTagList var0 = new NBTTagList();
			NBTTagCompound var1 = new NBTTagCompound();
			
			for(EntityPlayer var : par0.player.world.playerEntities) {
				ITag var2 = CapabilityTag.tag(var);
				String var3 = !var2.get().hasNoTags() ? var2.get().getCompoundTag("display").getString("Name") : "";
				
				byte var4 = var2.get().getCompoundTag("display").getByte(Util.COLOUR);
				
				NBTTagCompound var5 = new NBTTagCompound();
				
				var5.setUniqueId(Util.UUID, var.getUniqueID());
				var5.setString(Util.DISPLAY, var3);
				var5.setByte(Util.COLOUR, var4);
				
				var0.appendTag(var5);
			}
			
			var1.setTag(Util.MAP, var0);
			
			NetworkHandle.INSTANCE.sendTo(new PacketSyncMap(var1), (EntityPlayerMP)par0.player);
		}
	}
	
	@SubscribeEvent
	public void onCapabilityAttach(net.minecraftforge.event.AttachCapabilitiesEvent<Entity> par0) {
		if(par0.getObject() instanceof EntityPlayer) {
			final Tag var0 = new Tag((EntityPlayer)par0.getObject());
			
			par0.addCapability(new ResourceLocation(Util.MODID, Util.TAG), CapabilityTag.createProvider(var0));
		}
	}
	
	@SubscribeEvent
    public void onPlayerEntityCloned(net.minecraftforge.event.entity.player.PlayerEvent.Clone par0) {
		ITag var0 = CapabilityTag.tag(par0.getOriginal());
		ITag var1 = CapabilityTag.tag(par0.getEntityLiving());
		
		try {
			var1.set(var0.get());
		} catch(Exception parE) {}
	}
	
	@SubscribeEvent
	public void onPlayerLoggedIn(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent par0) {
		sync(par0.player);
	}
	
	@SubscribeEvent
	public void onPlayerRespawn(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent par0) {
		sync(par0.player);
	}
	
	private void sync(EntityPlayer par0) {
		if(!par0.world.isRemote) {
			ITag var0 = CapabilityTag.tag(par0);
			
			NetworkHandle.INSTANCE.sendTo(new PacketSyncTag(var0.get()), (EntityPlayerMP)par0);
		}
	}
}
