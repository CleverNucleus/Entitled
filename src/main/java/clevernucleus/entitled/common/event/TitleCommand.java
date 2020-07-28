package clevernucleus.entitled.common.event;

import java.util.Map;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import clevernucleus.entitled.common.Entitled;
import clevernucleus.entitled.common.init.Registry;
import clevernucleus.entitled.common.util.Util;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.MessageArgument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * This is where the magic happens! Command registration.
 */
public class TitleCommand {
	
	/** Map containing colour string values mapped to vanilla item representations. */
	private static final Map<String, Item> COLOUR_MAP = Util.map(var -> {
		var.put("white", Items.WHITE_DYE);
		var.put("orange", Items.ORANGE_DYE);
		var.put("magenta", Items.MAGENTA_DYE);
		var.put("light_blue", Items.LIGHT_BLUE_DYE);
		var.put("yellow", Items.YELLOW_DYE);
		var.put("lime", Items.LIME_DYE);
		var.put("pink", Items.PINK_DYE);
		var.put("gray", Items.GRAY_DYE);
		var.put("light_gray", Items.LIGHT_GRAY_DYE);
		var.put("cyan", Items.CYAN_DYE);
		var.put("purple", Items.PURPLE_DYE);
		var.put("blue", Items.BLUE_DYE);
		var.put("brown", Items.BROWN_DYE);
		var.put("green", Items.GREEN_DYE);
		var.put("red", Items.RED_DYE);
		var.put("black", Items.BLACK_DYE);
	});
	
	/** Error procedure. */
	private static boolean error(final CommandContext<CommandSource> par0, final PlayerEntity par1) {
		if(par1.world.isRemote) return true;
		
		PlayerList var0 = par0.getSource().getServer().getPlayerList();
		
		if(!var0.getPlayers().contains(par1)) {
			par0.getSource().sendFeedback(new TranslationTextComponent("message.entitled.error", TextFormatting.GRAY, TextFormatting.RED, par1.getDisplayName(), TextFormatting.GRAY), true);
			
			return true;
		}
		
		return false;
	}
	
	/** A partially formed entitled command. */
	private static int run(CommandContext<CommandSource> par0) throws CommandSyntaxException {
        par0.getSource().sendFeedback(new TranslationTextComponent("message.entitled.help", TextFormatting.RED, TextFormatting.GRAY), true);
        
        return 1;
    }
	
	/** Locking command */
	private static int run(CommandContext<CommandSource> par0, PlayerEntity par1, boolean par2) throws CommandSyntaxException {
		if(error(par0, par1)) return 1;
		if(par1.hasPermissionLevel(2)) {
			par0.getSource().sendFeedback(new TranslationTextComponent("message.entitled.lockable", TextFormatting.GRAY, TextFormatting.RED, par1.getDisplayName(), TextFormatting.GRAY), true);
			
			return 1;
		}
		
		par1.getCapability(Registry.TAG, null).ifPresent(var -> {
			var.setLocked(par2);
			
			if(par2) {
				par0.getSource().sendFeedback(new TranslationTextComponent("message.entitled.locked", TextFormatting.GRAY, TextFormatting.RED, par1.getDisplayName(), TextFormatting.GRAY), true);
			} else {
				par0.getSource().sendFeedback(new TranslationTextComponent("message.entitled.unlocked", TextFormatting.GRAY, TextFormatting.RED, par1.getDisplayName(), TextFormatting.GRAY), true);
			}
		});
        
        return 1;
    }
	
	/** Clearing command. */
	private static int run(CommandContext<CommandSource> par0, PlayerEntity par1) throws CommandSyntaxException {
		if(error(par0, par1)) return 1;
		
		par1.getCapability(Registry.TAG, null).ifPresent(var -> {
			if(!var.isEmpty()) {
				var.drop(par1);
			}
			
			var.clear();
			var.sync(par1);
			par0.getSource().sendFeedback(new TranslationTextComponent("message.entitled.clear", TextFormatting.GRAY, TextFormatting.RED, par1.getDisplayName(), TextFormatting.GRAY), true);
		});
        
        return 1;
    }
	
