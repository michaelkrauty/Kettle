package me.michaelkrauty.Kettle.command.permissions;

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
public class PermissionCommand extends AbstractCommand {

	private final Kettle kettle;

	public PermissionCommand(String command, String usage, String description, Kettle instance) {
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
			if (args[1].equalsIgnoreCase("add")) {
				if (!targetUser.hasPermission(args[1])) {
					targetUser.addPermission(args[1]);
					sender.sendMessage(ChatColor.GRAY + "Added permission.");
					return true;
				}
				sender.sendMessage(ChatColor.GRAY + "That player already has that permission.");
				return true;
			}
			if (args[1].equalsIgnoreCase("remove")) {
				if (targetUser.hasPermission(args[1])) {
					targetUser.removePermission(args[1]);
					sender.sendMessage(ChatColor.GRAY + "Removed permission.");
					return true;
				}
				sender.sendMessage(ChatColor.GRAY + "That player doesn't have that permission.");
				return true;
			}
			if (args[1].equalsIgnoreCase("list")) {
				if (!targetUser.getPermissions().isEmpty()) {
					sender.sendMessage(ChatColor.GRAY + target.getDisplayName() + "'s permissions:");
					for (String permission : targetUser.getPermissions()) {
						sender.sendMessage(ChatColor.GRAY + permission);
					}
					return true;
				}
				sender.sendMessage(ChatColor.GRAY + "That player doesn't have any permissions.");
				return true;
			}
			sender.sendMessage(ChatColor.RED + cmd.getUsage());
			return true;
		}
		sender.sendMessage(ChatColor.RED + "Couldn't find that player.");
		return true;
	}
}
