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
public class SetspawnCommand extends AbstractCommand {

	private final Kettle kettle;

	public SetspawnCommand(String command, String usage, String description, Kettle instance) {
		super(command, usage, description);
		kettle = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player player = (Player) sender;
		if (kettle.objects.getUser(player).isAdminLoggedIn()) {
			kettle.dataFile.set("spawn", player.getLocation());
			return true;
		}
		sender.sendMessage(ChatColor.RED + "You don't have permission to do that.");
		return true;
	}
}
