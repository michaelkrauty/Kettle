package me.michaelkrauty.Kettle.command.groups;

import me.michaelkrauty.Kettle.Kettle;
import me.michaelkrauty.Kettle.Objects.User;
import me.michaelkrauty.Kettle.util.AbstractCommand;
import me.michaelkrauty.Kettle.util.Groups;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created on 7/24/2014.
 *
 * @author michaelkrauty
 */
public class GroupCommand extends AbstractCommand {

	private final Kettle kettle;

	public GroupCommand(String command, String usage, String description, Kettle instance) {
		super(command, usage, description);
		kettle = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + cmd.getUsage());
			return true;
		}
		Player target;
		if ((target = kettle.getServer().getPlayer(args[0])) instanceof Player) {
			User targetUser = kettle.objects.getUser(target);
			if (Groups.groupExists(args[1])) {
				targetUser.setGroup(args[1]);
				sender.sendMessage(ChatColor.GRAY + "Group set.");
				return true;
			}
			sender.sendMessage(ChatColor.RED + "That group doesn't exist.");
			return true;
		}
		sender.sendMessage(ChatColor.RED + "Couldn't find that player.");
		return true;
	}
}
