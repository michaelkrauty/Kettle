package me.michaelkrauty.Kettle.command.backpack;

import me.michaelkrauty.Kettle.Kettle;
import me.michaelkrauty.Kettle.Objects.Backpack;
import me.michaelkrauty.Kettle.util.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.UUID;

/**
 * Created on 7/6/2014.
 *
 * @author michaelkrauty
 */
public class BackpackCommand extends AbstractCommand {

	private final Kettle kettle;

	public BackpackCommand(String command, String usage, String description, Kettle instance) {
		super(command, usage, description);
		this.kettle = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		final Player player = (Player) sender;
		if (args.length == 0) {
			player.sendMessage(ChatColor.GREEN + "--[Backpacks]--");
			player.sendMessage(ChatColor.RED + "/backpack get [name]" + ChatColor.GRAY + ": Get a backpack ([name] is optional)");
			player.sendMessage(ChatColor.RED + "/backpack name <name>" + ChatColor.GRAY + ": Name the backpack you are holding.");
			player.sendMessage(ChatColor.DARK_GREEN + "Backpacks are physical items. You can carry as many as you want. If you drop a backpack, anyone can pick it up & get the items inside.");
			player.sendMessage(ChatColor.DARK_RED + "WARNING: " + ChatColor.RED + "Do NOT rename a backpack with an anvil! You will never be able to access the items inside of it again!");
			return true;
		}
		if (args[0].equalsIgnoreCase("get") || args[0].equalsIgnoreCase("give")) {
			String name = "Backpack";
			if (args.length != 1) {
				name = "";
				for (int i = 1; i < args.length; i++) {
					if (i == args.length - 1)
						name = name + args[i];
					else
						name = name + args[i] + " ";
				}
			}
			if (player.getInventory().firstEmpty() != -1) {
				ItemStack backpack = new ItemStack(Material.CHEST, 1);
				ItemMeta meta = backpack.getItemMeta();
				meta.setDisplayName(name);
				UUID uuid = UUID.randomUUID();
				meta.setLore(Arrays.asList(uuid.toString()));
				backpack.setItemMeta(meta);
				player.getInventory().addItem(backpack);
				kettle.objects.backpacks.add(new Backpack(kettle, uuid.toString(), name));
				return true;
			}
			player.sendMessage(ChatColor.RED + "Your inventory is too full to give you a backpack!");
			return true;
		}
		if (args[0].equalsIgnoreCase("name") || args[0].equalsIgnoreCase("rename")) {
			if (player.getItemInHand() != null) {
				if (player.getItemInHand().getType() == Material.CHEST) {
					String lore;
					if ((lore = player.getItemInHand().getItemMeta().getLore().get(0)) != null) {
						Backpack backpack;
						if ((backpack = kettle.objects.getBackpack(lore)) != null) {
							String name = "";
							for (int i = 1; i < args.length; i++) {
								if (i == args.length - 1)
									name = name + args[i];
								else
									name = name + args[i] + " ";
							}
							ItemMeta meta = player.getItemInHand().getItemMeta();
							meta.setDisplayName(name);
							player.getItemInHand().setItemMeta(meta);
							player.sendMessage(ChatColor.GRAY + "Renamed this backpack to " + name);
							return true;
						}
					}
				}
			}
			player.sendMessage(ChatColor.RED + "Make sure you're holding a backpack in your hand.");
			return true;
		}
		player.sendMessage(ChatColor.RED + cmd.getUsage());
		return true;
	}
}
