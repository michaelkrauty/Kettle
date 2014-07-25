package me.michaelkrauty.Kettle.command.essential;

import me.michaelkrauty.Kettle.Kettle;
import me.michaelkrauty.Kettle.Objects.User;
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
public class HealCommand extends AbstractCommand {

	private final Kettle kettle;

	public HealCommand(String command, String usage, String description, Kettle instance) {
		super(command, usage, description);
		kettle = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player player = (Player) sender;
		User user = kettle.objects.getUser(player);
		if (user.hasPermission("commands.heal")) {
			user.heal();
			player.sendMessage(ChatColor.GRAY + "You were healed.");
			return true;
		}
		player.sendMessage(ChatColor.RED + "You don't have permission to do that.");
		return true;
	}
}
