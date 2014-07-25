package me.michaelkrauty.Kettle.command.essential;

import me.michaelkrauty.Kettle.Kettle;
import me.michaelkrauty.Kettle.Objects.User;
import me.michaelkrauty.Kettle.util.AbstractCommand;
import me.michaelkrauty.Kettle.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created on 7/23/2014.
 *
 * @author michaelkrauty
 */
public class NicknameCommand extends AbstractCommand {

	private final Kettle kettle;

	public NicknameCommand(String command, String usage, String description, List<String> aliases, Kettle instance) {
		super(command, usage, description, aliases);
		kettle = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player player = (Player) sender;
		User user = kettle.objects.getUser(player);
		if (user.hasPermission("commands.nickname")) {
			if (args.length == 0) {
				sender.sendMessage(ChatColor.RED + cmd.getUsage());
				return true;
			}
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("off") || args[0].equalsIgnoreCase("remove")) {
					user.setNickname(null);
					player.sendMessage(ChatColor.GRAY + "Nickname removed.");
					return true;
				}
				if (Util.stripColor(args[0]).length() <= 16) {
					String nick = Util.format(args[0]);
					user.setNickname(nick);
					player.sendMessage(ChatColor.GRAY + "Nickname changed to " + nick);
					return true;
				}
				player.sendMessage(ChatColor.RED + "Nicknames can only contain 16 characters. The nickname you requested has " + Util.stripColor(args[0]).length() + " characters.");
				return true;
			}
			player.sendMessage(ChatColor.RED + cmd.getUsage());
			return true;
		}
		player.sendMessage(ChatColor.RED + "You don't have permission to do that.");
		return true;
	}
}