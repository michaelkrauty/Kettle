package me.michaelkrauty.Kettle.command.essential;

import me.michaelkrauty.Kettle.Kettle;
import me.michaelkrauty.Kettle.util.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created on 7/24/2014.
 *
 * @author michaelkrauty
 */
public class RealnameCommand extends AbstractCommand {

	private final Kettle kettle;

	public RealnameCommand(String command, String usage, String description, Kettle instance) {
		super(command, usage, description);
		kettle = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + cmd.getUsage());
			return true;
		}
		if (args.length == 1) {
			Player target = null;
			for (Player player : kettle.getServer().getOnlinePlayers()) {
				if (player.getDisplayName().toLowerCase().contains(args[0].toLowerCase())) {
					target = player;
				}
			}
			if (target != null) {
				sender.sendMessage(ChatColor.GRAY + target.getDisplayName() + " is " + target.getName());
				return true;
			}
			sender.sendMessage(ChatColor.RED + "Couldn't find that player.");
			return true;
		}
		sender.sendMessage(ChatColor.RED + cmd.getUsage());
		return true;
	}
}
