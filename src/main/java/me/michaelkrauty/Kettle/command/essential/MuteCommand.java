package me.michaelkrauty.Kettle.command.essential;

import me.michaelkrauty.Kettle.Kettle;
import me.michaelkrauty.Kettle.Objects.User;
import me.michaelkrauty.Kettle.util.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created on 6/28/2014.
 *
 * @author michaelkrauty
 */
public class MuteCommand extends AbstractCommand {

	private final Kettle kettle;

	public MuteCommand(String command, String usage, String description, Kettle instance) {
		super(command, usage, description);
		kettle = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (sender instanceof ConsoleCommandSender)
			return true;
		Player player = (Player) sender;
		User user = kettle.objects.getUser(player);
		if (user.isAdminLoggedIn()) {
			if (args.length == 0) {
				sender.sendMessage(ChatColor.RED + "/mute <player>");
				return true;
			}
			if (args[0].equalsIgnoreCase("list")) {
				String players = "";
				if (kettle.objects.users.size() != 0) {
					for (int i = 0; i < kettle.objects.users.size(); i++) {
						if (i != kettle.objects.users.size() - 1) {
							players = players + kettle.objects.users.get(i).getPlayer().getName() + ", ";
						} else {
							players = players + kettle.objects.users.get(i).getPlayer().getName();
						}
					}
				} else {
					players = "none";
				}
				sender.sendMessage(ChatColor.GRAY + players);
			}

			if (kettle.getServer().getPlayer(args[0]) instanceof Player) {
				Player target = kettle.getServer().getPlayer(args[0]);
				User targetUser = kettle.objects.getUser(target);
				if (args.length == 1) {
					if (!targetUser.isMuted()) {
						targetUser.mute();
						sender.sendMessage(ChatColor.GRAY + "Muted " + target.getDisplayName());
						return true;
					}
					kettle.objects.getUser(target).unmute();
					sender.sendMessage(ChatColor.GRAY + "Unmuted " + target.getDisplayName());
					return true;
				}
				if (args.length == 2) {
					try {
						kettle.objects.getUser(target).mute(Integer.parseInt(args[1]));
						sender.sendMessage(ChatColor.GRAY + "Muted " + target.getName() + " for " + args[1] + " seconds.");
					} catch (Exception e) {
						sender.sendMessage(cmd.getUsage());
					}
				}
				sender.sendMessage(cmd.getUsage());
			}
			return true;
		}
		player.sendMessage(ChatColor.RED + "You don't have permission to do that.");
		return true;
	}
}
