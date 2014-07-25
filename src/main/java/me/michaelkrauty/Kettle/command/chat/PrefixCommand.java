package me.michaelkrauty.Kettle.command.chat;

import me.michaelkrauty.Kettle.Kettle;
import me.michaelkrauty.Kettle.Objects.User;
import me.michaelkrauty.Kettle.util.AbstractCommand;
import me.michaelkrauty.Kettle.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created on 7/24/2014.
 *
 * @author michaelkrauty
 */
public class PrefixCommand extends AbstractCommand {

	private final Kettle kettle;

	public PrefixCommand(String command, String usage, String description, Kettle instance) {
		super(command, usage, description);
		kettle = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(cmd.getUsage());
			return true;
		}

		Player target;
		if ((target = kettle.getServer().getPlayer(args[0])) instanceof Player) {
			User targetUser = kettle.objects.getUser(target);
			if (args[1].equalsIgnoreCase("off") || args[1].equalsIgnoreCase("remove")) {
				targetUser.setPrefix(null);
				sender.sendMessage(ChatColor.GRAY + "Prefix removed.");
				return true;
			}
			String prefix = "";
			for (int i = 1; i < args.length; i++) {
				if (i == args.length - 1)
					prefix = prefix + args[i];
				else
					prefix = prefix + args[i] + " ";
			}
			prefix = Util.format(prefix);
			targetUser.setPrefix(prefix);
			sender.sendMessage(ChatColor.GRAY + "Prefix set to " + prefix);
			return true;
		}
		sender.sendMessage(ChatColor.RED + cmd.getUsage());
		return true;
	}
}
