package me.michaelkrauty.Kettle.command.essential;

import me.michaelkrauty.Kettle.Kettle;
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
public class MuteCommand extends AbstractCommand {

	private final Kettle kettle;

	public MuteCommand(String command, String usage, String description, Kettle instance) {
		super(command, usage, description);
		kettle = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "/mute <player>");
			return true;
		}
		if (kettle.getServer().getPlayer(args[0]) instanceof Player) {
			if (!kettle.mutedPlayers.contains(sender))
				kettle.mutedPlayers.add((Player) sender);
			else
				kettle.mutedPlayers.remove(sender);
		}
		if (args[0].equalsIgnoreCase("list")) {
			String players = "";
			if (kettle.mutedPlayers.size() != 0) {
				for (int i = 0; i < kettle.mutedPlayers.size(); i++) {
					if (i != 0) {
						players = players + kettle.mutedPlayers.get(i).getName() + ", ";
					} else {
						players = players + kettle.mutedPlayers.get(i).getName();
					}
				}
			} else {
				players = "none";
			}
			sender.sendMessage(ChatColor.GRAY + players);
		}
		return true;
	}
}