	/** Colouring command. */
	private static int run(CommandContext<CommandSource> par0, PlayerEntity par1, String par2) throws CommandSyntaxException {
		if(error(par0, par1)) return 1;
		if(!COLOUR_MAP.containsKey(par2)) {
			par0.getSource().sendFeedback(new TranslationTextComponent("message.entitled.unknown", TextFormatting.RED, par2, TextFormatting.GRAY), true);
	        
			return 1;
		}
		
		par1.getCapability(Registry.TAG, null).ifPresent(var -> {
			if(var.isEmpty()) {
				par0.getSource().sendFeedback(new TranslationTextComponent("message.entitled.empty", TextFormatting.RED, par1.getDisplayName(), TextFormatting.GRAY), true);
			} else {
				Util.safeTag(var.getStackInSlot(0), var0 -> {
					ItemStack var1 = new ItemStack(COLOUR_MAP.get(par2));
					CompoundNBT var2 = new CompoundNBT();
					
					var1.write(var2);
					var0.put("colour", var2);
				});
				
				var.sync(par1);
				par0.getSource().sendFeedback(new TranslationTextComponent("message.entitled.colour", TextFormatting.GRAY, TextFormatting.RED, par1.getDisplayName(), TextFormatting.GRAY, par2), true);
			}
		});
        
        return 1;
    }
	
	/** Colouring and nameing command. */
	private static int run(CommandContext<CommandSource> par0, PlayerEntity par1, String par2, ITextComponent par3) throws CommandSyntaxException {
		if(error(par0, par1)) return 1;
		if(!COLOUR_MAP.containsKey(par2)) {
			par0.getSource().sendFeedback(new TranslationTextComponent("message.entitled.unknown", TextFormatting.RED, par2, TextFormatting.GRAY), true);
	        
			return 1;
		}
		
		par1.getCapability(Registry.TAG, null).ifPresent(var -> {
			ItemStack var0 = new ItemStack(Items.NAME_TAG);
			Util.safeTag(var0, var1 -> {
				ItemStack var2 = new ItemStack(COLOUR_MAP.get(par2));
				CompoundNBT var3 = new CompoundNBT();
				
				var2.write(var3);
				var1.put("colour", var3);
			});
			
			var0.setDisplayName(par3);
			var.setStackInSlot(0, var0);
			var.sync(par1);
			par0.getSource().sendFeedback(new TranslationTextComponent("message.entitled.display", TextFormatting.GRAY, TextFormatting.RED, par1.getDisplayName(), TextFormatting.GRAY, TextFormatting.WHITE, par3, TextFormatting.GRAY, par2), true);
		});
		
        return 1;
    }
	
	/**
	 * Register the command(s) to the game.
	 * @param par0
	 */
	public static void register(CommandDispatcher<CommandSource> par0) {
		par0.register(Commands.literal(Entitled.MODID).requires(var -> {
			return var.hasPermissionLevel(2);
		}).executes(var -> {
			return run(var);
		}).then(Commands.argument("player", EntityArgument.player()).suggests((var0, var1) -> {
			PlayerList var2 = var0.getSource().getServer().getPlayerList();
			
			return ISuggestionProvider.suggest(var2.getPlayers().stream().map(var3 -> var3.getGameProfile().getName()), var1);
		}).then(Commands.literal("unlock").executes(var -> {
			return run(var, EntityArgument.getPlayer(var, "player"), false);
		})).then(Commands.literal("lock").executes(var -> {
			return run(var, EntityArgument.getPlayer(var, "player"), true);
		})).then(Commands.literal("clear").executes(var -> {
			return run(var, EntityArgument.getPlayer(var, "player"));
		})).then(Commands.argument("color", StringArgumentType.string()).suggests((var0, var1) -> {
			return ISuggestionProvider.suggest(COLOUR_MAP.keySet().stream(), var1);
		}).executes(var -> {
			return run(var, EntityArgument.getPlayer(var, "player"), StringArgumentType.getString(var, "color"));
		}).then(Commands.argument("display", MessageArgument.message()).executes(var -> {
			return run(var, EntityArgument.getPlayer(var, "player"), StringArgumentType.getString(var, "color"), MessageArgument.getMessage(var, "display"));
		})))));
	}
}
