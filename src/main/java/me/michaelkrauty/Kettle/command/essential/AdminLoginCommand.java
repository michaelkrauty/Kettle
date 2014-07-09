package me.michaelkrauty.Kettle.command.essential;

import me.michaelkrauty.Kettle.Kettle;
import me.michaelkrauty.Kettle.Objects.Objects;
import me.michaelkrauty.Kettle.Objects.User;
import me.michaelkrauty.Kettle.util.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created on 6/28/2014.
 *
 * @author michaelkrauty
 */
public class AdminLoginCommand extends AbstractCommand {

	private final Kettle kettle;

	public AdminLoginCommand(String command, String usage, String description, Kettle instance) {
		super(command, usage, description);
		kettle = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player player = (Player) sender;
		User user = kettle.objects.getUser(player);
		if (user.isAdmin()) {
			if (args.length != 0) {
				if (args[0].equalsIgnoreCase(kettle.configFile.getString("superuser_pass"))) {
					if (!kettle.admins.contains(player.getName())) {
						kettle.admins.add(player.getName());
						sender.sendMessage(ChatColor.GREEN + "Logged in.");
						return true;
					}
					player.sendMessage(ChatColor.GREEN + "You're already logged in.");
					return true;
				}
				sender.sendMessage(ChatColor.RED + "Incorrect password.");
				return true;
			}
			String admins = "";
			for (int i = 0; i < kettle.admins.size(); i++) {
				if (i != kettle.admins.size() - 1) {
					admins = admins + kettle.admins.get(i) + ", ";
				} else {
					admins = admins + kettle.admins.get(i);
				}
			}
			sender.sendMessage(ChatColor.GRAY + "Online admins: " + admins);
			return true;
		}
		return true;
	}
}