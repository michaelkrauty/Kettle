package me.michaelkrauty.Kettle.command.essential;

import me.michaelkrauty.Kettle.Kettle;
import me.michaelkrauty.Kettle.Objects.User;
import me.michaelkrauty.Kettle.util.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created on 5/21/2014.
 *
 * @author michaelkrauty
 */
public class TeleportCommand extends AbstractCommand {

	private final Kettle kettle;

	public TeleportCommand(String command, String usage, String description, List<String> aliases, Kettle instance) {
		super(command, usage, description, aliases);
		kettle = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player player = (Player) sender;
		User user = kettle.objects.getUser(player);
		if (user.isAdminLoggedIn()) {
			if (args.length == 0) {
				sender.sendMessage(cmd.getUsage());
				return true;
			}
			if (args.length == 1) {
					if (kettle.getServer().getPlayer(args[0]) instanceof Player) {
						Player target = kettle.getServer().getPlayer(args[0]);
						user.teleport(target);
						sender.sendMessage("Teleported to " + target.getName());
						return true;
					} else {
						sender.sendMessage(ChatColor.RED + "Couldn't find that player.");
						return true;
					}
			}
			if (args.length == 2) {
				if (kettle.getServer().getPlayer(args[0]) instanceof Player) {
					Player player1 = kettle.getServer().getPlayer(args[0]);
					User user1 = kettle.objects.getUser(player1);
					if (kettle.getServer().getPlayer(args[1]) instanceof Player) {
						Player player2 = kettle.getServer().getPlayer(args[1]);
						user1.teleport(player2);
						sender.sendMessage(ChatColor.GRAY + "Teleported " + player1.getName() + " to " + player2.getName());
						return true;
					} else {
						sender.sendMessage(ChatColor.RED + "Couldn't find that player.");
						return true;
					}
				} else {
					sender.sendMessage(ChatColor.RED + "Couldn't find that player.");
					return true;
				}
			}
			sender.sendMessage(cmd.getUsage());
			return true;
		}
		kettle.getServer().dispatchCommand(sender, "tpa" + args[0]);
		return true;
	}
}
